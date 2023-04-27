package com.aster.yuno.index.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangyao
 */
public class ConversionUtils {

    private ConversionUtils() {
    }

    /**
     * 单个对象转换
     *
     * @param source 源对象
     * @param clazz  目标对象class
     * @param <T>    目标对象 泛型
     * @return 返回目标类对象
     */
    public static <T> T convertObj(Object source, Class<T> clazz) {
        try {
            Class<?> classType = Class.forName(clazz.getName());
            Object target = classType.getDeclaredConstructor().newInstance();
            @SuppressWarnings({"unchecked"})
            T result = (T) conversion(source, target);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 对象集合转换
     *
     * @param sourceList 源对象集合
     * @param clazz      目标对象class
     * @param <T>        目标对象 泛型
     * @return 返回目标类对象集合
     */
    public static <T> List<T> convertList(List<?> sourceList, Class<T> clazz) {
        List<T> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sourceList)) {
            return voList;
        }
        for (Object po : sourceList) {
            T dto = convertObj(po, clazz);
            if (dto != null) {
                voList.add(dto);
            }
        }
        return voList;
    }

    private static Object conversion(Object from, Object to) {
        if (from == null) {
            return null;
        }
        try {
            BeanUtils.copyProperties(from, to);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return to;
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
