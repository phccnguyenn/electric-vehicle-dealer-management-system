package com.evdealer.ev_dealer_management.utils.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileTypeValidator
        implements ConstraintValidator<ValidFileType, MultipartFile> {

    private String[] allowedTypes;
    private String message;

    @Override
    public void initialize(ValidFileType constraintAnnotation) {
        this.allowedTypes = constraintAnnotation.allowedTypes();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {

        if (multipartFile == null || multipartFile.getContentType() == null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        for (String type : allowedTypes) {
            if (type.equals(multipartFile.getContentType())) {
                try {
                    BufferedImage image = ImageIO.read(multipartFile.getInputStream());
                    return image != null;
                } catch (IOException e) {
                    return false;
                }
            }
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return false;
    }
}
