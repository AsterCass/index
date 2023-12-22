# construct

```xml
        <dependency>
            <artifactId>aster-yuno-index-simple</artifactId>
            <name>aster-yuno-index-simple</name>
            <version>1.0.16</version>
        </dependency>
```

## base

index:
* aster-yuno-index-core 
* aster-yuno-index-gateway
* aster-yuno-index-object
* aster-yuno-index-simple
* aster-yuno-index-web

## dependencies

aster-yuno-index-core:
* spring-boot-starter
* spring-cloud-starter-consul-config
* spring-cloud-starter-consul-discovery
* lombok

aster-yuno-index-object:
* \# aster-yuno-index-core
* fastjson2

aster-yuno-index-gateway:
* \# aster-yuno-index-object
* spring-cloud-starter-gateway
* spring-cloud-starter-openfeign
* spring-boot-starter-data-redis-reactive
* spring-cloud-starter-circuitbreaker-reactor-resilience4j

aster-yuno-index-web:
* \# aster-yuno-index-object
* spring-boot-starter-web
* spring-cloud-starter-openfeign

aster-yuno-index-object-bus:
* \# aster-yuno-index-web
* hutool-all
* ip2region

aster-yuno-index-simple
* \# aster-yuno-index-object-bus
* spring-boot-starter-aop
* mysql-connector-j
* mybatis-spring-boot-starter
* mapper
* springdoc-openapi-starter-webmvc-ui
* spring-boot-configuration-processor
