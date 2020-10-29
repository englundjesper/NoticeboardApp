package se.experis.academy.noticeboard.utils;

import io.lettuce.core.RedisURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RedisConfig {

    private static Jedis getConnection() throws URISyntaxException {
        URI redisURI = new URI("redis://h:p05678bbf8d604efc616a656fbab1df89041e9b59eaccb501bccaf19993b37052@ec2-34-239-50-117.compute-1.amazonaws.com:14079");
        Jedis jedis = new Jedis(redisURI);
        return jedis;
    }

    public static JedisPool getPool() throws URISyntaxException {
        URI redisURI = new URI(System.getenv("redis://h:p05678bbf8d604efc616a656fbab1df89041e9b59eaccb501bccaf19993b37052@ec2-34-239-50-117.compute-1.amazonaws.com:14079"));
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        JedisPool pool = new JedisPool(poolConfig, redisURI);
        return pool;
    }
    @Bean
    public RedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        var uri = RedisURI.create(System.getenv("redis://h:p05678bbf8d604efc616a656fbab1df89041e9b59eaccb501bccaf19993b37052@ec2-34-239-50-117.compute-1.amazonaws.com:14079"));
        new RedisStandaloneConfiguration(uri.getHost(), uri.getPort());
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        return jedisConnectionFactory;
    }
}
