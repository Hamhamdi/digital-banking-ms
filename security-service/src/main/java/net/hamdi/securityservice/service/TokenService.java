package net.hamdi.securityservice.service;

import net.hamdi.securityservice.dto.TokenDetails;
import net.hamdi.securityservice.exception.TokenNotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;

    public TokenService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public TokenDetails introspectToken(String token) {
        // vérifier le token dans Redis ou via une librairie JWT
        if (redisTemplate.hasKey(token)) {
            return (TokenDetails) redisTemplate.opsForValue().get(token);
        }
        throw new TokenNotFoundException("Token invalide ou expiré");
    }
}
