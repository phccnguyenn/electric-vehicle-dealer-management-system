USE evdealer_db;

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
    CONSTRAINT CK_battery_chemistry
        CHECK (chemistry_type IN ('NCA','NCM','LFP','SolidState'))
);
GO


/* ============== colors ============================= */
IF OBJECT_ID('dbo.color', 'U') IS NOT NULL DROP TABLE dbo.colors;
GO
CREATE TABLE dbo.color (
    color_id    BIGINT IDENTITY(1,1) PRIMARY KEY,
    color_name  VARCHAR(100) NOT NULL UNIQUE,
    color_hex   VARCHAR(7)   NOT NULL UNIQUE,
    extra_cost  FLOAT NULL
);
GO

/* ============== dimensions ========================= */
IF OBJECT_ID('dbo.dimension', 'U') IS NOT NULL DROP TABLE dbo.dimensions;
GO
CREATE TABLE dbo.dimension (
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

/* ==============  motor ============================== */
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
    voltage_range_v FLOAT NULL
);
GO

/* ============== performances ======================= */
IF OBJECT_ID('dbo.performance', 'U') IS NOT NULL DROP TABLE dbo.performances;
GO
CREATE TABLE dbo.performance (
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
CREATE INDEX IX_performances_battery_id ON dbo.performances(battery_id);
CREATE INDEX IX_performances_motor_id   ON dbo.performances(motor_id);
GO

/* ============== category =========================== */
IF OBJECT_ID('dbo.category', 'U') IS NOT NULL DROP TABLE dbo.category;
GO
CREATE TABLE dbo.category (
    category_id  BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_type     VARCHAR(30) NULL
);
GO

/* ============== Table: car ================================ */
IF OBJECT_ID('dbo.car', 'U') IS NOT NULL DROP TABLE dbo.car;
GO
CREATE TABLE dbo.car (
    id              BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_name        VARCHAR(255) NULL,
    price           DECIMAL(19,2) NULL,
    drive_type      VARCHAR(10) NULL,
    seat_number     INT NULL,
    year            INT NULL,
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

----------------------------------------------INSERT-----------------------------------------
-- ===== colors =====
INSERT INTO dbo.color (color_name, color_hex, extra_cost)
VALUES
 ('White Pearl', '#FFFFFF', 0),
 ('Deep Ocean Blue', '#003366', 150),
 ('Sunset Red', '#C8102E', 200),
 ('Midnight Black', '#000000', 100);

-- ===== category =====
INSERT INTO dbo.category (car_type)
VALUES ('ECO'), ('PLUS'), ('PREMIUM');

-- ===== battery =====
INSERT INTO dbo.battery
(chemistry_type, age, charge_time_sec, usage_duration_sec, weight_kg, voltage_v, capacity_kwh, cycle_life)
VALUES
 ('LFP', 1, 1800, 7200, 350, 400, 75, 3000),
 ('NCM', 2, 2400, 10800, 380, 450, 82, 2500),
 ('SolidState', 1, 1500, 14400, 320, 500, 100, 4000);

-- ===== motor =====
INSERT INTO dbo.motor
(motor_type, manufacturer, power_kw, torque_nm, max_rpm, cooling_type, voltage_range_v)
VALUES
 ('PMSM', 'VF-ECO-001', 150, 300, 9000, 'LIQUID', 450),
 ('PMSM', 'VF-PLUS-002', 200, 350, 10000, 'LIQUID', 500),
 ('PMSM', 'VF-PREM-003', 250, 420, 11000, 'LIQUID', 550);

-- ===== performances =====
INSERT INTO dbo.performance
(range_miles, acceleration_sec, top_speed_mph, towing_lbs, battery_id, motor_id)
VALUES
 (250, 8.5, 110, 2000, 1, 1),
 (310, 7.2, 125, 2500, 2, 2),
 (400, 6.0, 140, 3000, 3, 3);

-- ===== dimensions =====
INSERT INTO dbo.dimension
(weight_lbs, ground_clearance_in, width_folded_in, width_extended_in, height_in, length_mm, length_in, wheels_size_cm)
VALUES
 (4200, 6.5, 70, 78, 64, 4750, 187.0, 48),
 (4400, 7.0, 72, 80, 66, 4850, 191.0, 50),
 (4600, 7.5, 74, 82, 68, 4950, 195.0, 52);

-- ===== car =====
INSERT INTO dbo.car
(car_name, price, drive_type, seat_number, year, dimension_id, performance_id, color_id, category_id)
VALUES
 ('VinFast VF e34', 39000, 'FWD', 5, 2023, 1, 1, 1, 1),
 ('VinFast VF 8', 52000, 'AWD', 5, 2024, 2, 2, 2, 2),
 ('VinFast VF 9', 68000, 'AWD', 7, 2024, 3, 3, 3, 3);

-- ===== car_image =====
INSERT INTO dbo.car_image (car_id, file_name, file_path, file_url)
VALUES
 (1, 'vf-e34-front.jpg', '/images/vf_e34/front.jpg', 'https://cdn.ev-dms.vn/vf_e34/front.jpg'),
 (1, 'vf-e34-side.jpg', '/images/vf_e34/side.jpg', 'https://cdn.ev-dms.vn/vf_e34/side.jpg'),
 (2, 'vf8-front.jpg', '/images/vf8/front.jpg', 'https://cdn.ev-dms.vn/vf8/front.jpg'),
 (2, 'vf8-interior.jpg', '/images/vf8/interior.jpg', 'https://cdn.ev-dms.vn/vf8/interior.jpg'),
 (3, 'vf9-front.jpg', '/images/vf9/front.jpg', 'https://cdn.ev-dms.vn/vf9/front.jpg'),
 (3, 'vf9-side.jpg', '/images/vf9/side.jpg', 'https://cdn.ev-dms.vn/vf9/side.jpg');
GO

