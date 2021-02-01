package com.stevenwan.svlas.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * @author steven-wan
 * @desc
 * @date 2021-02-01 10:25
 */
public class ObjectUtils {
    public static void isNullThrowsExcetion(Object obj, String message) {
        if (obj instanceof CharSequence) {
            if (StringUtils.isBlank((String) obj)) {
                throw new RuntimeException(message);
            }
        } else if (obj instanceof Collection) {
            if (CollectionUtil.isEmpty((Collection) obj)) {
                throw new RuntimeException(message);
            }
        } else if (obj instanceof Map) {
            if (CollectionUtil.isEmpty((Map) obj)) {
                throw new RuntimeException(message);
            }
        } else {
            if (Objects.isNull(obj)) {
                throw new RuntimeException(message);
            }
        }
    }

    public static boolean isNotNull(Object obj) {
        if (obj instanceof CharSequence) {
            return StringUtils.isNotBlank((String) obj);
        } else if (obj instanceof Collection) {
            return CollectionUtil.isNotEmpty((Collection) obj);
        } else if (obj instanceof Map) {
            return CollectionUtil.isNotEmpty((Map) obj);
        } else {
            return Objects.nonNull(obj);
        }
    }
}
