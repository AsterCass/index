package com.aster.yuno.index.utils;

import com.aster.yuno.index.ann.TrimParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

@Aspect
@Component
public class TrimParamStringsAspect {

    @Around("execution(* *(.., @com.aster.yuno.index.ann.TrimParam (*), ..))")
    public Object trimParamStrings(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Method method = getCurrentMethod(joinPoint);
        if (method == null) {
            return joinPoint.proceed(args);
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int annCount = 0; annCount < parameterAnnotations.length; annCount++) {
            for (Annotation annotation : parameterAnnotations[annCount]) {
                if (annotation instanceof TrimParam) {
                    Object needTrimParam = args[annCount];
                    if (needTrimParam instanceof String thisStr) {
                        args[annCount] = thisStr.trim();
                    } else if (needTrimParam instanceof Collection<?> thisObjList) {
                        args[annCount] = thisObjList.stream().map(obj -> {
                            if (obj instanceof String thisStr) {
                                return thisStr.trim();
                            } else {
                                return obj;
                            }
                        }).toList();
                    } else {
                        trimFields(args[annCount]);
                    }
                }
            }
        }
        return joinPoint.proceed(args);
    }

    private void trimFields(Object object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(object);
                    if (value != null) {
                        field.set(object, value.trim());
                    }
                } catch (Exception ignore) {
                }
            }
        }
    }

    private Method getCurrentMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        try {
            return joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException ignore) {
            return null;
        }
    }
}
