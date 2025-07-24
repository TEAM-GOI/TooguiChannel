package com.ssafy.tooguisquare.repository;

import com.ssafy.tooguisquare.redis.RedisUser;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RedisUserRepository {

    private final String hashReference = "RedisUser";
    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, RedisUser> hashOperations;

    public void saveUserStatusGameing(RedisUser user){
        hashOperations.putIfAbsent(hashReference, user.getUserId(), user);
    }

    public void updateUserStatusGameing(RedisUser user){
        hashOperations.put(hashReference, user.getUserId(), user);
    }

    public RedisUser getOneRedisUser(Long id){
        return hashOperations.get(hashReference, id);
    }

    public void deleteUser(Long id){
        hashOperations.delete(hashReference, id);
    }

}

