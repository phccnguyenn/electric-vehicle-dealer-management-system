package com.evdealer.ev_dealer_management.car.controller;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.service.CarService;
//import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping()
    public List<Car> getAllCars(@RequestParam(required = false) CarStatus carStatus ,
                                @RequestParam(required = false) Integer year) {
        if (carStatus != null) {
            return carService.getByStatus(carStatus);
        }
        if (year != null) {
            return carService.getByYear(year);
        }
        return carService.getAll();
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Integer id) {
        return carService.getById(Long.valueOf(id));
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
    public void deleteCar(@PathVariable Integer id) {
        carService.delete(Long.valueOf(id));

    }
    @GetMapping("/{carId}/configs")
    public List<Car> getCarConfigs(@PathVariable Long carId) {
       return carService.getConfigsByCarId(carId) ;
    }

    @GetMapping("/search")
    public List<Car> searchCarsByName(@RequestParam String keyword) {
        return carService.searchByName(keyword);
    }


}
