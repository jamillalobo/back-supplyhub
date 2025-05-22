package com.supplyhub.domain.product.dto;

import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.product.Product;

import java.time.LocalDate;

public record ListDataProductsDto(
        String productName,
        String employee,
        LocalDate fabricationDate,
        LocalDate expiredDate
) {
    public ListDataProductsDto(Product product) {
        this(product.getProductName(), product.getEmployee().getUsername(), product.getFabricationDate(), product.getExpiredDate());
    }
}
