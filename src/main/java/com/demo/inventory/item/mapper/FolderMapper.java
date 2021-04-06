package com.demo.inventory.item.mapper;

import com.demo.inventory.item.dto.FolderDto;
import com.demo.inventory.item.model.Folder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface FolderMapper {

    Folder toFolder(FolderDto dto);

    @InheritInverseConfiguration
    FolderDto fromFolder(Folder folder);
}
