package com.demo.inventory.item.service;

import com.demo.inventory.configuration.setup.StartDataRunner;
import com.demo.inventory.exception.ItemException;
import com.demo.inventory.item.mapper.ImageMapper;
import com.demo.inventory.item.model.Item;
import com.demo.inventory.item.repository.ImageRepository;
import com.demo.inventory.item.repository.ItemRepository;
import com.demo.inventory.item.utils.ItemUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {"file-upload"})
public class ImageServiceTest {

    // Added to avoid errors
    @MockBean
    private StartDataRunner startDataRunner;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ImageMapper mapper;

    @MockBean
    private MultipartFile file;

    @MockBean
    private ItemUtils itemUtils;

    private ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageService(imageRepository, itemRepository, mapper, itemUtils);
    }

    @Test
    void shouldNotAddImageBecauseItIsNotImageFileType() throws IOException {

        when(itemUtils.getAllowedFileTypes())
                .thenReturn(List.of("image/jpeg", "image/jpg", "image/png"));
        when(itemUtils.getMaxFileSize())
                .thenReturn(1000000L);

        when(itemRepository.findByItemIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Item()));
        when(file.getBytes()).thenReturn(new byte[]{});
        when(file.getSize()).thenReturn(200000L);
        when(file.getContentType()).thenReturn("pdf");

        assertThatThrownBy(() -> imageService.addImage(1L, file, 1L))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("A file must be a image!");
    }

    @Test
    void shouldNotAddImageBecauseItIsTooBig() throws IOException {

        when(itemUtils.getAllowedFileTypes())
                .thenReturn(List.of("image/jpeg", "image/jpg", "image/png"));
        when(itemUtils.getMaxFileSize())
                .thenReturn(1000000L);

        when(itemRepository.findByItemIdAndUserId(1L, 1L)).thenReturn(Optional.of(new Item()));
        when(file.getBytes()).thenReturn(new byte[]{});
        when(file.getSize()).thenReturn(1200000L);
        when(file.getContentType()).thenReturn("image/png");

        assertThatThrownBy(() -> imageService.addImage(1L, file, 1L))
                .isInstanceOf(ItemException.class)
                .hasMessageContaining("A file cannot be over 1 Mb");
    }
}
