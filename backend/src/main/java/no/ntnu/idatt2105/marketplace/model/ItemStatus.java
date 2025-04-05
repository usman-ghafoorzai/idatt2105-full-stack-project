package no.ntnu.idatt2105.marketplace.model;

/**
 * Enum representing the status of an item in the marketplace.
 * <p>
 * The status can be one of the following:
 * </p>
 * <ul>
 *   <li>{@code ACTIVE} - The item is available for sale.</li>
 *   <li>{@code SOLD} - The item has been sold.</li>
 *   <li>{@code RESERVED} - The item is reserved by a buyer.</li>
 * </ul>
 */
public enum ItemStatus {
    ACTIVE,
    SOLD,
    RESERVED
}
