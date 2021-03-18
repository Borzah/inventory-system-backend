package com.demo.inventory.item.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private Long itemId;
    private String itemName;
    private Long folderId;
    private Long userId;
    private Long categoryId;
    private Date dateAdded;
    private String description;
    private String serialNumber;
}
