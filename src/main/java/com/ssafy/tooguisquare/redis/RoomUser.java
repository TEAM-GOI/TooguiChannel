package com.ssafy.tooguisquare.redis;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomUser implements Serializable {
    private Long userId;
    private boolean isReady;
    private boolean isManager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomUser roomUser = (RoomUser) o;
        return Objects.equals(userId, roomUser.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
