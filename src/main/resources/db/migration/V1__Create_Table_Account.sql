CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    due_date DATE NOT NULL,
    payment_date DATE,
    "value" DECIMAL(10,2) NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    situation VARCHAR(50) NOT NULL
)