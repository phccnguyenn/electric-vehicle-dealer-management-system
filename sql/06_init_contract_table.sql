USE evdealer_db;

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'dbo.contract') AND type = 'U')
BEGIN
    CREATE TABLE contract (
        contract_id         BIGINT PRIMARY KEY AUTO_INCREMENT,   -- Khóa chính, tự động tăng (ID duy nhất của hợp đồng)

        contract_code       VARCHAR(50) NOT NULL UNIQUE,          -- Mã hợp đồng (ví dụ: "C2025-001"), duy nhất để dễ tra cứu
        contract_date       DATE NOT NULL,                        -- Ngày ký hợp đồng
        delivery_date       DATE,                                 -- Ngày dự kiến giao xe cho khách
        expiration_date     DATE,                                 -- Ngày hết hiệu lực của hợp đồng (nếu có)

        total_amount        DECIMAL(15,2),                        -- Tổng giá trị hợp đồng (chưa trừ giảm giá, chưa tính thuế)
        discount_amount     DECIMAL(15,2),                        -- Số tiền giảm giá (nếu có)
        tax_amount          DECIMAL(15,2),                        -- Thuế VAT hoặc các loại phí kèm theo
        final_amount        DECIMAL(15,2),                        -- Số tiền khách phải trả sau khi trừ giảm giá và cộng thuế

        payment_method      VARCHAR(30),                          -- Hình thức thanh toán: 'cash', 'transfer', 'loan', v.v.
        payment_status      VARCHAR(20),                          -- Trạng thái thanh toán: 'Pending', 'Paid', 'Partial'

        status              VARCHAR(20),                          -- Trạng thái hợp đồng: 'Active', 'Cancelled', 'Completed'
        notes               TEXT,                                 -- Ghi chú thêm (tùy chọn)

        dealer_id           BIGINT NOT NULL,                      -- FK: Đại lý phụ trách hợp đồng
        customer_id         BIGINT NOT NULL,                      -- FK: Khách hàng mua xe
        salesperson_id      BIGINT,                               -- FK: Nhân viên bán hàng (tùy chọn)

        created_by            NVARCHAR(100) NOT NULL,
        created_on            DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
        last_modified_by      NVARCHAR(100) NOT NULL,
        last_modified_on      DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),

        -- Khóa ngoại liên kết tới bảng dealer và customer
        FOREIGN KEY (dealer_id) REFERENCES dealer(dealer_id),
        FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
    );
END;