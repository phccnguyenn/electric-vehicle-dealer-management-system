IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.payments') AND type = 'U')
BEGIN
CREATE TABLE payments (
           id BIGINT IDENTITY(1,1) PRIMARY KEY,
           order_id BIGINT NOT NULL,
           amount DECIMAL(20,2) NOT NULL,
           paid_at DATETIMEOFFSET NOT NULL,
           type NVARCHAR(50) NOT NULL,
           CONSTRAINT FK_payments_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
END
GO