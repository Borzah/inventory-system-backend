package com.demo.inventory.admin.dto;

import lombok.*;

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
    private Date registeredAt;
    private Date lastItemAddedAt;
}
