package com.demo.inventory.item.repository;

import com.demo.inventory.item.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByImageId(Long imageId);
}
