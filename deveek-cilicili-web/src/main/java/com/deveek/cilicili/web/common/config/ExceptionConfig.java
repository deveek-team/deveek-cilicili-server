package com.deveek.cilicili.web.common.config;

import com.deveek.common.support.GlobalExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author harvey 
 */
@Configuration
public class ExceptionConfig {
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
