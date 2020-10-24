package com.carnetwork.hansen.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * author：create by Administrator on 2020/7/6
 * email：1564063766@qq.com
 * remark:URI转换(针对特殊字符的问题)
 */
public class UriUtil {

    public UriUtil() {
    }

    public static UriUtil getInstance() {
        return UriUtil.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static UriUtil instance = new UriUtil();
    }

    //获取查询值参数
    public String replaceSpecialCharacters(String name) {
        name = name.replace("+", replaceString("+"));
        name = name.replace("$", replaceString("$"));
        name = name.replace("%", replaceString("%"));
        name = name.replace("#", replaceString("#"));
        return name;
    }

    /**
     * 特换特殊字符
     *
     * @param characters
     * @return
     */
    private String replaceString(String characters) {
        String encode = null;
        try {
            encode = URLEncoder.encode(characters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }

}
