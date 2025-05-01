package com.supplyhub.dto.product;

import java.time.LocalDate;

public record CreateProductDataDto(
        String cpf,
        LocalDate fabricationDate,
        String productName,
        boolean terms,
        LocalDate expiredDate
) {
}
