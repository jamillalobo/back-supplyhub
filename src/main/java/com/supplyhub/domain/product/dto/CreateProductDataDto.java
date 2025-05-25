package com.supplyhub.domain.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateProductDataDto(
        @NotBlank(message = "Product name is required")
        @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters")
        String productName,

        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 11, message = "CPF must be 11 characters")
        String cpf,

        @NotBlank(message = "Received date is required")
        LocalDate receivedDate,

        @NotBlank(message = "Fabrication date is required")
        LocalDate fabricationDate,

        @NotBlank(message = "Expired date is required")
        LocalDate expiredDate,

        @NotBlank(message = "Peso is required")
        float peso,

        @NotBlank(message = "Type Peso date is required")
        @NotNull
        @NotEmpty
        String typePeso,

        boolean terms
) {
}
