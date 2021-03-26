package com.demo.inventory.item.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Folder")
@Entity
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long folderId;
    @Column(name = "folder_name")
    private String folderName;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "user_id")
    private Long userId;

    public Folder(String folderName, Long userId) {
        this.folderName = folderName;
        this.userId = userId;
    }

    public Folder(String folderName, Long parentId, Long userId) {
        this.folderName = folderName;
        this.parentId = parentId;
        this.userId = userId;
    }
}
