package com.ssafy.tooguisquare.dto.request;

import lombok.Builder;


@Builder
public record RoomEnterRequest(
        Long roomId,
        int roomNum,
        String password
) {
}
