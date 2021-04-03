package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.ImageDto;
import com.demo.inventory.item.model.Image;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ImageMapper {

    ImageMapper MAPPER = Mappers.getMapper(ImageMapper.class);

    Image toImage(ImageDto dto);

    @InheritInverseConfiguration
    ImageDto fromImage(Image image);
}
