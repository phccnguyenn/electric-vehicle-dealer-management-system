USE evdealer_db;

IF NOT EXISTS (SELECT 1 FROM dbo.rating)
BEGIN
    INSERT INTO dbo.rating (phone, content, created_on)
     VALUES
     -- TP.HCM
     ('0901234567', N'Dịch vụ rất tốt!', SYSDATETIMEOFFSET()),
     ('0902345678', N'Giao xe chậm hơn dự kiến.', SYSDATETIMEOFFSET()),
     ('0903456789', N'Hài lòng với sản phẩm.', SYSDATETIMEOFFSET()),
     ('0904567890', N'Nhân viên hỗ trợ nhiệt tình.', SYSDATETIMEOFFSET()),
     ('0905678901', N'Nhận nhầm sản phẩm.', SYSDATETIMEOFFSET()),
     ('0906789012', N'Phản hồi nhanh chóng.', SYSDATETIMEOFFSET()),
     ('0907890123', N'Cài đặt hơi khó hiểu.', SYSDATETIMEOFFSET()),
     ('0908901234', N'Thái độ nhân viên tốt.', SYSDATETIMEOFFSET()),
     ('0910012345', N'Bao bì sản phẩm bị hư.', SYSDATETIMEOFFSET()),
     ('0911123456', N'Trải nghiệm tổng thể tốt.', SYSDATETIMEOFFSET()),

     -- Hà Nội
     ('0912345678', N'Dịch vụ chăm sóc khách hàng tuyệt vời.', SYSDATETIMEOFFSET()),
     ('0987654321', N'Giao hàng chậm.', SYSDATETIMEOFFSET()),
     ('0911223344', N'Nhân viên hỗ trợ vấn đề nhanh chóng.', SYSDATETIMEOFFSET()),
     ('0999887766', N'Không hài lòng với dịch vụ.', SYSDATETIMEOFFSET()),
     ('0955667788', N'Trải nghiệm tốt, sẽ giới thiệu cho người khác.', SYSDATETIMEOFFSET());
END;
