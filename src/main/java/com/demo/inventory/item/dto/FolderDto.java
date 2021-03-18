package com.demo.inventory.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDto {

    private Long folderId;
    private String folderName;
    private String folderPathName;
    private Long parentId;
    private Long userId;
}
