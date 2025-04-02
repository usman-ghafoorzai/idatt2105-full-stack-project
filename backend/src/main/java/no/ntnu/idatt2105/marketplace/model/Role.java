package no.ntnu.idatt2105.marketplace.model;

/**
 * Enum representing the roles of a user in the marketplace.
 * <p>
 * The roles can be one of the following:
 * </p>
 * <ul>
 *   <li>{@code User} - A regular user of the marketplace.</li>
 *   <li>{@code Admin} - An administrator with elevated privileges.</li>
 * </ul>
 */
public enum Role {
  USER,
  ADMIN
}
