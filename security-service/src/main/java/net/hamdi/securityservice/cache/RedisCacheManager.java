package net.hamdi.securityservice.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheManager {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // add token to redis
    public void addTokenToCache(String token, Object value) {
        redisTemplate.opsForValue().set(token, value);
    }
    // remove token from redis
    public void removeTokenFromCache(String token) {
        redisTemplate.delete(token);
    }
}
