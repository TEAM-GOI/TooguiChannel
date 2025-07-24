package com.ssafy.tooguisquare.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorType {


    //도메인별로 ErrorType 정의, 에러 발생 시 errorCode,msg 반환

    //****************************Room****************************//
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND,"입장하려는 방이 존재하지 않습니다"),
    IS_NOT_AVAILABLE_REDISUSER(HttpStatus.I_AM_A_TEAPOT,"유저동선 추적오류가 발생했습니다"),
    NOT_AVAILABLE_CHANNEL(HttpStatus.BAD_REQUEST,"해당채널이 존재하지 않습니다"),
    NOT_VALID_FRIEND_NUM(HttpStatus.BAD_REQUEST,"삭제할 친구가 나의 친구가 아닙니다"),
    NOT_FOUND_ROOM(HttpStatus.NOT_FOUND, "존재하는 방 정보가 아닙니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED,"등록된 사용자가 없어요 !"),

    //******************** Square & Room **************************//
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND,"해당채널이 존재하지 않습니다."),
    NOT_FOUND_REDISUSER(HttpStatus.I_AM_A_TEAPOT,"유저동선 추적오류 입니다."),
    INVALID_PASSWORD(HttpStatus.LOCKED, "비밀번호가 일치하지 않습니다."),
    IS_FULL_ROOM(HttpStatus.UPGRADE_REQUIRED, "방이 가득찼다."),
    IS_NOT_AVAILABLE_REDIS_GAMEROOM(HttpStatus.FORBIDDEN, "레디스에 방 정보가 없습니다."),
    FAIL_TO_CREATE_ROOM(HttpStatus.BAD_REQUEST, "방 생성에 실패했습니다."),

    //******************** CHANNEL **************************//
    CHANNEL_IS_FULL(HttpStatus.NOT_FOUND,"입장하려는 채널이 가득 찼습니다."),
    NOT_FOUND_CHANNEL(HttpStatus.I_AM_A_TEAPOT,"채널이 존재하지 않습니다 이놈아."),
    ERR_CREATE_ROOMNUM(HttpStatus.FORBIDDEN, "방 번호 생성 실패"),
    LOCK_INTERRUPTED_ERROR(HttpStatus.LOCKED, "Lock획득 대기 상태 중 인터럽트가 발생하였습니다."),
    LOCK_NOT_AVAILABLE(HttpStatus.LOCKED, "Lock을 획득하지 못했습니다."),
    UNLOCKING_A_LOCK_WHICH_IS_NOT_LOCKED(HttpStatus.LOCKED, "이미 종료된 Lock입니다."),


    //****************************Test****************************//
    IS_ALREADY_EXIST_NICKNAME(HttpStatus.CONFLICT,"이미 존재하는 UUID 입니다.");

    private final HttpStatus status; //http status
    private final String msg; //error message

}
