USE [auth]
GO
/****** Object:  Table [dbo].[forgot_password_table]    Script Date: 06-06-2024 18:38:43 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[forgot_password_table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [int] NULL,
	[token] [nvarchar](max) NULL,
	[time_to_expire] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[forgot_password_table] ADD  DEFAULT (getdate()) FOR [time_to_expire]
GO
ALTER TABLE [dbo].[forgot_password_table]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([userid])
GO
ALTER TABLE [dbo].[forgot_password_table]  WITH CHECK ADD FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([userid])
GO
