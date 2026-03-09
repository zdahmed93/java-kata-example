CREATE DATABASE IF NOT EXISTS kata_ngoumo_subscription;
USE kata_ngoumo_subscription;

CREATE TABLE IF NOT EXISTS subscription_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);

CREATE TABLE customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    customer_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,

    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    status VARCHAR(30) NOT NULL,
    auto_renew BOOLEAN NOT NULL DEFAULT FALSE,

    next_cycle_with_prorated DECIMAL(10,2),

    CONSTRAINT fk_subscription_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(id),

    CONSTRAINT fk_subscription_plan
        FOREIGN KEY (plan_id)
        REFERENCES subscription_plan(id)
) ENGINE=InnoDB;

INSERT INTO subscription_plan (type, duration, price)
VALUES
    ('MONTHLY', 30, 9.99),
    ('QUARTERLY', 90, 24.99),
    ('ANNUAL', 365, 79.99);

INSERT INTO customer (email, name)
VALUES ('john.doe@example.com', 'John Doe');
