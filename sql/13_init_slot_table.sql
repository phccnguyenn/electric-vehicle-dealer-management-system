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