package com.vrozsa;

import com.vrozsa.exceptions.InvalidContextVariableTypeException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeConverter {

    private TypeConverter() {
    }

    public static List<?> getSetAsList(Object set, String keyword) {
        if (set instanceof List<?> setAsList) {
            return new ArrayList<>(setAsList);
        }
        else if  (set instanceof Object[] setAsArray) {
            return Arrays.asList(setAsArray);
        }
        else if (set.getClass().isArray()) {
            int length = Array.getLength(set);
            List<Object> list = new ArrayList<>(length);
            for (var i = 0; i < length; i++) {
                list.add(Array.get(set, i));
            }
            return list;
        }

        throw new InvalidContextVariableTypeException(keyword, "Lists or Arrays");
    }
}
