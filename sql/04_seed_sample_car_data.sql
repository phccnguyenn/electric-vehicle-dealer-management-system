USE evdealer_db;

-- /* ============== category =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.category)
BEGIN
    INSERT INTO dbo.category (
        category_name,
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    ('Sedan', 'admin', SYSDATETIMEOFFSET(), 'admin', SYSDATETIMEOFFSET()),
    ('SUV', 'admin', SYSDATETIMEOFFSET(), 'admin', SYSDATETIMEOFFSET()),
    ('Hatchback', 'admin', SYSDATETIMEOFFSET(), 'admin', SYSDATETIMEOFFSET()),
    ('Electric', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
    ('Hybrid', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
    ('Truck', 'manager', SYSDATETIMEOFFSET(), 'manager', SYSDATETIMEOFFSET()),
    ('Van', 'manager', SYSDATETIMEOFFSET(), 'manager', SYSDATETIMEOFFSET()),
    ('Convertible', 'admin', SYSDATETIMEOFFSET(), 'admin', SYSDATETIMEOFFSET()),
    ('Sports Car', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
    ('Luxury', 'admin', SYSDATETIMEOFFSET(), 'admin', SYSDATETIMEOFFSET());
END;

-- /* ============== battery =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.battery)
BEGIN
    INSERT INTO [dbo].[battery] (
        [chemistry_type],
        [age],
        [charge_time_hour],
        [usage_duration_hour],
        [weight_kg],
        [voltage_v],
        [capacity_kwh],
        [cycle_life],
        [created_by],
        [created_on],
        [last_modified_by],
        [last_modified_on]
    )
    VALUES
    ('NCA', 1.0, 2.0, 7.5, 46.3, 400, 72.0, 1800, 'admin', GETDATE(), 'admin', GETDATE()),
    ('NCM', 1.2, 2.5, 8.0, 48.0, 380, 70.0, 2000, 'admin', GETDATE(), 'admin', GETDATE()),
    ('LFP', 2.0, 3.0, 7.0, 52.5, 360, 60.0, 2500, 'admin', GETDATE(), 'admin', GETDATE()),
    ('SOLID_STATE', 0.5, 1.5, 9.0, 40.0, 420, 80.0, 3000, 'admin', GETDATE(), 'admin', GETDATE());
END;

-- /* ============== motor =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.motor)
BEGIN
    INSERT INTO [dbo].[motor] (
        [motor_type],
        [serial_number],
        [power_kw],
        [torque_nm],
        [max_rpm],
        [cooling_type],
        [voltage_range_v],
        [created_by],
        [created_on],
        [last_modified_by],
        [last_modified_on]
    )
    VALUES
    ('AC_INDUCTION', 'ACM-20251014-001', 250, 400, 18000, 'LIQUID', 400, 'admin', GETDATE(), 'admin', GETDATE()),
    ('PERMANENT_MAGNET', 'PMM-20251014-002', 200, 370, 16000, 'LIQUID', 360, 'admin', GETDATE(), 'admin', GETDATE()),
    ('DC_BRUSHED', 'DCB-20251014-003', 90, 180, 6000, 'AIR', 200, 'admin', GETDATE(), 'admin', GETDATE()),
    ('DC_BRUSHLESS', 'DCBL-20251014-004', 120, 220, 9000, 'OIL', 250, 'admin', GETDATE(), 'admin', GETDATE()),
    ('SYNCHRONOUS', 'SYNC-20251014-005', 180, 350, 12000, 'LIQUID', 380, 'admin', GETDATE(), 'admin', GETDATE());
END;

-- =================== color ===================
IF NOT EXISTS (SELECT 1 FROM dbo.color)
BEGIN
    INSERT INTO dbo.color (color_name, color_hex_code, extra_cost, created_by, created_on, last_modified_by, last_modified_on)
    VALUES
    ('Red','#FF0000',500,'admin',GETDATE(),'admin',GETDATE()),
    ('Blue','#0000FF',400,'admin',GETDATE(),'admin',GETDATE()),
    ('Green','#00FF00',300,'admin',GETDATE(),'admin',GETDATE()),
    ('Black','#000000',0,'admin',GETDATE(),'admin',GETDATE()),
    ('White','#FFFFFF',0,'admin',GETDATE(),'admin',GETDATE());
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

-- /* ============== performance =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.performance)
BEGIN
    INSERT INTO dbo.performance (battery_id, motor_id, range_miles, acceleration_sec, top_speed_mph, towing_lbs)
    VALUES
    -- Sedan LX
    (1, 1, 300, 7.5, 130, 1000),
    -- SUV GX
    (2, 2, 280, 8.5, 120, 2500),
    -- Hatchback ZX
    (3, 3, 250, 9.0, 115, 800),
    -- Electric EV1
    (4, 4, 320, 6.5, 140, 1200),
    -- Hybrid HVX
    (2, 5, 300, 7.8, 125, 1500),
    -- Truck TX
    (1, 2, 220, 10.0, 110, 5000),
    -- Van VX
    (3, 3, 260, 9.5, 110, 2000),
    -- Convertible CVX
    (4, 4, 280, 5.8, 150, 900),
    -- Sports Car SX
    (2, 5, 270, 4.5, 180, 500),
    -- Luxury LX
    (1, 1, 300, 6.8, 160, 1200);
END;

-- /* ============== car =========================== */
IF NOT EXISTS (SELECT 1 FROM dbo.car)
BEGIN
    INSERT INTO dbo.car (
        dimension_id,
        performance_id,
        color_id,
        category_id,
        car_name,
        price,
        drive_type,
        [year],
        created_by,
        created_on,
        last_modified_by,
        last_modified_on
    )
    VALUES
    (1, 1, 1, 1, 'Sedan LX', 0, 'FWD', 2023, 'admin', GETDATE(), 'admin', GETDATE()),
    (2, 2, 2, 2, 'SUV GX', 0, 'AWD', 2025, 'admin', GETDATE(), 'admin', GETDATE()),
    (3, 3, 3, 3, 'Hatchback ZX', 0, 'FWD', 2023, 'admin', GETDATE(), 'admin', GETDATE()),
    (4, 4, 4, 4, 'Electric EV1', 0, 'RWD', 2025, 'admin', GETDATE(), 'admin', GETDATE()),
    (1, 5, 5, 5, 'Hybrid HVX', 0, 'AWD', 2024, 'admin', GETDATE(), 'admin', GETDATE()),
    (2, 1, 1, 6, 'Truck TX', 0, 'FOUR_WD', 2024, 'admin', GETDATE(), 'admin', GETDATE()),
    (3, 2, 2, 7, 'Van VX', 0, 'FWD', 2022, 'admin', GETDATE(), 'admin', GETDATE()),
    (4, 3, 3, 8, 'Convertible CVX', 0, 'RWD', 2022, 'admin', GETDATE(), 'admin', GETDATE()),
    (5, 4, 4, 9, 'Sports Car SX', 0, 'RWD', 2025, 'admin', GETDATE(), 'admin', GETDATE()),
    (5, 5, 5, 10, 'Luxury LX', 0, 'AWD', 2023, 'admin', GETDATE(), 'admin', GETDATE());
END;

-- =================== car_image ===================
IF NOT EXISTS (SELECT 1 FROM dbo.car_image)
BEGIN
    INSERT INTO dbo.car_image (
        car_id,
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
    (10, 'yellow_banana_intorior_01.png', '/uploads/thumbnail/image/yellow_banana_intorior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_01.png'),
    (10, 'yellow_banana_intorior_01.png', '/uploads/thumbnail/image/yellow_banana_intorior_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/yellow_banana_intorior_01.png');

END;