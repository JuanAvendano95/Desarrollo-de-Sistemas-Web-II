package com.tiendasara.Tienda.Sara.services;

import com.tiendasara.Tienda.Sara.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
