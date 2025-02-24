USE [auth]
GO
/****** Object:  StoredProcedure [dbo].[sp_validate_email]    Script Date: 06-06-2024 18:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[sp_validate_email] 
    @email VARCHAR(100)
AS
BEGIN
DECLARE @count INT
	select @count = count(1) from users where 
	email = @email
	IF @count > 0
	BEGIn 
	SELECT 1 AS validYN
	END
	ELSE
	BEGIn
	SELECT 0 AS validYN
	END
END
GO
