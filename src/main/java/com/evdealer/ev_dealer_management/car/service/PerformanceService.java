package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Performance;
import com.evdealer.ev_dealer_management.car.model.dto.performance.PerformancePostDto;
import com.evdealer.ev_dealer_management.car.repository.PerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    public Performance createPerformance(PerformancePostDto performancePostDto) {

        Performance performance = Performance.builder()
                .batteryType(performancePostDto.batteryType())
                .motorType(performancePostDto.motorType())
                .rangeMiles(performancePostDto.rangeMiles())
                .accelerationSec(performancePostDto.accelerationSec())
                .topSpeedMph(performancePostDto.topSpeedMph())
                .towingLbs(performancePostDto.towingLbs())
                .build();

        return performanceRepository.save(performance);
    }

}
