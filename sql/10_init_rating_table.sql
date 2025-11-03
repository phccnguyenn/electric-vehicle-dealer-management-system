USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.rating') AND type = 'U')
BEGIN
    CREATE TABLE dbo.rating (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        phone NVARCHAR(50) NOT NULL,
        content NVARCHAR(MAX) NULL,
        created_on DATETIMEOFFSET NULL
    );
END;
GO