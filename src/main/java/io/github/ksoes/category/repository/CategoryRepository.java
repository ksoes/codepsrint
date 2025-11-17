package io.github.ksoes.category.repository;

import io.github.ksoes.category.domain.Category;
import io.github.ksoes.common.domain.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    List<Category> findAllByUseYn(YesOrNo useYn);
    Boolean existsByCategoryName(String categoryName);
}
