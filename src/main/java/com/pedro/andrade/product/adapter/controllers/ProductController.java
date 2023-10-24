package com.pedro.andrade.product.adapter.controllers;

import com.pedro.andrade.product.application.dtos.ProductDTO;
import com.pedro.andrade.product.domain.models.Product;
import com.pedro.andrade.product.domain.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid ProductDTO productDTO) {
        var productModel = new Product();
        BeanUtils.copyProperties(productDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productModel));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<Product> productO = productService.getOneProduct(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productO.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductDTO productRecordDto) {
        Optional<Product> productO = productService.getOneProduct(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = productO.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productService.saveProduct(productModel));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<Product> productO = productService.getOneProduct(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productService.deleteProduct(productO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
