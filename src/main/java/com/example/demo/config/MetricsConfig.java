package com.example.demo.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.micrometer.core.instrument.logging.LoggingMeterRegistry;


@Configuration
public class MetricsConfig {

    @Bean
    public MeterRegistry loggingMeterRegistry() {
        return new LoggingMeterRegistry();
    }
}

