package com.demo.inventory.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDto {

    private Long folderId;
    @NotBlank(message = "Folder name cannot be blank")
    private String folderName;
    private String folderPathName;
    private Long parentId;
    @NotNull(message = "User id cannot be null")
    private Long userId;
}
