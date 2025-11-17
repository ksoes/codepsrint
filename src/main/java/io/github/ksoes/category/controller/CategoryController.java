package io.github.ksoes.category.controller;

import io.github.ksoes.category.dto.CategoryCond;
import io.github.ksoes.category.dto.CategoryForm;
import io.github.ksoes.category.service.CategoryService;
import io.github.ksoes.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/category/list")
    public ResponseEntity<?> searchCategoryList(@RequestBody CategoryCond cond) {
        log.info("========== 카테고리 목록조회 ==========");
        return ResponseEntity.ok(ApiResponse.success("조회 완료", categoryService.searchCategoryList(cond)));
    }

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryForm form) {
        log.info("========== 카테고리 생성 ==========");
        categoryService.createCategory(form);
        return ResponseEntity.ok(ApiResponse.success("카테고리 수정 완료", null));
    }
}
