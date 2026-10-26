CREATE TABLE products (
    id               UUID PRIMARY KEY,
    sku              VARCHAR UNIQUE,
    name             VARCHAR not null,
    generic_name     VARCHAR,
    brand            VARCHAR not null,
    manufacturer     VARCHAR not null,
    category         VARCHAR,
    hsn_code         VARCHAR not null,   
    unit             VARCHAR CHECK (unit IN ('STRIP', 'BOX', 'BOTTLE')),
    pack_size        INT not null,
    mrp              NUMERIC(10,2) NOT NULL,
    created_at       TIMESTAMP NOT NULL DEFAULT now(),
    updated_at       TIMESTAMP NOT NULL DEFAULT now()
);