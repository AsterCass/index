package com.aster.yuno.index.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class SnowflakeUtils {

    private static Snowflake SNOWFLAKE;

    public SnowflakeUtils(@Value("${cask.snow.workId:1}") Long workerId,
                          @Value("${cask.snow.dataId:1}") Long datacenterId) {
        log.info("[op:SnowflakeUtils] init workId = {}, dataId = {}", workerId, datacenterId);
        SNOWFLAKE = IdUtil.getSnowflake(workerId, datacenterId);
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
