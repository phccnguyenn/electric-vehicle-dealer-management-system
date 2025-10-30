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