package no.ntnu.idatt2105.marketplace.controller;

import no.ntnu.idatt2105.marketplace.model.UserImage;
import no.ntnu.idatt2105.marketplace.service.UserImageService;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserImageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserImageService userImageService;

    @InjectMocks
    private UserImageController userImageController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userImageController).build();
    }

    @Nested
    @DisplayName("Positive Tests")
    class PositiveTests {

        @Test
        void testUploadImage_Success() throws Exception {
            MockMultipartFile image = new MockMultipartFile("image", "profile.jpg", "image/jpeg", "image data".getBytes());

            mockMvc.perform(multipart("/api/users/{userId}/image", 1L)
                    .file(image))
                .andExpect(status().isOk())
                .andExpect(content().string("Image uploaded successfully"));

            verify(userImageService, times(1)).saveImage(eq(1L), any());
        }

        @Test
        void testGetImage_Success() throws Exception {
            UserImage image = new UserImage();
            image.setContentType("image/jpeg");
            image.setFileName("profile.jpg");
            image.setData("image data".getBytes());

            when(userImageService.getImageByUserId(1L)).thenReturn(Optional.of(image));

            mockMvc.perform(get("/api/users/{userId}/image", 1L))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "image/jpeg"))
                .andExpect(header().string("Content-Disposition", "inline; filename=\"profile.jpg\""));
        }
    }

    @Nested
    @DisplayName("Negative Tests")
    class NegativeTests {

        @Test
        void testUploadImage_Failure() throws Exception {
            MockMultipartFile image = new MockMultipartFile("image", "profile.jpg", "image/jpeg", "image data".getBytes());
            doThrow(new IOException("Upload failed")).when(userImageService).saveImage(eq(1L), any());

            mockMvc.perform(multipart("/api/users/{userId}/image", 1L)
                    .file(image))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Failed to upload image"));
        }

        @Test
        void testGetImage_NotFound() throws Exception {
            when(userImageService.getImageByUserId(1L)).thenReturn(Optional.empty());

            mockMvc.perform(get("/api/users/{userId}/image", 1L))
                .andExpect(status().isNotFound());
        }
    }
}
