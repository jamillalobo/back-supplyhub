package com.supplyhub.controllers;

import com.supplyhub.dto.product.CreateProductDataDto;
import com.supplyhub.entities.Product;
import com.supplyhub.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody CreateProductDataDto productData) {
        Product product = productService.createProduct(productData);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

}