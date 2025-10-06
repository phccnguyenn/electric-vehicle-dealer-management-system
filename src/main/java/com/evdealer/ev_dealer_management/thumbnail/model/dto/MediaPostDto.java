package com.evdealer.ev_dealer_management.thumbnail.model.dto;

import com.evdealer.ev_dealer_management.utils.annotation.ValidFileType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record MediaPostDto (

        String caption,

        @NotNull
        @ValidFileType(
                allowedTypes = {"image/jpeg", "image/png", "image/gif"},
                message = "File type not allowed. Allowed types are: JPEG, PNG, GIF"
        )
        MultipartFile multipartFile,

        String fileNameOverride
) {
}
