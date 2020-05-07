package com.back.service;

public interface RedisService {

    void set(String key, String value);

    void set(String key, String value, int expiration);

    String get(String key);

    void del(String key);

    void batchDel(String redisKeyPre);
}
