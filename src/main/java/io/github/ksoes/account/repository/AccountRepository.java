package io.github.ksoes.account.repository;

import io.github.ksoes.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(String id);
    Optional<Account> findById(String id);
}
