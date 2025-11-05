USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.payments') AND type = 'U')
BEGIN
CREATE TABLE payments (
           id BIGINT IDENTITY(1,1) PRIMARY KEY,
           order_id BIGINT NOT NULL,
           amount DECIMAL(20,2) NOT NULL,
           paid_at DATETIMEOFFSET NOT NULL,
           type NVARCHAR(50) NOT NULL,
           created_by NVARCHAR(100) NULL,
           created_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
           last_modified_by NVARCHAR(100) NULL,
           last_modified_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
           CONSTRAINT FK_payments_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.payments)
BEGIN
    INSERT INTO dbo.payments (
        order_id,
        amount,
        paid_at,
        type,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    -- Order 1: 75000 total, 5000 paid
    (1, 5000.00, DATEADD(DAY, -20, SYSUTCDATETIME()), N'IN_FULL', N'staff01', SYSUTCDATETIME(), N'staff01', SYSUTCDATETIME()),

    -- Order 2: Fully paid
    (2, 82000.00, DATEADD(DAY, -15, SYSUTCDATETIME()), N'IN_FULL', N'staff02', SYSUTCDATETIME(), N'staff02', SYSUTCDATETIME()),

    -- Order 3: 0 paid
    -- No payments yet

    -- Order 4: Fully paid
    (4, 72000.00, DATEADD(DAY, -10, SYSUTCDATETIME()), N'INSTALLMENT', N'staff04', SYSUTCDATETIME(), N'staff04', SYSUTCDATETIME()),

    -- Order 5: 95000 total, 25000 paid (installments)
    (5, 10000.00, DATEADD(DAY, -12, SYSUTCDATETIME()), N'INSTALLMENT', N'staff05', SYSUTCDATETIME(), N'staff05', SYSUTCDATETIME()),
    (5, 15000.00, DATEADD(DAY, -5, SYSUTCDATETIME()), N'INSTALLMENT', N'staff05', SYSUTCDATETIME(), N'staff05', SYSUTCDATETIME()),

    -- Order 6: 88000 total, 5000 paid
    (6, 5000.00, DATEADD(DAY, -3, SYSUTCDATETIME()), N'IN_FULL', N'staff06', SYSUTCDATETIME(), N'staff06', SYSUTCDATETIME()),

    -- Order 7: Fully paid in two installments
    (7, 40000.00, DATEADD(DAY, -30, SYSUTCDATETIME()), N'IN_FULL', N'staff07', SYSUTCDATETIME(), N'staff07', SYSUTCDATETIME()),
    (7, 59000.00, DATEADD(DAY, -15, SYSUTCDATETIME()), N'INSTALLMENT', N'staff07', SYSUTCDATETIME(), N'staff07', SYSUTCDATETIME());

END;
GO
