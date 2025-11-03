USE evdealer_db;

IF NOT EXISTS (SELECT 1 FROM dbo.category)
BEGIN
INSERT INTO dbo.customer (dealer_id, full_name, email, phone, address, created_by, created_on, last_modified_by, last_modified_on)
VALUES
-- TP.HCM
(3, N'Nguyễn Thị Mai', 'mai.nguyen@example.com', '0901234567', N'12 Lê Lợi, Quận 1, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Trần Văn Hưng', 'hung.tran@example.com', '0902345678', N'34 Nguyễn Huệ, Quận 1, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Lê Thị Hồng', 'hong.le@example.com', '0903456789', N'56 Hai Bà Trưng, Quận 3, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Phạm Văn Nam', 'nam.pham@example.com', '0904567890', N'78 Võ Thị Sáu, Quận 3, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Đặng Thị Phương', 'phuong.dang@example.com', '0905678901', N'90 Cách Mạng Tháng 8, Quận 10, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Hoàng Văn Tuấn', 'tuan.hoang@example.com', '0906789012', N'123 Lý Thường Kiệt, Quận 10, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Ngô Thị Lan', 'lan.ngo@example.com', '0907890123', N'456 Trường Chinh, Tân Bình, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Võ Văn Cường', 'cuong.vo@example.com', '0908901234', N'789 Hoàng Văn Thụ, Tân Bình, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Bùi Thị Hạnh', 'hanh.bui@example.com', '0910012345', N'321 Phan Xích Long, Phú Nhuận, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(3, N'Trịnh Văn Long', 'long.trinh@example.com', '0911123456', N'654 Nguyễn Văn Trỗi, Phú Nhuận, TP.HCM', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),

-- HN
(4, N'Nguyễn Văn Tiến', 'tien.nguyen@example.com', '0912345678', N'123 Lê Duẩn, Hà Nội', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(4, N'Trần Thị Lan', 'lan.tran@example.com', '0987654321', N'456 Nguyễn Trãi, Hà Nội', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(4, N'Lê Minh Tuấn', 'tuan.le@example.com', '0911223344', N'789 Phạm Hùng, Hà Nội', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(4, N'Phạm Thị Hương', 'huong.pham@example.com', '0999887766', N'321 Trần Hưng Đạo, Hà Nội', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET()),
(4, N'Đỗ Văn Quang', 'quang.do@example.com', '0955667788', N'654 Hoàng Hoa Thám, Hà Nội', 'system', SYSDATETIMEOFFSET(), 'system', SYSDATETIMEOFFSET());
END;
GO