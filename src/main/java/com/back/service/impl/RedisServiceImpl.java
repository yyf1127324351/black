package com.back.service.impl;

import com.back.service.RedisService;
import com.constant.ConfigConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Set;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    private static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    private static String IP = ConfigConstants.REDIS_IP;
    private static int PORT = Integer.valueOf(ConfigConstants.REDIS_PORT);
    private static String PASSWORD = ConfigConstants.REDIS_PWD;

    private static JedisPool jedisPool;
    private static JedisPoolConfig config = new JedisPoolConfig();

    static {
        config.setMaxTotal(100);
        config.setMaxIdle(20);
        config.setMaxWaitMillis(1000L);
        if (StringUtils.isNotBlank(PASSWORD)) {
            jedisPool = new JedisPool(config, IP, PORT, 1000, PASSWORD);
        }else {
            jedisPool = new JedisPool(config, IP, PORT, 1000);
        }
    }

    /**
     * 简单的key-value 存储
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, String value) {
        if (StringUtils.isBlank(key)) {
            log.debug("Redis key is null");
        } else {
            Jedis redis = null;
            try {
                redis = jedisPool.getResource();
                redis.set(key, value);
            } catch (Exception e) {
                log.error("redis set value error", e);
            } finally {
                if (redis != null) {
                    redis.close();
                }
            }
        }
    }

    /**
     * 简单的key-value 存储
     *
     * @param key        key
     * @param value      值
     * @param expiration 过期时间
     */
    public void set(String key, String value, int expiration) {
        if (StringUtils.isBlank(key)) {
            log.debug("Redis key is null");
        } else {
            Jedis redis = null;
            try {
                redis = jedisPool.getResource();
                redis.set(key, value);
                if (expiration > 0) {
                    redis.expire(key, expiration);
                }
            } catch (Exception e) {
                log.error("redis set value error", e);
            } finally {
                if (redis != null) {
                    redis.close();
                }
            }
        }
    }


    /**
     * 简单的Key-value获取值
     *
     * @param key key
     */
    public String get(String key) {
        if (StringUtils.isBlank(key)) {
            log.debug("Redis key is null");
            return null;
        } else {
            Jedis redis = null;
            try {
                redis = jedisPool.getResource();
                return redis.get(key);
            } catch (Exception e) {
                log.error("redis get value error", e);
            } finally {
                if (redis != null) {
                    redis.close();
                }
            }
        }
        return null;
    }

    /**
     * 根据key删除值
     *
     * @param key 键值
     */
    @Override
    public void del(String key) {
        Jedis redis = null;
        try {
            redis = jedisPool.getResource();
            redis.del(key);
        } catch (Exception e) {
            log.error("delError", e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

    /**
     * 批量删除key-value
     *
     * @param pattern key表达式
     */
    public void batchDel(String pattern) {
        Jedis redis = null;
        try {
            redis = jedisPool.getResource();
            Set<String> keys = redis.keys(pattern + "*");
            for (String keyStr : keys) {
                redis.del(keyStr);
            }
        } catch (Exception e) {
            log.error("batchDelError", e);
        } finally {
            if (redis != null) {
                redis.close();
            }
        }
    }

}
