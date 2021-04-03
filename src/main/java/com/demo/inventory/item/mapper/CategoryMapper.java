package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.model.Category;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface CategoryMapper {

    CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryDto dto);

    @InheritInverseConfiguration
    CategoryDto fromCategory(Category category);
}
