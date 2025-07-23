package com.ssafy.tooguisquare.repository;

import com.ssafy.tooguisquare.entity.Channel;
import com.ssafy.tooguisquare.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {
    Room findFirstByChannelOrderByRoomNumDesc(Channel ch);
    boolean existsByChannelAndRoomNumAndStatusBetween(Channel channel, int roomNum, int status, int status2);
    Room findByRoomNumAndStatusBetween(int roomNum, int status, int status2);

}
