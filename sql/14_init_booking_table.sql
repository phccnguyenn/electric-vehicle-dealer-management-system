USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.booking') AND type = 'U')
BEGIN
    CREATE TABLE dbo.booking (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        slot_id BIGINT NOT NULL,
        customer_phone NVARCHAR(20) NOT NULL,
        -- add more fields if your Booking entity has them, e.g.:
        -- customer_name NVARCHAR(100),
        -- booking_time DATETIMEOFFSET DEFAULT SYSDATETIMEOFFSET(),

        CONSTRAINT fk_booking_slot
            FOREIGN KEY (slot_id)
            REFERENCES dbo.slot(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
END;
GO