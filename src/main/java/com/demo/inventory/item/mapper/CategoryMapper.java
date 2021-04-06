package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    CategoryDto fromCategory(Category category);
}
