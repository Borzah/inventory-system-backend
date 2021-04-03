package com.demo.inventory.data.mapper;

import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.item.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface ItemResponseMapper {

    ItemResponseMapper MAPPER = Mappers.getMapper(ItemResponseMapper.class);

    ItemResponse itemToItemResponse(Item item, String folderName, String categoryName, byte[] imageBytes);
}
