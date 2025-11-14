package io.github.ksoes.Account.controller;

import io.github.ksoes.Account.dto.AccountForm;
import io.github.ksoes.Account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountForm form) {
        accountService.saveAccount(form);
        return ResponseEntity.ok("회원가입 완료");
    }
}
