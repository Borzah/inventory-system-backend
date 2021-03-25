package com.demo.inventory.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long itemId;
    @NotBlank(message = "Item name cannot be null or blank!")
    private String itemName;
    private Long folderId;
    @NotNull(message = "Item userId cannot be null!")
    private Long userId;
    private Long categoryId;
    private Date dateAdded;
    private String description;
    private String serialNumber;
    private Float itemPrice;
}
