package com.evdealer.ev_dealer_management.testdrive.service;

import com.evdealer.ev_dealer_management.common.exception.NoPermissionException;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import com.evdealer.ev_dealer_management.testdrive.model.CarModelInSlot;
import com.evdealer.ev_dealer_management.testdrive.model.Slot;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotDetailsGetDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotPostDto;
import com.evdealer.ev_dealer_management.testdrive.model.dto.SlotUpdateDto;
import com.evdealer.ev_dealer_management.testdrive.repository.SlotRepository;
import com.evdealer.ev_dealer_management.user.model.User;
import com.evdealer.ev_dealer_management.user.model.enumeration.RoleType;
import com.evdealer.ev_dealer_management.user.repository.UserRepository;
import com.evdealer.ev_dealer_management.user.service.DealerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SlotService {

    private final UserRepository userRepository;
    private final DealerService dealerService;
    private final CarModelInSlotService carModelInSlotService;
    private final SlotRepository slotRepository;

    public List<SlotDetailsGetDto> getAllSlotsByCurrentDealer() {

        User dealer = dealerService.getCurrentUser();

        return slotRepository.findAllByDealerInfo(dealer.getDealerInfo())
                .stream()
                .map(SlotDetailsGetDto::fromModel)
                .toList();
    }

    public List<SlotDetailsGetDto> getAllOngoingSlots() {
        return slotRepository.findAllOngoingSlots()
                .stream()
                .map(SlotDetailsGetDto::fromModel)
                .toList();
    }

    public <T> T getSlotById(Long slotId, Class<T> type) {
        Slot slot = slotRepository.findById(slotId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.SLOT_NOT_FOUND, slotId));

        if (type.equals(Slot.class)) {
            return type.cast(slot);
        } else if (type.equals(SlotDetailsGetDto.class)) {
            return type.cast(SlotDetailsGetDto.fromModel(slot));
        } else {
            throw new IllegalArgumentException("Unsupported return type: " + type);
        }
    }

    @Transactional
    public SlotDetailsGetDto createSlot(SlotPostDto slotPostDto) {

        // Get the current dealer manager
        User dealerManager = dealerService.getCurrentUser();
        if (!dealerManager.getRole().equals(RoleType.DEALER_MANAGER)) {
            throw new NoPermissionException("You do not have permission for this function");
        }

        // Get Dealer Staff is in charge with this slot
        User dealerStaff = userRepository.findById(slotPostDto.dealerStaffId())
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.USER_NOT_FOUND, slotPostDto.dealerStaffId()));

        validateSlotTimeRange(slotPostDto.startTime(), slotPostDto.endTime());

        Slot slot = Slot.builder()
                .dealerInfo(dealerManager.getDealerInfo())
                .dealerStaff(dealerStaff)
                .numCustomers(slotPostDto.numCustomers())
                .startTime(slotPostDto.startTime())
                .endTime(slotPostDto.endTime())
                .build();

        Slot savedSlot = slotRepository.save(slot);

        CarModelInSlot carModelInSlot = carModelInSlotService.createCarModelInSlot(savedSlot, slotPostDto.carModelInSlotPostDto());
        savedSlot.getCarModelInSlots().add(carModelInSlot);
        return SlotDetailsGetDto.fromModel(slotRepository.save(savedSlot));
    }

    public SlotDetailsGetDto updateSlot(Long slotId, SlotUpdateDto slotUpdateDto) {

        Slot slot = getSlotById(slotId, Slot.class);

        validateSlotTimeRange(slotUpdateDto.newStartTime(), slotUpdateDto.newEndTime());

        if (slotUpdateDto.newStartTime() != null
                && !slotUpdateDto.newStartTime().equals(slot.getStartTime())) {
            slot.setStartTime(slotUpdateDto.newStartTime());
        }

        if (slotUpdateDto.newEndTime() != null
                && !slotUpdateDto.newEndTime().equals(slot.getEndTime())) {
            slot.setEndTime(slotUpdateDto.newEndTime());
        }

        if (slotUpdateDto.newNumCustomers() != null
                && !slotUpdateDto.newNumCustomers().equals(slot.getNumCustomers())) {
            slot.setNumCustomers(slotUpdateDto.newNumCustomers());
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
