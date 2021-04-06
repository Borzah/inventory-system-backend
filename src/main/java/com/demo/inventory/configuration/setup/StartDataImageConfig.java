package com.demo.inventory.configuration.setup;

import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component
@AllArgsConstructor
public class StartDataImageConfig {

    private final ImageRepository imageRepository;

    protected void addStartImageDataIfNeeded() throws IOException {
            byte[] computerImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/computer.jpg"));
            byte[] cleanCodeImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/cleancode.jpg"));
            byte[] discImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/disc.jpg"));
            byte[] dorianGreyImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/doriangrey.jpg"));
            byte[] hammerImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/hammer.jpg"));
            byte[] ukuleleImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/ukulele.jpg"));
            byte[] wireImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/wire.jpg"));
            Image computerImage = new Image(1L, computerImageBytes);
            Image ukuleleImage = new Image(2L, ukuleleImageBytes);
            Image cleanCodeBookImage = new Image(3L, cleanCodeImageBytes);
            Image hammerImage = new Image(4L, hammerImageBytes);
            Image discImage = new Image(6L, discImageBytes);
            Image wireImage = new Image(8L, wireImageBytes);
            Image dorianGreyBookImage = new Image(9L, dorianGreyImageBytes);
            imageRepository.saveAll(List.of(computerImage, ukuleleImage, cleanCodeBookImage, hammerImage, discImage, wireImage,
                    dorianGreyBookImage));
    }
}
