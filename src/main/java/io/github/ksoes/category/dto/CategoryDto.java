package io.github.ksoes.category.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.github.ksoes.common.domain.YesOrNo;
import lombok.Getter;

@Getter
public class CategoryDto {
    private Long id;
    private String categoryName;
    private YesOrNo useYn;

    @QueryProjection
    public CategoryDto(Long id, String categoryName, YesOrNo useYn) {
        this.id = id;
        this.categoryName = categoryName;
        this.useYn = useYn;
    }
}
