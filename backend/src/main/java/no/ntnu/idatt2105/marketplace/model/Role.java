package no.ntnu.idatt2105.marketplace.model;

import com.fasterxml.jackson.annotation.JsonCreator;

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
  ADMIN;

  @JsonCreator
  public static Role fromString(String role) {
    return Role.valueOf(role.toUpperCase());
  }
}
