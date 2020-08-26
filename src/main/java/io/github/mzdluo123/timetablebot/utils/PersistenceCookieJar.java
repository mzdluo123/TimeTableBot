package io.github.mzdluo123.timetablebot.utils;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.List;


public class PersistenceCookieJar implements CookieJar {

    List<Cookie> cache = new ArrayList<>();

    //Http请求结束，Response中有Cookie时候回调
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        //内存中缓存Cookie
        cache.addAll(cookies);
        System.out.println("save");
    }

    //Http发送请求前回调，Request中设置Cookie
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        //过期的Cookie
        List<Cookie> invalidCookies = new ArrayList<>();
        //有效的Cookie
        List<Cookie> validCookies = new ArrayList<>();

        for (Cookie cookie : cache) {

            if (cookie.expiresAt() < System.currentTimeMillis()) {
                //判断是否过期
                invalidCookies.add(cookie);
            } else if (cookie.matches(url)) {
                //匹配Cookie对应url
                validCookies.add(cookie);
            }
        }

        //缓存中移除过期的Cookie
        cache.removeAll(invalidCookies);

        //返回List<Cookie>让Request进行设置
        System.out.println("load");
        return validCookies;
    }
}