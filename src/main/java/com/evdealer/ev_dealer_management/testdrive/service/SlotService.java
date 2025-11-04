package com.evdealer.ev_dealer_management.testdrive.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.service.CarService;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotDetailsGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotPostDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotUpdateDto;
import com.evdealer.ev_dealer_management.testdrive.repository.SlotRepository;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SlotService {

    private final DealerService dealerService;
    private final CarService carService;
    private final SlotRepository slotRepository;

    public List<SlotDetailsGetDto> getAllSlotByDealer() {
        // User dealer = dealerService.getCurrentUser();
        return slotRepository.findAll()
                .stream()
                .map(SlotDetailsGetDto::fromModel)
                .toList();
    }

    @Transactional
    public SlotDetailsGetDto createSlot(SlotPostDto dto) {

        validateSlotTimeRange(dto.startTime(), dto.endTime());
        User dealer = dealerService.getCurrentUser();
        Car car = carService.getCarById(dto.carId());

        Slot slot = new Slot();
        slot.setDealer(dealer);
        slot.setCar(car);
        slot.setAmount(dto.amount());
        slot.setStartTime(dto.startTime());
        slot.setEndTime(dto.endTime());

        return SlotDetailsGetDto.fromModel(slotRepository.save(slot));
    }

    public SlotDetailsGetDto updateSlot(SlotUpdateDto slotUpdateDto) {

        validateSlotTimeRange(slotUpdateDto.newStartTime(), slotUpdateDto.newEndTime());

        Slot slot = slotRepository.findById(slotUpdateDto.slotId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.SLOT_NOT_FOUND, slotUpdateDto.slotId()));


        if (slotUpdateDto.newStartTime() != null
                && !slotUpdateDto.newStartTime().equals(slot.getStartTime())) {
            slot.setStartTime(slotUpdateDto.newStartTime());
        }

        if (slotUpdateDto.newEndTime() != null
                && !slotUpdateDto.newEndTime().equals(slot.getEndTime())) {
            slot.setEndTime(slotUpdateDto.newEndTime());
        }

        if (slotUpdateDto.newAmount() != null
                && !slotUpdateDto.newAmount().equals(slot.getAmount())) {
            slot.setAmount(slotUpdateDto.newAmount());
        }

        return SlotDetailsGetDto.fromModel(slotRepository.save(slot));
    }

    public void deleteSlot(Long slotId) {

        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.SLOT_NOT_FOUND, slotId));

        slotRepository.delete(slot);
    }

    private void validateSlotTimeRange(OffsetDateTime startTime, OffsetDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time must not be null");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }

}
