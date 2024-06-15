package com.deveek.cilicili.web.user.config;

import com.deveek.log.aspect.ApiLogAspect;
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
