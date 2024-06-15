package com.deveek.cilicili.web.user.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author harvey 
 */
@Configuration
public class BloomFilterConfig {
    @Bean
    public RBloomFilter userBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("user:bloomfilter:use_id");
        bloomFilter.tryInit(100000, 0.01);
        return bloomFilter;
    }
}
