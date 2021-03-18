package com.demo.inventory.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Image")
@Entity
public class Image {

    @Id
    @Column(name = "image_id")
    private Long imageId;
    @Column(name = "image_bytes")
    private byte[] imageBytes;
}
