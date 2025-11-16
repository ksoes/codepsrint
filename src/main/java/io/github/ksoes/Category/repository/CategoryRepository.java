package io.github.ksoes.Category.repository;

import io.github.ksoes.Category.domain.Category;
import io.github.ksoes.common.domain.YesOrNo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUseYn(YesOrNo useYn);
}
