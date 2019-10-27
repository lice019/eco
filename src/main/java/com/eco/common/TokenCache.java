package com.eco.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * description: TakenCache token的缓存，用途设置一些数据的有效时间<br>
 * date: 2019/10/27 15:17 <br>
 * author: lc <br>
 * version: 1.0 <br>
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    /*
     * 初始化缓存容器为1000，最大10000，有效期12小时
     */
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {

                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void put(String key, String value) {
        localCache.put(key, value);
    }

    public static String get(String key) {
        String value;
        try {
            value = localCache.get(key);
            if ("null".equals(value))
                return null;
            return value;
        } catch (Exception e) {
            logger.error("localCache get error", e);
        }
        return null;
    }
}
