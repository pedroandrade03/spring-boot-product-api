CREATE TABLE products (
                          id_product UUID NOT NULL,
                          name VARCHAR(255) NOT NULL,
                          value DECIMAL(10,2) NOT NULL,
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);