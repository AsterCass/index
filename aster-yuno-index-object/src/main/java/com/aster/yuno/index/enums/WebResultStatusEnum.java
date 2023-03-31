package com.aster.yuno.index.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author astercasc
 */
@Getter
@AllArgsConstructor
public enum WebResultStatusEnum {


    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功", "success"),


    /**
     * 系统未知错误
     */
    SYSTEM_UNKNOWN_ERROR(400, "系统未知错误", "unknown_error"),

    /**
     * 内部流转终止
     */
    LOGIC_FORBID(500, "内部流转终止", "logic_forbid"),

    ;


    private final Integer code;

    private final String desc;

    private final String info;


}
