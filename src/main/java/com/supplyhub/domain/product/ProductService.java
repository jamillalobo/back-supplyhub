package com.supplyhub.domain.product;

import com.supplyhub.domain.product.dto.CreateProductDataDto;
import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.employee.EmployeeRepository;
import com.supplyhub.domain.product.dto.ListDataProductsDto;
import com.supplyhub.domain.product.dto.ListDispatchedProductsDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public void dispatchProducts(List<Long> productIds) {
        productRepository.findAllById(productIds)
                .forEach(product -> {
                    product.setStatus("EXPEDIDO");
                    product.setDispatchedDate(Date.from(Instant.now()));
                    productRepository.save(product);
                });
    }

    public List<ListDispatchedProductsDto> getDispatchedProducts() {
        return productRepository.findByStatus("EXPEDIDO")
                .stream()
                .map(ListDispatchedProductsDto::new)
                .collect(Collectors.toList());
    }

    public List<ListDataProductsDto> getReceivedProducts() {
        return productRepository.findByStatus("RECEBIDO")
                .stream()
                .map(ListDataProductsDto::new)
                .collect(Collectors.toList());
    }

}
