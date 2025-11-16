package io.github.ksoes.Room.domain;

import io.github.ksoes.Category.domain.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카테고리 하나당 상태가 다른 방이 여러개 존재하므로 Category(1):Room(N)
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    private String statusCd;
}
