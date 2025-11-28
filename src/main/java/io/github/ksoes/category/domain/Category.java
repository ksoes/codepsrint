package io.github.ksoes.category.domain;

import io.github.ksoes.quiz.domain.Quiz;
import io.github.ksoes.room.domain.Room;
import io.github.ksoes.common.domain.YesOrNo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 카테고리 하나에 상태가 다른 Room이 여러개 존재 가능하므로 Category(1):Room(N)
    @OneToMany(mappedBy = "category")
    private List<Room> rooms;

    @OneToMany(mappedBy = "category")
    private List<Quiz> quizzes;

    private String categoryName;

    @Enumerated(EnumType.STRING)
    private YesOrNo useYn;

    public void updateCategory(String categoryName, YesOrNo useYn) {
        this.categoryName = categoryName;
        this.useYn = useYn;
    }
}
