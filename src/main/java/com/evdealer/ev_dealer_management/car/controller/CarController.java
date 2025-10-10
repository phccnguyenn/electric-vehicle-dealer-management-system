package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
import com.evdealer.ev_dealer_management.car.service.CarService;
import com.evdealer.ev_dealer_management.config.SecurityConfig;
import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping("/create")
    public ResponseEntity<CarDetailGetDto> addNewCar(
             @RequestPart("car") CarPostDto carPostDto,
             @RequestPart("media") MediaPostDto mediaPostDto) {
        return ResponseEntity.ok(carService.createCar(carPostDto, mediaPostDto));
    }

}
