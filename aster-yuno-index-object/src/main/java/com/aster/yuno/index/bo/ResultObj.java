package com.aster.yuno.index.bo;


import com.aster.yuno.index.enums.WebResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj<T> {

    private Integer status;

    private String message;

    private T data;

    private ResultObj(Integer status, T data) {
        this.data = data;
        this.status = status;
    }

    private ResultObj(Integer status, String message) {
        this.message = message;
        this.status = status;
    }

    public static <T> ResultObj<T> success() {
        return new ResultObj<>(WebResultStatusEnum.SUCCESS.getCode(), WebResultStatusEnum.SUCCESS.getInfo());
    }

    public static <T> ResultObj<T> success(T data) {
        return new ResultObj<>(WebResultStatusEnum.SUCCESS.getCode(), data);
    }

    public static <T> ResultObj<T> error(Integer code, String message) {
        return new ResultObj<>(code, message);
    }

    public static <T> ResultObj<T> error(String message) {
        return new ResultObj<>(
                WebResultStatusEnum.SYSTEM_UNKNOWN_ERROR.getCode(), message);
    }


}
