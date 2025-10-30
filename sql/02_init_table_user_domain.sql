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
        --address             NVARCHAR(255) NOT NULL,
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