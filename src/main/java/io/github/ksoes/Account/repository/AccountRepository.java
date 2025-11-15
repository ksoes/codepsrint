package io.github.ksoes.Account.repository;

import io.github.ksoes.Account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsById(String id);
    Optional<Account> findById(String id);
}
