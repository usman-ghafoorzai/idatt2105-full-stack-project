package no.ntnu.idatt2105.marketplace.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import no.ntnu.idatt2105.marketplace.model.Item;
import no.ntnu.idatt2105.marketplace.model.ItemImage;
import no.ntnu.idatt2105.marketplace.repository.ItemImageRepository;
import no.ntnu.idatt2105.marketplace.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemImageServiceTest {

  @Mock
  private ItemImageRepository itemImageRepository;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private MultipartFile multipartFile;

  @InjectMocks
  private ItemImageService itemImageService;

  private Item item;
  private ItemImage image;

  @BeforeEach
  void setup() throws IOException {
    item = new Item();
    item.setId(1L);

    image = new ItemImage();
    image.setId(10L);
    image.setItem(item);
    image.setFileName("test.jpg");
    image.setContentType("image/jpeg");
    image.setData("data".getBytes());
  }

  @Nested
  @DisplayName("Positive cases")
  class Positive {

    @Test
    void testUploadItemImages() throws IOException {
      when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

      itemImageService.uploadItemImages(1L, new MultipartFile[] { multipartFile });

      verify(itemImageRepository, times(1)).save(any(ItemImage.class));
    }

    @Test
    void testGetItemImages() {
      when(itemImageRepository.findByItemId(1L)).thenReturn(List.of(image));

      List<ItemImage> result = itemImageService.getItemImages(1L);

      assertThat(result).hasSize(1);
      assertThat(result.get(0).getFileName()).isEqualTo("test.jpg");
    }

    @Test
    void testGetItemImageById() {
      when(itemImageRepository.findByItemIdAndId(1L, 10L)).thenReturn(Optional.of(image));

      Optional<ItemImage> result = itemImageService.getItemImageById(1L, 10L);

      assertThat(result).isPresent();
    }

    @Test
    void testDeleteImage() {
      when(itemImageRepository.findByItemIdAndId(1L, 10L)).thenReturn(Optional.of(image));

      boolean result = itemImageService.deleteImage(1L, 10L);

      assertThat(result).isTrue();
      verify(itemImageRepository, times(1)).delete(image);
    }

    @Test
    void testDeleteAllImages() {
      when(itemImageRepository.findByItemId(1L)).thenReturn(List.of(image));

      itemImageService.deleteAllImages(1L);

      verify(itemImageRepository, times(1)).deleteAll(List.of(image));
    }

    @Test
    void testGetFirstImage() {
      when(itemImageRepository.findByItemId(1L)).thenReturn(List.of(image));

      Optional<ItemImage> result = itemImageService.getFirstImage(1L);

      assertThat(result).isPresent();
      assertThat(result.get().getFileName()).isEqualTo("test.jpg");
    }

    @Test
    void testGetItemImageIds() {
      when(itemImageRepository.findByItemId(1L)).thenReturn(List.of(image));

      List<Long> result = itemImageService.getItemImageIds(1L);

      assertThat(result).containsExactly(10L);
    }
  }

  @Nested
  @DisplayName("Negative cases")
  class Negative {

    @Test
    void testUploadItemImagesThrowsIfItemNotFound() {
      when(itemRepository.findById(1L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> itemImageService.uploadItemImages(1L, new MultipartFile[] { multipartFile }))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Item not found");
    }

    @Test
    void testDeleteImageReturnsFalseIfNotFound() {
      when(itemImageRepository.findByItemIdAndId(1L, 999L)).thenReturn(Optional.empty());

      boolean result = itemImageService.deleteImage(1L, 999L);

      assertThat(result).isFalse();
    }

    @Test
    void testGetFirstImageReturnsEmptyIfNoneExist() {
      when(itemImageRepository.findByItemId(1L)).thenReturn(List.of());

      Optional<ItemImage> result = itemImageService.getFirstImage(1L);

      assertThat(result).isEmpty();
    }
  }
}
