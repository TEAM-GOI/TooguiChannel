package com.ssafy.tooguisquare.redis;

import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisUser implements Serializable {

    private Long userId;

    //false == 대기중  // true == 게임중
    private boolean status;

    // 방 입/퇴장시 필요한 유저 정보를 캐싱하여 저장하기 위해
    private String nickName;

    private Long exp;

    private int imageId;
}
