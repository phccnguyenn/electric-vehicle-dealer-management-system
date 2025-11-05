USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders_activities') AND type = 'U')
BEGIN
CREATE TABLE dbo.orders_activities (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        order_id BIGINT NOT NULL,
        status NVARCHAR(50) NOT NULL,
        changed_at DATETIME2 NOT NULL,

        CONSTRAINT FK_orders_activities_order
        FOREIGN KEY (order_id) REFERENCES dbo.orders(id)
        ON DELETE CASCADE
);
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.orders_activities)
BEGIN
    INSERT INTO dbo.orders_activities (order_id, status, changed_at)
        VALUES
        -- Order 1 (APPROVED)
        (1, 'CREATED', DATEADD(DAY, -5, SYSUTCDATETIME())),
        (1, 'APPROVED', DATEADD(DAY, -2, SYSUTCDATETIME())),

        -- Order 2 (REJECTED)
        (2, 'CREATED', DATEADD(DAY, -10, SYSUTCDATETIME())),
        (2, 'APPROVED', DATEADD(DAY, -7, SYSUTCDATETIME())),
        (2, 'REJECTED', DATEADD(DAY, -1, SYSUTCDATETIME())),

        -- Order 3 (DELIVERED)
        (3, 'CREATED', DATEADD(DAY, -15, SYSUTCDATETIME())),
        (3, 'APPROVED', DATEADD(DAY, -12, SYSUTCDATETIME())),
        (3, 'IN_DELIVERY', DATEADD(DAY, -5, SYSUTCDATETIME())),
        (3, 'DELIVERED', DATEADD(DAY, -1, SYSUTCDATETIME())),

        -- Order 4 (DELIVERED)
        (4, 'CREATED', DATEADD(DAY, -20, SYSUTCDATETIME())),
        (4, 'APPROVED', DATEADD(DAY, -18, SYSUTCDATETIME())),
        (4, 'IN_DELIVERY', DATEADD(DAY, -10, SYSUTCDATETIME())),
        (4, 'DELIVERED', DATEADD(DAY, -2, SYSUTCDATETIME())),

        -- Order 5 (IN_DELIVERY)
        (5, 'CREATED', DATEADD(DAY, -7, SYSUTCDATETIME())),
        (5, 'APPROVED', DATEADD(DAY, -5, SYSUTCDATETIME())),
        (5, 'IN_DELIVERY', DATEADD(DAY, -1, SYSUTCDATETIME())),

        -- Order 6 (APPROVED)
        (6, 'CREATED', DATEADD(DAY, -4, SYSUTCDATETIME())),
        (6, 'APPROVED', DATEADD(DAY, -2, SYSUTCDATETIME())),

        -- Order 7 (COMPLETED)
        (7, 'CREATED', DATEADD(DAY, -12, SYSUTCDATETIME())),
        (7, 'APPROVED', DATEADD(DAY, -10, SYSUTCDATETIME())),
        (7, 'IN_DELIVERY', DATEADD(DAY, -5, SYSUTCDATETIME())),
        (7, 'DELIVERED', DATEADD(DAY, -2, SYSUTCDATETIME())),
        (7, 'COMPLETED', DATEADD(DAY, 0, SYSUTCDATETIME()));
END;
GO