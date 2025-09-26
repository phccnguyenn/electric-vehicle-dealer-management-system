package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.CarConfig;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.repository.CarConfigRepository;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor

public class CarServiceImpl implements CarService {


    private final CarRepository carRepository;
    private final CarConfigRepository carConfigRepository;

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getByStatus(CarStatus status) {
        return carRepository.findByStatus(status);
    }

    @Override
    public List<Car> getByYear(Integer year) {
        return carRepository.findByYear(year);
    }

    @Override
    public List<Car> searchByName(String keyword) {
        return carRepository.findByCarNameContainingIgnoreCase(keyword == null ? "" : keyword);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElse(null) ;
    }

    @Override
    public Car getByModel(String model) {
        return null;
    }

    @Override
    public Car create(Car car) {
        return null;
    }

    @Override
    public Car update(Long id, Car car) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
    @Override
    public List<Car> getConfigsByCarId(Long carId) {
        return List.of();
    }

    @Override
    public CarConfig addConfigToCar(Long carId, CarConfig config) {
        return null;
    }
}
