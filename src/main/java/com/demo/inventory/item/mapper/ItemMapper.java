package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.ItemDto;
import com.demo.inventory.item.model.Item;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ItemMapper {

    ItemMapper MAPPER = Mappers.getMapper(ItemMapper.class);

    Item toItem(ItemDto dto);

    @InheritInverseConfiguration
    ItemDto fromItem(Item item);

}
