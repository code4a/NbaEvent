package com.code4a.retrofitutil.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by code4a on 2017/5/26.
 */

public class HttpUtil {

    public static Map<String, String> getJsonHeaderMap() {
        Map<String, String> jsonHeaderMap = new HashMap<>();
        jsonHeaderMap.put("Content-Type", "application/json");
        jsonHeaderMap.put("Accept", "application/json");
        return jsonHeaderMap;
    }
}
