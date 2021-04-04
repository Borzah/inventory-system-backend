package com.demo.inventory.configuration.setup;

import com.demo.inventory.item.model.Category;
import com.demo.inventory.item.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class StartDataCategoryConfig {

    private final CategoryRepository categoryRepository;

    protected void addStartCategoryDataIfNeeded() {
            Category johnElectronics = new Category(2L, "Electronics");
            Category johnTools = new Category(2L, "Tools");
            Category johnMedia = new Category(2L, "Media");
            Category johnBooks = new Category(2L, "Books");
            Category annaKitchen = new Category(3L, "Kitchen");
            Category annaVaria = new Category(3L, "Varia");
            categoryRepository.saveAll(List.of(johnElectronics, johnTools, johnMedia, johnBooks, annaKitchen, annaVaria));
    }
}
