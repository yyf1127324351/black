package com.back.service;

import java.io.IOException;
import java.util.List;


public interface RedisService {

	/**
	 * 简单的key-value 存储
	 */
	public void set(String key, String value);

	public void set(String key, String value, int expiration);

	public void append(String key, String value);

	public void append(String key, String value, int expiration);

	public String get(String key);

	public List<String> mget(String[] key);


	/**
	 * key-Object 存储
	 * 
	 * @throws IOException
	 */
	public void setKey_Obj(String key, Object value, int expiration);

	/**
	 * 简单的Key-Object获取值
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object getObjectByKey(String key) throws IOException, ClassNotFoundException;

	/**
	 * 根据key删除值
	 * 
	 * @throws IOException
	 */
	public void del(String key);

	/**
	 * list插值
	 * 
	 * @param key
	 * @param value
	 */
	void setList(String key, String value);

	/**
	 * list取值
	 * 
	 * @param key
	 * @return
	 */
	List<String> getListByKey(String key);

	/**
	 * 过期处理
	 * 
	 * @param key
	 * @param time
	 */
	void expire(String key, Integer time);


	/**
	 * 批量key object获取
	 * 
	 * @param keys
	 * @return
	 */
	Object[] getObjects(String[] keys);

	public Long ttl(String key);


	void batchDel(String redisKeyPre);
}
