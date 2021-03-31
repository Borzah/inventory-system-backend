package com.demo.inventory.admin.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStatisticsResponse {

    private String username;
    private int numOfItems;
    private int numOfFolders;
    private Timestamp registeredAt;
    private Timestamp lastItemAddedAt;
}
