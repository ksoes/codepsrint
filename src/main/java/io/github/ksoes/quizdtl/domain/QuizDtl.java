package io.github.ksoes.quizdtl.domain;

import io.github.ksoes.quiz.domain.Quiz;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class QuizDtl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="quiz_id")
    private Quiz quiz;

    private String question;
    private String answer;
}
