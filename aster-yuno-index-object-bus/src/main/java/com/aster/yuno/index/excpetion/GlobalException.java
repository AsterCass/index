package com.aster.yuno.index.excpetion;


import com.aster.yuno.index.bo.ResultObj;
import com.aster.yuno.index.enums.WebResultStatusEnum;
import com.aster.yuno.index.exception.CaskRuntimeException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Configuration
public class GlobalException {
    @ExceptionHandler({Exception.class})
    public ResultObj<String> exception(Exception e) {
        e.printStackTrace();

        String className = getClassName(e);
        //use class name instead <--enable-preview> to use "type pattern matching"
        return switch (className) {
            case ("org.springframework.web.bind.MethodArgumentNotValidException") -> {
                var firstValidError = ((BindException) e).getBindingResult().getAllErrors().get(0);
                var field = "argument";
                var errorArg = Objects.requireNonNull(firstValidError.getArguments())[0];
                if (errorArg instanceof DefaultMessageSourceResolvable messageArg) {
                    field = messageArg.getCode();
                }
                yield ResultObj.error(WebResultStatusEnum.LOGIC_FORBID.getCode(),
                        String.format("[%s] %s", field, firstValidError.getDefaultMessage()));
            }
            case ("com.aster.yuno.index.exception.CaskSpecialRuntimeException") ->
                    ResultObj.error(WebResultStatusEnum.SPECIAL_LOGIC_FORBID.getCode(), e.getMessage());
            case ("com.aster.yuno.index.exception.CaskNotIdCardAuthException") ->
                    ResultObj.error(WebResultStatusEnum.NOT_ID_CARD_AUTH.getCode(), e.getMessage());
            case ("com.aster.yuno.index.exception.CaskNotLoginException") ->
                    ResultObj.error(WebResultStatusEnum.NOT_LOGIN.getCode(), e.getMessage());
            case "com.aster.yuno.index.exception.CaskRuntimeException" ->
                    ResultObj.error(WebResultStatusEnum.LOGIC_FORBID.getCode(), e.getMessage());
            default -> ResultObj.error(WebResultStatusEnum.SYSTEM_UNKNOWN_ERROR.getCode(), e.getMessage());
        };
    }

    private static String getClassName(Exception e) {

        String className = e.getClass().getName();

        List<String> specialRuntimeExceptionClass = Arrays.asList(
                "com.aster.yuno.index.exception.CaskSpecialRuntimeException",
                "com.aster.yuno.index.exception.CaskNotIdCardAuthException",
                "com.aster.yuno.index.exception.CaskNotLoginException"
        );

        if (!specialRuntimeExceptionClass.contains(className) && e instanceof CaskRuntimeException) {
            className = "com.aster.yuno.index.exception.CaskRuntimeException";
        }
        return className;
    }



}
