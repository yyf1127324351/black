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

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	private static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

	private static String IP = ConfigConstants.REDIS_IP;
	private static int PORT = Integer.valueOf(ConfigConstants.REDIS_PORT);
	private static String PASSWORD = ConfigConstants.REDIS_PWD;

	private static JedisPool jedisPool;
	private static JedisPoolConfig config = new JedisPoolConfig();

	static {
		config.setMaxTotal(100);
		config.setMaxIdle(20);
		config.setMaxWaitMillis(1000L);
		if (IP.equals("192.168.2.18")) {
			jedisPool = new JedisPool(config, IP, PORT, 1000, PASSWORD);
		} else{
			jedisPool = new JedisPool(config, IP, PORT, 1000);
		}
	}
	/**
	 * 简单的key-value 存储
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				redis.set(key, value);
			} catch (Exception e) {
				logger.error("redis set value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
	}

	/**
	 * 简单的key-value 存储
	 * @param key
	 * @param value
	 * @param expiration 过期时间
	 */
	public void set(String key, String value, int expiration) {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				redis.set(key, value);
				if (expiration > 0) {
					redis.expire(key, expiration);
				}
			} catch (Exception e) {
				logger.error("redis set value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
	}

	/**
	 * 简单的key-value 存储 在原有值得基础上添加,如若之前没有该key，则导入该key
	 * 之前已经设定了,此句执行便会对应的key的value进行append
	 *
	 * @param key
	 * @param value
	 */
	public void append(String key, String value) {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				redis.append(key, value);
			} catch (Exception e) {
				logger.error("redis append value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
	}

	/**
	 * 简单的key-value 存储 在原有值得基础上添加,如若之前没有该key，则导入该key
	 * 之前已经设定了,此句执行便会对应的key的value进行append
	 * 
	 * @param key
	 * @param value
	 * @param expiration 过期时间
	 */
	public void append(String key, String value, int expiration) {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				redis.append(key, value);
				if (expiration > 0) {
					redis.expire(key, expiration);
				}
			} catch (Exception e) {
				logger.error("redis append value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
	}

	/**
	 * 简单的Key-value获取值
	 * @param key
	 */
	public String get(String key) {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
			return null;
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				return redis.get(key);
			} catch (Exception e) {
				logger.error("redis get value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
		return null;
	}

	/**
	 * 获取多个值
	 * @param key
	 */
	public List<String> mget(String[] key) {
		if (key == null || key.length == 0) {
			logger.debug("Redis key is null");
			return null;
		} else {
			Jedis redis = null;
			try {
				redis = jedisPool.getResource();
				return redis.mget(key);
			} catch (Exception e) {
				logger.error("redis get value error", e);
			} finally {
				if (redis != null) {
					redis.close();
				}
			}
		}
		return null;
	}

	@Override
	public Object[] getObjects(String[] key) {
		if (key == null || key.length == 0) {
			logger.debug("Redis key is null");
			return null;
		} else {
			Jedis j = null;
			List<byte[]> bs = null;
			try {
				j = jedisPool.getResource();
				byte[][] bytes = new byte[key.length][];
				for (int i = 0; i < key.length; i++) {
					bytes[i] = key[i].getBytes();
				}
				bs = j.mget(bytes);
			} catch (Exception e) {
				logger.error("GetObjects error", e);
			} finally {
				if (j != null) {
					j.close();
				}
			}
			Object[] array = new Object[key.length];
			for (int i = 0; i < key.length; i++) {
				byte[] b = bs.get(i);
				if (null != b && b.length > 0) {
					ByteArrayInputStream bis = new ByteArrayInputStream(b);
					try {
						ObjectInputStream inputStream = null;
						inputStream = new ObjectInputStream(bis);
						try {
							Object o = inputStream.readObject();
							array[i] = o;
						} catch (ClassNotFoundException e) {
						}
					} catch (IOException e) {
					}
				}
			}
			return array;
		}
	}

	public void setKey_Obj(String key, Object value) {
		setKey_Obj(key, value, 0);
	}

	/**
	 * key-Object 存储
	 * 
	 * @throws IOException
	 */
	public void setKey_Obj(String key, Object value, int expiration) {
		if (StringUtils.isBlank(key)) {
			logger.debug("data2Cache key is null");
		} else {
			initSetObject(key, value, expiration);
		}
	}

	/**
	 * 简单的Key-Object获取值
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object getObjectByKey(String key) throws IOException, ClassNotFoundException {
		if (StringUtils.isBlank(key)) {
			logger.debug("Redis key is null");
		}
		return initGetObject(key);
	}

	/**
	 * 根据key删除值
	 * 
	 * @throws IOException
	 */
	@Override
	public void del(String key) {
		Jedis redis = null;
		try {
			redis = jedisPool.getResource();
			redis.del(key);
		} catch (Exception e) {
			logger.error("del error", e);
		} finally {
			if (redis != null) {
				redis.close();
			}
		}
	}

	public void initSetObject(String key, Object ob, int expiration) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		Jedis redis = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(ob);
			byte[] byteArray = bos.toByteArray();
			redis = jedisPool.getResource();
			redis.set(key.getBytes(), byteArray);
			if (expiration > 0) {
				redis.expire(key.getBytes(), expiration);
			}
		} catch (Exception e) {
			logger.error("initSetObject error", e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException ex) {
					logger.error("RedisServiceImpl initSetObject error.Msg:", ex);
				}
			}
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException ex) {
					logger.error("RedisServiceImpl initSetObject error.Msg:", ex);
				}
			}
			if (redis != null) {
				redis.close();
			}
		}
	}

	public Object initGetObject(String key) {
		Jedis j = null;
		Object o = null;
		byte[] bs = null;
		try {
			j = jedisPool.getResource();
			bs = j.get(key.getBytes());
		} catch (Exception e) {
			logger.error("initGetObject error", e);
		} finally {
			if (j != null) {
				j.close();
			}
		}
		if (null != bs && bs.length > 0) {
			ByteArrayInputStream bis = new ByteArrayInputStream(bs);
			try {
				ObjectInputStream inputStream = null;
				inputStream = new ObjectInputStream(bis);
				try {
					o = inputStream.readObject();
				} catch (ClassNotFoundException e) {
				}
				inputStream.close();
				bis.close();
			} catch (IOException e) {
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException ex) {
						logger.error("RedisServiceImpl initSetObject error.Msg:", ex);
					}
				}
			}
		}
		return o;
	}

	/**
	 * 根据key获取List
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public List<String> getListByKey(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		List<String> list = null;
		Jedis edis = null;
		try {
			edis = jedisPool.getResource();
			list = edis.lrange(key, 0, -1);
		} finally {
			if (edis != null) {
				edis.close();
			}
		}
		return list;
	}

	public void setList(String key, String value) {
		if (StringUtils.isBlank(key)) {
			return;
		}
		Jedis edis = null;
		try {
			edis = jedisPool.getResource();
			edis.rpush(key, value);
		} finally {
			if (edis != null) {
				edis.close();
			}
		}
	}

	@Override
	public void expire(String key, Integer time) {
		Jedis redis = null;
		try {
			redis = jedisPool.getResource();
			redis.expire(key, time);
		} catch (Exception e) {
		} finally {
			if (redis != null) {
				redis.close();
			}
		}
	}

	@Override
	public Long ttl(String key) {
		Jedis redis = null;
		Long ttl = null;
		try {
			redis = jedisPool.getResource();
			ttl = redis.ttl(key.getBytes());
		} catch (Exception e) {
			logger.error("initGetObject error", e);
		} finally {
			if (redis != null) {
				redis.close();
			}
		}
		return ttl;
	}

	public void batchDel(String pattern){
		Jedis redis = null;
		try {
			redis = jedisPool.getResource();
			Set<String> keys = redis.keys(pattern+"*");
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String keyStr = it.next();
				redis.del(keyStr);
			}
		} finally {
			if (redis != null) {
				redis.close();
			}
		}
	}

}
