package com.aster.yuno.index.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;


@Data
@EqualsAndHashCode(callSuper = true)
public class ProxyGeneralRequestParam extends ProxyAuthParam {

    private RequestMethod requestMethod;

    private String url;

    private Map<String, String> queryParam;

    private Map<String, String> header;

    private String bodyParam;

}
