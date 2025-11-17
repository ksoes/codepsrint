package io.github.ksoes.user.domain;

import io.github.ksoes.account.domain.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    private String name;
    private String birth;
    private BigDecimal totalScore;
    private String authCd;

    @OneToOne(mappedBy = "user")
    private Account account;

    public void updateUser(String name, String authCd) {
        this.name = name;
        this.authCd = authCd;
    }
}
