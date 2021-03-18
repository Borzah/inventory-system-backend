package com.demo.inventory.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {

    private Long imageId;
    private byte[] imageBytes;

    public ImageDto(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
