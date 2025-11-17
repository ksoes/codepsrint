package io.github.ksoes.category.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.ksoes.category.dto.CategoryCond;
import io.github.ksoes.category.dto.CategoryDto;
import io.github.ksoes.common.domain.YesOrNo;
import lombok.RequiredArgsConstructor;
import static org.springframework.util.StringUtils.hasText;

import java.util.List;

import static io.github.ksoes.category.domain.QCategory.category;
import io.github.ksoes.category.dto.QCategoryDto;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CategoryDto> searchCategory(CategoryCond cond) {
        return queryFactory
                .select(new QCategoryDto(
                        category.id,
                        category.categoryName,
                        category.useYn
                ))
                .from(category)
                .where(
                        categoryNameLike(cond.getCategoryName()),
                        useYn(cond.getUseYn())
                )
                .fetch();
    }

    private BooleanExpression categoryNameLike(String categoryName) {
        return hasText(categoryName) ? category.categoryName.contains(categoryName) : null;
    }

    private BooleanExpression useYn(YesOrNo useYn) {
        return useYn != null ? category.useYn.eq(useYn) : null;
    }
}
