package com.pinganfang.imagelibrary.util;

/**
 * Created by LIUYONGKUI726 on 2016-08-26.
 */
public class NullUtils {
    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static <T> boolean isNull(T object) {
        if (object == null) {
           return true;
        }
        return false;
    }
}
