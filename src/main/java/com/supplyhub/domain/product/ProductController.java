package com.supplyhub.domain.product;

import com.supplyhub.domain.product.dto.CreateProductDataDto;
import com.supplyhub.domain.product.dto.ListDataProductsDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/dispatch")
    @Transactional
    public ResponseEntity<String> dispatchProducts(@RequestBody List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return ResponseEntity.badRequest().body("A lista de IDs de produtos não pode estar vazia.");
        }
        productService.dispatchProducts(productIds);
        return ResponseEntity.ok("Status dos produtos atualizados para 'EXPEDIDO'.");
    }

    @GetMapping("/dispatched")
    public ResponseEntity<List<ListDataProductsDto>> getDispatchedProducts() {
        List<ListDataProductsDto> dispatchedProducts = productService.getDispatchedProducts();
        return ResponseEntity.ok(dispatchedProducts);
    }

    @GetMapping("/received")
    public ResponseEntity<List<ListDataProductsDto>> getReceivedProducts() {
        List<ListDataProductsDto> dispatchedProducts = productService.getReceivedProducts();
        return ResponseEntity.ok(dispatchedProducts);
    }

}