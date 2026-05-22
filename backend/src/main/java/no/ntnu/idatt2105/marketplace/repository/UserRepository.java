package no.ntnu.idatt2105.marketplace.repository;

import no.ntnu.idatt2105.marketplace.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a User by their username.
     * @param username the username of the User to find
     * @return the User with the specified username, or null if not found
     */
    User findByUsername(String username);

    /**
     * Finds a User by their email.
     * @param email the email of the User to find
     * @return the User with the specified email, or null if not found
     */
    User findByEmail(String email);
}
