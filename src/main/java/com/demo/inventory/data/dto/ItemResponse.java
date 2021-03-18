package com.demo.inventory.data.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private Long itemId;
    private String itemName;
    private Long folderId;
    private String folderName;
    private Long userId;
    private String categoryName;
    private Date dateAdded;
    private String description;
    private String serialNumber;
    private byte[] imageBytes;
}
