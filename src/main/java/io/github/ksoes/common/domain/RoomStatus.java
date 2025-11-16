package io.github.ksoes.common.domain;

import lombok.Getter;

@Getter
public enum RoomStatus {
    WAITING("10", "대기중"),
    RUN("20", "진행중"),
    FINISH("30", "완료");

    private final String status;
    private final String description;

    RoomStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
