package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.CarConfig;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;

import java.util.List;
public interface CarService {
    List<Car> getAll();
    List<Car> getByStatus(CarStatus status);
    List<Car> getByYear(Integer year);
    List<Car> searchByName(String keyword);
    List<Car> getConfigsByCarId(Long carId);

    CarConfig addConfigToCar(Long carId, CarConfig config);
    Car getById(Long id);
    Car getByModel(String model);

    Car create(Car car);
    Car update(Long id, Car car);
    void delete(Long id);


}
