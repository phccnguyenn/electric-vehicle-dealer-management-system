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
        dealer_hierarchy_id BIGINT NULL,
        username            VARCHAR(100)  NOT NULL UNIQUE,
        hashed_password     VARCHAR(255)  NOT NULL,
        full_name           NVARCHAR(150) NOT NULL,
        email               VARCHAR(150)  NOT NULL UNIQUE,
        phone               VARCHAR(20)   NOT NULL UNIQUE,
        is_active           BIT           NOT NULL DEFAULT 1,
        role                VARCHAR(50)   NULL,
        city                NVARCHAR(255) NULL,
        created_by          NVARCHAR(100) NULL,
        created_on          DATETIMEOFFSET NULL,
        last_modified_by    NVARCHAR(100) NULL,
        last_modified_on    DATETIMEOFFSET NULL
    );
END;
GO


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
GO


--========================== customer ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.customer') AND type = 'U')
BEGIN
    CREATE TABLE dbo.customer (
        id                  BIGINT IDENTITY(1, 1) PRIMARY KEY,
        dealer_id           BIGINT NOT NULL,
        full_name           NVARCHAR(150) NOT NULL,
        email               VARCHAR(150) NULL,
        phone               VARCHAR(20) NOT NULL UNIQUE,
        address             NVARCHAR(255) NULL,
        created_by          NVARCHAR(100) NULL,
        created_on          DATETIMEOFFSET NULL,
        last_modified_by    NVARCHAR(100) NULL,
        last_modified_on    DATETIMEOFFSET NULL,

        CONSTRAINT FK_dealer_id FOREIGN KEY (dealer_id)
                    REFERENCES dbo.users(user_id)
                    ON DELETE CASCADE
                    ON UPDATE CASCADE
    );
END;
GO

ALTER TABLE dbo.customer NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.customer)
BEGIN
INSERT INTO dbo.customer (dealer_id, full_name, email, phone, address, created_by, created_on, last_modified_by, last_modified_on)
VALUES
-- TP.HCM
(3, N'Nguyễn Thị Mai', 'mai.nguyen@example.com', '0901234567', N'12 Lê Lợi, Quận 1, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Trần Văn Hưng', 'hung.tran@example.com', '0902345678', N'34 Nguyễn Huệ, Quận 1, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Lê Thị Hồng', 'hong.le@example.com', '0903456789', N'56 Hai Bà Trưng, Quận 3, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Phạm Văn Nam', 'nam.pham@example.com', '0904567890', N'78 Võ Thị Sáu, Quận 3, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Đặng Thị Phương', 'phuong.dang@example.com', '0905678901', N'90 Cách Mạng Tháng 8, Quận 10, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Hoàng Văn Tuấn', 'tuan.hoang@example.com', '0906789012', N'123 Lý Thường Kiệt, Quận 10, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Ngô Thị Lan', 'lan.ngo@example.com', '0907890123', N'456 Trường Chinh, Tân Bình, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Võ Văn Cường', 'cuong.vo@example.com', '0908901234', N'789 Hoàng Văn Thụ, Tân Bình, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Bùi Thị Hạnh', 'hanh.bui@example.com', '0910012345', N'321 Phan Xích Long, Phú Nhuận, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),
(3, N'Trịnh Văn Long', 'long.trinh@example.com', '0911123456', N'654 Nguyễn Văn Trỗi, Phú Nhuận, TP.HCM', N'Đại lý Sài Gòn', SYSDATETIMEOFFSET(), N'Đại lý Sài Gòn', SYSDATETIMEOFFSET()),

-- HN
(4, N'Nguyễn Văn Tiến', 'tien.nguyen@example.com', '0912345678', N'123 Lê Duẩn, Hà Nội', N'Đại lý Hà Nội', SYSDATETIMEOFFSET(), N'Đại lý Hà Nội', SYSDATETIMEOFFSET()),
(4, N'Trần Thị Lan', 'lan.tran@example.com', '0987654321', N'456 Nguyễn Trãi, Hà Nội', N'Đại lý Hà Nội', SYSDATETIMEOFFSET(), N'Đại lý Hà Nội', SYSDATETIMEOFFSET()),
(4, N'Lê Minh Tuấn', 'tuan.le@example.com', '0911223344', N'789 Phạm Hùng, Hà Nội', N'Đại lý Hà Nội', SYSDATETIMEOFFSET(), N'Đại lý Hà Nội', SYSDATETIMEOFFSET()),
(4, N'Phạm Thị Hương', 'huong.pham@example.com', '0999887766', N'321 Trần Hưng Đạo, Hà Nội', N'Đại lý Hà Nội', SYSDATETIMEOFFSET(), N'Đại lý Hà Nội', SYSDATETIMEOFFSET()),
(4, N'Đỗ Văn Quang', 'quang.do@example.com', '0955667788', N'654 Hoàng Hoa Thám, Hà Nội', N'Đại lý Hà Nội', SYSDATETIMEOFFSET(), N'Đại lý Hà Nội', SYSDATETIMEOFFSET()),

-- Quy Nhơn
(7, N'Nguyễn Thị Thu', 'thu.nguyen@example.com', '0935123456', N'12 Nguyễn Tất Thành, Quy Nhơn, Bình Định', N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET(), N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET()),
(7, N'Trần Văn Hải', 'hai.tran@example.com', '0936234567', N'34 Trần Hưng Đạo, Quy Nhơn, Bình Định', N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET(), N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET()),
(7, N'Lê Thị Mỹ', 'my.le@example.com', '0937345678', N'56 Lý Thường Kiệt, Quy Nhơn, Bình Định', N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET(), N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET()),
(7, N'Phạm Văn Dũng', 'dung.pham@example.com', '0938456789', N'78 Nguyễn Huệ, Quy Nhơn, Bình Định', N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET(), N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET()),
(7, N'Đặng Thị Kim', 'kim.dang@example.com', '0939567890', N'90 Hai Bà Trưng, Quy Nhơn, Bình Định', N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET(), N'Đại lý Quy Nhơn', SYSDATETIMEOFFSET()),

-- Hải Phòng
(10, N'Nguyễn Văn Hòa', 'hoa.nguyen@example.com', '0941234567', N'12 Điện Biên Phủ, Hải Phòng', N'Đại lý Hải Phòng', SYSDATETIMEOFFSET(), N'Đại lý Hải Phòng', SYSDATETIMEOFFSET()),
(10, N'Trần Thị Liên', 'lien.tran@example.com', '0942345678', N'34 Trần Phú, Hải Phòng', N'Đại lý Hải Phòng', SYSDATETIMEOFFSET(), N'Đại lý Hải Phòng', SYSDATETIMEOFFSET()),
(10, N'Lê Văn Thắng', 'thang.le@example.com', '0943456789', N'56 Lạch Tray, Hải Phòng', N'Đại lý Hải Phòng', SYSDATETIMEOFFSET(), N'Đại lý Hải Phòng', SYSDATETIMEOFFSET()),
(10, N'Phạm Thị Mai', 'mai.pham@example.com', '0944567890', N'78 Tô Hiệu, Hải Phòng', N'Đại lý Hải Phòng', SYSDATETIMEOFFSET(), N'Đại lý Hải Phòng', SYSDATETIMEOFFSET()),
(10, N'Đỗ Văn Kiên', 'kien.do@example.com', '0945678901', N'90 Cầu Đất, Hải Phòng', N'Đại lý Hải Phòng', SYSDATETIMEOFFSET(), N'Đại lý Hải Phòng', SYSDATETIMEOFFSET());
END;
ALTER TABLE dbo.customer CHECK CONSTRAINT ALL;
GO


--========================== CAR DOMAIN ==================================
--========================== CAR DOMAIN ==================================
--========================== CAR DOMAIN ==================================

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
GO

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
GO

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
GO

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
GO

-- /* ============== car model =========================== */
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_model') AND type = 'U')
BEGIN
CREATE TABLE dbo.car_model (
    id                     BIGINT IDENTITY(1,1) PRIMARY KEY,
    car_model_type         NVARCHAR(30) NOT NULL
);
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.car_model)
BEGIN
    INSERT INTO dbo.car_model (
        car_model_type
    )
    VALUES
    (N'TESLA_MODEL_3'),
    (N'TESLA_MODEL_Y'),
    (N'TESLA_MODEL_S'),
    (N'TESLA_MODEL_X'),
    (N'TESLA_MODEL_Z');
END;
GO

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
    color               NVARCHAR(100) NOT NULL,

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
GO

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
    (5, 5, 5, 'Luxury LX', 'TEST_DRIVE_ONLY', N'Vàng Nhạt', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (2, 3, 1, 'EcoCity S', 'FOR_SALE', N'Nâu', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (4, 2, 3, 'Speedster GT', 'FOR_SALE', N'Xanh Lá', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (1, 1, 2, 'Comfort XL', 'TEST_DRIVE_ONLY', N'Xám Bạc', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (3, 4, 2, 'Urban X', 'FOR_SALE', N'Xanh Dương Đậm', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (2, 2, 5, 'Traveler Z', 'TEST_DRIVE_ONLY', N'Đen Bóng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (6, 9, 1, 'EcoLite A1', 'SOLD_OUT', N'Trắng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (7, 6, 2, 'SpeedMax R', 'TEST_DRIVE_ONLY', N'Cam Vàng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (9, 7, 3, 'Comfort Plus', 'FOR_SALE', N'Bạc Sáng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (10, 10, 4, 'Urban X2', 'TEST_DRIVE_ONLY', N'Đen Bóng', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (8, 9, 5, 'Traveler Z', 'TEST_DRIVE_ONLY', N'Xanh Dương Nhạt', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE());
END;
GO

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
GO

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
    (10, 'yellow_banana_intorior_03.png', '/uploads/thumbnail/image/yellow_banana_intorior_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_03.png'),

    (11, 'brown_01.png', '/uploads/thumbnail/image/brown_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown_01.png'),
    (11, 'brown_02.png', '/uploads/thumbnail/image/brown_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown_02.png'),
    (11, 'brown_03.png', '/uploads/thumbnail/image/brown_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown_03.png'),
    (11, 'brown-interior-01.png', '/uploads/thumbnail/image/brown-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown-interior-01.png'),
    (11, 'brown-interior-02.png', '/uploads/thumbnail/image/brown-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown-interior-02.png'),
    (11, 'brown-interior-03.png', '/uploads/thumbnail/image/brown-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/brown-interior-03.png'),

    (12, 'green-01.png', '/uploads/thumbnail/image/green-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-01.png'),
    (12, 'green-02.png', '/uploads/thumbnail/image/green-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-02.png'),
    (12, 'green-03.png', '/uploads/thumbnail/image/green-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-03.png'),
    (12, 'green-interior-01.png', '/uploads/thumbnail/image/green-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-interior-01.png'),
    (12, 'green-interior-02.png', '/uploads/thumbnail/image/green-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-interior-02.png'),
    (12, 'green-interior-03.png', '/uploads/thumbnail/image/green-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/green-interior-03.png'),

    (13, 'grey-01.png', '/uploads/thumbnail/image/grey-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-01.png'),
    (13, 'grey-02.png', '/uploads/thumbnail/image/grey-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-02.png'),
    (13, 'grey-03.png', '/uploads/thumbnail/image/grey-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-03.png'),
    (13, 'grey-interior-01.png', '/uploads/thumbnail/image/grey-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-interior-01.png'),
    (13, 'grey-interior-02.png', '/uploads/thumbnail/image/grey-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-interior-02.png'),
    (13, 'grey-interior-03.png', '/uploads/thumbnail/image/grey-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/grey-interior-03.png'),

    (14, 'blue-01.png', '/uploads/thumbnail/image/blue-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-01.png'),
    (14, 'blue-02.png', '/uploads/thumbnail/image/blue-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-02.png'),
    (14, 'blue-03.png', '/uploads/thumbnail/image/blue-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-03.png'),
    (14, 'blue-interior-01.png', '/uploads/thumbnail/image/blue-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-interior-01.png'),
    (14, 'blue-interior-02.png', '/uploads/thumbnail/image/blue-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-interior-02.png'),
    (14, 'blue-interior-03.png', '/uploads/thumbnail/image/blue-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/blue-interior-03.png'),

    (15, 'black-01.png', '/uploads/thumbnail/image/black-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-01.png'),
    (15, 'black-02.png', '/uploads/thumbnail/image/black-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-02.png'),
    (15, 'black-03.png', '/uploads/thumbnail/image/black-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-03.png'),
    (15, 'black-interior-01.png', '/uploads/thumbnail/image/black-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-interior-01.png'),
    (15, 'black-interior-02.png', '/uploads/thumbnail/image/black-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-interior-02.png'),
    (15, 'black-interior-03.png', '/uploads/thumbnail/image/black-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/black-interior-03.png'),

    (16, 'white-01.png', '/uploads/thumbnail/image/white-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-01.png'),
    (16, 'white-02.png', '/uploads/thumbnail/image/white-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-02.png'),
    (16, 'white-03.png', '/uploads/thumbnail/image/white-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-03.png'),
    (16, 'white-interior-01.png', '/uploads/thumbnail/image/white-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-interior-01.png'),
    (16, 'white-interior-02.png', '/uploads/thumbnail/image/white-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-interior-02.png'),
    (16, 'white-interior-03.png', '/uploads/thumbnail/image/white-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white-interior-03.png'),

    (17, 'Orange-01.png', '/uploads/thumbnail/image/Orange-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-01.png'),
    (17, 'Orange-02.png', '/uploads/thumbnail/image/Orange-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-02.png'),
    (17, 'Orange-03.png', '/uploads/thumbnail/image/Orange-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-03.png'),
    (17, 'Orange-interior-01.png', '/uploads/thumbnail/image/Orange-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-interior-01.png'),
    (17, 'Orange-interior-02.png', '/uploads/thumbnail/image/Orange-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-interior-02.png'),
    (17, 'Orange-interior-03.png', '/uploads/thumbnail/image/Orange-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/Orange-interior-03.png'),

    (18, 'CopperMist-01.png', '/uploads/thumbnail/image/CopperMist-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-01.png'),
    (18, 'CopperMist-02.png', '/uploads/thumbnail/image/CopperMist-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-02.png'),
    (18, 'CopperMist-03.png', '/uploads/thumbnail/image/CopperMist-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-03.png'),
    (18, 'CopperMist-interior-01.png', '/uploads/thumbnail/image/CopperMist-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-interior-01.png'),
    (18, 'CopperMist-interior-02.png', '/uploads/thumbnail/image/CopperMist-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-interior-02.png'),
    (18, 'CopperMist-interior-03.png', '/uploads/thumbnail/image/CopperMist-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/CopperMist-interior-03.png'),

    (19, 'GlossyBlack-01.png', '/uploads/thumbnail/image/GlossyBlack-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-01.png'),
    (19, 'GlossyBlack-02.png', '/uploads/thumbnail/image/GlossyBlack-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-02.png'),
    (19, 'GlossyBlack-03.png', '/uploads/thumbnail/image/GlossyBlack-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-03.png'),
    (19, 'GlossyBlack-interior-01.png', '/uploads/thumbnail/image/GlossyBlack-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-interior-01.png'),
    (19, 'GlossyBlack-interior-02.png', '/uploads/thumbnail/image/GlossyBlack-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-interior-02.png'),
    (19, 'GlossyBlack-interior-03.png', '/uploads/thumbnail/image/GlossyBlack-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/GlossyBlack-interior-03.png'),

    (20, 'FrostBlue-01.png', '/uploads/thumbnail/image/FrostBlue-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-01.png'),
    (20, 'FrostBlue-02.png', '/uploads/thumbnail/image/FrostBlue-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-02.png'),
    (20, 'FrostBlue-03.png', '/uploads/thumbnail/image/FrostBlue-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-03.png'),
    (20, 'FrostBlue-interior-01.png', '/uploads/thumbnail/image/FrostBlue-interior-01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-interior-01.png'),
    (20, 'FrostBlue-interior-02.png', '/uploads/thumbnail/image/FrostBlue-interior-02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-interior-02.png'),
    (20, 'FrostBlue-interior-03.png', '/uploads/thumbnail/image/FrostBlue-interior-03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/FrostBlue-interior-03.png');
END;
GO


--========================== TEST DRIVING DOMAIN ==================================
--========================== TEST DRIVING DOMAIN ==================================
--========================== TEST DRIVING DOMAIN ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.slot') AND type = 'U')
BEGIN
    CREATE TABLE dbo.slot (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        dealer_staff_id     BIGINT NOT NULL,
        num_customers       INT NOT NULL,
        start_time          DATETIMEOFFSET NOT NULL,
        end_time            DATETIMEOFFSET NOT NULL,
        created_by            NVARCHAR(100) NOT NULL,
        created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
        last_modified_by      NVARCHAR(100) NOT NULL,
        last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

        CONSTRAINT fk_slot_dealer_staff_id
            FOREIGN KEY (dealer_staff_id)
            REFERENCES dbo.users(user_id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
    );
END;
GO


ALTER TABLE dbo.slot NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.slot)
BEGIN
    INSERT INTO dbo.slot (dealer_staff_id, num_customers, start_time, end_time, created_by, created_on, last_modified_by, last_modified_on) VALUES
    (4, 4, '2025-11-06T08:00:00+07:00', '2025-11-06T09:00:00+07:00', N'Đại lý Sài Gòn', GETDATE(), N'Đại lý Sài Gòn', GETDATE()),
    (4, 2, '2025-11-06T09:30:00+07:00', '2025-11-06T10:30:00+07:00', N'Đại lý Sài Gòn', GETDATE(), N'Đại lý Sài Gòn', GETDATE()),
    (6, 4, '2025-11-06T13:00:00+07:00', '2025-11-06T14:00:00+07:00', N'Đại lý Hà Nội', GETDATE(), N'Đại lý Hà Nội', GETDATE()),
    (6, 4, '2025-11-15T13:00:00+07:00', '2025-11-16T14:00:00+07:00', N'Đại lý Hà Nội', GETDATE(), N'Đại lý Hà Nội', GETDATE()),
    (8, 3, '2025-11-07T10:00:00+07:00', '2025-11-07T11:00:00+07:00', N'Đại lý Quy Nhơn', GETDATE(), N'Đại lý Quy Nhơn', GETDATE()),
    (9, 5, '2025-11-07T15:00:00+07:00', '2025-11-07T16:00:00+07:00', N'Đại lý Quy Nhơn', GETDATE(), N'Đại lý Quy Nhơn', GETDATE()),
    (11, 3, '2025-11-08T08:30:00+07:00', '2025-11-08T09:30:00+07:00', N'Đại lý Hải Phòng', GETDATE(), N'Đại lý Hải Phòng', GETDATE());
END;
GO
ALTER TABLE dbo.slot CHECK CONSTRAINT ALL;



IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.car_model_in_slot') AND type = 'U')
BEGIN
    CREATE TABLE dbo.car_model_in_slot (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        slot_id             BIGINT NOT NULL,
        car_model_id        BIGINT NOT NULL,
        max_trial_car       INT NOT NULL,

        CONSTRAINT fk_car_model_in_slot_slot_id
            FOREIGN KEY (slot_id)
            REFERENCES dbo.slot(id)
            ON UPDATE CASCADE
            ON DELETE CASCADE,

        CONSTRAINT fk_car_model_in_slot_car_model_id
            FOREIGN KEY (car_model_id)
            REFERENCES dbo.car_model(id)
            ON UPDATE CASCADE
            ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.car_model_in_slot)
BEGIN
    INSERT INTO dbo.car_model_in_slot (slot_id, car_model_id, max_trial_car)
    VALUES
        (1, 4, 3),
        (2, 2, 2),
        (3, 5, 4),
        (4, 5, 3),
        (5, 2, 2),
        (6, 4, 2),
        (7, 2, 3);
END;
GO



IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.booking') AND type = 'U')
BEGIN
    CREATE TABLE dbo.booking (
        id              BIGINT IDENTITY(1,1) PRIMARY KEY,
        slot_id         BIGINT NOT NULL,
        customer_name   NVARCHAR(50) NOT NULL,
        customer_phone  NVARCHAR(10) NOT NULL,

        CONSTRAINT fk_booking_slot
            FOREIGN KEY (slot_id)
            REFERENCES dbo.slot(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.booking)
BEGIN
    -- 5 6 QN
    -- 7 HP
    INSERT INTO dbo.booking (slot_id, customer_name, customer_phone)
        VALUES
        (1, N'Nguyễn Thị Mai', N'0901234567'),
        (1, N'Ngô Thị Lan', N'0907890123'),
        (2, N'Hoàng Văn Tuấn', N'0906789012'),
        (3, N'Võ Văn Cường', N'0908901234'),
        (4, N'Nguyễn Văn Tiến', N'0912345678'),
        (4, N'Lê Minh Tuấn', N'0911223344'),
        (3, N'Phạm Thị Hương', N'0999887766'),
        (3, N'Đỗ Văn Quang', N'0955667788'),
        (7, N'Trần Thị Liên', N'0942345678'),
        (5, N'Nguyễn Thị Thu', N'0935123456'),
        (6, N'Lê Thị Mỹ', N'0937345678'),
        (5, N'Phạm Văn Dũng', N'0938456789'),
        (7, N'Đỗ Văn Kiên', N'0945678901'),
        (7, N'Lê Văn Thắng', N'0943456789');
END;
GO

--========================== PRICE PROGRAM DOMAIN ==================================
--========================== PRICE PROGRAM DOMAIN ==================================
--========================== PRICE PROGRAM DOMAIN ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.dealer_hierarchy') AND type = 'U')
BEGIN
    CREATE TABLE dbo.dealer_hierarchy (
        id                  BIGINT IDENTITY(1,1) PRIMARY KEY,
        --dealer_id           BIGINT NULL,
        level_type          INT NOT NULL,

--        CONSTRAINT fk_dealer_hierarchy_id
--            FOREIGN KEY (dealer_id)
--            REFERENCES dbo.users(user_id)
    );
END;
GO

ALTER TABLE dbo.dealer_hierarchy NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.dealer_hierarchy)
BEGIN
    INSERT INTO dbo.dealer_hierarchy (level_type)
    VALUES (1), (2), (3);
END;
GO
ALTER TABLE dbo.dealer_hierarchy CHECK CONSTRAINT ALL;

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
        last_modified_by        NVARCHAR(100) NULL,

        CONSTRAINT fk_price_program_dealer_hierarchy
                    FOREIGN KEY (dealer_hierarchy_id)
                    REFERENCES dbo.dealer_hierarchy(id)
                    ON DELETE SET NULL
                    ON UPDATE CASCADE
    );
END;
GO

-- Insert sample Price Programs
IF NOT EXISTS (SELECT 1 FROM dbo.price_program)
BEGIN
    INSERT INTO dbo.price_program (dealer_hierarchy_id, start_day, end_day, created_by, created_on, last_modified_by, last_modified_on)
    VALUES
    (1, '2025-11-05', '2025-12-31', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE()),
    (2, '2025-12-01', '2026-01-31', 'EVD Staff', GETDATE(), 'EVD Administrator', GETDATE()),
    (3, '2025-10-15', '2025-11-30', 'EVD Administrator', GETDATE(), 'EVD Administrator', GETDATE());
    END;
GO

-- ====== PROGRAM DETAIL TABLE ======
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.program_detail') AND type = 'U')
BEGIN
    CREATE TABLE dbo.program_detail (
        id BIGINT               IDENTITY(1,1) PRIMARY KEY,
        car_model_id            BIGINT NOT NULL,
        price_program_id        BIGINT NOT NULL,
        min_sale_price               DECIMAL(15,2) NULL,
        suggested_sale_price         DECIMAL(15,2) NULL,
        max_sale_price               DECIMAL(15,2) NULL,

        CONSTRAINT fk_price_program_id
            FOREIGN KEY (price_program_id)
            REFERENCES dbo.price_program(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT fk_car_model_id
            FOREIGN KEY (car_model_id)
            REFERENCES dbo.car_model(id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    );
END;
GO

-- Insert Program Details for each program
IF NOT EXISTS (SELECT 1 FROM dbo.program_detail)
BEGIN
    INSERT INTO dbo.program_detail (car_model_id, price_program_id, min_sale_price, suggested_sale_price, max_sale_price)
    VALUES
    -- For Program 1
    (4, 1, 750000000, 800000000, 875000000),
    (3, 1, 1200000000, 1237500000, 1300000000),

    -- For Program 2
    (1, 2, 875000000, 925000000, 1000000000),
    (5, 2, 1050000000, 1125000000, 1200000000),

    -- For Program 3
    (2, 3, 775000000, 850000000, 900000000),
    (3, 3, 700000000, 737500000, 800000000);
END;
GO



--========================== ORDER DOMAIN ==================================
--========================== ORDER DOMAIN ==================================
--========================== ORDER DOMAIN ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders') AND type = 'U')
BEGIN
    CREATE TABLE dbo.orders (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        car_model_id BIGINT NULL,
        car_id BIGINT NULL,
        staff_id BIGINT NULL,
        customer_id BIGINT NOT NULL,
        total_amount DECIMAL(20,2) NOT NULL,
        amount_paid DECIMAL(20,2) DEFAULT 0,
        quotation_url NVARCHAR(255) NULL,
        contract_url NVARCHAR(255) NULL,
        status NVARCHAR(50) NOT NULL,
        payment_status NVARCHAR(50) NOT NULL,
        created_by NVARCHAR(100) NULL,
        created_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
        last_modified_by NVARCHAR(100) NULL,
        last_modified_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),

        CONSTRAINT FK_orders_car_model FOREIGN KEY (car_model_id) REFERENCES car_model(id),
        CONSTRAINT FK_orders_car FOREIGN KEY (car_id) REFERENCES car_detail(id),
        CONSTRAINT FK_orders_staff FOREIGN KEY (staff_id) REFERENCES users(user_id),
        CONSTRAINT FK_orders_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
    );
END;
GO

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.payments') AND type = 'U')
BEGIN
CREATE TABLE payments (
           id BIGINT IDENTITY(1,1) PRIMARY KEY,
           order_id BIGINT NOT NULL,
           amount DECIMAL(20,2) NOT NULL,
           paid_at DATETIMEOFFSET NOT NULL,
           type NVARCHAR(50) NOT NULL,
           created_by NVARCHAR(100) NULL,
           created_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
           last_modified_by NVARCHAR(100) NULL,
           last_modified_on DATETIMEOFFSET NOT NULL DEFAULT SYSUTCDATETIME(),
           CONSTRAINT FK_payments_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
END;
GO

-- Insert sample data into dbo.orders WITHOUT CONSTRAINT
ALTER TABLE dbo.orders NOCHECK CONSTRAINT ALL;
IF NOT EXISTS (SELECT 1 FROM dbo.orders)
BEGIN
    INSERT INTO dbo.orders (
        car_model_id,
        car_id,
        staff_id,
        customer_id,
        total_amount,
        amount_paid,
        quotation_url,
        contract_url,
        status,
        payment_status,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    (2, 2, 9, 20, 870000000.00, 870005000.00, N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/quotation-1.pdf', N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/contract-1.pdf', N'COMPLETED', N'FINISHED', N'Trần Thị Liên', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Trần Thị Liên', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),
    (3, 3, 9, 17, 737500000.00, 368750000.00, N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/quotation-2.pdf', N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/contract-2.pdf', N'IN_DELIVERY', N'DEPOSIT_PAID', N'Trần Thị Liên', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Trần Thị Liên', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),
    (4, 4, 8, 18, 737500000.00, 0.00, N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/quotation-3.pdf', N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/contract-3.pdf', N'APPROVED', N'PENDING', N'Trần Thị Hạnh', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Trần Thị Hạnh', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),
    (3, 12,  6,  16, 1237500000.00, 1237500000.00,  N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/quotation-4.pdf', N'http://localhost:8000/evdealer/uploads/thumbnail/contract_and_quotation/contract-4.pdf', N'COMPLETED', N'FINISHED', N'Nguyễn Văn Bình', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET), N'Nguyễn Văn Bình', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET)),
    (1, NULL,  4, 6, 880000000.00,  0.00,  NULL, NULL, N'PENDING', N'PENDING',  N'Nguyễn Văn Quý', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Nguyễn Văn Quý', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),
    (2, NULL,  11, 25, 990000000.00, 297000000.00,  NULL, NULL, N'PENDING', N'DEPOSIT_PAID', N'Phạm Văn Dũng', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Phạm Văn Dũng', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),
    (5, NULL,  11,  21, 1220000000.00, 0.00,  NULL, NULL, N'PENDING', N'PENDING',  N'Phạm Văn Dũng', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET), N'Phạm Văn Dũng', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET));
END
GO
ALTER TABLE dbo.orders CHECK CONSTRAINT ALL;

IF NOT EXISTS (SELECT 1 FROM dbo.payments)
BEGIN
    INSERT INTO dbo.payments (
        order_id,
        amount,
        paid_at,
        type,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    (1, 870000000.00, '2025-11-13T00:32:33.5774464+00:00', N'IN_FULL', N'Trần Thị Liên', '2025-11-13T00:32:33.5774464+00:00', N'Trần Thị Liên', '2025-11-13T00:32:33.5774464+00:00'),
    (2, 368750000.00, '2025-11-13T00:32:33.5774464+00:00', N'INSTALLMENT', N'Trần Thị Liên', '2025-11-13T00:32:33.5774464+00:00', N'Trần Thị Liên', '2025-11-13T00:32:33.5774464+00:00'),
    (3, 412500000.00, '2025-11-13T00:32:33.5774464+00:00', N'INSTALLMENT', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00'),
    (3, 412500000.00, '2025-11-13T00:32:33.5774464+00:00', N'INSTALLMENT', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00'),
    (3, 412500000.00, '2025-11-13T00:32:33.5774464+00:00', N'INSTALLMENT', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00', N'Nguyễn Văn Bình', '2025-11-13T00:32:33.5774464+00:00'),
    (6, 297000000.00, '2025-11-13T00:32:33.5774464+00:00', N'INSTALLMENT', N'Phạm Văn Dũng', '2025-11-13T00:32:33.5774464+00:00', N'Phạm Văn Dũng', '2025-11-13T00:32:33.5774464+00:00');
END;
GO



IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.orders_activities') AND type = 'U')
BEGIN
CREATE TABLE dbo.orders_activities (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        order_id BIGINT NOT NULL,
        order_status NVARCHAR(50) NOT NULL,
        changed_at DATETIME2 NOT NULL,

        CONSTRAINT FK_orders_activities_order
        FOREIGN KEY (order_id) REFERENCES dbo.orders(id)
        ON DELETE CASCADE
);
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.orders_activities)
BEGIN
    INSERT INTO dbo.orders_activities (order_id, order_status, changed_at)
        VALUES
        -- Order 1 (APPROVED)
        (1, 'PENDING', CAST('2025-11-05T16:04:00+07:00' AS DATETIMEOFFSET)),
        (1, 'APPROVED', CAST('2025-11-05T16:04:00+07:00' AS DATETIMEOFFSET)),
        (1, 'IN_DELIVERY', CAST('2025-11-06T16:04:00+07:00' AS DATETIMEOFFSET)),
        (1, 'DELIVERED', CAST('2025-11-10T16:04:00+07:00' AS DATETIMEOFFSET)),
        (1, 'COMPLETED', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 2 (REJECTED)
        (2, 'PENDING', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET)),
        (2, 'APPROVED', CAST('2025-11-10T16:04:00+07:00' AS DATETIMEOFFSET)),
        (2, 'IN_DELIVERY', CAST('2025-11-15T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 3 (DELIVERED)
        (3, 'PENDING', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET)),
        (3, 'APPROVED', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 4 (DELIVERED)
        (1, 'PENDING', CAST('2025-11-05T16:04:00+07:00' AS DATETIMEOFFSET)),
        (4, 'APPROVED', CAST('2025-11-05T16:04:00+07:00' AS DATETIMEOFFSET)),
        (4, 'IN_DELIVERY', CAST('2025-11-06T16:04:00+07:00' AS DATETIMEOFFSET)),
        (4, 'DELIVERED', CAST('2025-11-10T16:04:00+07:00' AS DATETIMEOFFSET)),
        (4, 'COMPLETED', CAST('2025-11-20T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 5 (IN_DELIVERY)
        (5, 'PENDING', CAST('2025-11-09T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 6 (APPROVED)
        (6, 'PENDING', CAST('2025-11-12T16:04:00+07:00' AS DATETIMEOFFSET)),

        -- Order 7 (COMPLETED)
        (7, 'PENDING', CAST('2025-11-15T16:04:00+07:00' AS DATETIMEOFFSET));
END;
GO



--========================== WAREHOUSE DOMAIN ==================================
--========================== WAREHOUSE DOMAIN ==================================
--========================== WAREHOUSE DOMAIN ==================================
--========================== WAREHOUSE DOMAIN ==================================
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.warehouse') AND type = 'U')
BEGIN
    CREATE TABLE dbo.warehouse (
        id              BIGINT IDENTITY(1,1) PRIMARY KEY,
        warehouse_name  NVARCHAR(255) NOT NULL
    );
END;
GO

IF NOT EXISTS (SELECT 1 FROM dbo.warehouse)
BEGIN
    INSERT INTO dbo.warehouse (warehouse_name)
    VALUES (N'Kho Khu Vực TP. Hồ Chí Minh');
END;
GO


IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.warehouse_car') AND type = 'U')
BEGIN
    CREATE TABLE dbo.warehouse_car (
        id                      BIGINT IDENTITY(1,1) PRIMARY KEY,
        warehouse_id            BIGINT NOT NULL,
        car_detail_id           BIGINT NOT NULL,
        quantity                INT NOT NULL,
        warehouse_car_status    VARCHAR(50) NOT NULL,
        created_by              NVARCHAR(100) NULL,
        created_on              DATETIMEOFFSET NULL,
        last_modified_by        NVARCHAR(100) NULL,
        last_modified_on        DATETIMEOFFSET NULL,

        CONSTRAINT fk_wc_warehouse_id
            FOREIGN KEY (warehouse_id) REFERENCES dbo.warehouse(id)
            ON DELETE CASCADE,

        CONSTRAINT fk_wc_car_detail_id
            FOREIGN KEY (car_detail_id) REFERENCES dbo.car_detail(id)
            ON DELETE CASCADE
    );
END;
GO

ALTER TABLE dbo.warehouse_car NOCHECK CONSTRAINT ALL;

IF NOT EXISTS (SELECT 1 FROM dbo.warehouse_car)
BEGIN
    INSERT INTO dbo.warehouse_car (
        warehouse_id,
        car_detail_id,
        quantity,
        warehouse_car_status,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
        (1, 2, 0, 'OUT_OF_STOCK', N'EVD Administrator', SYSDATETIMEOFFSET(), N'EVD Administrator', SYSDATETIMEOFFSET()),
        (1, 3, 0, 'OUT_OF_STOCK', N'EVD Administrator', SYSDATETIMEOFFSET(), N'EVD Administrator', SYSDATETIMEOFFSET()),
        (1, 16, 0, 'OUT_OF_STOCK', N'EVD Administrator', SYSDATETIMEOFFSET(), N'EVD Administrator', SYSDATETIMEOFFSET()),
        (1, 1, 2, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 4, 4, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 5, 5, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 6, 3, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 8, 2, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 11, 4, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 12, 3, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 14, 3, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 18, 3, 'IN_STOCK', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 12, 1, 'RESERVED', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 14, 1, 'RESERVED', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE()),
        (1, 18, 1, 'RESERVED', N'EVD Administrator', GETDATE(), N'EVD Administrator', GETDATE());
END;
GO

ALTER TABLE dbo.warehouse_car CHECK CONSTRAINT ALL;
-- 1 4 5 6 8 11 12 14 18 for sale