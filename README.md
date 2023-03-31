# construct

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
* spring-cloud-starter-consul-discovery
* lombok

aster-yuno-index-object:
* \# aster-yuno-index-core
* fastjson2

aster-yuno-index-gateway:
* \# aster-yuno-index-object
* spring-cloud-starter-gateway
* spring-cloud-starter-openfeign
* spring-cloud-starter-consul-config

aster-yuno-index-web:
* \# aster-yuno-index-object
* spring-boot-starter-web
* spring-cloud-starter-openfeign
* spring-cloud-starter-consul-config

aster-yuno-index-object-bus:
* \# aster-yuno-index-web
* hutool-all

aster-yuno-index-simple
* \# aster-yuno-index-object-bus
* spring-boot-starter-aop
* mysql-connector-j
* mybatis-spring-boot-starter
* mapper
* springdoc-openapi-starter-webmvc-ui
