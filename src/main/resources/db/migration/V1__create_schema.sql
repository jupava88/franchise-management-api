CREATE TABLE franchises (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE locations (
    id BIGINT NOT NULL AUTO_INCREMENT,
    franchise_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_locations_franchise_id (franchise_id),
    CONSTRAINT fk_locations_franchise
        FOREIGN KEY (franchise_id) REFERENCES franchises (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    location_id BIGINT NOT NULL,
    name VARCHAR(120) NOT NULL,
    stock INT NOT NULL,
    PRIMARY KEY (id),
    INDEX idx_products_location_id (location_id),
    CONSTRAINT chk_products_stock CHECK (stock >= 0),
    CONSTRAINT fk_products_location
        FOREIGN KEY (location_id) REFERENCES locations (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
