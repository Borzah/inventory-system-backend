package com.demo.inventory.configuration;

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

    public void addStartImageDataIfNeeded() throws IOException {
            byte[] computerImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/computer.jpg"));
            byte[] cleanCodeImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/cleancode.jpg"));
            byte[] cupImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/cup.jpg"));
            byte[] discImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/disc.jpg"));
            byte[] dorianGreyImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/doriangrey.jpg"));
            byte[] flowerImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/flower.jpeg"));
            byte[] hammerImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/hammer.jpg"));
            byte[] headphonesImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/headphones.jpg"));
            byte[] notebookImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/notebook.jpg"));
            byte[] ukuleleImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/ukulele.jpg"));
            byte[] wireImageBytes = Files.readAllBytes(Paths.get("src/main/resources/static/assets/wire.jpg"));
            Image computerImage = new Image(1L, computerImageBytes);
            Image ukuleleImage = new Image(2L, ukuleleImageBytes);
            Image cleanCodeBookImage = new Image(3L, cleanCodeImageBytes);
            Image hammerImage = new Image(4L, hammerImageBytes);
            Image discImage = new Image(6L, discImageBytes);
            Image wireImage = new Image(8L, wireImageBytes);
            Image dorianGreyBookImage = new Image(9L, dorianGreyImageBytes);
            Image flowerImage = new Image(10L, flowerImageBytes);
            Image headphonesImage = new Image(11L, headphonesImageBytes);
            Image cupImage = new Image(12L, cupImageBytes);
            Image notebookImage = new Image(13L, notebookImageBytes);
            imageRepository.saveAll(List.of(computerImage, ukuleleImage, cleanCodeBookImage, hammerImage, discImage, wireImage,
                    dorianGreyBookImage, flowerImage, headphonesImage, cupImage, notebookImage));
    }
}
