USE evdealer_db

----------------------------------------------INSERT-----------------------------------------


-- ========== 1. COLORS =======================================

INSERT INTO dbo.colors (color_name, color_hex, extra_cost)
VALUES
 ('White Pearl', '#FFFFFF', 0),
 ('Deep Ocean Blue', '#003366', 150),
 ('Sunset Red', '#C8102E', 200),
 ('Midnight Black', '#000000', 100);
GO


-- ========== 2. CATEGORY =====================================
INSERT INTO dbo.category (category_name, created_by, created_on, last_modified_by, last_modified_on)
VALUES
 ('ECO',      N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('PLUS',     N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('PREMIUM',  N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET());
GO


-- ========== 3. BATTERY ======================================
INSERT INTO dbo.battery
(chemistry_type, age, charge_time_sec, usage_duration_sec, weight_kg, voltage_v, capacity_kwh, cycle_life,
 created_by, created_on, last_modified_by, last_modified_on)
VALUES
 ('LFP', 1, 1800, 7200, 350, 400, 75, 3000, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('NCM', 2, 2400, 10800, 380, 450, 82, 2500, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('SolidState', 1, 1500, 14400, 320, 500, 100, 4000, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET());
GO


-- ========== 4. MOTOR ========================================
INSERT INTO dbo.motor
(motor_type, manufacturer, power_kw, torque_nm, max_rpm, cooling_type, voltage_range_v,
 created_by, created_on, last_modified_by, last_modified_on)
VALUES
 ('PERMANENT_MAGNET', 'VF-ECO-001', 150, 300, 9000, 'LIQUID', 450, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('SYNCHRONOUS',      'VF-PLUS-002', 200, 350, 10000, 'LIQUID', 500, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET()),
 ('PERMANENT_MAGNET', 'VF-PREM-003', 250, 420, 11000, 'LIQUID', 550, N'admin', SYSDATETIMEOFFSET(), N'admin', SYSDATETIMEOFFSET());
GO


-- ========== 5. PERFORMANCE ==================================
INSERT INTO dbo.performances
(range_miles, acceleration_sec, top_speed_mph, towing_lbs, battery_id, motor_id)
VALUES
 (250, 8.5, 110, 2000, 1, 1),
 (310, 7.2, 125, 2500, 2, 2),
 (400, 6.0, 140, 3000, 3, 3);
GO


-- ========== 6. DIMENSIONS ===================================
INSERT INTO dbo.dimensions
(weight_lbs, ground_clearance_in, width_folded_in, width_extended_in, height_in, length_mm, length_in, wheels_size_cm)
VALUES
 (4200, 6.5, 70, 78, 64, 4750, 187.0, 48),
 (4400, 7.0, 72, 80, 66, 4850, 191.0, 50),
 (4600, 7.5, 74, 82, 68, 4950, 195.0, 52);
GO


-- ========== 7. CAR ==========================================
INSERT INTO dbo.car
(car_name, price, drive_type, seat_number, [year], dimension_id, performance_id, color_id, category_id, created_at)
VALUES
 ('VinFast VF e34', 39000, 'FWD', 5, 2023, 1, 1, 1, 1, SYSUTCDATETIME()),
 ('VinFast VF 8',   52000, 'AWD', 5, 2024, 2, 2, 2, 2, SYSUTCDATETIME()),
 ('VinFast VF 9',   68000, 'AWD', 7, 2024, 3, 3, 3, 3, SYSUTCDATETIME());
GO


-- ========== 8. CAR_IMAGE ====================================
INSERT INTO dbo.car_image (car_id, file_name, file_path, file_url)
VALUES
 (1, 'white_01.png', '/uploads/thumbnail/image/white_01.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_01.png'),
 (1, 'white_02.png', '/uploads/thumbnail/image/white_02.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_02.png'),
 (1, 'white_03.png', '/uploads/thumbnail/image/white_03.png', 'http://localhost:8000/evdealer/uploads/thumbnail/image/white_03.png');
GO