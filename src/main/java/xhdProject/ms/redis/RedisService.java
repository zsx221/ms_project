package xhdProject.ms.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

@Service
public class RedisService {

    private static JedisPool jPool;

    @Autowired
    private RedisPoolFactory redisPoolFactory;

    /**
     * JedisPool 无法通过@Autowired注入，可能由于是方法bean的原因，此处可以先注入RedisConfig，
     * 然后通过@PostConstruct初始化的时候将factory直接赋给jedisPool
     */
    @PostConstruct
    public void init() {
        jPool = redisPoolFactory.RedisPoolFactory();
    }

    // 获取单个对象
    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();
            String realKey = keyPrefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringtoBean(str, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    // 把数据库的数据添加进缓存

    public <T> boolean set(KeyPrefix keyPrefix, String key, T value) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();
            String str = beanToString(value);
            if (str == null || str.length() == 0) {
                return false;
            }
            if (key == null) {

            }
            String realKey = keyPrefix.getPrefix() + key;
            int seconds = keyPrefix.expirSeconds();
            if (seconds <= 0)// 永不过期
            {

            } else {
                jedis.setex(realKey, seconds, str);// 设置一个过期时间
            }
            jedis.set(realKey, str);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    // 判断key是否存在
    public <T> boolean exists(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();

            String realKey = keyPrefix.getPrefix() + key;

            return jedis.exists(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    // 删除
    public <T> boolean delete(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();

            String realKey = keyPrefix.getPrefix() + key;
            long ret=jedis.del(realKey);
            return ret>0;
        } finally {
            returnToPool(jedis);
        }
    }
    // 自增
    public <T> Long inc(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();// 生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.incr(realKey);
        } finally {
            returnToPool(jedis);
        }
    }

    // 自减
    public <T> Long decr(KeyPrefix keyPrefix, String key) {
        Jedis jedis = null;// 设置连接池里面拿一个jedis对象使用完就手动关闭
        try {
            jedis = jPool.getResource();// 生成真正的key
            String realKey = keyPrefix.getPrefix() + key;
            return jedis.decr(realKey);
        } finally {
            returnToPool(jedis);// 回收掉这个jedis
        }
    }

    private <T> String beanToString(T value) {// bean的对象转字符串
        if (value == null)
            return null;
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {// bean对象，就转成string
            return JSON.toJSONString(value);
        }

    }

    @SuppressWarnings("unchecked")
    private <T> T stringtoBean(String str, Class<T> clazz) {
        // TODO Auto-generated method stub
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {// bean对象，就转成string
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }

}
