package com.autoever.messageconsumerapp.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.autoever.messageconsumerapp.external")
@Configuration
public class FeignClientConfig {
}
