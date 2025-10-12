USE evdealer_db

-- ============== battery ============================
IF OBJECT_ID('dbo.battery', 'U') IS NOT NULL DROP TABLE dbo.battery;
GO
CREATE TABLE dbo.battery (
    battery_id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    chemistry_type      VARCHAR(20) NULL,
    age                 INT NOT NULL,
    charge_time_sec     INT NOT NULL,
    usage_duration_sec  INT NULL,
    weight_kg           FLOAT NULL,
    voltage_v           FLOAT NULL,
    capacity_kwh        FLOAT NULL,
    cycle_life          INT NULL,
	created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

    CONSTRAINT CK_battery_chemistry
        CHECK (chemistry_type IN ('NCA','NCM','LFP','SolidState'))
);
GO

-- ============== colors =============================
IF OBJECT_ID('dbo.colors', 'U') IS NOT NULL DROP TABLE dbo.colors;
GO
CREATE TABLE dbo.colors (
    color_id    BIGINT IDENTITY(1,1) PRIMARY KEY,
    color_name  VARCHAR(100) NOT NULL UNIQUE,
    color_hex   VARCHAR(7)   NOT NULL UNIQUE,
    extra_cost  FLOAT NULL
);
GO

-- ============== dimensions =========================
IF OBJECT_ID('dbo.dimensions', 'U') IS NOT NULL DROP TABLE dbo.dimensions;
GO
CREATE TABLE dbo.dimensions (
    dimension_id         BIGINT IDENTITY(1,1) PRIMARY KEY,
    weight_lbs           FLOAT NULL,
    ground_clearance_in  FLOAT NULL,
    width_folded_in      FLOAT NULL,
    width_extended_in    FLOAT NULL,
    height_in            FLOAT NULL,
    length_mm            FLOAT NULL,
    length_in            FLOAT NULL,
    wheels_size_cm       FLOAT NULL

);
GO

-- /* ==============  motor ============================== */
IF OBJECT_ID('dbo.motor', 'U') IS NOT NULL DROP TABLE dbo.motor;
GO
CREATE TABLE dbo.motor (
    motor_id        BIGINT IDENTITY(1,1) PRIMARY KEY,
    motor_type      VARCHAR(30) NULL,
    manufacturer    VARCHAR(255) NULL UNIQUE,
    power_kw        FLOAT NULL,
    torque_nm       FLOAT NULL,
    max_rpm         INT NULL,
    cooling_type    VARCHAR(30) NULL,
    voltage_range_v FLOAT NULL,
	created_by            NVARCHAR(100)  NULL,
    created_on            DATETIMEOFFSET NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NULL,
    last_modified_on      DATETIMEOFFSET NULL DEFAULT SYSDATETIMEOFFSET(),
	 CONSTRAINT CK_motor_type
        CHECK (motor_type IN ('AC_INDUCTION', 'PERMANENT_MAGNET', 'DC_BRUSHED', 'DC_BRUSHLESS', 'SYNCHRONOUS'))
);
GO

-- /* ============== performances ======================= */
IF OBJECT_ID('dbo.performances', 'U') IS NOT NULL DROP TABLE dbo.performances;
GO
CREATE TABLE dbo.performances (
    performance_id  BIGINT IDENTITY(1,1) PRIMARY KEY,
    range_miles     FLOAT NULL,
    acceleration_sec FLOAT NULL,
    top_speed_mph   FLOAT NULL,
    towing_lbs      FLOAT NULL,
    battery_id      BIGINT NULL,
    motor_id        BIGINT NULL,
    CONSTRAINT FK_perf_battery
        FOREIGN KEY (battery_id) REFERENCES dbo.battery(battery_id)
        ON UPDATE NO ACTION ON DELETE SET NULL,
    CONSTRAINT FK_perf_motor
        FOREIGN KEY (motor_id) REFERENCES dbo.motor(motor_id)
        ON UPDATE NO ACTION ON DELETE SET NULL
);
GO


-- /* ============== category =========================== */
IF OBJECT_ID('dbo.category', 'U') IS NOT NULL DROP TABLE dbo.category;
GO
CREATE TABLE dbo.category (
    category_id  BIGINT IDENTITY(1,1) PRIMARY KEY,
    category_name     VARCHAR(30) NULL ,
	created_by            NVARCHAR(100) NULL,
    created_on            DATETIMEOFFSET NULL,
    last_modified_by      NVARCHAR(100) NULL,
    last_modified_on      DATETIMEOFFSET NULL
);
GO

-- /* ============== Table: car ================================ */
IF OBJECT_ID('dbo.car', 'U') IS NOT NULL DROP TABLE dbo.car;
GO
CREATE TABLE dbo.car (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_name        VARCHAR(255) NULL,
    price           DECIMAL(19,2) NULL,
    drive_type      VARCHAR(10) NULL,
    seat_number     INT NULL,
    [year]           INT NULL,
    dimension_id    BIGINT NULL,
    performance_id  BIGINT NULL,
    color_id        BIGINT NULL,
    category_id     BIGINT NULL,

    created_at      DATETIME2(3) DEFAULT SYSUTCDATETIME() NOT NULL,
    updated_at      DATETIME2(3) NULL,

    CONSTRAINT CK_car_drive_type
        CHECK (drive_type IN ('FWD','RWD','AWD')),

    CONSTRAINT FK_car_dimension
        FOREIGN KEY (dimension_id) REFERENCES dbo.dimensions(dimension_id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_performance
        FOREIGN KEY (performance_id) REFERENCES dbo.performances(performance_id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_color
        FOREIGN KEY (color_id) REFERENCES dbo.colors(color_id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_category
        FOREIGN KEY (category_id) REFERENCES dbo.category(category_id)
        ON UPDATE NO ACTION ON DELETE SET NULL
);
GO
CREATE INDEX IX_car_color_id     ON dbo.car(color_id);
CREATE INDEX IX_car_category_id  ON dbo.car(category_id);
CREATE INDEX IX_car_dimension_id ON dbo.car(dimension_id);
CREATE INDEX IX_car_performance_id ON dbo.car(performance_id);
GO

/* ==============  car_image ========================== */
IF OBJECT_ID('dbo.car_image', 'U') IS NOT NULL DROP TABLE dbo.car_image;
GO
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
GO
CREATE INDEX IX_car_image_car_id ON dbo.car_image(car_id);
GO






