package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface FolderMapper {

    FolderMapper MAPPER = Mappers.getMapper(FolderMapper.class);

    Folder toFolder(FolderDto dto);

    @InheritInverseConfiguration
    FolderDto fromFolder(Folder folder);
}
