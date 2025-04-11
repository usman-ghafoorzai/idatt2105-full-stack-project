package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.ItemImage;
import no.ntnu.idatt2105.marketplace.service.ItemImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ItemImageControllerTest {

  private MockMvc mockMvc;

  @Mock
  private ItemImageService itemImageService;

  @InjectMocks
  private ItemImageController itemImageController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(itemImageController).build();
  }

  @Nested
  @DisplayName("Positive Tests")
  class PositiveTests {

    @Test
    void testUploadImages_Success() throws Exception {
      MockMultipartFile image = new MockMultipartFile("images", "image.jpg", "image/jpeg", "image data".getBytes());

      mockMvc.perform(multipart("/api/items/{itemId}/images", 1L)
              .file(image))
          .andExpect(status().isCreated())
          .andExpect(content().string("Images uploaded successfully"));

      verify(itemImageService, times(1)).uploadItemImages(eq(1L), any());
    }

    @Test
    void testGetImageIds_Success() throws Exception {
      when(itemImageService.getItemImageIds(1L)).thenReturn(List.of(101L, 102L));

      mockMvc.perform(get("/api/items/{itemId}/images", 1L))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0]").value(101))
          .andExpect(jsonPath("$[1]").value(102));
    }

    @Test
    void testGetItemImage_Success() throws Exception {
      ItemImage image = new ItemImage();
      image.setContentType("image/png");
      image.setFileName("test.png");
      image.setData("test data".getBytes());

      when(itemImageService.getItemImageById(1L, 1L)).thenReturn(Optional.of(image));

      mockMvc.perform(get("/api/items/{itemId}/images/{imageId}", 1L, 1L))
          .andExpect(status().isOk())
          .andExpect(header().string("Content-Type", "image/png"))
          .andExpect(header().string("Content-Disposition", "inline; filename=\"test.png\""));
    }

    @Test
    void testDeleteImage_Success() throws Exception {
      when(itemImageService.deleteImage(1L, 1L)).thenReturn(true);

      mockMvc.perform(delete("/api/items/{itemId}/images/{imageId}", 1L, 1L))
          .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAllImages_Success() throws Exception {
      mockMvc.perform(delete("/api/items/{itemId}/images", 1L))
          .andExpect(status().isNoContent());

      verify(itemImageService, times(1)).deleteAllImages(1L);
    }

    @Test
    void testGetFirstImage_Success() throws Exception {
      ItemImage image = new ItemImage();
      image.setContentType("image/jpeg");
      image.setFileName("first.jpg");
      image.setData("data".getBytes());

      when(itemImageService.getFirstImage(1L)).thenReturn(Optional.of(image));

      mockMvc.perform(get("/api/items/{itemId}/images/first", 1L))
          .andExpect(status().isOk())
          .andExpect(header().string("Content-Type", "image/jpeg"))
          .andExpect(header().string("Content-Disposition", "inline; filename=\"first.jpg\""));
    }
  }

  @Nested
  @DisplayName("Negative Tests")
  class NegativeTests {

    @Test
    void testUploadImages_Failure() throws Exception {
      MockMultipartFile image = new MockMultipartFile("images", "error.jpg", "image/jpeg", "error".getBytes());
      doThrow(new IOException("Upload failed")).when(itemImageService).uploadItemImages(eq(1L), any());

      mockMvc.perform(multipart("/api/items/{itemId}/images", 1L)
              .file(image))
          .andExpect(status().isInternalServerError())
          .andExpect(content().string("Failed to upload images"));
    }

    @Test
    void testGetImageIds_NotFound() throws Exception {
      when(itemImageService.getItemImageIds(1L)).thenReturn(Collections.emptyList());

      mockMvc.perform(get("/api/items/{itemId}/images", 1L))
          .andExpect(status().isNotFound());
    }

    @Test
    void testGetItemImage_NotFound() throws Exception {
      when(itemImageService.getItemImageById(1L, 99L)).thenReturn(Optional.empty());

      mockMvc.perform(get("/api/items/{itemId}/images/{imageId}", 1L, 99L))
          .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteImage_NotFound() throws Exception {
      when(itemImageService.deleteImage(1L, 404L)).thenReturn(false);

      mockMvc.perform(delete("/api/items/{itemId}/images/{imageId}", 1L, 404L))
          .andExpect(status().isNotFound());
    }

    @Test
    void testGetFirstImage_NotFound() throws Exception {
      when(itemImageService.getFirstImage(1L)).thenReturn(Optional.empty());

      mockMvc.perform(get("/api/items/{itemId}/images/first", 1L))
          .andExpect(status().isNotFound());
    }
  }
}
