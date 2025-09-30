package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.CarConfig;
import com.evdealer.ev_dealer_management.car.model.enumeration.CarStatus;
import com.evdealer.ev_dealer_management.car.repository.CarConfigRepository;
import com.evdealer.ev_dealer_management.car.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;



@Service
@Transactional
@RequiredArgsConstructor

public class CarServiceImpl  {


    private final CarRepository carRepository;
    private final CarConfigRepository carConfigRepository;


    public List<Car> filter(CarStatus status, Integer year,String keyword ) {
            Specification<Car> specification = (root, query1 ,cb) ->
            {
                List<Predicate> predicates = new ArrayList<>();
                if(status != null) {
                    predicates.add(cb.equal(root.get("status"), status));
                }
                if(year != null) {
                    predicates.add(cb.equal(root.get("year"), year));

                }
                if(keyword != null) {
                    predicates.add(cb.like(cb.lower(root.get("carName")), "%" + keyword.toLowerCase() + "%"));
                }

                return cb.and(predicates.toArray(new Predicate[0]));
            } ;
        return carRepository.findAll(specification);
    }



    public Car getById(Long id) {
        return carRepository.findById(id).orElse(null) ;
    }


    public Car getByModel(String model) {
        return carRepository.findByCarModelIgnoreCase(model);
    }


    public Car create(Car car) {
        return carRepository.save(car);
    }


    public Car update(Long id, Car car) {
        return carRepository.findById(id).map (existingCar -> {
            existingCar.setCarName(car.getCarName());
            existingCar.setCarModel(car.getCarModel());
            existingCar.setDescription(car.getDescription());
            existingCar.setStatus(car.getStatus());
            return carRepository.save(existingCar);
        }).orElse(null);
    }


    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    public List<CarConfig> getConfigsByCarId(Long carId) {
        return carConfigRepository.findByCarId(carId);
    }

//
//    public CarConfig addConfigToCar(Long carId, CarConfig config) {
//        return carRepository.findById(carId).map(car -> {
//            config.setCar(car);
//            return carConfigRepository.save(config);
//        }).orElse(null);
//    }
}
