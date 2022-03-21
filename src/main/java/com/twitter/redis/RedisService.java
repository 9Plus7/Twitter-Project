package com.twitter.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.twitter.repo.TweetRepository;
import com.twitter.model.Tweet;
import com.twitter.model.TweetUI;

/**
 * 通过Jedis操作Redis
 */
@Service
public class RedisService {
    @Autowired
    JedisPool jedisPool;

    /**
     * 获取对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * 设置对象
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(String key,T value){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String str=beanToString(value);
            if (str==null||str.length()<=0){
                return false;
            }
            jedis.set(key,str);
            return true;
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * 删除
     * @param key
     * @return
     */
    public boolean delete(String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            long ret=jedis.del(key);
            return ret>0;
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * <p>
     * 设置key value,如果key已经存在则返回0,nx==> not exist
     * </p>
     *
     * @param key
     * @param value
     * @return 成功返回1 如果存在 和 发生异常 返回 0
     */
    public Long save(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.setnx(key, value);

        } finally {
            closeJedis(jedis);
        }
    }



    /**
     * 判断key是否存在
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.exists(key);
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * 增加值
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long incr(String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.incr(key);
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * 减少值
     * @param key
     * @param <T>
     * @return
     */
    public<T> Long decr(String key){
        Jedis jedis=null;
        try{
            jedis=jedisPool.getResource();
            return jedis.decr(key);
        }finally {
            closeJedis(jedis);
        }
    }

    /**
     * 关闭jedis
     * @param jedis
     */
    private void closeJedis(Jedis jedis) {//用完记得要关闭
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 将String转换为其他对象
     *
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 将其他对象转换为String
     * @param value
     * @param <T>
     * @return
     */
    private <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }
}
