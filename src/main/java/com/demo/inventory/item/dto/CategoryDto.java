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
public class CategoryDto {

    private Long categoryId;
    private Long userId;
    @Size(max = 40, message
            = "Category name cannot be over 40 characters")
    @NotBlank(message = "Category name cannot be null or blank")
    private String categoryName;

    public CategoryDto(Long userId, String categoryName) {
        this.userId = userId;
        this.categoryName = categoryName;
    }
}
