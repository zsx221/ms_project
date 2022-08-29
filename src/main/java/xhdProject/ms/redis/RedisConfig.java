package xhdProject.ms.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int poolMaxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private int poolMaxWait;

    @Value("${spring.redis.jedis.pool.max-active}")
    private int poolMaxTotal;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getPassword() {
        return password;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }


}
