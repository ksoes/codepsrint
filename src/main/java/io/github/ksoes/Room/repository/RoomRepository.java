package io.github.ksoes.Room.repository;

import io.github.ksoes.Room.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByCategoryIdAndStatusCd(Long categoryId, String statusCd);
    Room existsByCategoryId(Long categoryId);
}
