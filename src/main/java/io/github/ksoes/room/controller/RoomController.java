package io.github.ksoes.room.controller;

import io.github.ksoes.room.dto.RoomCond;
import io.github.ksoes.room.service.RoomService;
import io.github.ksoes.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public ResponseEntity<?> searchRooms(@RequestBody RoomCond cond) {
        log.info("========== 방 목록조회 ==========");
        return ResponseEntity.ok(ApiResponse.success("조회 완료", roomService.searchRooms(cond)));
    }

    @PostMapping("/room")
    public void createRoom() {
        log.info("========== 방 생성 ==========");
        roomService.createRoom();
    }

    @PostMapping("/api/test")
    public void apiTest() {
        roomService.test();
    }
}
