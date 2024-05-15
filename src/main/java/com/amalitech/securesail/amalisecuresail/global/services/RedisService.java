package com.amalitech.securesail.amalisecuresail.global.services;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value, Duration timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut);
    }
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public void updateValue(String key, String newValue){
        redisTemplate.opsForValue().set(key, newValue);
    }
    public void updateValue(String key, String newValue, Duration timeout){
        redisTemplate.opsForValue().set(key, newValue, timeout);
    }
    public void deleteValue(String key){
        redisTemplate.delete(key);
    }
}