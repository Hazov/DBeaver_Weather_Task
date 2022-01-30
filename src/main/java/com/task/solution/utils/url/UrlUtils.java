package com.task.solution.utils.url;

public class UrlUtils {
    public static String makeRequestWithParam(String address, String... params){
        StringBuilder url = new StringBuilder(address + "?");
        url.append(params[0]);
        for (int i = 1; i < params.length - 1; i++)
            url.append("&").append(params[i]);
        url.append("&").append(params[params.length - 1]);
        return url.toString();
    }


}
