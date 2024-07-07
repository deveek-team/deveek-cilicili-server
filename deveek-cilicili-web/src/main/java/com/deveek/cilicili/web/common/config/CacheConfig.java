package com.deveek.cilicili.web.common.config;

import com.deveek.cache.bloomfilter.BloomFilterCheckAspect;
import com.deveek.cache.bloomfilter.BloomFilterFactory;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author harvey 
 */
@EnableCaching
@Configuration
public class CacheConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    
    @Value("${spring.data.redis.port}")
    private String redisPort;
    
    @Value("${spring.data.redis.database}")
    private Integer redisDatabase;
    
    @Value("${spring.data.redis.username}")
    private String redisUsername;
    
    @Value("${spring.data.redis.password}")
    private String redisPassword;
    
    
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());
        
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        
        redisTemplate.afterPropertiesSet();
        
        return redisTemplate;
    }
    
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        
        String address = String.format("redis://%s:%s", redisHost, redisPort);
        
        config.useSingleServer()
            .setAddress(address)
            .setDatabase(redisDatabase)
            .setUsername(redisUsername)
            .setPassword(redisPassword);
        
        return Redisson.create(config);
    }
    
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
        
        return RedisCacheManager.builder(redisCacheWriter)
            .cacheWriter(redisCacheWriter)
            .cacheDefaults(redisCacheConfiguration)
            .build();
    }

    @Bean
    public BloomFilterCheckAspect bloomFilterCheckAspect(){
        return new BloomFilterCheckAspect();
    }

    @Bean
    public BloomFilterFactory bloomFilterFactory(){
        return new BloomFilterFactory();
    }
}
