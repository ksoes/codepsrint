package io.github.ksoes.room.dto;

import io.github.ksoes.room.domain.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private String statusCd;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.categoryId = room.getCategory().getId();
        this.categoryName = room.getCategory().getCategoryName();
        this.statusCd = room.getStatusCd();
    }
}
