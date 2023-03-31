package com.aster.yuno.index.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.aster.yuno.index.config.SnowflakeConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SnowflakeUtils {

    @Resource
    private SnowflakeConfig snowflakeConfig;

    private static Snowflake SNOWFLAKE;

    @PostConstruct
    public void postConstruct() {
        log.info("[op:SnowflakeUtils] init workId = {}, dataId = {}",
                snowflakeConfig.getWorkId(), snowflakeConfig.getDataId());
        SNOWFLAKE = IdUtil.getSnowflake(snowflakeConfig.getWorkId(), snowflakeConfig.getDataId());
    }


    @Deprecated
    public static Long getId() {
        return SNOWFLAKE.nextId();
    }

    @Deprecated
    public static String getIdStr() {
        return SNOWFLAKE.nextIdStr();
    }

    public static String getArticleStr() {
        return ("AT" + SNOWFLAKE.nextIdStr()).substring(0, 18);
    }


}
