package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelGetDetailDto;
import com.evdealer.ev_dealer_management.car.model.dto.model.CarModelInfoGetDto;
import com.evdealer.ev_dealer_management.car.service.CarModelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car-model")
@RequiredArgsConstructor
@Tag(name = "Cars", description = "Car management APIs")
public class CarModelController {

    private final CarModelService carModelService;

    @GetMapping("/all")
    public ResponseEntity<List<CarModelInfoGetDto>> getAllCarModels() {
        return ResponseEntity.ok(carModelService.getAllCarModel());
    }

    @GetMapping("/get-trial-model-car")
    public ResponseEntity<List<CarModelInfoGetDto>> getTrialCarModel() {
        return ResponseEntity.ok(carModelService.getTrialCarModel());
    }

    @PostMapping("/create")
    public ResponseEntity<CarModelGetDetailDto> addNewCarModel(@RequestParam(name = "carModelName") String carModelName) {
        return ResponseEntity.ok(carModelService.addNewCarModel(carModelName));
    }

    @DeleteMapping("/remove/{carModelId}")
    public ResponseEntity<Void> removeCarModelById(@PathVariable(name = "carModelId") Long carModelId) {
        carModelService.removeCarModelById(carModelId);
        return ResponseEntity.noContent().build();
    }

}
