USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders') AND type = 'U')
BEGIN
CREATE TABLE dbo.orders (
            id BIGINT IDENTITY(1,1) PRIMARY KEY,
            --order_code NVARCHAR(50) NOT NULL UNIQUE,
            car_id BIGINT NOT NULL,
            staff_id BIGINT NOT NULL,
            customer_id BIGINT NOT NULL,
            total_amount DECIMAL(20,2) NOT NULL,
            amount_paid DECIMAL(20,2) DEFAULT 0,
            quotation_url NVARCHAR(255) NULL,
            contract_url NVARCHAR(255) NULL,
            status NVARCHAR(50) NOT NULL,
            payment_status NVARCHAR(50) NOT NULL,
            created_by NVARCHAR(100) NULL,
            created_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
            last_modified_by NVARCHAR(100) NULL,
            last_modified_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
            CONSTRAINT FK_orders_car FOREIGN KEY (car_id) REFERENCES car(id),
            CONSTRAINT FK_orders_staff FOREIGN KEY (staff_id) REFERENCES users(user_id),
            CONSTRAINT FK_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);
END
GO