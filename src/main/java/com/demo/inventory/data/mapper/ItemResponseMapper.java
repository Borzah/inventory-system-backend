package com.demo.inventory.data.mapper;

import com.demo.inventory.data.dto.ItemResponse;
import com.demo.inventory.item.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ItemResponseMapper {

    ItemResponse itemToItemResponse(Item item, String folderName, String categoryName, byte[] imageBytes);
}
