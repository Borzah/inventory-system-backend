package com.demo.inventory.admin.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

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
