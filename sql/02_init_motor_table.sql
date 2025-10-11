
USE evdealer_db;

CREATE TABLE motor (
    motor_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    motor_type NVARCHAR(50),
    manufacturer NVARCHAR(255) UNIQUE,
    power_kw FLOAT,
    torque_nm FLOAT,
    max_rpm INT,
    cooling_type NVARCHAR(50),
    voltage_range_v FLOAT
);
