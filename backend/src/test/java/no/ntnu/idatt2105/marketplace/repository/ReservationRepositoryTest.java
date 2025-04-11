package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  void testSaveAndFindByUserId() {
    Reservation reservation = createAndSaveReservation();

    List<Reservation> reservations = reservationRepository.findByUserId(reservation.getUser().getId());

    assertThat(reservations).isNotEmpty();
    assertThat(reservations.get(0).getItem().getTitle()).isEqualTo("Test Item");
  }

  @Test
  void testDeleteByUserIdAndItemId() {
    Reservation reservation = createAndSaveReservation();

    reservationRepository.deleteByUserIdAndItemId(
        reservation.getUser().getId(), reservation.getItem().getId());

    Optional<Reservation> deleted = reservationRepository.findByItemId(reservation.getItem().getId());

    assertThat(deleted).isNotPresent();
  }

  @Test
  void testFindByItemId() {
    Reservation reservation = createAndSaveReservation();

    Optional<Reservation> found = reservationRepository.findByItemId(reservation.getItem().getId());

    assertThat(found).isPresent();
    assertThat(found.get().getUser().getUsername()).isEqualTo("reservationuser");
  }

  @Test
  void testFindByItemIdNotFound() {
    Optional<Reservation> found = reservationRepository.findByItemId(999L);

    assertThat(found).isNotPresent();
  }

  private Reservation createAndSaveReservation() {
    User user = new User();
    user.setUsername("reservationuser");
    user.setEmail("reservation@example.com");
    user.setPassword("password");
    user.setFirstName("Re");
    user.setLastName("Server");
    user.setRole(Role.USER);
    user = userRepository.save(user);

    Category category = new Category();
    category.setName("Electronics");
    category = categoryRepository.save(category);

    Item item = new Item();
    item.setTitle("Test Item");
    item.setDescription("A reserved item.");
    item.setPrice(456.0);
    item.setStatus(ItemStatus.ACTIVE);
    item.setSeller(user);
    item.setCategories(Set.of(category));
    item = itemRepository.save(item);

    Reservation reservation = new Reservation(user, item);

    return reservationRepository.save(reservation);
  }
}
