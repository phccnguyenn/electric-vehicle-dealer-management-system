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