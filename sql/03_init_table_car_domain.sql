USE evdealer_db;

-- /* ============== battery ============================ */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.battery') AND type = 'U')
BEGIN
CREATE TABLE dbo.battery (
    id                      BIGINT IDENTITY(1,1) PRIMARY KEY,
    chemistry_type          VARCHAR(20) NULL,
    age                     INT NOT NULL,
    charge_time_hour        INT NOT NULL,
    usage_duration_hour     INT NULL,
    weight_kg               FLOAT NULL,
    voltage_v               FLOAT NULL,
    capacity_kwh            FLOAT NULL,
    cycle_life              INT NULL,

    created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

    CONSTRAINT CK_battery_chemistry
        CHECK (chemistry_type IN ('NCA','NCM','LFP','SOLID_STATE'))
);
END;

-- /* ============== color ============================= */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.color') AND type = 'U')
BEGIN
CREATE TABLE dbo.color (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    color_name  VARCHAR(100) NOT NULL UNIQUE,
    color_hex_code   VARCHAR(7)   NOT NULL UNIQUE,
    extra_cost  FLOAT NULL,

	created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET()

);
END;

-- /* ============== dimension ========================= */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.dimension') AND type = 'U')
BEGIN
CREATE TABLE dbo.dimension (
    id                   BIGINT IDENTITY(1,1) PRIMARY KEY,
	seat_number          INT NULL,
    weight_lbs           FLOAT NULL,
    ground_clearance_in  FLOAT NULL,
    width_folded_in      FLOAT NULL,
    width_extended_in    FLOAT NULL,
    height_in            FLOAT NULL,
    length_mm            FLOAT NULL,
    length_in            FLOAT NULL,
    wheels_size_cm       FLOAT NULL

);
END;

--/* ==============  motor ============================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.motor') AND type = 'U')
BEGIN
CREATE TABLE dbo.motor (
    id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
    motor_type          VARCHAR(30) NULL,
    serial_number       VARCHAR(255) NULL UNIQUE,
    power_kw            FLOAT NULL,
    torque_nm           FLOAT NULL,
    max_rpm             INT NULL,
    cooling_type        VARCHAR(30) NULL,
    voltage_range_v     FLOAT NULL,

    created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

	 CONSTRAINT CK_motor_type
        CHECK (motor_type IN ('AC_INDUCTION', 'PERMANENT_MAGNET', 'DC_BRUSHED', 'DC_BRUSHLESS', 'SYNCHRONOUS'))


);
END;

-- /* ============== performances ======================= */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.performance') AND type = 'U')
BEGIN
CREATE TABLE dbo.performance (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
	battery_id      BIGINT NULL,
    motor_id        BIGINT NULL,

    range_miles         FLOAT NULL,
    acceleration_sec    FLOAT NULL,
    top_speed_mph       FLOAT NULL,
    towing_lbs          FLOAT NULL,


    CONSTRAINT FK_perf_battery
        FOREIGN KEY (battery_id) REFERENCES dbo.battery(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,
    CONSTRAINT FK_perf_motor
        FOREIGN KEY (motor_id) REFERENCES dbo.motor(id)
        ON UPDATE NO ACTION ON DELETE SET NULL
);
END;


-- /* ============== category =========================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.category') AND type = 'U')
BEGIN
CREATE TABLE dbo.category (
    id                     BIGINT IDENTITY(1,1) PRIMARY KEY,
    category_name          VARCHAR(30) NULL ,

	created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

);
END;

-- /* ============== car ================================ */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car') AND type = 'U')
BEGIN
CREATE TABLE dbo.car (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
	dimension_id    BIGINT NULL,
    performance_id  BIGINT NULL,
    color_id        BIGINT NULL,
    category_id     BIGINT NULL,

    car_name         VARCHAR(255) NULL,
    price            DECIMAL(19,2) NULL,
    drive_type       VARCHAR(10) NULL,
    [year]           INT NULL,

	created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),


    CONSTRAINT CK_car_drive_type
        CHECK (drive_type IN ('FWD','RWD','AWD','FOUR_WD')),

    CONSTRAINT FK_car_dimension
        FOREIGN KEY (dimension_id) REFERENCES dbo.dimension(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_performance
        FOREIGN KEY (performance_id) REFERENCES dbo.performance(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_color
        FOREIGN KEY (color_id) REFERENCES dbo.color(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_category
        FOREIGN KEY (category_id) REFERENCES dbo.category(id)
        ON UPDATE NO ACTION ON DELETE SET NULL
);
END;


-- /* ==============  car_image ========================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_image') AND type = 'U')
BEGIN
CREATE TABLE dbo.car_image (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_id      BIGINT NOT NULL,

    file_name   VARCHAR(255) NULL,
    file_path   VARCHAR(1024) NULL,
    file_url    VARCHAR(1024) NULL,

    CONSTRAINT FK_car_image_car
        FOREIGN KEY (car_id) REFERENCES dbo.car(id)
        ON UPDATE NO ACTION ON DELETE CASCADE
);
END;
