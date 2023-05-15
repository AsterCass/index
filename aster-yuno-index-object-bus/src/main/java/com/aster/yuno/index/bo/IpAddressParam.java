package com.aster.yuno.index.bo;

import com.aster.yuno.index.utils.IpSearchUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Data
public class IpAddressParam {

    private final static String NGINX_SET_REAL_IP_KEY = "x-real-ip";

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * ip位置
     */
    private String ipAddressName;

    public IpAddressParam() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader(NGINX_SET_REAL_IP_KEY);
        if (!ObjectUtils.isEmpty(ip)) {
            this.ipAddress = ip;
            this.ipAddressName = IpSearchUtils.getIpName(ip);
        }
    }
}
