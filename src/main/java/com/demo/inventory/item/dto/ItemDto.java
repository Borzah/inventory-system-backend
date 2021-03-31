package com.demo.inventory.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long itemId;
    @NotBlank(message = "Item name cannot be null or blank!")
    @Size(max = 40, message
            = "Item name cannot be over 40 characters")
    private String itemName;
    private Long folderId;
    @NotNull(message = "Item userId cannot be null!")
    private Long userId;
    private Long categoryId;
    private Timestamp dateAdded;
    private String description;
    @Size(max = 50, message
            = "Item serial number cannot be over 50 characters")
    private String serialNumber;
    @Positive(message = "Item price must be higher than 0")
    private Float itemPrice;
}
