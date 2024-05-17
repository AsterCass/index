package com.aster.yuno.index.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.aster.yuno.index.config.SnowflakeConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Slf4j
@Import({SnowflakeConfig.class})
@Configuration
public class SnowflakeUtils {

    @Resource
    private ApplicationContext applicationContext;

    public static Snowflake SNOWFLAKE_GEN;

    private static final Integer MAX_LEN = 20;


    @PostConstruct
    public void initSnowflake() {
        if (null == SNOWFLAKE_GEN) {
            SnowflakeConfig snowflakeConfig =
                    applicationContext.getBean(SnowflakeConfig.class);
            log.info("[op:SnowflakeUtils] init workId = {}, dataId = {}",
                    snowflakeConfig.getWorkId(), snowflakeConfig.getDataId());
            SNOWFLAKE_GEN = IdUtil.getSnowflake(
                    snowflakeConfig.getWorkId(), snowflakeConfig.getDataId());
        }
    }


    @Deprecated
    public static Long getId() {
        return SNOWFLAKE_GEN.nextId();
    }


    public static String getIdStr() {
        return SNOWFLAKE_GEN.nextIdStr();
    }

    public static String getIdStr(String prefix) {
        return prefix + SNOWFLAKE_GEN.nextIdStr().substring(0, MAX_LEN - prefix.length());
    }

}
