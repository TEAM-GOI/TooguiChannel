package com.ssafy.tooguisquare.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record RoomCreateRequest(
    @NotBlank(message = "방 제목은 필수입니다.")
    String title,
    boolean isPrivate,
    String password,

    int status,
    @Positive(message = "시작 년도는 양수여야 합니다.")
    int startYear,
    @Positive(message = "종료 년도는 양수여야 합니다.")
    int endYear,

    @NotNull(message = "채널 아이디는 필수입니다.")
    Long channelId
){
    @Builder
    public RoomCreateRequest{

    }
}
