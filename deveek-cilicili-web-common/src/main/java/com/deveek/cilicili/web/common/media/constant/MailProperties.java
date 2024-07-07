package com.deveek.cilicili.web.common.media.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author harvey
 */
@Data
@Component
@ConfigurationProperties(prefix = "deveek-cilicili-core.mail")
public class MailProperties {
    private String username;
    
    private String password;
    
    private String host;
}
