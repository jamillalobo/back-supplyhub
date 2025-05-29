package com.supplyhub.domain.product.dto;

import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.product.Product;

import java.time.LocalDate;
import java.util.Date;

public record ListDataProductsDto(
        Long id,
        String productName,
        String employee,
        LocalDate fabricationDate,
        LocalDate expiredDate,
        LocalDate receivedDate,
        float peso,
        String typePeso
) {
    public ListDataProductsDto(Product product) {
        this(product.getId(), product.getProductName(), product.getEmployee().getUsername(), product.getFabricationDate(), product.getExpiredDate(), product.getReceivedDate(), product.getPeso(), product.getTypePeso());
    }
}
