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
END
GO