package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
import com.evdealer.ev_dealer_management.car.service.CarService;
import com.evdealer.ev_dealer_management.config.SecurityConfig;
import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailGetDto> getDetailCarById(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(carService.getDetailCarById(carId));
    }

    @PostMapping("/create")
    public ResponseEntity<CarDetailGetDto> addNewCar(
             @RequestPart("car") CarPostDto carPostDto,
             @RequestPart("file") MultipartFile[] files) {

        List<MediaPostDto> medias = new ArrayList<>();
        for (MultipartFile file : files) {
            medias.add(new MediaPostDto(null, file, null));
        }

        return ResponseEntity.ok(carService.createCar(carPostDto, medias));
    }

}
