package com.ssafy.tooguisquare.repository;

import com.ssafy.tooguisquare.redis.WaitingRoom;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisRoomRepository {
    private final String hashReference = "WaitingRoom"; //DB의 테이블 역할
    @Resource(name = "redisTemplate") // 빨간 줄 무시
    private HashOperations<String, Long, WaitingRoom> hashOperations;

    public void saveWaitingRoom(WaitingRoom room){
        hashOperations.putIfAbsent(hashReference, room.getId(), room);
    }

    public WaitingRoom getOneWaitingRoom(Long id){
        return hashOperations.get(hashReference, id);
    }

    public void updateWaitingRoom(WaitingRoom room){
        hashOperations.put(hashReference, room.getId(), room);
    }

    public Map<Long, WaitingRoom> getAllWaitingRooms(){
        return hashOperations.entries(hashReference);
    }

    public void deleteWaitingRoom(Long id){
        hashOperations.delete(hashReference, id);
    }
}
