package com.webservice.bookstore.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public String getRefreshToken(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setRefreshToken(String key, String value, Long time){
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        redisTemplate.opsForValue().set(key, value);
    }

    public void deleteRefreshToken(String key){
        redisTemplate.delete(key);
    }

}
