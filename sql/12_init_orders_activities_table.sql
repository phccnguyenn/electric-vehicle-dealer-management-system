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