package com.webservice.bookstore.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    public String getData(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setData(String key, String value, Long time){
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(value.getClass()));
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void deleteData(String key){
        redisTemplate.delete(key);
    }

    public void setData(String key, String value){
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,value);
    }




}
