USE evdealer_db;

IF NOT EXISTS (SELECT 1 FROM dbo.battery)
BEGIN

END;