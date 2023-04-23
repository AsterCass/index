package com.aster.yuno.index.excpetion;


import com.aster.yuno.index.bo.ResultObj;
import com.aster.yuno.index.enums.WebResultStatusEnum;
import com.aster.yuno.index.exception.CaskRuntimeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Configuration
public class GlobalException {


    @ExceptionHandler({Exception.class})
    public ResultObj<String> exception(Exception e) {
        e.printStackTrace();
        if (e instanceof CaskRuntimeException) {
            return ResultObj.error(WebResultStatusEnum.LOGIC_FORBID.getCode(), e.getMessage());
        }
        return ResultObj.error(WebResultStatusEnum.SYSTEM_UNKNOWN_ERROR.getCode(), e.getMessage());
    }

}
