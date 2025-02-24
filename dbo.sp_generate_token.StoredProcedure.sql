USE [auth]
GO
/****** Object:  StoredProcedure [dbo].[sp_generate_token]    Script Date: 06-06-2024 18:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_generate_token] 
    @email VARCHAR(100)
AS
BEGIN
    DECLARE @userid INT
	Declare @token NVARCHAR(MAX) = NEWID();
    SELECT @userid = userid FROM users WHERE email = @email;
    INSERT INTO dbo.forgot_password_table (user_id, token, time_to_expire) 
    VALUES (@userid, @token, DATEADD(mi, 15, GETDATE()));
	
	select @token As GeneratedToken 
END
GO
