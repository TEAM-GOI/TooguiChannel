package com.ssafy.tooguisquare.repository;

import com.ssafy.tooguisquare.redis.RoomUser;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;

public class RedisRoomUserRepository {
    private final String hashReference = "RoomUser"; //DB의 테이블 역할
    @Resource(name = "redisTemplate") // 빨간 줄 무시
    private HashOperations<String, Long, RoomUser> hashOperations;

    public RoomUser getOneRoomUser(Long id){
        return hashOperations.get(hashReference, id);
    }
    public void updateRoomUser(RoomUser user){
        hashOperations.put(hashReference, user.getUserId(), user);
    }
    public void deleteRoomUser(Long id){
        hashOperations.delete(hashReference, id);
    }
}
