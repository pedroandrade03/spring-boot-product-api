package com.pedro.andrade.product.domain.services;

import com.pedro.andrade.product.adapter.controllers.ProductController;
import com.pedro.andrade.product.domain.models.Product;
import com.pedro.andrade.product.infrastructure.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                UUID idProduct = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(idProduct)).withSelfRel());
            }
        }
        return productList;
    }

    public Optional<Product> getOneProduct(UUID id) {
        Optional<Product> productModelOptional = productRepository.findById(id);
        if (productModelOptional.isPresent()) {
            Product product = productModelOptional.get();
            product.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("listProducts"));
        }
        return productModelOptional;
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }
}