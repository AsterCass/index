package com.aster.yuno.index.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author astercasc
 */
@Getter
@AllArgsConstructor
public enum CaskStatusEnum {

    /**
     * normal
     */
    NORMAL(0, "normal"),

    /**
     * deleted
     */
    DEL(-1, "deleted"),

    ;


    private final Integer code;

    private final String desc;

}
