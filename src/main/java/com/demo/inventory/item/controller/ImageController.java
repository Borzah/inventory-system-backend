package com.demo.inventory.item.controller;

import com.demo.inventory.item.model.Image;
import com.demo.inventory.item.service.ImageService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Secured(Roles.USER)
@RestController
@RequestMapping("images")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("{imageId}")
    public Image addImage(@PathVariable Long imageId,
                          @RequestParam("imageFile") MultipartFile file,
                          @RequestHeader("Authorization") String authToken)
            throws IOException {
        return imageService.addImage(imageId, file, authToken);
    }

    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }
}
