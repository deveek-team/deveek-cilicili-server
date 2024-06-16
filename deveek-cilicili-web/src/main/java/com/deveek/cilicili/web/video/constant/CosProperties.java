package com.deveek.cilicili.web.video.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author Shooter
 */
@Component
@ConfigurationProperties(prefix = "deveek-cilicili-core.cos")
@Data
public class CosProperties {

    private String secretId;
    private String secretKey;
    private String region;
    private String url;
    private String bucketName;

}
