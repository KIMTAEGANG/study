package com.study.common.utils;

import java.time.LocalDateTime;

public class ObjectUtils {
    public static LocalDateTime ifNull(LocalDateTime target, LocalDateTime source) {
        if(target != null) return target;
        return source;
    }
}
