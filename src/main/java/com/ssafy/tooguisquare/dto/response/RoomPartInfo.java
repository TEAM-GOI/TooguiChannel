package com.ssafy.tooguisquare.dto.response;

import lombok.Builder;

@Builder
public record RoomPartInfo(Long userId, String userNick, boolean isReady, boolean isManager, Long exp, int imageId, Long roomId) {
}
