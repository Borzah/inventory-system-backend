package com.demo.inventory.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {

    private Long folderId;
    @NotBlank(message = "Folder name cannot be blank")
    private String folderName;
    private String folderPathName;
    private Long parentId;
    @NotNull(message = "User id cannot be null")
    private Long userId;
}
