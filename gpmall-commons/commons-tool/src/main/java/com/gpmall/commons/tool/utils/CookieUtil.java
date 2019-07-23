package com.gpmall.commons.tool.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), key)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    public static Cookie genCookieWithDomain(String key, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        enrichCookie(cookie, "/", maxAge);
        return cookie;
    }

    public static Cookie genCookie(String key, String value, String uri, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        enrichCookie(cookie, uri, maxAge);
        return cookie;
    }

    public static void enrichCookie(Cookie cookie, String uri, int maxAge) {
        cookie.setPath(uri);
        cookie.setMaxAge(maxAge);
    }

    public static void setCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }


}