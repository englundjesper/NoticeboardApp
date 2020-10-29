package se.experis.academy.noticeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import se.experis.academy.noticeboard.utils.RedisConfig;

import javax.swing.*;
import java.net.URISyntaxException;

@SpringBootApplication
public class NoticeboardAppApplication {

    public static void main(String[] args) throws URISyntaxException {
        SpringApplication.run(NoticeboardAppApplication.class, args);

    }

}
