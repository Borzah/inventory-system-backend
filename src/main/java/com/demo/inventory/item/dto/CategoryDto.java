package com.demo.inventory.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long categoryId;
    private Long userId;
    private String categoryName;

    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
