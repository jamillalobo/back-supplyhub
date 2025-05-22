package com.supplyhub.domain.product;

import com.supplyhub.domain.product.dto.CreateProductDataDto;
import com.supplyhub.domain.product.dto.DispatchProductRequestDto;
import com.supplyhub.domain.product.dto.ListDataProductsDto;
import com.supplyhub.domain.product.dto.ProductResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    @PatchMapping("/{id}/dispatch") // Specific endpoint for dispatching
    @Transactional
    public ResponseEntity<ProductResponseDto> dispatchProduct(
            @PathVariable Long id,
            @RequestBody @Valid DispatchProductRequestDto dispatchRequestData) {
        ProductResponseDto updatedProduct = productService.dispatchProduct(id, dispatchRequestData);
        return ResponseEntity.ok(updatedProduct);
    }
}