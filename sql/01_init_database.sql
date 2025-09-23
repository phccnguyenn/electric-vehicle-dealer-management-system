IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'evdealer_db')
BEGIN
    CREATE DATABASE evdealer_db;
END;
GO