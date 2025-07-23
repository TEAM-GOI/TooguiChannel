package com.ssafy.tooguisquare.repository;

import com.ssafy.tooguisquare.dto.response.SquareRoom;
import com.ssafy.tooguisquare.entity.Channel;
import com.ssafy.tooguisquare.entity.Room;
import java.util.List;

public interface RoomRepositoryCustom {
    List<SquareRoom> findRoomCanEnter(Long channelId);
    Room findLargestRmNum(Channel ch);
    //Long countRmNumByCh(Channel ch, int roomNum);
    Long findRoomIdByRoomNumAndStatusZero(int roomNum);
}
