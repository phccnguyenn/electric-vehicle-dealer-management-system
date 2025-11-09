IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'evdealer_db')
BEGIN
    CREATE DATABASE evdealer_db;
END;
GO

USE evdealer_db

--========================== users ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.users') AND type = 'U')
BEGIN
    CREATE TABLE dbo.users (
        user_id             BIGINT IDENTITY(1,1) PRIMARY KEY,
        parent_id           BIGINT NULL,
        username            VARCHAR(100)  NOT NULL UNIQUE,
        hashed_password     VARCHAR(255)  NOT NULL,
        full_name           NVARCHAR(150) NOT NULL,
        email               VARCHAR(150)  NOT NULL UNIQUE,
        phone               VARCHAR(20)   NOT NULL UNIQUE,
        is_active           BIT           NOT NULL DEFAULT 1,
        role                VARCHAR(50)   NULL,
        city                NVARCHAR(255) NULL,
        level               INT,
        created_by          NVARCHAR(100) NULL,
        created_on          DATETIMEOFFSET NULL,
        last_modified_by    NVARCHAR(100) NULL,
        last_modified_on    DATETIMEOFFSET NULL
    );
END;



--========================== tokens ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.tokens') AND type = 'U')
BEGIN
    CREATE TABLE dbo.tokens (
        id           BIGINT IDENTITY(1,1) PRIMARY KEY,
        token        VARCHAR(500) NOT NULL UNIQUE,
        token_type   VARCHAR(20)  NOT NULL DEFAULT 'BEARER',
        revoked      BIT          NOT NULL DEFAULT 0,
        expired      BIT          NOT NULL DEFAULT 0,
        user_id      BIGINT       NOT NULL,

        CONSTRAINT FK_token_user FOREIGN KEY (user_id)
            REFERENCES dbo.users(user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
END;



--========================== customer ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.customer') AND type = 'U')
BEGIN
    CREATE TABLE dbo.customer (
        id                  BIGINT IDENTITY(1, 1) PRIMARY KEY,
        dealer_id           BIGINT NOT NULL,
        full_name           NVARCHAR(150) NOT NULL,
        email               VARCHAR(150) NULL,
        phone               VARCHAR(20) NOT NULL UNIQUE,
        address             NVARCHAR(255) NOT NULL,
        created_by          NVARCHAR(100) NULL,
        created_on          DATETIMEOFFSET NULL,
        last_modified_by    NVARCHAR(100) NULL,
        last_modified_on    DATETIMEOFFSET NULL
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

-- /* ============== dimension =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.dimension)
BEGIN
    INSERT INTO dbo.dimension (seat_number, weight_lbs, ground_clearance_in, width_folded_in, width_extended_in, height_in, length_mm, length_in, wheels_size_cm)
    VALUES
    -- Sedan LX
    (5, 3200, 5.5, 70, 72, 56, 4700, 185, 60),
    -- SUV GX
    (7, 4500, 8.0, 75, 78, 66, 5000, 197, 65),
    -- Hatchback ZX
    (5, 2800, 5.0, 68, 70, 55, 4300, 169, 58),
    -- Electric EV1
    (5, 3600, 5.5, 72, 74, 57, 4600, 181, 62),
    -- Hybrid HVX
    (5, 3400, 5.5, 71, 73, 56, 4550, 179, 61),
    -- Truck TX
    (2, 6000, 9.0, 80, 82, 70, 5500, 217, 75),
    -- Van VX
    (8, 5000, 6.5, 76, 78, 68, 5200, 205, 68),
    -- Convertible CVX
    (4, 3000, 5.0, 70, 72, 54, 4400, 173, 60),
    -- Sports Car SX
    (2, 3200, 4.5, 72, 74, 48, 4500, 177, 61),
    -- Luxury LX
    (5, 5000, 5.5, 74, 76, 60, 5000, 197, 65);
END;

-- /* ============== performances ======================= */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.performance') AND type = 'U')
BEGIN
CREATE TABLE dbo.performance (
    id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
	battery_type        NVARCHAR(255) NOT NULL,
    motor_type          NVARCHAR(255) NULL,

    range_miles         FLOAT NOT NULL,
    acceleration_sec    FLOAT NOT NULL,
    top_speed_mph       FLOAT NOT NULL,
    towing_lbs          FLOAT NOT NULL
);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.performance)
BEGIN
    INSERT INTO dbo.performance (battery_type, motor_type, range_miles, acceleration_sec, top_speed_mph, towing_lbs)
    VALUES
    -- Sedan LX
    ('STANDARD', 'DC_BRUSHED', 300, 7.5, 130, 1000),
    -- SUV GX
    ('STANDARD', 'DC_BRUSHLESS', 280, 8.5, 120, 2500),
    -- Hatchback ZX
    ('STANDARD', 'DC_BRUSHED', 250, 9.0, 115, 800),
    -- Electric EV1
    ('FAST_CHARGE', 'DC_BRUSHED', 320, 6.5, 140, 1200),
    -- Hybrid HVX
    ('STANDARD', 'AC_INDUCTION', 300, 7.8, 125, 1500),
    -- Truck TX
    ('LONG_RANGE', 'AC_INDUCTION', 220, 10.0, 110, 5000),
    -- Van VX
    ('LONG_RANGE', 'SYNCHRONOUS', 260, 9.5, 110, 2000),
    -- Convertible CVX
    ('STANDARD', 'DC_BRUSHLESS', 280, 5.8, 150, 900),
    -- Sports Car SX
    ('FAST_CHARGE', 'PERMANENT_MAGNET', 270, 4.5, 180, 500),
    -- Luxury LX
    ('STANDARD', 'PERMANENT_MAGNET', 300, 6.8, 160, 1200);
END;


-- /* ============== car model =========================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_model') AND type = 'U')
BEGIN
CREATE TABLE dbo.car_model (
    id                     BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_model_type         NVARCHAR(30) NOT NULL
);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.car_model)
BEGIN
    INSERT INTO dbo.car_model (
        car_model_type
    )
    VALUES
    ('TESLA_MODEL_3'),
    ('TESLA_MODEL_Y'),
    ('TESLA_MODEL_S'),
    ('TESLA_MODEL_X'),
    ('TESLA_MODEL_Z');
END;

-- /* ============== car_detail ================================ */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_detail') AND type = 'U')
BEGIN
CREATE TABLE dbo.car_detail (
    id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_model_id        BIGINT NULL,
	dimension_id        BIGINT NULL,
    performance_id      BIGINT NULL,

    car_name            NVARCHAR(255) NOT NULL,
    car_status          NVARCHAR(255) NOT NULL,
    color               NVARCHAR(10) NOT NULL,

	created_by            NVARCHAR(100) NOT NULL,
    created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    last_modified_by      NVARCHAR(100) NOT NULL,
    last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),


    CONSTRAINT CK_car_status
        CHECK (car_status IN ('FOR_SALE','SOLD_OUT','TEST_DRIVE_ONLY')),

    CONSTRAINT FK_car_dimension
        FOREIGN KEY (dimension_id) REFERENCES dbo.dimension(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_performance
        FOREIGN KEY (performance_id) REFERENCES dbo.performance(id)
        ON UPDATE NO ACTION ON DELETE SET NULL,

    CONSTRAINT FK_car_model
        FOREIGN KEY (car_model_id) REFERENCES dbo.car_model(id)
        ON UPDATE NO ACTION ON DELETE SET NULL
);
END;

IF NOT EXISTS (SELECT 1 FROM dbo.car_detail)
BEGIN
    INSERT INTO dbo.car_detail (
        dimension_id,
        performance_id,
        car_model_id,
        car_name,
        car_status,
        color,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    (1, 1, 1, 'Sedan LX', 'FOR_SALE', N'Xanh', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (2, 2, 2, 'SUV GX', 'SOLD_OUT', N'Vàng Đen', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (3, 3, 3, 'Hatchback ZX', 'SOLD_OUT', N'Cam Đen', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (4, 4, 4, 'Electric EV1', 'FOR_SALE', N'Hồng Đen', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (1, 5, 5, 'Hybrid HVX', 'FOR_SALE', N'Xanh', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (2, 1, 1, 'Truck TX', 'FOR_SALE', N'Xanh Nâu', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (3, 2, 2, 'Van VX', 'TEST_DRIVE_ONLY', N'Đỏ', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (4, 3, 3, 'Convertible CVX', 'FOR_SALE', N'Trắng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (5, 4, 4, 'Sports Car SX', 'TEST_DRIVE_ONLY', N'Vàng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (5, 5, 5, 'Luxury LX', 'TEST_DRIVE_ONLY', N'Vàng Nhạt', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE());
END;

-- /* ==============  car_image ========================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_image') AND type = 'U')
BEGIN
CREATE TABLE dbo.car_image (
    id          BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_detail_id      BIGINT NOT NULL,
    file_name   VARCHAR(255) NULL,
    file_path   VARCHAR(1024) NULL,
    file_url    VARCHAR(1024) NULL,

    CONSTRAINT FK_car_image_car
        FOREIGN KEY (car_detail_id) REFERENCES dbo.car_detail(id)
        ON UPDATE NO ACTION ON DELETE CASCADE
);
END;

-- =================== car_image ===================
IF NOT EXISTS (SELECT 1 FROM dbo.car_image)
BEGIN
    INSERT INTO dbo.car_image (
        car_detail_id,
        file_name,
        file_path,
        file_url
    )
    VALUES
    (1, 'sample_blue_01.jpg', '/uploads/thumbnail/image/sample_blue_01.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_01.jpg'),
    (1, 'sample_blue_02.jpg', '/uploads/thumbnail/image/sample_blue_02.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_02.jpg'),
    (1, 'sample_blue_03.jpg', '/uploads/thumbnail/image/sample_blue_03.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_03.jpg'),
    (1, 'sample_blue_01.jpg', '/uploads/thumbnail/image/sample_blue_01.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_interior_01.jpg'),
    (1, 'sample_blue_02.jpg', '/uploads/thumbnail/image/sample_blue_02.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_interior_02.jpg'),
    (1, 'sample_blue_03.jpg', '/uploads/thumbnail/image/sample_blue_03.jpg', 'http://localhost:8000/evdealer/uploads/thumbnail/image/sample_blue_interior_03.jpg'),

    (2, 'black_gold_01.png', '/uploads/thumbnail/image/black_gold_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_01.png'),
    (2, 'black_gold_02.png', '/uploads/thumbnail/image/black_gold_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_02.png'),
    (2, 'black_gold_03.png', '/uploads/thumbnail/image/black_gold_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_03.png'),
    (2, 'black_gold_intorior_01.png', '/uploads/thumbnail/image/black_gold_intorior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_intorior_01.png'),
    (2, 'black_gold_intorior_02.png', '/uploads/thumbnail/image/black_gold_intorior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_intorior_02.png'),
    (2, 'black_gold_intorior_03.png', '/uploads/thumbnail/image/black_gold_intorior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_gold_intorior_03.png'),

    (3, 'black_orange_01.png', '/uploads/thumbnail/image/black_orange_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_01.png'),
    (3, 'black_orange_02.png', '/uploads/thumbnail/image/black_orange_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_02.png'),
    (3, 'black_orange_03.png', '/uploads/thumbnail/image/black_orange_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_03.png'),
    (3, 'black_orange_Interior_01.png', '/uploads/thumbnail/image/black_orange_Interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_Interior_01.png'),
    (3, 'black_orange_Interior_02.png', '/uploads/thumbnail/image/black_orange_Interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_Interior_02.png'),
    (3, 'black_orange_Interior_03.png', '/uploads/thumbnail/image/black_orange_Interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_orange_Interior_03.png'),

    (4, 'black_pink_01.png', '/uploads/thumbnail/image/black_pink_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_01.png'),
    (4, 'black_pink_02.png', '/uploads/thumbnail/image/black_pink_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_02.png'),
    (4, 'black_pink_03.png', '/uploads/thumbnail/image/black_pink_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_03.png'),
    (4, 'black_pink_interior_01.png', '/uploads/thumbnail/image/black_pink_interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_interior_01.png'),
    (4, 'black_pink_interior_02.png', '/uploads/thumbnail/image/black_pink_interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_interior_02.png'),
    (4, 'black_pink_interior_03.png', '/uploads/thumbnail/image/black_pink_interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black_pink_interior_03.png'),

    (5, 'blue_light_01.png', '/uploads/thumbnail/image/blue_light_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_01.png'),
    (5, 'blue_light_02.png', '/uploads/thumbnail/image/blue_light_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_02.png'),
    (5, 'blue_light_03.png', '/uploads/thumbnail/image/blue_light_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_03.png'),
    (5, 'blue_light_04.png', '/uploads/thumbnail/image/blue_light_04.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_04.png'),
    (5, 'blue_light_interior_01.png', '/uploads/thumbnail/image/blue_light_interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_interior_01.png'),
    (5, 'blue_light_interior_02.png', '/uploads/thumbnail/image/blue_light_interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_interior_02.png'),
    (5, 'blue_light_interior_03.png', '/uploads/thumbnail/image/blue_light_interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue_light_interior_03.png'),

    (6, 'green_brown_01.png', '/uploads/thumbnail/image/green_brown_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green_brown_01.png'),
    (6, 'green_brown_04.png', '/uploads/thumbnail/image/green_brown_04.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green_brown_04.png'),
    (6, 'green_brown_interior_01.png', '/uploads/thumbnail/image/green_brown_interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green_brown_interior_01.png'),
    (6, 'green_brown_interior_02.png', '/uploads/thumbnail/image/green_brown_interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green_brown_interior_02.png'),
    (6, 'green_brown_interior_03.png', '/uploads/thumbnail/image/green_brown_interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green_brown_interior_03.png'),

    (7, 'red_01.png', '/uploads/thumbnail/image/red_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_01.png'),
    (7, 'red_02.png', '/uploads/thumbnail/image/red_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_02.png'),
    (7, 'red_03.png', '/uploads/thumbnail/image/red_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_03.png'),
    (7, 'red_04.png', '/uploads/thumbnail/image/red_04.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_04.png'),
    (7, 'red_05.png', '/uploads/thumbnail/image/red_05.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_05.png'),
    (7, 'red_interior_01.png', '/uploads/thumbnail/image/red_interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_interior_01.png'),
    (7, 'red_interior_02.png', '/uploads/thumbnail/image/red_interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/red_interior_02.png'),

    (8, 'white_01.png', '/uploads/thumbnail/image/white_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_01.png'),
    (8, 'white_02.png', '/uploads/thumbnail/image/white_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_02.png'),
    (8, 'white_03.png', '/uploads/thumbnail/image/white_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_03.png'),
    (8, 'white_Interior_01.png', '/uploads/thumbnail/image/white_Interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_Interior_01.png'),
    (8, 'white_Interior_02.png', '/uploads/thumbnail/image/white_Interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_Interior_02.png'),
    (8, 'white_Interior_03.png', '/uploads/thumbnail/image/white_Interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_Interior_03.png'),

    (9, 'yellow_01.png', '/uploads/thumbnail/image/yellow_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_01.png'),
    (9, 'yellow_02.png', '/uploads/thumbnail/image/yellow_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_02.png'),
    (9, 'yellow_03.png', '/uploads/thumbnail/image/yellow_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_03.png'),
    (9, 'yellow_04.png', '/uploads/thumbnail/image/yellow_04.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_04.png'),
    (9, 'yellow_05.png', '/uploads/thumbnail/image/yellow_05.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_05.png'),
    (9, 'yellow_interior_01.png', '/uploads/thumbnail/image/yellow_interior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_interior_01.png'),
    (9, 'yellow_interior_02.png', '/uploads/thumbnail/image/yellow_interior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_interior_02.png'),
    (9, 'yellow_interior_03.png', '/uploads/thumbnail/image/yellow_interior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_interior_03.png'),

    (10, 'yellow_banana_01.png', '/uploads/thumbnail/image/yellow_banana_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_01.png'),
    (10, 'yellow_banana_02.png', '/uploads/thumbnail/image/yellow_banana_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_02.png'),
    (10, 'yellow_banana_intorior_01.png', '/uploads/thumbnail/image/yellow_banana_intorior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_01.png'),
    (10, 'yellow_banana_intorior_02.png', '/uploads/thumbnail/image/yellow_banana_intorior_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_02.png'),
    (10, 'yellow_banana_intorior_03.png', '/uploads/thumbnail/image/yellow_banana_intorior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_03.png');

END;