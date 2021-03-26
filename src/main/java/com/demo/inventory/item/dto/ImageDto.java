package com.demo.inventory.item.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {

    @NotNull(message = "Image id cannot be null")
    private Long imageId;
    private byte[] imageBytes;
}
