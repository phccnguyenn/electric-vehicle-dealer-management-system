USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.dealer_hierarchy') AND type = 'U')
BEGIN
    CREATE TABLE dbo.dealer_hierarchy (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        dealer_id           BIGINT NULL,
        dealer_level        INT NOT NULL,

        CONSTRAINT fk_dealer_id
            FOREIGN KEY (dealer_id)
            REFERENCES dbo.users(user_id)
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.dealer_hierarchy)
BEGIN
    INSERT INTO dbo.dealer_hierarchy (dealer_level)
    VALUES (1), (2), (3);
END;
GO