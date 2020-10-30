package se.experis.academy.noticeboard.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@PropertySource("application.properties")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Bean
    public JedisPool getJedisPool() {
        try {
            URI redisURI = new URI(redisHost);
            return new JedisPool(new JedisPoolConfig(),
                    redisURI.getHost(),
                    redisURI.getPort(),
                    Protocol.DEFAULT_TIMEOUT,
                    redisURI.getUserInfo().split(":",2)[1]);
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        }
    }
    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }
}
