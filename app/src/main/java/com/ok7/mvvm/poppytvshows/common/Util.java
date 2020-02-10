package com.ok7.mvvm.poppytvshows.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public final class Util {

    private Util() {

    }

    public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
        return new HashSet<>(list1).equals(new HashSet<>(list2));
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<>();
        for (T t : list1) {
            if (list2.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }
}
