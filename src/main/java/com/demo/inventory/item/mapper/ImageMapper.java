package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.ImageDto;
import com.demo.inventory.item.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ImageMapper {

    ImageDto fromImage(Image image);
}
