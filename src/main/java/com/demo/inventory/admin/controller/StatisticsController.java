package com.demo.inventory.admin.controller;

import com.demo.inventory.security.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

@Secured(Roles.ADMIN)
@RestController
public class StatisticsController {


}
