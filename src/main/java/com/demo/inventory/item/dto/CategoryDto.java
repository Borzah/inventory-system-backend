package com.demo.inventory.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private Long categoryId;
    @NotNull(message = "User id cannot be null")
    private Long userId;
    @NotBlank(message = "Category name cannot be null or blank")
    private String categoryName;

    public CategoryDto(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }
}
