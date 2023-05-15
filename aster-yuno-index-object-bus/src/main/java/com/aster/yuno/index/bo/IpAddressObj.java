package com.aster.yuno.index.bo;

import lombok.Data;
import org.springframework.util.ObjectUtils;

@Data
public class IpAddressObj {

    private final static String SPLIT_SYMBOL = "\\|";

    /**
     * country
     */
    private String country;

    /**
     * region
     */
    private String region;

    /**
     * province
     */
    private String province;

    /**
     * city
     */
    private String city;

    /**
     * operator
     */
    private String operator;


    public IpAddressObj(String detail) {
        if (!ObjectUtils.isEmpty(detail)) {
            String[] values = detail.split(SPLIT_SYMBOL);
            this.country = convertEmptyStr(values[0]);
            this.region = convertEmptyStr(values[1]);
            this.province = convertEmptyStr(values[2]);
            this.city = convertEmptyStr(values[3]);
            this.operator = convertEmptyStr(values[4]);
        }
    }

    private String convertEmptyStr(String str) {
        return "0".equals(str) ? "" : str;
    }


}
