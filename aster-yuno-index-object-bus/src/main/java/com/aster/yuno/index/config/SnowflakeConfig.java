package com.aster.yuno.index.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(SnowflakeConfig.SNOWFLAKE_CONFIG)
public class SnowflakeConfig {

    public static final String SNOWFLAKE_CONFIG = "yuno.snow";

    private Long workId = 1L;

    private Long dataId = 1L;


}
