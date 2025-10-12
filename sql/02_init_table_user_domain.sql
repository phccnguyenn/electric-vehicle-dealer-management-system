USE evdealer_db

--========================== users ==================================

IF OBJECT_ID('dbo.users', 'U') IS NOT NULL DROP TABLE dbo.users;
GO
CREATE TABLE dbo.users (
    user_id             BIGINT IDENTITY(1,1) PRIMARY KEY,
    username            VARCHAR(100)  NOT NULL UNIQUE,
    hashed_password     VARCHAR(255)  NOT NULL,
    full_name           NVARCHAR(150) NOT NULL,
    email               VARCHAR(150)  NOT NULL UNIQUE,
    phone               VARCHAR(20)   NOT NULL UNIQUE,
    is_active           BIT           NOT NULL DEFAULT 1,
    role                VARCHAR(50)   NULL,
    created_by          NVARCHAR(100) NULL,
    created_on          DATETIMEOFFSET NULL,
    last_modified_by    NVARCHAR(100) NULL,
    last_modified_on    DATETIMEOFFSET NULL
);
GO

--========================== tokens==================================


IF OBJECT_ID('dbo.tokens', 'U') IS NOT NULL DROP TABLE dbo.tokens;
GO
CREATE TABLE dbo.tokens (
    id           INT IDENTITY(1,1) PRIMARY KEY,
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
GO