package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Color;
import com.evdealer.ev_dealer_management.car.model.dto.color.ColorPostDto;
import com.evdealer.ev_dealer_management.car.repository.ColorRepository;
import com.evdealer.ev_dealer_management.common.exception.NotFoundException;
import com.evdealer.ev_dealer_management.common.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public Color getOrCreateColor(ColorPostDto colorPostDto) {
        return colorRepository.findByColorNameAndColorHexCode(colorPostDto.colorName(), colorPostDto.colorHex())
                .orElseGet(() -> addNewColor(colorPostDto));
    }

    public void updateColor(Long colorId, Car car) {
        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new NotFoundException(Constants.ErrorCode.COLOR_NOT_FOUND, colorId));
        color.getCars().add(car);
        colorRepository.save(color);
    }

    private Color addNewColor(ColorPostDto colorPostDto) {
        Color color = new Color();
        color.setColorName(colorPostDto.colorName());
        color.setColorHexCode(colorPostDto.colorHex());
        color.setExtraCost(colorPostDto.extraCost());
        return colorRepository.save(color);
    }


}
