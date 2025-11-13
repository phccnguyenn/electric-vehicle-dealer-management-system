package com.evdealer.ev_dealer_management.car.service;

import com.evdealer.ev_dealer_management.car.model.CarDetail;
import com.evdealer.ev_dealer_management.car.model.CarImage;
import com.evdealer.ev_dealer_management.car.repository.CarImageRepository;
import com.evdealer.ev_dealer_management.thumbnail.repository.FileSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarImageService {

    private final static String URL_BASE = "http://54.206.57.206:8000/evdealer";
    private final FileSystemRepository fileSystemRepository;
    private final CarImageRepository carImageRepository;

    public List<CarImage> uploadImageToCar(CarDetail carDetail, MultipartFile[] files) {

        List<CarImage> carImages = new ArrayList<>();

        Arrays.stream(files).forEach(
            file -> {

                String filePath;

                try {
                    filePath = fileSystemRepository.persistFile(file.getOriginalFilename(), file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("File error with " + e);
                }

                String filePathStr = filePath.substring(1).replace("\\", "/");
                String fileUrl = URL_BASE + filePathStr;
                CarImage carImage = new CarImage();
                carImage.setFileName(file.getOriginalFilename());
                carImage.setFilePath(filePathStr);
                carImage.setFileUrl(fileUrl);
                carImage.setCarDetail(carDetail);

                carImages.add(carImage);
            }
        );

        return carImageRepository.saveAll(carImages);
    }

}
