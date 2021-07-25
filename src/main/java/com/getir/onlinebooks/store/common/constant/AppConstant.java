package com.getir.onlinebooks.store.common.constant;

import java.util.HashMap;
import java.util.Map;

public class AppConstant {
    public static Map<Integer, String> monthMap = new HashMap<>();

    static {
        monthMap.put(1, "JAN");
        monthMap.put(2, "FEB");
        monthMap.put(3, "MAR");
        monthMap.put(4, "APR");
        monthMap.put(5, "MAY");
        monthMap.put(6, "JUN");
        monthMap.put(7, "JUL");
        monthMap.put(8, "AUG");
        monthMap.put(9, "SEP");
        monthMap.put(10, "OCT");
        monthMap.put(11, "NOV");
        monthMap.put(12, "DEC");
    }
}
