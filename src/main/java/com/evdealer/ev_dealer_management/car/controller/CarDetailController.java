package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.details.CarDetailGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.details.CarListGetDto;
import com.evdealer.ev_dealer_management.car.model.dto.details.CarPatchDto;
import com.evdealer.ev_dealer_management.car.model.dto.details.CarDetailPostDto;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.service.CarDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/carDetail")
@RequiredArgsConstructor
@Tag(name = "Cars", description = "Car management APIs")
public class CarDetailController {

    private final CarDetailService carDetailService;

    @GetMapping("/all")
    public ResponseEntity<CarListGetDto> getAllCars(
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(carDetailService.getAllCars(pageNo, pageSize));
    }

    @GetMapping("/filter-with-carDetail-model/{carModelId}")
    public ResponseEntity<CarListGetDto> getAllDetailCarsByCarModelId(
            @PathVariable(name = "carModelId") Long carModelId,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return ResponseEntity.ok(carDetailService.getAllCarsByCarModelId(carModelId, pageNo, pageSize));
    }

    @GetMapping("/{carId}/detail")
    public ResponseEntity<CarDetailGetDto> getDetailCarById(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(carDetailService.getDetailCarById(carId));
    }

    @GetMapping("/random-detail-carDetail")
    public ResponseEntity<CarDetailGetDto> getOneRandomSpecCar(
            @RequestParam(required = false) Long carModelId,
            @RequestParam(required = false) CarStatus carDetailStatus,
            @RequestParam(required = false) String carColor
    ) {
        return ResponseEntity.ok(carDetailService
                .getOneRandomCarDetail(
                        carModelId,
                        carColor,
                        carDetailStatus));
    }

    @PostMapping("/create")
    public ResponseEntity<CarDetailGetDto> addNewCar(@Valid @RequestBody CarDetailPostDto carDetailPostDto) {
        return ResponseEntity.ok(carDetailService.createCar(carDetailPostDto));
    }

    @PostMapping(path = "/{carId}/upload/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarDetailGetDto> uploadCarImages(
            @PathVariable(name = "carId") Long carId,
            @RequestPart("files") MultipartFile[] files) {
        return ResponseEntity.ok(carDetailService.uploadImagesForCar(carId, files));
    }

    @PatchMapping("/{carId}/update")
    public ResponseEntity<Void> partialUpdate(@PathVariable(value = "carId") Long carId,
                                              @RequestBody CarPatchDto carPatchDto) {
        carDetailService.updatePartialCarByCarId(carId, carPatchDto);
        return ResponseEntity.noContent().build();
    }

}
