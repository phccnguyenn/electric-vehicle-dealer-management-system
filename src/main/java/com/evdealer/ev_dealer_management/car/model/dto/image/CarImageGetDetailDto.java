package com.evdealer.ev_dealer_management.car.model.dto.image;


import com.evdealer.ev_dealer_management.car.model.CarImage;

public record CarImageGetDetailDto (
        String fileName,
        String filePath,
        String fileUrl
){
    public static CarImageGetDetailDto fromModel(CarImage carImage) {
        return new CarImageGetDetailDto(
                carImage.getFileName(),
                carImage.getFilePath(),
                carImage.getFileUrl()
        );
    }
}
