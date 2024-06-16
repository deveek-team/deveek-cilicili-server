package com.deveek.cilicili.web.media.config;

import com.deveek.cilicili.web.common.media.constant.CosProperties;
import com.deveek.cilicili.web.media.support.CosUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author Shooter
 */
@Configuration
@Slf4j
public class CosConfig {
    @Bean
    @ConditionalOnMissingBean
    public COSClient initCOSClient(CosProperties cosProperties){
        COSCredentials cred = new BasicCOSCredentials(cosProperties.getSecretId(), cosProperties.getSecretKey());
        Region region = new Region(cosProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public CosUtil cosUtil(CosProperties cosProperties){
        return new CosUtil(
            cosProperties.getSecretId(),
            cosProperties.getSecretKey(),
            cosProperties.getRegion(),
            cosProperties.getUrl(),
            cosProperties.getBucketName(),
            initCOSClient(cosProperties)
        );
    }
}