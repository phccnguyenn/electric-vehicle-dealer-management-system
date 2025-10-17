package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.car.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPatchDto;
import com.evdealer.ev_dealer_management.car.model.dto.car.CarPostDto;
import com.evdealer.ev_dealer_management.car.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return ResponseEntity.ok(carService.getAllCars(pageNo, pageSize));
    }

    @GetMapping("/{carId}/detail")
    public ResponseEntity<CarDetailGetDto> getDetailCarById(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(carService.getDetailCarById(carId));
    }

    @PostMapping("/create")
    public ResponseEntity<CarDetailGetDto> addNewCar(@RequestBody CarPostDto carPostDto) {
        return ResponseEntity.ok(carService.createCar(carPostDto));
    }

    @PostMapping(path = "/{carId}/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDetailGetDto> uploadCarImages(
            @PathVariable(name = "carId") Long carId,
            @RequestPart("files") MultipartFile[] files) {
        return ResponseEntity.ok(carService.uploadImagesForCar(carId, files));
    }

    @PatchMapping("/{carId}/update")
    public ResponseEntity<Void> partialUpdate(@PathVariable(value = "carId") Long carId,
                                              @RequestBody CarPatchDto carPatchDto) {
        carService.updatePartialCarByCarId(carId, carPatchDto);
        return ResponseEntity.noContent().build();
    }

}
