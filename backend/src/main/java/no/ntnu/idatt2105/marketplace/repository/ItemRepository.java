package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for managing Item entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Finds an Item by its title.
     * @param title the title of the Item to find
     * @return the Item with the specified title, or null if not found
     */
    Item findByTitle(String title);
}

