package com.supplyhub.domain.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.employee.EmployeeRepository;
import com.supplyhub.domain.product.dto.CreateProductDataDto;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_WithValidEmployee_ShouldCreateProduct() {
        // Arrange
        CreateProductDataDto dto = new CreateProductDataDto(
                    "Presunto",
                    "12345678900",
                    LocalDate.of(2024, 5, 10),
                    LocalDate.of(2024, 4, 1),
                    LocalDate.of(2024, 6, 1),
                    2.5f,
                    "KG",
                    true
        );

        Employee mockEmployee = new Employee();

        when(employeeRepository.findByCpf("12345678900")).thenReturn(mockEmployee);

        // Act
        Product createdProduct = productService.createProduct(dto);

        // Assert
        assertNotNull(createdProduct);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_WithInvalidEmployee_ShouldThrowException() {
        // Arrange
                CreateProductDataDto dto = new CreateProductDataDto(
                    "Presunto",
                    "cpf_invalido",
                    LocalDate.of(2024, 5, 10),
                    LocalDate.of(2024, 4, 1),
                    LocalDate.of(2024, 6, 1),
                    2.5f,
                    "KG",
                    true
        );

        when(employeeRepository.findByCpf("cpf_invalido")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> productService.createProduct(dto));
        verify(productRepository, never()).save(any(Product.class));
    }
}
