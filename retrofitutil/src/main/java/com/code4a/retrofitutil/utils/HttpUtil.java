package com.code4a.retrofitutil.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by code4a on 2017/5/26.
 */

public class HttpUtil {

    private static final String BEARER_HEADER_VALUE = "Bearer";
    private static final String AUTH_HEADER_KEY = "Authorization";

    public static Map<String, String> getJsonHeaderMap() {
        Map<String, String> jsonHeaderMap = new HashMap<>();
        jsonHeaderMap.put("Content-Type", "application/json");
        jsonHeaderMap.put("Accept", "application/json");
        return jsonHeaderMap;
    }

    public static Map<String, String> getFormHeaderMap() {
        Map<String, String> jsonHeaderMap = new HashMap<>();
        jsonHeaderMap.put("Content-Type", "application/x-www-form-urlencoded");
        jsonHeaderMap.put("Accept", "*/*");
        return jsonHeaderMap;
    }

    public static Map<String, String> getBearerAuthHeaderMap(String accessToken) {
        Map<String, String> jsonHeaderMap = new HashMap<>();
        jsonHeaderMap.put("Content-Type", "application/json");
        jsonHeaderMap.put("Accept", "application/json");
        jsonHeaderMap.put(AUTH_HEADER_KEY, new StringBuilder()
                .append(BEARER_HEADER_VALUE)
                .append(" ")
                .append(accessToken).toString());
        return jsonHeaderMap;
    }
}
