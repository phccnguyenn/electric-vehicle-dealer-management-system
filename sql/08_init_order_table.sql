USE evdealer_db;
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders') AND type = 'U')
BEGIN
    CREATE TABLE dbo.orders (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        car_id BIGINT NOT NULL,
        staff_id BIGINT NULL,
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
END;
GO

-- Insert sample data into dbo.orders WITHOUT CONSTRAINT
ALTER TABLE dbo.orders NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.orders)
BEGIN
    INSERT INTO dbo.orders (
        car_id,
        staff_id,
        customer_id,
        total_amount,
        amount_paid,
        quotation_url,
        contract_url,
        status,
        payment_status,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    (1,  4,  5, 75000.00,  5000.00,  N'https://files.evdealer.com/q/quotation_1001.pdf', N'https://files.evdealer.com/c/contract_1001.pdf', N'APPROVED',      N'PENDING', N'staff01', SYSUTCDATETIME(), N'staff01', SYSUTCDATETIME()),
    (2,  6,  6, 82000.00, 82000.00,  N'https://files.evdealer.com/q/quotation_1002.pdf', N'https://files.evdealer.com/c/contract_1002.pdf', N'REJECTED',    N'DEPOSIT_PAID',            N'staff02', SYSUTCDATETIME(), N'staff02', SYSUTCDATETIME()),
    (3,  4,  4, 65000.00,     0.00,  N'https://files.evdealer.com/q/quotation_1003.pdf', N'https://files.evdealer.com/c/contract_1003.pdf', N'DELIVERED',    N'PARTIAL',          N'staff03', SYSUTCDATETIME(), N'staff03', SYSUTCDATETIME()),
    (4,  8,  8, 72000.00, 72000.00,  N'https://files.evdealer.com/q/quotation_1004.pdf', N'https://files.evdealer.com/c/contract_1004.pdf', N'DELIVERED',    N'DEPOSIT_PAID',            N'staff04', SYSUTCDATETIME(), N'staff04', SYSUTCDATETIME()),
    (6,  11, 10, 88000.00,  5000.00,  N'https://files.evdealer.com/q/quotation_1006.pdf', N'https://files.evdealer.com/c/contract_1006.pdf', N'APPROVED',      N'PARTIAL',  N'staff06', SYSUTCDATETIME(), N'staff06', SYSUTCDATETIME()),
    (7,  8, 11, 99000.00, 99000.00,  N'https://files.evdealer.com/q/quotation_1007.pdf', N'https://files.evdealer.com/c/contract_1007.pdf', N'COMPLETED',    N'DEPOSIT_PAID',            N'staff07', SYSUTCDATETIME(), N'staff07', SYSUTCDATETIME()),
    (5,  6,  9, 95000.00, 25000.00,  N'https://files.evdealer.com/q/quotation_1005.pdf', N'https://files.evdealer.com/c/contract_1005.pdf', N'IN_DELIVERY',  N'PARTIAL',  N'staff05', SYSUTCDATETIME(), N'staff05', SYSUTCDATETIME());
END;
GO
ALTER TABLE dbo.orders CHECK CONSTRAINT ALL;

