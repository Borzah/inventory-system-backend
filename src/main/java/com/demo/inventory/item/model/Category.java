package com.demo.inventory.item.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Category")
@Entity
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "category_name")
    private String categoryName;

    public Category(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }
}
