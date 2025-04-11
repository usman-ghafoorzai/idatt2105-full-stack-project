package no.ntnu.idatt2105.marketplace.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.dto.ItemCreateDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemResponseDTO;
import no.ntnu.idatt2105.marketplace.dto.ItemUpdateDTO;
import no.ntnu.idatt2105.marketplace.model.Category;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.User;
import no.ntnu.idatt2105.marketplace.repository.CategoryRepository;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;
import no.ntnu.idatt2105.marketplace.repository.UserRepository;

/**
 * Service class for handling item-related business logic.
 * Provides methods to retrieve, save, update, and delete item entities.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public ItemResponseDTO convertToResponse(Item item) {
    ItemResponseDTO response = new ItemResponseDTO();
    response.setId(item.getId());
    response.setTitle(item.getTitle());
    response.setDescription(item.getDescription());
    response.setPrice(item.getPrice());
    if (item.getLocationLatitude() != null) {
      response.setLocationLatitude(item.getLocationLatitude());
    }
    if (item.getLocationLongitude() != null) {
      response.setLocationLongitude(item.getLocationLongitude());
    }
    if (item.getStatus() != null) {
      response.setStatus(item.getStatus().name());
    }
    
    if (item.getCategories() != null) {
      Set<ItemResponseDTO.CategoryDTO> categoryDTOs = item.getCategories()
          .stream()
          .map(category -> {
            ItemResponseDTO.CategoryDTO categoryDTO = new ItemResponseDTO.CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            return categoryDTO;
          })
          .collect(Collectors.toSet());
      response.setCategories(categoryDTOs);
    }
    
    ItemResponseDTO.SellerDTO sellerDTO = new ItemResponseDTO.SellerDTO();
    sellerDTO.setId(item.getSeller().getId());
    sellerDTO.setUsername(item.getSeller().getUsername());
    sellerDTO.setEmail(item.getSeller().getEmail());
    response.setSeller(sellerDTO);

    response.setCreatedAt(item.getCreatedAt());

    return response;
  }

  /**
   * Retrieves an item by its unique identifier.
   *
   * @param id the unique identifier of the item
   * @return an {@code Optional} containing the item respone dTO if found, otherwise empty
   */
  public Optional<ItemResponseDTO> getItemById(Long id) {
    return itemRepository.findById(id).map(this::convertToResponse);
  }

  /**
   * Retrieves an item entity by its unique identifier.
   * @param id the unique identifier of the item
   * @return an {@code Optional} containing the item entity if found, otherwise empty
   */
  public Optional<Item> getItemEntityById(Long id) {
    return itemRepository.findById(id);
  }


  /**
   * Saves a new item entity to the database.
   *
   * @param createDTO the item entity as a {@link ItemCreateDTO} object
   * @return the saved item entity as a response DTO {@link ItemResponseDTO}
   * @throws IllegalArgumentException if the category or seller is not found
   */
  public ItemResponseDTO saveItem(ItemCreateDTO createDTO) throws IllegalArgumentException {
    Item item = new Item();
    item.setTitle(createDTO.getTitle());
    item.setDescription(createDTO.getDescription());
    item.setPrice(createDTO.getPrice());
    if (createDTO.getLocationLatitude() != null) {
      item.setLocationLatitude(createDTO.getLocationLatitude());
    }
    if (createDTO.getLocationLongitude() != null) {
      item.setLocationLongitude(createDTO.getLocationLongitude());
    }

    // Lookup category by id
    if (createDTO.getCategoryIds() != null) {
      Set<Category> categories = createDTO.getCategoryIds()
          .stream()
          .map(categoryId -> categoryRepository.findById(categoryId)
              .orElseThrow(() -> new IllegalArgumentException("Category not found")))
          .collect(Collectors.toSet());
      item.setCategories(categories);
    }

    // Lookup seller by id
    User seller = userRepository.findById(createDTO.getSellerId())
        .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
    item.setSeller(seller);

    Item saved = itemRepository.save(item);
    return convertToResponse(saved);
  }

  /**
   * Saves an item entity to the database.
   * @param item the item entity to be saved
   * @return the saved item entity
   */
  public Item saveItemEntity(Item item) {
    return itemRepository.save(item);
  }


  /**
   * Updates an existing item entity.
   *
   * @param id the unique identifier of the item to be updated
   * @param updateDTO the item entity as a {@link ItemUpdateDTO} object
   * @return an {@code Optional} containing the updated item response DTO if found, otherwise empty
   */
  public Optional<ItemResponseDTO> updateItem(Long id, ItemUpdateDTO updateDTO) {
    return itemRepository.findById(id).map(existingItem -> {
      existingItem.setTitle(updateDTO.getTitle());
      existingItem.setDescription(updateDTO.getDescription());
      existingItem.setPrice(updateDTO.getPrice());
      existingItem.setLocationLatitude(updateDTO.getLocationLatitude());
      existingItem.setLocationLongitude(updateDTO.getLocationLongitude());

      // Update category if provided
      if (updateDTO.getCategoryIds() != null) {
        Set<Category> categories = updateDTO.getCategoryIds()
            .stream()
            .map(categoryId -> categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found")))
            .collect(Collectors.toSet());
        existingItem.setCategories(categories);
      }
      Item updated = itemRepository.save(existingItem);
      return convertToResponse(updated);
    });
  }

  /**
   * Deletes an item entity by its unique identifier.
   *
   * @param id the unique identifier of the item to be deleted
   */
  public void deleteItem(Long id) {
    if (!itemRepository.existsById(id)) {
      throw new IllegalArgumentException("Item not found");
    }
    itemRepository.deleteById(id);
  }

  /**
   * Retrieves all items in the database.
   *
   * @return a list of all items
   */
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  /**
   * Retrieves all items in the database as a set of response DTOs.
   *
   * @return a set of all items as response DTOs
   */
  public Set<ItemResponseDTO> getItemsByCategoryIds(Set<Long> categoryIds) {
    Specification<Item> spec = (root, query, cb) -> root.get("categories").get("id").in(categoryIds);
    return itemRepository.findAll(spec)
        .stream()
        .map(this::convertToResponse)
        .collect(Collectors.toSet());
  }

  public List<ItemResponseDTO> getFilteredItems(String title, String categoryName, Double minPrice, Double maxPrice,
      String status) {
    Specification<Item> spec = Specification.where(null);

    if (title != null) {
      spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
    }
    if (categoryName != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.join("category").get("name"), categoryName));
    }
    if (minPrice != null) {
      spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
    }
    if (maxPrice != null) {
      spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
    }
    if (status != null) {
      spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
    }

    return itemRepository.findAll(spec)
        .stream()
        .map(this::convertToResponse)
        .collect(Collectors.toList());
  }

  /**
   * Deletes all items from the database.
   * This method is intended for testing purposes only.
   */
  public void deleteAllItems() {
    itemRepository.deleteAll();
  }
}
