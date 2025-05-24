package com.supplyhub.domain.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.employee.EmployeeRepository;
import com.supplyhub.domain.product.dto.CreateProductDataDto;
import com.supplyhub.domain.product.dto.ListDataProductsDto;
import com.supplyhub.domain.product.dto.ListDispatchedProductsDto;


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

    @Test
    void dispatchProducts_WithValidId_ShouldUpdateProductStatus() {
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

        Product mockProduct = new Product(dto, mockEmployee);
        
        mockProduct.setId(1L);
        mockProduct.setStatus("RECEBIDO"); // Status inicial

        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(mockProduct));

        // Act
        productService.dispatchProducts(List.of(1L));

        // Assert
        assertEquals("EXPEDIDO", mockProduct.getStatus());
        assertNotNull(mockProduct.getDispatchedDate());
        verify(productRepository).save(mockProduct);
    }

    @Test
    void dispatchProducts_WithInvalidId_ShouldDoNothing() {
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
        mockEmployee.setCpf("12345678900");
        
        Product validProduct = new Product(dto, mockEmployee);
        validProduct.setId(1L);
        validProduct.setStatus("RECEBIDO");
        
        Long invalidId = 999L; // ID inválido que não existe
        
        when(productRepository.findAllById(List.of(invalidId))).thenReturn(Collections.emptyList());
        
        // Act
        productService.dispatchProducts(List.of(invalidId));

        // Assert
        verify(productRepository, never()).save(any(Product.class));
        verify(productRepository).findAllById(List.of(invalidId));
        
        assertEquals("RECEBIDO", validProduct.getStatus());
        assertNull(validProduct.getDispatchedDate());
    }

    @Test
    void getDispatchedProducts_ShouldReturnListOfDispatchedProducts() {
        // Arrange
        CreateProductDataDto dto1 = new CreateProductDataDto(
            "Presunto",
            "12345678900",
            LocalDate.of(2024, 5, 10),
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 6, 1),
            2.5f,
            "KG",
            true
        );
        
        CreateProductDataDto dto2 = new CreateProductDataDto(
            "Queijo",
            "12345678900",
            LocalDate.of(2024, 5, 11),
            LocalDate.of(2024, 4, 2),
            LocalDate.of(2024, 6, 2),
            1.5f,
            "KG",
            true
        );

        Employee mockEmployee = new Employee();
        mockEmployee.setCpf("12345678900");

        Product dispatchedProduct1 = new Product(dto1, mockEmployee);
        dispatchedProduct1.setId(1L);
        dispatchedProduct1.setStatus("EXPEDIDO");
        dispatchedProduct1.setDispatchedDate(Date.from(Instant.now()));

        Product dispatchedProduct2 = new Product(dto2, mockEmployee);
        dispatchedProduct2.setId(2L);
        dispatchedProduct2.setStatus("EXPEDIDO");
        dispatchedProduct2.setDispatchedDate(Date.from(Instant.now()));

        when(productRepository.findByStatus("EXPEDIDO")).thenReturn(List.of(dispatchedProduct1, dispatchedProduct2));

        // Act
        List<ListDispatchedProductsDto> result = productService.getDispatchedProducts();

        // Assert
        assertEquals(2, result.size(), "Deveria retornar 2 produtos expedidos");
        
        // Verifica o primeiro produto
        assertEquals("Presunto", result.get(0).productName());
        assertEquals(2.5f, result.get(0).peso());
        assertNotNull(result.get(0).dispatchedDate());
        
        // Verifica o segundo produto
        assertEquals("Queijo", result.get(1).productName());
        assertEquals(1.5f, result.get(1).peso());
        assertNotNull(result.get(1).dispatchedDate());

        // Verifica se o repositório foi chamado corretamente
        verify(productRepository).findByStatus("EXPEDIDO");
    }

    @Test
    void getReceivedProducts_ShouldReturnListOfReceivedProducts() {
        // Arrange
        CreateProductDataDto dto = new CreateProductDataDto(
            "Mortadela",
            "12345678900",
            LocalDate.of(2024, 5, 12),
            LocalDate.of(2024, 4, 5),
            LocalDate.of(2024, 6, 5),
            1.8f,
            "KG",
            true
        );

        Employee mockEmployee = new Employee();
        mockEmployee.setCpf("12345678900");

        Product receivedProduct = new Product(dto, mockEmployee);
        receivedProduct.setId(1L);
        receivedProduct.setStatus("RECEBIDO");

        when(productRepository.findByStatus("RECEBIDO")).thenReturn(List.of(receivedProduct));

        // Act
        List<ListDataProductsDto> result = productService.getReceivedProducts();

        // Assert
        assertEquals(1, result.size(), "Deveria retornar 1 produto recebido");
        assertEquals("Mortadela", result.get(0).productName());
        assertEquals(1.8f, result.get(0).peso());
        verify(productRepository).findByStatus("RECEBIDO");
    }
}
