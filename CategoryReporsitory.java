package com.tiendasara.Tienda.Sara.services;

import com.tiendasara.Tienda.Sara.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
