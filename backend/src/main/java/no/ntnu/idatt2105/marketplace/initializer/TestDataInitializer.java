package no.ntnu.idatt2105.marketplace.initializer;

import no.ntnu.idatt2105.marketplace.model.*;
import no.ntnu.idatt2105.marketplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

@Component
@Profile("submission")
public class TestDataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemImageRepository itemImageRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create Users
        User user1 = new User();
        user1.setUsername("test");
        user1.setEmail("test@test.com");
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRole(Role.USER);

        User user2 = new User();
        user2.setUsername("user");
        user2.setEmail("jane@example.com");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setRole(Role.USER);

        userRepository.saveAll(List.of(user1, user2));

        // Create Categories
        Category electronics = new Category();
        electronics.setName("Electronics");

        Category bicycles = new Category();
        bicycles.setName("Bicycles");

        Category vintage = new Category();
        vintage.setName("Vintage");

        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        smartphones.setParentCategory(electronics);

        categoryRepository.saveAll(List.of(electronics, bicycles, vintage, smartphones));

        // Create Items
        Item item1 = new Item();
        item1.setTitle("Big bedframe");
        item1.setDescription("A big bedframe for sale.");
        item1.setPrice(2599.99);
        item1.setLocationLatitude(60.39299);
        item1.setLocationLongitude(5.32415);
        item1.setSeller(user1);
        item1.setCategories(Set.of(bicycles, vintage));
        

        Item item2 = new Item();
        item2.setTitle("Bed");
        item2.setDescription("A comfortable bed for sale.");
        item2.setPrice(799.99);
        item2.setLocationLatitude(60.39299);
        item2.setLocationLongitude(5.32415);
        item2.setSeller(user2);
        item2.setCategories(Set.of(smartphones));

        itemRepository.saveAll(List.of(item1, item2));

        // Create Item Images from sample-images
        ItemImage image1 = new ItemImage();
        image1.setFileName("bedframe.jpg");
        image1.setContentType("image/jpeg");
        image1.setItem(item1);
        image1.setData(loadImageData("sample-images/item_images-data (3).bin")); 

        ItemImage image2 = new ItemImage();
        image2.setFileName("bed.jpg");
        image2.setContentType("image/jpeg");
        image2.setItem(item2);
        image2.setData(loadImageData("sample-images/item_images-data (2).bin")); 

        itemImageRepository.saveAll(List.of(image1, image2));

        // Create Reservations
        Reservation reservation = new Reservation();
        reservation.setUser(user2);
        reservation.setItem(item1);
        reservationRepository.save(reservation);

        // Create Bookmarks
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user1);
        bookmark.setItem(item2);
        bookmarkRepository.save(bookmark);

        // Create User Images
        UserImage userImage1 = new UserImage();
        userImage1.setFileName("john_profile.jpg");
        userImage1.setContentType("image/jpeg");
        userImage1.setUser(user1);
        userImage1.setData(loadImageData("sample-images/item_images-data (1).bin")); 

        UserImage userImage2 = new UserImage();
        userImage2.setFileName("jane_profile.jpg");
        userImage2.setContentType("image/jpeg");
        userImage2.setUser(user2);
        userImage2.setData(loadImageData("sample-images/item_images-data.bin")); 

        userImageRepository.saveAll(List.of(userImage1, userImage2));
    }

    // Helper method to load image data from file
    private byte[] loadImageData(String path) {
        try {
            // Load the binary data from the resource file
            return Files.readAllBytes(new ClassPathResource(path).getFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException("Error loading image file: " + path, e);
        }
    }
}
