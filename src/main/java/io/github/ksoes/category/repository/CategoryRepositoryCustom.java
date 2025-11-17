package io.github.ksoes.category.repository;

import io.github.ksoes.category.dto.CategoryCond;
import io.github.ksoes.category.dto.CategoryDto;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<CategoryDto> searchCategory(CategoryCond cond);
}
