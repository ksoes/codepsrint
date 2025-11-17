package io.github.ksoes.category.service;

import io.github.ksoes.category.domain.Category;
import io.github.ksoes.category.dto.CategoryCond;
import io.github.ksoes.category.dto.CategoryDto;
import io.github.ksoes.category.dto.CategoryForm;
import io.github.ksoes.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /*
    * 카테고리 목록조회
    * */
    public List<CategoryDto> searchCategoryList(CategoryCond cond) {
        return categoryRepository.searchCategory(cond);
    }

    /*
    * 카테고리 수정
    * */
    @Transactional
    public void createCategory(CategoryForm categoryForm) {
        List<CategoryForm.CreateList> createDatas = Optional.ofNullable(categoryForm.getCreateData()).orElse(Collections.emptyList());
        List<CategoryForm.UpdateList> updateLists = Optional.ofNullable(categoryForm.getUpdateData()).orElse(Collections.emptyList());
        List<CategoryForm.DeleteList> deleteLists = Optional.ofNullable(categoryForm.getDeleteData()).orElse(Collections.emptyList());

        // 생성
        for (CategoryForm.CreateList form : createDatas) {
             if (categoryRepository.existsByCategoryName(form.getCategoryName())) {
                 throw new IllegalArgumentException("이미 존재하는 카테고리입니다.");
             }

             categoryRepository.save(Category.builder()
                     .categoryName(form.getCategoryName())
                     .useYn(form.getUseYn())
                     .build());
        }

        // 수정
        for (CategoryForm.UpdateList form : updateLists) {
            categoryRepository.findById(form.getId()).ifPresent(
                    category -> category.updateCategory(form.getCategoryName(), form.getUseYn())
            );
        }

        // 삭제
        for (CategoryForm.DeleteList form : deleteLists) {
            categoryRepository.findById(form.getId()).ifPresent(categoryRepository::delete);
        }
    }
}
