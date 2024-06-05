package com.aster.yuno.index.utils;

import com.aster.yuno.index.exception.CaskRuntimeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

/**
 * @author wangyao
 */
public class RangeStrSplitUtils {


    public static RangeResult rangeSplit(String data) {
        if (ObjectUtils.isEmpty(data)) {
            throw new CaskRuntimeException("解析范围数据错误");
        }
        String[] rangeArray = data.split("-");
        if (2 != rangeArray.length) {
            throw new CaskRuntimeException("解析范围格式错误");
        }
        RangeResult ret = new RangeResult();
        if (rangeArray[0].toLowerCase().equals(RangeResultInfinityStatusEnum.MIN.code)) {
            ret.infinityStatus = RangeResultInfinityStatusEnum.MIN;
            ret.end = rangeArray[1];
        } else if (rangeArray[1].toLowerCase().equals(RangeResultInfinityStatusEnum.MAX.code)) {
            ret.start = rangeArray[0];
            ret.infinityStatus = RangeResultInfinityStatusEnum.MAX;
        } else {
            ret.start = rangeArray[0];
            ret.end = rangeArray[1];
            ret.infinityStatus = RangeResultInfinityStatusEnum.BETWEEN;
        }
        return ret;
    }


    @Data
    public static class RangeResult {
        private String start;
        private String end;
        private RangeResultInfinityStatusEnum infinityStatus;
    }

    @Getter
    @AllArgsConstructor
    public enum RangeResultInfinityStatusEnum {

        MIN("min", "负无穷"),
        BETWEEN("between", "区间"),
        MAX("max", "正无穷"),
        ;

        private final String code;
        private final String desc;
    }

}
