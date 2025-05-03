package com.supplyhub.services;

import com.supplyhub.dto.product.CreateProductDataDto;
import com.supplyhub.entities.Employee;
import com.supplyhub.entities.Product;
import com.supplyhub.repositories.EmployeeRepository;
import com.supplyhub.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
        @Autowired
        private ProductRepository productRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Product createProduct(CreateProductDataDto productData) {
        Employee employee = employeeRepository.findByCpf(productData.cpf());

        if (employee == null) {
            throw new RuntimeException("Funcionario não encontrado!");
        }
        Product product = new Product(productData, employee);
        productRepository.save(product);
        return product;
    }

}
