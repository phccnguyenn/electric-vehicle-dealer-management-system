package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public CarDetailGetDto createCar(CarPostDto carPostDto) {

    }

}
