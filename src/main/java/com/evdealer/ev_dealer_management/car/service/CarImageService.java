package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.Car;
import com.evdealer.ev_dealer_management.car.model.CarImage;
import com.evdealer.ev_dealer_management.car.repository.CarImageRepository;
import com.evdealer.ev_dealer_management.config.FilesystemPropsConfig;
import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
import com.evdealer.ev_dealer_management.thumbnail.repository.FileSystemRepository;
import com.evdealer.ev_dealer_management.thumbnail.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarImageService {

    private final MediaService mediaService;
    private final FilesystemPropsConfig filesystemPropsConfig;
    private final CarImageRepository carImageRepository;

    public List<CarImage> uploadImageToCar(Car car, List<MediaPostDto> mediaPostDtos) {

        List<CarImage> carImages = new ArrayList<>();
        for (MediaPostDto image : mediaPostDtos) {

            String imageName = (image.fileNameOverride() != null)
                    ? image.fileNameOverride() : image.multipartFile().getOriginalFilename();

            String filePath = filesystemPropsConfig.getDirectory() + imageName;
            String fileUrl = "http://localhost:8000/evdealer/uploads/thumbnail/image/"
                    + UriUtils.encodePathSegment(imageName, StandardCharsets.UTF_8);

            CarImage carImage = CarImage.builder()
                    .car(car)
                    .filePath(filePath)
                    .fileName(imageName)
                    .fileUrl(fileUrl)
                    .build();

            mediaService.addCarImages(image);
            carImages.add(carImageRepository.save(carImage));
        }

        return carImages;
    }

}
