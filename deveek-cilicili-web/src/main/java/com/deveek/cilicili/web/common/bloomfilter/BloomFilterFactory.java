package com.deveek.cilicili.web.common.bloomfilter;

import com.deveek.common.constant.Result;
import com.deveek.common.exception.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shooter
 * 布隆过滤器工厂
 */
@Slf4j
@Component
public class BloomFilterFactory implements InitializingBean, ApplicationContextAware {
    private static final Map<String, RBloomFilter> BLOOM_FILTER_MAP = new ConcurrentHashMap<>();
    private ApplicationContext context;

    public RBloomFilter getBloomFilter(String bloomFilterName) {
        RBloomFilter bloomFilter = BLOOM_FILTER_MAP.get(bloomFilterName);

        if (Objects.isNull(bloomFilter)) {
            throw new ClientException(Result.FAILURE);
        }

        return bloomFilter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        context.getBeansOfType(RBloomFilter.class)
                .values()
                .forEach(each -> BLOOM_FILTER_MAP.put(each.getName(), each));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
