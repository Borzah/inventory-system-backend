package com.demo.inventory.admin.controller;

import com.demo.inventory.admin.dto.UserStatisticsResponse;
import com.demo.inventory.admin.service.StatisticsService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Secured(Roles.ADMIN)
@RestController
@RequestMapping("statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public List<UserStatisticsResponse> getUserStatistics() {
        return statisticsService.getUserStatistics();
    }
}
