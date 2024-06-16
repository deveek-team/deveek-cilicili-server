package com.deveek.cilicili.web.common.video.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author Shooter
 */
@Data
@Component
@ConfigurationProperties(prefix = "deveek-cilicili-core.cos")
public class CosProperties {
    private String secretId;
    
    private String secretKey;
    
    private String region;
    
    private String url;
    
    private String bucketName;
}
