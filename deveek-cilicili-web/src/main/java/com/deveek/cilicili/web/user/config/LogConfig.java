package com.deveek.cilicili.web.user.config;

import com.deveek.log.aspect.ApiLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author harvey
 * @Email harveysuen0803@gmail.com
 * @Date 2024-06-01
 */
@Configuration
public class LogConfig {
    @Bean
    public ApiLogAspect apiLogAspect() {
        return new ApiLogAspect();
    }
}
