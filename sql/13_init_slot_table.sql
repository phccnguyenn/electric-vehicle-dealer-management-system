USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.slot') AND type = 'U')
BEGIN
    CREATE TABLE dbo.slot (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        dealer_id           BIGINT NOT NULL,
        car_id              BIGINT NOT NULL,
        start_time          DATETIMEOFFSET NOT NULL,
        end_time            DATETIMEOFFSET NOT NULL,
        amount              INT NOT NULL,

        CONSTRAINT fk_slot_dealer
            FOREIGN KEY (dealer_id)
            REFERENCES dbo.users(user_id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,

        CONSTRAINT fk_slot_car
            FOREIGN KEY (car_id)
            REFERENCES dbo.car(id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
    );

END;
GO


ALTER TABLE dbo.slot NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.slot)
BEGIN
    INSERT INTO dbo.slot (dealer_id, car_id, start_time, end_time, amount) VALUES
    (3, 1, '2025-11-06T08:00:00+07:00', '2025-11-06T09:00:00+07:00', 3),
    (10, 2, '2025-11-06T09:30:00+07:00', '2025-11-06T10:30:00+07:00', 2),
    (5, 6, '2025-11-06T13:00:00+07:00', '2025-11-06T14:00:00+07:00', 4),
    (5, 3, '2025-11-07T10:00:00+07:00', '2025-11-07T11:00:00+07:00', 5),
    (7, 5, '2025-11-07T15:00:00+07:00', '2025-11-07T16:00:00+07:00', 1),
    (3, 1, '2025-11-08T08:30:00+07:00', '2025-11-08T09:30:00+07:00', 2);
END;
GO
ALTER TABLE dbo.slot CHECK CONSTRAINT ALL;