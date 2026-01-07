package com.evdealer.ev_dealer_management.config;

import com.evdealer.ev_dealer_management.auth.model.dto.RegisterRequest;
import com.evdealer.ev_dealer_management.auth.model.dto.RegisterResponse;
import com.evdealer.ev_dealer_management.user.model.DealerInfo;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.auth.service.AuthService;
import com.evdealer.ev_dealer_management.common.exception.DuplicatedException;
import com.evdealer.ev_dealer_management.user.repository.DealerInfoRepository;
import com.evdealer.ev_dealer_management.warehouse.service.WarehouseService;
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

    private final DealerInfoRepository dealerInfoRepository;
    private final WarehouseService warehouseService;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
//HANG XE
        List<RegisterRequest> requests = new ArrayList<>();
        requests.add(RegisterRequest.builder()
                .username("evd.admin")
                .dealerInfo(null)
                .password("00000")
                .fullName("Lê Lý Thị Mộng")
                .email("admin@evdcompany.com")
                .phone("0987654321")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.EVM_ADMIN)
                .build());

        requests.add(RegisterRequest.builder()
                .username("evd.staff001")
                .password("00000")
                .fullName("Lê Lý Thị Lan")
                .email("staff001@evdcompany.com")
                .phone("0986000976")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.EVM_STAFF)
                .dealerInfo(null)
                .build());
//DAI LY
        DealerInfo sgDealerInfo =  dealerInfoRepository.findById(1L).orElse(null);
        requests.add(RegisterRequest.builder()
                .dealerInfo(sgDealerInfo)
                .username("sg.dealer.manager001")
                .password("00000")
                .fullName("Đặng Tiến Hoàng")
                .email("manager.saigon@evdealer.com")
                .phone("0909123456")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.DEALER_MANAGER)
                .build());

        requests.add(RegisterRequest.builder()
                .dealerInfo(sgDealerInfo)
                .username("sg.dealer.staff001")
                .password("00000")
                .fullName("Nguyễn Văn Quý")
                .email("dealer_staff001@evdcompany.com")
                .phone("0916777021")
                .city("Thành phố Hồ Chí Minh")
                .isActive(true)
                .role(RoleType.DEALER_STAFF)
                .build());

        DealerInfo hnDealerInfo =  dealerInfoRepository.findById(2L).orElse(null);
        requests.add(RegisterRequest.builder()
                .dealerInfo(hnDealerInfo)
                .username("hn.dealer.manager001")
                .password("00000")
                .fullName("Lê Hồng Thị")
                .email("manager.hanoi@evdealer.com")
                .phone("0986094321")
                .city("Thủ đô Hà Nội")
                .isActive(true)
                .role(RoleType.DEALER_MANAGER)
                .build());

        requests.add(RegisterRequest.builder()
                .username("hn.dealer.staff001")
                .dealerInfo(hnDealerInfo)
                .password("00000")
                .fullName("Nguyễn Văn Bình")
                .email("hn.dealer_staff001@evdcompany.com")
                .phone("0916951021")
                .city("Thủ đô Hà Nội")
                .isActive(true)
                .role(RoleType.DEALER_STAFF)
                .build());

        DealerInfo qnDealerInfo =  dealerInfoRepository.findById(3L).orElse(null);
        requests.add(RegisterRequest.builder()
                .dealerInfo(qnDealerInfo)
                .username("qn.dealer.manager001")
                .password("00000")
                .fullName("Lê Ngọc Lan")
                .email("qn_dealer_manager001@evdcompany.com")
                .phone("0986094322")
                .city("TP. Quy Nhơn, Bình Định")
                .isActive(true)
                .role(RoleType.DEALER_MANAGER)
                .build());

        requests.add(RegisterRequest.builder()
                .dealerInfo(qnDealerInfo)
                .username("qn.dealer.staff001")
                .password("00000")
                .fullName("Trần Thị Hạnh")
                .email("qn_dealer_staff001@evdcompany.com")
                .phone("0918765432")
                .city("Quy Nhơn")
                .isActive(true)
                .role(RoleType.DEALER_STAFF)
                .build());

        try {
            requests.forEach(
                account -> {
                    RegisterResponse response = authService.registryAccount(account);
                    log.info("[LOG] - {} account with username {}", account.role(), account.username());
                }
            );
        } catch (DuplicatedException e) {
            log.warn("Accounts already exist — skipping user seeding");
        }

//        warehouseService.seedCarModelInWarehouse();
    }

}
