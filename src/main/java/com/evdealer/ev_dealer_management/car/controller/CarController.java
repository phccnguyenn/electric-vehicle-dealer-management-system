package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {

        this.carService = carService;
    }

    @GetMapping
    public List<Car> list(
            @RequestParam(required = false) CarStatus status,
            @RequestParam(required = false) Integer year,
            @RequestParam(name = "q", required = false) String q
    ) {
        return carService.filter(status, year, q);
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {

        return carService.getById(id);
    }

    @GetMapping("/model/{model}")
    public Car getCarByModel(@PathVariable String model) {

        return carService.getByModel(model);
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {

            return carService.create(car);
    }

    @PutMapping("/{id}")
    public Car updateCar(@PathVariable Integer id, @RequestBody Car car) {
        return carService.update(Long.valueOf(id), car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carService.delete(id);

    }
    @GetMapping("/{carId}/configs")
    public List<CarConfig> getCarConfigs(@PathVariable Long carId) {

        return carService.getConfigsByCarId(carId) ;
    }


}
