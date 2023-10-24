package com.pedro.andrade.product.infrastructure.repositories;

import com.pedro.andrade.product.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
