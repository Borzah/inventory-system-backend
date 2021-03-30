package com.demo.inventory.item.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolderDto {

    private Long folderId;
    @NotBlank(message = "Folder name cannot be blank")
    @Size(max = 40, message
            = "Folder name cannot be over 40 characters")
    private String folderName;
    private String folderPathName;
    private Long parentId;
    @NotNull(message = "User id cannot be null")
    private Long userId;
}
