package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
import com.evdealer.ev_dealer_management.car.service.CarService;
import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/all")
    public ResponseEntity<CarListGetDto> getAllCars(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(carService.getAllCourses(pageNo, pageSize));
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarDetailGetDto> getDetailCarById(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(carService.getDetailCarById(carId));
    }

    @PostMapping("/new")
    public ResponseEntity<CarDetailGetDto> addNewCar(@RequestBody CarPostDto carPostDto) {
        return ResponseEntity.ok(carService.createCar(carPostDto));
    }

    @PostMapping(path = "/{carId}/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDetailGetDto> uploadCarImages(
            @PathVariable(name = "carId") Long carId,
            @RequestPart("files") MultipartFile[] files) {

        List<MediaPostDto> medias = new ArrayList<>();
        for (MultipartFile file : files) {
            medias.add(new MediaPostDto(null, file, null));
        }

        return ResponseEntity.ok(carService.uploadCarImages(carId, medias));
    }

}
