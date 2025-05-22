package com.supplyhub.domain.product;

import com.supplyhub.domain.employee.Employee;
import com.supplyhub.domain.product.dto.CreateProductDataDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "Product")
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @Column(name = "fabrication_date", nullable = false)
    private LocalDate fabricationDate;

    @Column(name = "received_date", nullable = false)
    private LocalDate receivedDate;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(name = "dispatched_date", nullable = true)
    private Date dispatchedDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "peso", nullable = false)
    private float peso;

    @Column(name = "type_peso", nullable = false)
    private String typePeso;

    public Product(CreateProductDataDto productData, Employee employee) {
        this.setProductName(productData.productName());
        this.setEmployee(employee);
        this.setReceivedDate(productData.receivedDate());
        this.setFabricationDate(productData.fabricationDate());
        this.setExpiredDate(productData.expiredDate());
        this.setPeso(productData.peso());
        this.setTypePeso(productData.typePeso());
        this.status = "RECEBIDO";
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getFabricationDate() {
        return fabricationDate;
    }

    public void setFabricationDate(LocalDate fabricationDate) {
        this.fabricationDate = fabricationDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }
}
