package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.service.CategoryService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Secured(Roles.USER)
@RestController
@RequestMapping("categories")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("user/{userId}")
    public List<CategoryDto> getAllCategoriesByUserId(@PathVariable Long userId,
                                                      @RequestHeader("Authorization") String authToken) {
        return categoryService.getAllCategoriesByUserId(userId, authToken);
    }

    @PostMapping
    public CategoryDto addCategory(@Valid @RequestBody CategoryDto categoryDto,
                                   @RequestHeader("Authorization") String authToken) {
        return categoryService.addCategory(categoryDto, authToken);
    }
}
