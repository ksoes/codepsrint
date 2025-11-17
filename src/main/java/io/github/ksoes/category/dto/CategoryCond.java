package io.github.ksoes.category.dto;

import io.github.ksoes.common.domain.YesOrNo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCond {
    private String categoryName;
    private YesOrNo useYn;
}
