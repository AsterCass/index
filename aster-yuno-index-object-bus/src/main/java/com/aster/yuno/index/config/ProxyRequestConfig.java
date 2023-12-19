package com.aster.yuno.index.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(ProxyRequestConfig.PROXY_REQUEST_CONFIG)
public class ProxyRequestConfig {

    public static final String PROXY_REQUEST_CONFIG = "yuno.proxy";

    private String requestAuthKey = "";

    private String requestUrl = "";

}
