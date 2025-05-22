package com.supplyhub.domain.product.dto;

import jakarta.validation.constraints.NotBlank;

public record DispatchProductRequestDto(
        @NotBlank(message = "Destination cannot be blank")
        String destination
) {}
