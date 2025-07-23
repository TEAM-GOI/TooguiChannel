package com.ssafy.tooguisquare.controller;

import com.ssafy.tooguisquare.dto.request.RoomEnterRequest;
import com.ssafy.tooguisquare.dto.response.RoomPartInfo;
import com.ssafy.tooguisquare.response.SuccessResponse;
import com.ssafy.tooguisquare.response.SuccessType;
import com.ssafy.tooguisquare.service.RoomService;
import com.ssafy.tooguisquare.stomp.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final SimpMessageSendingOperations messageTemplate;
    private final RoomService roomService;

    //방 들어가기
    @PostMapping("/enter")
    public SuccessResponse<List<RoomPartInfo>> enterRoom(@RequestHeader("memberId") Long userId, @RequestBody RoomEnterRequest enterInfo){
        //log.info("RoomController enterRoom start");
        List<RoomPartInfo> rst = roomService.enterRoom(userId, enterInfo);
        //websocket 들어감 보내주기
        messageTemplate.convertAndSend("/sub/room/chat/" + rst.get(0).roomId(),
                MessageDto
                        .builder()
                        .type(MessageDto.MessageType.ROOM_ENTER)
                        .data(rst)
                        .build());
        return SuccessResponse.of(SuccessType.ENTER_ROOM_SUCCESSFULLY,rst);
    }

    @GetMapping("/userlist/{id}")
    public SuccessResponse<List<RoomPartInfo>> getUserList(@PathVariable("id") Long rId){
        return SuccessResponse.of(SuccessType.GET_ROOM_USER_LIST_SUCCESSFULLY, roomService.getUserList(rId));
    }

    //방 나가기
    @DeleteMapping("/exit/{id}")
    public SuccessResponse<Void> exitRoom(@AuthenticationPrincipal User user, @PathVariable("id") Long rId){
        List<RoomPartInfo> rInfo = roomService.exitRoom(user, rId);
        messageTemplate.convertAndSend("/sub/room/chat/" + rId,
                MessageDto
                        .builder()
                        .type(MessageDto.MessageType.ROOM_EXIT)
                        .data(rInfo)
                        .build());

        return SuccessResponse.from(SuccessType.EXIT_ROOM_SUCCESSFULLY);
    }

    @PutMapping("/kick")
    public SuccessResponse<List<RoomPartInfo>> kickUser(@AuthenticationPrincipal User user, @RequestBody KickRequest kinfo){
        //log.info("RoomController kickUser start");
        List<RoomPartInfo> rInfo = roomService.kickUser(user, kinfo.userId(), kinfo.roomId());
        messageTemplate.convertAndSend("/sub/room/chat/" + kinfo.roomId(),
                MessageDto
                        .builder()
                        .type(MessageDto.MessageType.ROOM_KICK)
                        .data(rInfo)
                        .build());

        return SuccessResponse.of(SuccessType.USER_KICK_OUT_SUCCESSFULLY,rInfo);
    }

    @PostMapping("/ready/{id}")
    public SuccessResponse<ReadyResponse> readyRoom(@AuthenticationPrincipal User user, @PathVariable("id") Long grId){
        ReadyResponse rsp = roomService.doingRoomReady(user, grId);
        //status = 0이면 레디를 한것, status = -1이면 레디를 취소한 것
        //status = 1이면 전체가 레디를 완료한 것
        sendMsg(grId, rsp, MessageDto.MessageType.READY);
        return SuccessResponse.of(SuccessType.USER_READY_SUCCESSFULLY,rsp);
    }

    public void sendMsg(Long grId, Object obj, MessageDto.MessageType type){
        messageTemplate.convertAndSend("/sub/room/chat/" + grId,
                MessageDto.builder()
                        .type(type)
                        .data(obj)
                        .build()); //웹소켓으로 게임에 참가한 모든 이용자들에게 게임 결과를 보낸다.
    }

}
