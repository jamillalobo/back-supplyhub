CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    employee_id BIGINT NOT NULL,
    fabrication_date DATE NOT NULL,
    expired_date DATE NOT NULL,
    destination VARCHAR(255),
    dispatched_date DATE,
    is_dispatched BOOLEAN NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES users(id)
);