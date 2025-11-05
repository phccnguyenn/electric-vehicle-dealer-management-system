USE evdealer_db

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.inventory') AND type = 'U')
BEGIN
    CREATE TABLE dbo.inventory (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        dealer_id           BIGINT NOT NULL,
        car_id              BIGINT NOT NULL,
        quantity            INT NOT NULL,
        reserved_quantity   INT NOT NULL DEFAULT 0,
        available_quantity  INT NOT NULL,
        inventory_status    VARCHAR(50) NOT NULL,
        notes               VARCHAR(500),
        created_by          NVARCHAR(100) NULL,
        created_on          DATETIMEOFFSET NULL,
        last_modified_by    NVARCHAR(100) NULL,
        last_modified_on    DATETIMEOFFSET NULL,

        CONSTRAINT fk_inventories_dealer
            FOREIGN KEY (dealer_id) REFERENCES dbo.users(user_id)
            ON DELETE CASCADE,

        CONSTRAINT fk_inventories_car
            FOREIGN KEY (car_id) REFERENCES dbo.car(id)
            ON DELETE CASCADE
    );
END;

ALTER TABLE dbo.inventory NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.inventory)
BEGIN
    INSERT INTO dbo.inventory (
            dealer_id,
            car_id,
            quantity,
            reserved_quantity,
            available_quantity,
            inventory_status,
            notes,
            created_by,
            created_on,
            last_modified_by,
            last_modified_on
        )
        VALUES
        -- Dealer 1 inventories
        (3, 1, 10, 2, 8, 'IN_STOCK', 'Initial stock for dealer 1', N'staff01', SYSUTCDATETIME(), N'staff01', SYSUTCDATETIME()),
        (3, 2, 5, 1, 4, 'IN_STOCK', 'Limited stock', N'staff01', SYSUTCDATETIME(), N'staff01', SYSUTCDATETIME()),

        -- Dealer 2 inventories
        (5, 3, 8, 0, 8, 'IN_STOCK', 'New arrivals', N'staff02', SYSUTCDATETIME(), N'staff02', SYSUTCDATETIME()),
        (5, 4, 12, 3, 9, 'IN_STOCK', 'Reserved cars for upcoming orders', N'staff02', SYSUTCDATETIME(), N'staff02', SYSUTCDATETIME()),

        -- Dealer 3 inventories
        (7, 5, 7, 2, 5, 'IN_STOCK', 'Regular stock', N'staff03', SYSUTCDATETIME(), N'staff03', SYSUTCDATETIME()),
        (7, 6, 4, 0, 4, 'IN_STOCK', 'Few units available', N'staff03', SYSUTCDATETIME(), N'staff03', SYSUTCDATETIME()),

        -- Dealer 4 inventories
        (10, 7, 15, 5, 10, 'IN_STOCK', 'High demand model', N'staff04', SYSUTCDATETIME(), N'staff04', SYSUTCDATETIME()),
        (10, 1, 3, 1, 2, 'IN_STOCK', 'Low stock', N'staff04', SYSUTCDATETIME(), N'staff04', SYSUTCDATETIME());

END;
GO
ALTER TABLE dbo.inventory CHECK CONSTRAINT ALL;
