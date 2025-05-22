package com.supplyhub.domain.product;

import com.supplyhub.domain.product.dto.CreateProductDataDto;
import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.employee.EmployeeRepository;
import com.supplyhub.domain.product.dto.DispatchProductRequestDto;
import com.supplyhub.domain.product.dto.ListDataProductsDto;
import com.supplyhub.domain.product.dto.ProductResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.List;

@Service
public class ProductService {
        @Autowired
        private ProductRepository productRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Product createProduct(CreateProductDataDto productData) {
        Employee employee = employeeRepository.findByCpf(productData.cpf());

        if (employee == null) {
            throw new RuntimeException("Funcionario não encontrado!");
        }
        Product product = new Product(productData, employee);
        productRepository.save(product);
        return product;
    }


    public List<ListDataProductsDto> getDispatchedProducts() {
        List<Product> dispatchedProducts = productRepository.findByIsDispatchedTrue();
        return dispatchedProducts.stream()
                .map(ListDataProductsDto::new)
                .toList();
    }

    public List<ListDataProductsDto> getReceivedProducts() {
        List<Product> receivedProducts = productRepository.findByIsDispatchedFalse();
        return receivedProducts.stream()
                .map(ListDataProductsDto::new)
                .toList();
    }

    @Transactional
    public ProductResponseDto dispatchProduct(Long productId, DispatchProductRequestDto dispatchData) {
        // Find the product by ID or throw an exception if not found
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));

        // Optional: Check if the product is already dispatched
        if (product.isDispatched()) {
            // You can choose to throw an error or allow updating destination/date again
            throw new IllegalStateException("Product with id: " + productId + " is already dispatched.");
        }

        // Update product fields for dispatch
        product.setDispatched(true);
        product.setDispatchedDate(new Date()); // Set current date and time for dispatch
        product.setDestination(dispatchData.destination());

        // Save the updated product
        Product dispatchedProduct = productRepository.save(product);

        // Return a DTO of the updated product
        return new ProductResponseDto(dispatchedProduct);
    }

}
