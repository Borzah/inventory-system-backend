package com.demo.inventory.item.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Item")
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "folder_id")
    private Long folderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "date_added")
    private Timestamp dateAdded;
    @Column(name = "description")
    private String description;
    @Column(name = "serial_number")
    private String serialNumber;
    @Column(name = "item_price")
    private Float itemPrice;
}
