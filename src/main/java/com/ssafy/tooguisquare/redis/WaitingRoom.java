package com.ssafy.tooguisquare.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaitingRoom implements Serializable {
    private Long id; //방의 아이디값(키값)
    private List<RoomUser> participants;
}
