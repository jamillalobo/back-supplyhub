package com.supplyhub.domain.product.dto;

import com.supplyhub.domain.product.Product;

import java.time.LocalDate;
import java.util.Date;

public record ProductResponseDto(
        Long id,
        String productName,
        Long employeeId, // Or a more detailed Employee DTO if needed
        LocalDate fabricationDate,
        LocalDate expiredDate,
        String destination,
        Date dispatchedDate,
        boolean isDispatched
) {
    public ProductResponseDto(Product product) {
        this(
                product.getId(),
                product.getProductName(),
                product.getEmployee().getId(), // Assuming you want to return the employee's ID
                product.getFabricationDate(),
                product.getExpiredDate(),
                product.getDestination(),
                product.getDispatchedDate(),
                product.isDispatched()
        );
    }
}
