package io.github.ksoes.Account.service;

import io.github.ksoes.Account.domain.Account;
import io.github.ksoes.Account.dto.AccountForm;
import io.github.ksoes.Account.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveAccount(AccountForm form) {
        if (accountRepository.existsById(form.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());

        accountRepository.save(Account.builder()
                .id(form.getId())
                .password(encodedPassword)
                .build());
    }
}
