package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.Color;
import com.evdealer.ev_dealer_management.car.model.dto.color.ColorPostDto;
import com.evdealer.ev_dealer_management.car.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public Color createColor(ColorPostDto colorPostDto, Car car) {
        Color color = colorRepository.findByColorName(colorPostDto.colorName())
                .orElseGet(() -> addColor(colorPostDto));

        color.getCars().add(car);
        return colorRepository.save(color);
    }

    private Color addColor(ColorPostDto colorPostDto) {
        Color color = new Color();
        color.setColorName(colorPostDto.colorName());
        color.setColorHex(colorPostDto.colorHex());
        color.setExtraCost(colorPostDto.extraCost());
        return colorRepository.save(color);
    }


}
