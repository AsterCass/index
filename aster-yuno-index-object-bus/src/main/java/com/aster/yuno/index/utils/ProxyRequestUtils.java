package com.aster.yuno.index.utils;

import com.alibaba.fastjson2.JSON;
import com.aster.yuno.index.bo.ProxyGeneralRequestParam;
import com.aster.yuno.index.bo.ResultObj;
import com.aster.yuno.index.config.ProxyRequestConfig;
import com.aster.yuno.index.exception.CaskRuntimeException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Import({ProxyRequestConfig.class})
@Configuration
public class ProxyRequestUtils {

    @Resource
    private ApplicationContext applicationContext;


    private static String proxyKey;

    private static String proxyUrl;

    @PostConstruct
    public void initProxyRequest() {
        ProxyRequestConfig proxyRequestConfig =
                applicationContext.getBean(ProxyRequestConfig.class);
        log.info("[op:initProxyRequest] key = {}, url = {}",
                proxyRequestConfig.getRequestAuthKey(), proxyRequestConfig.getRequestUrl());
        proxyKey = proxyRequestConfig.getRequestAuthKey();
        proxyUrl = proxyRequestConfig.getRequestUrl();
    }

    public static <T> T sendGetRequest(String url, Class<T> clazz) {
        return sendRequest(url, null, null, RequestMethod.GET, null, clazz, null);
    }

    public static <T> T sendGetRequest(String url,
                                       Map<String, String> queryParam, Class<T> clazz) {
        return sendRequest(url, queryParam, null, RequestMethod.GET, null, clazz, null);
    }

    public static <T> T sendGetRequest(String url,
                                       Map<String, String> queryParam,
                                       Map<String, String> header, Class<T> clazz) {
        return sendRequest(url, queryParam, header, RequestMethod.GET, null, clazz, null);
    }

    public static <T> T sendPostRequest(String url,
                                        Map<String, String> queryParam,
                                        Map<String, String> header, Class<T> clazz) {
        return sendRequest(url, queryParam, header, RequestMethod.POST, null, clazz, null);
    }


    public static <T> T sendPostRequest(String url, Object bodyObj, Class<T> clazz) {
        return sendRequest(url, null, null, RequestMethod.POST, bodyObj, clazz, null);
    }

    public static <T> T sendPostRequest(String url,
                                        Map<String, String> queryParam,
                                        Map<String, String> header,
                                        Object bodyObj, Class<T> clazz) {
        return sendRequest(url, queryParam, header, RequestMethod.POST, bodyObj, clazz, null);
    }

    public static <T> T sendRequest(String url,
                                    Map<String, String> queryParam,
                                    Map<String, String> header,
                                    RequestMethod method, Object bodyObj,
                                    Class<T> clazz,
                                    String customProxyUrl) {
        if (RequestMethod.GET != method && RequestMethod.POST != method) {
            throw new CaskRuntimeException("Request method not support");
        }
        //url
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        if (ObjectUtils.isEmpty(proxyUrl) || ObjectUtils.isEmpty(url)) {
            throw new CaskRuntimeException("Send proxy request url not allow empty");
        } else {
            if (ObjectUtils.isEmpty(customProxyUrl)) {
                requestBuilder.uri(URI.create(proxyUrl));
            } else {
                requestBuilder.uri(URI.create(customProxyUrl));
            }
        }
        //header
        requestBuilder.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //body
        ProxyGeneralRequestParam param = new ProxyGeneralRequestParam();
        param.setAuthCode(proxyKey);
        param.setUrl(url);
        param.setRequestMethod(method);
        if (!ObjectUtils.isEmpty(queryParam)) {
            param.setQueryParam(queryParam);
        }
        if (!ObjectUtils.isEmpty(header)) {
            param.setHeader(header);
        }
        if (!ObjectUtils.isEmpty(bodyObj)) {
            param.setBodyParam(JSON.toJSONString(bodyObj));
        }
        HttpRequest.BodyPublisher bodyPublisher =
                HttpRequest.BodyPublishers.ofString(JSON.toJSONString(param));
        requestBuilder.POST(bodyPublisher);
        //request
        HttpRequest request = requestBuilder.build();
        T ret = null;
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String retBody = response.body();
            @SuppressWarnings({"all"})
            ResultObj data = JSON.parseObject(retBody, ResultObj.class);
            ret = JSON.parseObject(JSON.toJSONString(data.getData()), clazz);
        } catch (Exception ex) {
            throw new CaskRuntimeException("Send post request fail: " + ex.getMessage());
        }
        if (ObjectUtils.isEmpty(ret)) {
            throw new CaskRuntimeException("Parse proxy request fail");
        }
        return ret;
    }

}
