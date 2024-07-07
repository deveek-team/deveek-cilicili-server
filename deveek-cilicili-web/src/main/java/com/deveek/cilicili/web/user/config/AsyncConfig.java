package com.deveek.cilicili.web.user.config;

import com.deveek.common.support.SendMailUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author vocmi
 */
@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "smsExecutor")
    public ThreadPoolTaskExecutor smsThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("SmsExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public SendMailUtil sendMailUtil(){
        return new SendMailUtil();
    }
}
