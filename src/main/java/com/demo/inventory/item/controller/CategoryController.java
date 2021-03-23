package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.service.CategoryService;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Secured(Roles.USER)
@RestController
@RequestMapping("categories")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public CategoryDto addCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.addCategory(categoryDto);
    }
}
