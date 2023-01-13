package com.example.demo.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;

import io.micrometer.core.instrument.logging.LoggingMeterRegistry;
import io.micrometer.core.instrument.MeterRegistry;


@Configuration
public class MicrometerConfig {

  @Bean
  public MeterRegistry loggingMeterRegistry() {
        return new LoggingMeterRegistry();
  }
}

