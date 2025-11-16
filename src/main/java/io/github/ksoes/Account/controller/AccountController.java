package io.github.ksoes.Account.controller;

import io.github.ksoes.Account.dto.AccountForm;
import io.github.ksoes.Account.service.AccountService;
import io.github.ksoes.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountForm form) {
        log.info("========== 회원가입 ==========");
        accountService.signup(form);
        return ResponseEntity.ok(ApiResponse.success("회원가입 완료", null));
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<?> signupAdmin(@RequestBody AccountForm form) {
        log.info("========== 관리자 회원가입 ==========");
        accountService.signupAdmin(form);
        return ResponseEntity.ok(ApiResponse.success("관리자 회원가입 완료", null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountForm form) {
        log.info("========== 로그인 ==========");
        accountService.login(form);
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", null));
    }
}
