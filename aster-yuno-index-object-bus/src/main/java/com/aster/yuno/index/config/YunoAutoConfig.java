package com.aster.yuno.index.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class YunoAutoConfig {

    @PostConstruct
    public void postYunoAutoConfig() {
        log.info("[op:postYunoAutoConfig] yuno auto config loaded");
    }

}
