USE evdealer_db;
GO

-- ====== PRICE_PROGRAM TABLE ======
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.price_program') AND type = 'U')
BEGIN
    CREATE TABLE dbo.price_program (
        id                      BIGINT IDENTITY(1,1) PRIMARY KEY,
        dealer_hierarchy_id     BIGINT NULL,
        start_day               DATETIMEOFFSET NOT NULL,
        end_day                 DATETIMEOFFSET NOT NULL,
        created_by              NVARCHAR(100) NULL,
        last_modified_on        DATETIMEOFFSET NULL,
        created_on              DATETIMEOFFSET DEFAULT GETDATE(),
        last_modified_by        NVARCHAR(100) NULL

        CONSTRAINT fk_price_program_dealer_hierarchy
                    FOREIGN KEY (dealer_hierarchy_id)
                    REFERENCES dbo.dealer_hierarchy(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
    );
END;
GO

-- ====== PROGRAM_DETAIL TABLE ======
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.program_detail') AND type = 'U')
BEGIN
    CREATE TABLE dbo.program_detail (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        car_id BIGINT NOT NULL,
        price_program_id BIGINT NOT NULL,
        min_price DECIMAL(15,2) NULL,
        suggested_price DECIMAL(15,2) NULL,
        max_price DECIMAL(15,2) NULL,

        CONSTRAINT fk_price_program_id
            FOREIGN KEY (price_program_id)
            REFERENCES dbo.price_program(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
END;
GO

-- Insert sample Price Programs
IF NOT EXISTS (SELECT 1 FROM dbo.price_program)
BEGIN
    INSERT INTO dbo.price_program (dealer_hierarchy_id, start_day, end_day, created_by)
    VALUES
    (1, '2025-11-01', '2025-12-31', 'EVD Administrator'),
    (2, '2025-12-01', '2026-01-31', 'EVD Staff'),
    (3, '2025-10-15', '2025-11-30', 'EVD Administrator');
    END;
GO

-- Insert Program Details for each program
IF NOT EXISTS (SELECT 1 FROM dbo.program_detail)
BEGIN
    INSERT INTO dbo.program_detail (car_id, price_program_id, min_price, suggested_price, max_price)
    VALUES
    -- For Program 1
    (4, 1, 30000.00, 32000.00, 35000.00),
    (4, 1, 28000.00, 29500.00, 32000.00),
    -- For Program 2
    (7, 2, 35000.00, 37000.00, 40000.00),
    (9, 2, 42000.00, 45000.00, 48000.00),
    -- For Program 3
    (6, 3, 31000.00, 34000.00, 36000.00);
END;
GO
