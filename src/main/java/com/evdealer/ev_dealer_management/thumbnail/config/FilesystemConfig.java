package com.evdealer.ev_dealer_management.thumbnail.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class FilesystemConfig {

    @Value("${file.directory}")
    private String directory;

}
