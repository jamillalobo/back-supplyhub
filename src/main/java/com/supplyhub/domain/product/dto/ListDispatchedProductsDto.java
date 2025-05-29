package com.supplyhub.domain.product.dto;

import com.supplyhub.domain.product.Product;

import java.time.LocalDate;
import java.util.Date;

public record ListDispatchedProductsDto(Long id,
                                        String productName,
                                        String employee,
                                        LocalDate fabricationDate,
                                        LocalDate expiredDate,
                                        Date dispatchedDate,
                                        float peso,
                                        String typePeso
) {
    public ListDispatchedProductsDto(Product product) {
        this(product.getId(), product.getProductName(), product.getEmployee().getUsername(), product.getFabricationDate(), product.getExpiredDate(), product.getDispatchedDate(), product.getPeso(), product.getTypePeso());
    }
}

