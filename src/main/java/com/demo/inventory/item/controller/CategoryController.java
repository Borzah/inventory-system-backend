package com.demo.inventory.item.controller;

import com.demo.inventory.item.dto.CategoryDto;
import com.demo.inventory.item.service.CategoryService;
import com.demo.inventory.security.InventoryUser;
import com.demo.inventory.security.Roles;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Secured(Roles.USER)
@RestController
@RequestMapping("categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategoriesByUserId(@AuthenticationPrincipal InventoryUser auth) {
        return categoryService.getAllCategoriesByUserId(auth.getId());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                   @AuthenticationPrincipal InventoryUser auth) {
        CategoryDto category = categoryService.addCategory(categoryDto, auth.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
