package com.demo.inventory.item.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Image")
@Entity
@Builder
public class Image {

    @Id
    @Column(name = "image_id")
    private Long imageId;
    @Column(name = "image_bytes")
    private byte[] imageBytes;
}
