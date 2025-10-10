package com.evdealer.ev_dealer_management.thumbnail.service;

import com.evdealer.ev_dealer_management.thumbnail.model.dto.MediaPostDto;
import com.evdealer.ev_dealer_management.thumbnail.repository.FileSystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class MediaService {

    private final FileSystemRepository fileSystemRepository;

    public MediaService(FileSystemRepository fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
    }

    public void addCarImages(MediaPostDto mediaPostDto) {

        MultipartFile file = mediaPostDto.multipartFile();
        if (file.isEmpty()) return;

        String filename = mediaPostDto.fileNameOverride() != null
                ? mediaPostDto.fileNameOverride()
                : file.getOriginalFilename();

        try {
            String savedPath = fileSystemRepository.persistFile(filename, file.getBytes());
            log.info("Saved image: {} with caption: {}", savedPath, mediaPostDto.caption());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

}
