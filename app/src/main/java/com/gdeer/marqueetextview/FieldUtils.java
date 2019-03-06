package com.gdeer.marqueetextview;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class FieldUtils {

    public static Field getDeclaredField(final Class<?> cls, final String fieldName, final
    boolean forceAccess) {
        if (cls == null || TextUtils.isEmpty(fieldName)) {
            return null;
        }
        try {
            // only consider the specified class by using getDeclaredField()
            final Field field = cls.getDeclaredField(fieldName);
            if (!isAccessible(field)) {
                if (forceAccess) {
                    field.setAccessible(true);
                } else {
                    return null;
                }
            }
            return field;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static boolean isAccessible(final Member m) {
        return m != null && Modifier.isPublic(m.getModifiers()) && !m.isSynthetic();
    }
}
