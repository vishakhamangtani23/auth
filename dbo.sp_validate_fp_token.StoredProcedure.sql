USE [auth]
GO
/****** Object:  StoredProcedure [dbo].[sp_validate_fp_token]    Script Date: 06-06-2024 18:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE     PROCEDURE [dbo].[sp_validate_fp_token]
@token VARCHAR(MAX)
AS
BEGIN
	DECLARE @count INT
	SELECT @count = COUNT(1) FROM dbo.forgot_password_table where 
 token = @token  and time_to_expire > GETDATE()
	IF (@count = 1)
		BEGIN
			SELECT user_id , 1 as validYN  , token 
			FROM dbo.forgot_password_table where token = @token
		END
	ELSE
		BEGIN
			SELECT 0 as validYN FROM dbo.forgot_password_table  where token = @token
		END
END
GO
