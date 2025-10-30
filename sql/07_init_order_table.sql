IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders') AND type = 'U')
BEGIN
CREATE TABLE orders (
            id BIGINT IDENTITY(1,1) PRIMARY KEY,
            order_code NVARCHAR(50) NOT NULL UNIQUE,
            car_id BIGINT NOT NULL,
            staff_id BIGINT NOT NULL,
            customer_id BIGINT NOT NULL,
            total_amount DECIMAL(20,2) NOT NULL,
            amount_paid DECIMAL(20,2) DEFAULT 0,
            quotation_url NVARCHAR(255) NULL,
            contract_url NVARCHAR(255) NULL,
            status NVARCHAR(50) NOT NULL,
            payment_status NVARCHAR(50) NOT NULL,
            CONSTRAINT FK_orders_car FOREIGN KEY (car_id) REFERENCES cars(car_id),
            CONSTRAINT FK_orders_staff FOREIGN KEY (staff_id) REFERENCES users(user_id),
            CONSTRAINT FK_orders_customer FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);
END
GO