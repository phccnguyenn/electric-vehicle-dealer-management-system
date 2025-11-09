package com.evdealer.ev_dealer_management.testdrive.service;

import com.evdealer.ev_dealer_management.car.model.CarModel;
import com.evdealer.ev_dealer_management.car.service.CarModelService;
import com.evdealer.ev_dealer_management.testdrive.model.CarModelInSlot;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.testdrive.model.dto.CarModelInSlotDetailDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.CarModelInSlotPostDto;
import com.evdealer.ev_dealer_management.testdrive.repository.CarModelInSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarModelInSlotService {

    private final CarModelService carModelService;
    private final CarModelInSlotRepository carModelInSlotRepository;

//    public CarModelInSlotDetailDto createCarModelInSlot(Slot slot, CarModelInSlotPostDto carModelInSlotPostDto) {
//        CarModel carModel = carModelService.getCarModelById(carModelInSlotPostDto.carModelId(), CarModel.class);
//
//        CarModelInSlot carModelInSlot = new CarModelInSlot();
//        carModelInSlot.setSlot(slot);
//        carModelInSlot.setCarModel(carModel);
//        carModelInSlot.setMaxTrialCar(carModelInSlotPostDto.maxTrialCar());
//
//        return CarModelInSlotDetailDto.fromModel(carModelInSlotRepository.save(carModelInSlot));
//    }

    public CarModelInSlot createCarModelInSlot(Slot slot, CarModelInSlotPostDto carModelInSlotPostDto) {
        CarModel carModel = carModelService.getCarModelById(carModelInSlotPostDto.carModelId(), CarModel.class);

        CarModelInSlot carModelInSlot = new CarModelInSlot();
        carModelInSlot.setSlot(slot);
        carModelInSlot.setCarModel(carModel);
        carModelInSlot.setMaxTrialCar(carModelInSlotPostDto.maxTrialCar());

        return carModelInSlotRepository.save(carModelInSlot);
    }

}
