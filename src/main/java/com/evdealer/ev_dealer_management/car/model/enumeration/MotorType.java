package com.evdealer.ev_dealer_management.car.model.enumeration;

public enum MotorType {
    DC_BRUSHED,
    DC_BRUSHLESS,

    AC_INDUCTION,
    PERMANENT_MAGNET,

    SYNCHRONOUS
}

/*

| Loại động cơ       | Nguồn | Dùng nam châm? | Ưu điểm chính            | Ví dụ xe               |
| ------------------ | ----- | -------------- | ------------------------ | ---------------------- |
| **Brushed DC**     | DC    | Không          | Rẻ, đơn giản             | Xe điện đời cũ         |
| **BLDC**           | DC    | Có             | Hiệu suất cao            | Xe nhỏ, scooter        |
| **Induction (AC)** | AC    | Không          | Bền, mạnh                | Tesla Model S          |
| **PMSM (AC)**      | AC    | Có             | Hiệu suất cao nhất       | Model 3, Ioniq 5       |
| **SRM**            | AC    | Không          | Siêu bền, không nam châm | Xe hybrid, công nghiệp |


 */