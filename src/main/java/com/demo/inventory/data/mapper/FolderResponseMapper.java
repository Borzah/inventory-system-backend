package com.demo.inventory.data.mapper;

import com.demo.inventory.data.dto.FolderResponse;
import com.demo.inventory.data.dto.ItemNodeResponse;
import com.demo.inventory.item.dto.FolderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel="spring")
public interface FolderResponseMapper {

    FolderResponseMapper MAPPER = Mappers.getMapper(FolderResponseMapper.class);

    FolderResponse createFolderResponse(Long currentFolderId,
                                        Long parentFolderId,
                                        String currentFolderPathName,
                                        List<FolderDto> folders,
                                        List<ItemNodeResponse> items);
}
