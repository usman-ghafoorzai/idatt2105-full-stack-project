package no.ntnu.idatt2105.marketplace.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.ItemImage;
import no.ntnu.idatt2105.marketplace.repository.ItemImageRepository;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;

/**
 * Service class for handling item image-related business logic.
 * Provides methods to save, retrieve, and delete item images.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final ItemRepository itemRepository;

    /**
     * Uploads images for a specific item. This method does not replace existing images,
     * but instead adds the new images to the item.
     *
     * @param itemId the ID of the item
     * @param files the uploaded image files
     * @throws IOException if reading the files fails
     */
    public void uploadItemImages(Long itemId, MultipartFile[] files) throws IOException {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));

        // Add new images to the existing set for this item
        for (MultipartFile file : files) {
            ItemImage image = new ItemImage();
            image.setItem(item);
            image.setData(file.getBytes());
            image.setFileName(file.getOriginalFilename());
            image.setContentType(file.getContentType());

            itemImageRepository.save(image);
        }
    }

    /**
     * Retrieves all images associated with a specific item.
     *
     * @param itemId the ID of the item
     * @return a list of ItemImage objects containing image data
     */
    public List<ItemImage> getItemImages(Long itemId) {
        return itemImageRepository.findByItemId(itemId);
    }

    /**
     * Retrieves a specific image for an item by its ID.
     *
     * @param itemId the ID of the item
     * @param imageId the ID of the image
     * @return an Optional containing the ItemImage if found, otherwise empty
     */
    public Optional<ItemImage> getItemImageById(Long itemId, Long imageId) {
        return itemImageRepository.findByItemIdAndId(itemId, imageId);
    }

    /**
     * Deletes a specific image for a specific item.
     *
     * @param itemId the ID of the item
     * @param imageId the ID of the image to delete
     * @return true if the image was deleted, false otherwise
     */
    public boolean deleteImage(Long itemId, Long imageId) {
        Optional<ItemImage> imageOptional = itemImageRepository.findByItemIdAndId(itemId, imageId);
        if (imageOptional.isPresent()) {
            itemImageRepository.delete(imageOptional.get());
            return true;
        }
        return false;
    }

    /**
     * Deletes all images associated with a specific item.
     *
     * @param itemId the ID of the item
     */
    public void deleteAllImages(Long itemId) {
        List<ItemImage> images = itemImageRepository.findByItemId(itemId);
        itemImageRepository.deleteAll(images);
    }

    /**
     * Retrieves the first image for a specific item.
     * 
     * @param itemId the ID of the item
     * @return the first ItemImage, or empty if no images are found
     */
    public Optional<ItemImage> getFirstImage(Long itemId) {
      List<ItemImage> images = itemImageRepository.findByItemId(itemId);
      if (images.isEmpty()) {
          return Optional.empty();
      }
      // Return the first image (sorted by ID)
      return Optional.of(images.get(0));  
  }
}
