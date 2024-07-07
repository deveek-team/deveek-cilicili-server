package com.deveek.cilicili.web.user.config;

import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.deveek.cilicili.web.common.bloomfilter.BloomFilterConstant.BLOOM_FILTER_USER_ID;
import static com.deveek.cilicili.web.common.bloomfilter.BloomFilterConstant.BLOOM_FILTER_USERNAME;

/**
 * @author harvey 
 */
@Configuration
public class BloomFilterConfig {
    @Bean
    public RBloomFilter userBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(BLOOM_FILTER_USER_ID);
        bloomFilter.tryInit(100000, 0.01);
        return bloomFilter;
    }

    @Bean
    public RBloomFilter usernameBloomFilter(RedissonClient redissonClient) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(BLOOM_FILTER_USERNAME);
        bloomFilter.tryInit(100000, 0.01);
        return bloomFilter;
    }
}
