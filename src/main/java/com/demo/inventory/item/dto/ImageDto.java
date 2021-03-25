package com.demo.inventory.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {

    @NotNull(message = "Image id cannot be null")
    private Long imageId;
    private byte[] imageBytes;

    public ImageDto(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
