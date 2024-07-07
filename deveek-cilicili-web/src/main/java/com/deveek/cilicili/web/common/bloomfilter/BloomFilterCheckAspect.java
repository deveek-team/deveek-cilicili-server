package com.deveek.cilicili.web.common.bloomfilter;

import com.deveek.common.exception.ClientException;
import com.deveek.common.support.SpELUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Shooter
 * 布隆过滤器切面
 */
@Slf4j
@Aspect
@Component
public class BloomFilterCheckAspect {

    @Resource
    private BloomFilterFactory bloomFilterFactory;

    /**
     * 对加上注解的接口进行布隆过滤器查询
     */
    @SneakyThrows
    @Around("@annotation(com.deveek.cilicili.web.common.bloomfilter.BloomFilterCheck)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        BloomFilterCheck bloomFilterCheck = getBloomFilterCheck(joinPoint);
        Object[] args = joinPoint.getArgs();
        String value = (String) SpELUtil.parse(bloomFilterCheck.value(), ((MethodSignature) joinPoint.getSignature()).getMethod(), args);
        String[] strings = value.split("-");
        RBloomFilter userBloomFilter = bloomFilterFactory.getBloomFilter(strings[0]);

        BloomResultWrapperEnum resultWrapperEnum = bloomFilterCheck.result();

        boolean isContain = userBloomFilter.contains(strings[1]);
        if (!isContain) {
            throw new ClientException(resultWrapperEnum.getResult());
        }

        return joinPoint.proceed(args);
    }

    public static BloomFilterCheck getBloomFilterCheck(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        return method.getAnnotation(BloomFilterCheck.class);
    }
}
