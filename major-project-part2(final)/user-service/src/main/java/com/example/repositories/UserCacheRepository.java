package com.example.repositories;

import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class UserCacheRepository {

    private static final String USER_KEY_PREFIX = "usr::";

    private static final Long USER_KEY_EXPIRY = 1l;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void save(User user){
        redisTemplate.opsForValue().set(getKey(user.getId()), user, USER_KEY_EXPIRY, TimeUnit.HOURS);
    }

    public User get(Integer userId){
        return (User) redisTemplate.opsForValue().get(getKey(userId));
    }

    private String getKey(Integer userId){
        return USER_KEY_PREFIX + userId;
    }
}
