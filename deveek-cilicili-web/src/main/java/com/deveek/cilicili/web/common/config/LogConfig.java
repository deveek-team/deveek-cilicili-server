package com.deveek.cilicili.web.common.config;

import com.deveek.common.aspect.ApiLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author harvey 
 */
@Configuration
public class LogConfig {
    @Bean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }
}
