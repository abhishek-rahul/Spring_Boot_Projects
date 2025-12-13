package com.abhicom.userservice.controller;

import com.abhicom.userservice.config.AppProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

    private final AppProperties appProperties; // constructor injection

    public ConfigController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/config/env")
    public String getEnv() {
        // returns "dev" or "prod" based on active profile
        return appProperties.getEnv();
    }

    @GetMapping("/config/app-name")
    public String getAppName() {
        return appProperties.getName();
    }
}
