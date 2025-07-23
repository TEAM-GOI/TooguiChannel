package com.ssafy.tooguisquare.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RoomListResponse(
        int totalRoomCount,
        List<SquareRoom> list

) {

}
