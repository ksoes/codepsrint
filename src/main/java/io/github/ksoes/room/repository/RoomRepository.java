package io.github.ksoes.room.repository;

import io.github.ksoes.room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByCategoryIdAndStatusCd(Long categoryId, String statusCd);
    Boolean existsByCategoryIdAndStatusCd(Long categoryId, String statusCd);
}
