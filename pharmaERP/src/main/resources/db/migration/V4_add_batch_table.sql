CREATE TABLE batches (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    batch_number VARCHAR NOT NULL,
    manufacturing_month INT NOT NULL,
    manufacturing_year INT NOT NULL,
    expiry_month INT NOT NULL,
    expiry_year INT NOT NULL,
    expiry_date DATE GENERATED ALWAYS AS (make_date (expiry_year, expiry_month, 1)) STORED,
    quantity_received INT NOT NULL,
    quantity_available INT NOT NULL,
    purchase_price NUMERIC(10, 2) NOT NULL,
    mrp NUMERIC(10, 2) NOT NULL,
    version INT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_valid_mnth CHECK (
        expiry_month BETWEEN 1 AND 12
        AND manufacturing_month BETWEEN 1 AND 12
    ),
    CONSTRAINT chk_valid_yr CHECK (
        expiry_year >= 2000
        AND manufacturing_year >= 2000
    ),
    CONSTRAINT chk_qty CHECK (
        quantity_available >= 0
        AND quantity_received >= 0
        AND quantity_available <= quantity_received
    ),
    CONSTRAINT uq_product_batch UNIQUE (batch_number, product_id),
    CONSTRAINT fk_batches_products FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE RESTRICT
);

CREATE INDEX idx_product_expiry ON batches (product_id, expiry_date);