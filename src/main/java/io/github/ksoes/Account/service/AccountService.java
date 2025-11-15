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

    /*
    * 회원가입
    * */
    @Transactional
    public void signin(AccountForm form) {
        if (accountRepository.existsById(form.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());
        accountRepository.save(Account.builder()
                .id(form.getId())
                .password(encodedPassword)
                .build());
    }

    /*
    * 로그인
    * */
    public void login(AccountForm form) {
        Account account = accountRepository.findById(form.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        boolean match = passwordEncoder.matches(form.getPassword(), account.getPassword());
        if (!match) {
            throw new IllegalArgumentException("올바른 아이디와 비밀번호를 입력해주세요.");
        }
    }
}
