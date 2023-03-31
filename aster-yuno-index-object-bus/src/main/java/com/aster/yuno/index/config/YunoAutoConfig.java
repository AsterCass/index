package com.aster.yuno.index.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({SnowflakeConfig.class})
public class YunoAutoConfig {
}
