package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import com.evdealer.ev_dealer_management.car.repository.CarModelRepository;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CarModelRepository carModelRepository;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {

        List<RegisterRequest> requests = new ArrayList<>();
        requests.add(RegisterRequest.builder()
                .username("evd.admin")
                .password("00000")
                .fullName("EVD Administrator")
                .email("admin@evdcompany.com")
                .phone("0987654321")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.EVM_ADMIN)
                .level(null)
                .parentPhone(null)
                .build());

        requests.add(RegisterRequest.builder()
                .username("evd.staff001")
                .password("00000")
                .fullName("EVD Staff")
                .email("staff001@evdcompany.com")
                .phone("0986000976")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.EVM_STAFF)
                .level(null)
                .parentPhone(null)
                .build());

        requests.add(RegisterRequest.builder()
                .username("sg.dealer.manager001")
                .password("00000")
                .fullName("Đại lý Sài Gòn")
                .email("sg_dealer_manager001@evdcompany.com")
                .phone("0986194321")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.DEALER_MANAGER)
                .parentPhone(null)
                .level(1)
                .build());

        // 4
        requests.add(RegisterRequest.builder()
                .username("sg.dealer.staff001")
                .password("00000")
                .fullName("Nguyễn Văn Quý")
                .email("dealer_staff001@evdcompany.com")
                .phone("0916777021")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .level(null)
                .role(RoleType.DEALER_STAFF)
                .parentPhone("0986194321")
                .build());

        requests.add(RegisterRequest.builder()
                .username("hn.dealer.manager001")
                .password("00000")
                .fullName("Đại lý Hà Nội")
                .email("hn_dealer_manager001@evdcompany.com")
                .phone("0986094321")
                .city("Thủ đô Hà Nội")
                .isActive(true)
                .level(2)
                .role(RoleType.DEALER_MANAGER)
                .parentPhone(null)
                .build());

        // 6
        requests.add(RegisterRequest.builder()
                .username("hn.dealer.staff001")
                .password("00000")
                .fullName("Nguyễn Văn Bình")
                .email("hn.dealer_staff001@evdcompany.com")
                .phone("0916951021")
                .city("Thủ đô Hà Nội")
                .isActive(true)
                .level(null)
                .role(RoleType.DEALER_STAFF)
                .parentPhone("0986094321")
                .build());

        requests.add(RegisterRequest.builder()
                .username("qn.dealer.manager001")
                .password("00000")
                .fullName("Đại lý Quy Nhơn")
                .email("qn_dealer_manager001@evdcompany.com")
                .phone("0912345678")
                .city("Quy Nhơn")
                .isActive(true)
                .level(3)
                .role(RoleType.DEALER_MANAGER)
                .parentPhone(null)
                .build());

        requests.add(RegisterRequest.builder()
                .username("qn.dealer.staff001")
                .password("00000")
                .fullName("Trần Thị Hạnh")
                .email("qn_dealer_staff001@evdcompany.com")
                .phone("0918765432")
                .city("Quy Nhơn")
                .isActive(true)
                .level(null)
                .role(RoleType.DEALER_STAFF)
                .parentPhone("0912345678")
                .build());

        requests.add(RegisterRequest.builder()
                .username("qn.dealer.staff002")
                .password("00000")
                .fullName("Trần Thị Liên")
                .email("qn_dealer_staff002@evdcompany.com")
                .phone("0900065432")
                .city("Quy Nhơn")
                .isActive(true)
                .level(null)
                .role(RoleType.DEALER_STAFF)
                .parentPhone("0912345678")
                .build());

        requests.add(RegisterRequest.builder()
                .username("hp.dealer.manager001")
                .password("00000")
                .fullName("Đại lý Hải Phòng")
                .email("hp_dealer_manager001@evdcompany.com")
                .phone("0913456789")
                .city("Hải Phòng")
                .isActive(true)
                .level(3)
                .role(RoleType.DEALER_MANAGER)
                .parentPhone(null)
                .build());

        requests.add(RegisterRequest.builder()
                .username("hp.dealer.staff001")
                .password("00000")
                .fullName("Phạm Văn Dũng")
                .email("hp_dealer_staff001@evdcompany.com")
                .phone("0919876543")
                .city("Hải Phòng")
                .level(null)
                .isActive(true)
                .role(RoleType.DEALER_STAFF)
                .parentPhone("0913456789")
                .build());

        try {
            requests.forEach(
                account -> {
                    RegisterResponse response = authService.register(account);
                    log.info("[LOG] - {} account with username {}", account.role(), account.username());
                }
            );
        } catch (DuplicatedException e) {
            log.warn("Accounts already exist — skipping user seeding");
        }


//        fakeCategoryCarData();
//        fakeBatteryData();
//        fakeMotorData();
    }

//    private void fakeCategoryCarData() {
//        List<Category> categories = Arrays.asList(
//                new Category(null, "ECO", null),
//                new Category(null, "PLUS", null),
//                new Category(null, "PREMIUM", null)
//        );
//
//        categories.forEach(category -> {
//            // Kiểm tra trùng categoryType
//            if (categoryRepository.findByCategoryName(category.getCategoryName()).isEmpty()) {
//                categoryRepository.save(category);
//                log.info("[LOG] - Category {} created", category.getCategoryName());
//            } else {
//                log.warn("[WARN] - Category {} already exists", category.getCategoryName());
//            }
//        });
//    }

//    private void fakeBatteryData() {
//        List<Battery> batteries = List.of(
//                Battery.builder()
//                        .chemistryType(ChemistryType.NCA)
//                        .age(1)
//                        .chargeTime(1)
//                        .usageDuration(Duration.ofHours(15))
//                        .weightKg(450f)
//                        .voltageV(400f)
//                        .capacityKwh(75f)
//                        .cycleLife(1200)
//                        .build(),
//                Battery.builder()
//                        .chemistryType(ChemistryType.NCM)
//                        .age(2)
//                        .chargeTime(Duration.ofHours(1))
//                        .usageDuration(Duration.ofHours(30))
//                        .weightKg(460f)
//                        .voltageV(410f)
//                        .capacityKwh(80f)
//                        .cycleLife(1300)
//                        .build(),
//                Battery.builder()
//                        .chemistryType(ChemistryType.LFP)
//                        .age(1)
//                        .chargeTime(Duration.ofHours(2))
//                        .usageDuration(Duration.ofHours(52))
//                        .weightKg(440f)
//                        .voltageV(395f)
//                        .capacityKwh(70f)
//                        .cycleLife(1500)
//                        .build(),
//                Battery.builder()
//                        .chemistryType(ChemistryType.SolidState)
//                        .age(3)
//                        .chargeTime(Duration.ofHours(1))
//                        .weightKg(430f)
//                        .voltageV(420f)
//                        .capacityKwh(85f)
//                        .cycleLife(1800)
//                        .build(),
//                Battery.builder()
//                        .chemistryType(ChemistryType.NCA)
//                        .age(2)
//                        .chargeTime(Duration.ofHours(1))
//                        .weightKg(455f)
//                        .voltageV(405f)
//                        .capacityKwh(78f)
//                        .cycleLife(1250)
//                        .build()
//        );
//
//        batteries.forEach(b -> batteryRepository.save(b));
//    }
//
//    private void fakeMotorData() {
//        List<Motor> motors = List.of(
//                Motor.builder()
//                        .motorType(MotorType.AC_INDUCTION)
//                        .serialNumber("MTR-001")
//                        .powerKw(150f)
//                        .torqueNm(300f)
//                        .maxRpm(15000)
//                        .coolingType(CoolingType.AIR)
//                        .voltageRangeV(400f)
//                        .build(),
//                Motor.builder()
//                        .motorType(MotorType.PERMANENT_MAGNET)
//                        .serialNumber("MTR-002")
//                        .powerKw(200f)
//                        .torqueNm(350f)
//                        .maxRpm(16000)
//                        .coolingType(CoolingType.LIQUID)
//                        .voltageRangeV(420f)
//                        .build(),
//                Motor.builder()
//                        .motorType(MotorType.SYNCHRONOUS)
//                        .serialNumber("MTR-003")
//                        .powerKw(180f)
//                        .torqueNm(320f)
//                        .maxRpm(15500)
//                        .coolingType(CoolingType.OIL)
//                        .voltageRangeV(410f)
//                        .build(),
//                Motor.builder()
//                        .motorType(MotorType.AC_INDUCTION)
//                        .serialNumber("MTR-004")
//                        .powerKw(160f)
//                        .torqueNm(310f)
//                        .maxRpm(15000)
//                        .coolingType(CoolingType.PASSIVE)
//                        .voltageRangeV(400f)
//                        .build(),
//                Motor.builder()
//                        .motorType(MotorType.DC_BRUSHLESS)
//                        .serialNumber("MTR-005")
//                        .powerKw(220f)
//                        .torqueNm(370f)
//                        .maxRpm(16500)
//                        .coolingType(CoolingType.LIQUID)
//                        .voltageRangeV(430f)
//                        .build()
//        );
//
//        motors.forEach(m -> motorRepository.save(m));
//    }

}
