USE [auth]
GO
/****** Object:  StoredProcedure [dbo].[sp_register_user]    Script Date: 06-06-2024 18:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


CREATE   PROCEDURE [dbo].[sp_register_user]
@username VARCHAR(100),
@password NVARCHAR(MAX),
@email VARCHAR(200) ,
@fullname VARCHAR(200),
@mobile VARCHAR(20)
AS	
BEGIN 
DECLARE @converted_password VARBINARY(MAX) = CONVERT(VARBINARY(MAX), HashBytes('SHA2_256',@password))
	INSERT INTO dbo.users
	(username, password , email , fullname ,mobile) VALUES
	(@username,@converted_password,@email,@fullname , @mobile )
END
GO
