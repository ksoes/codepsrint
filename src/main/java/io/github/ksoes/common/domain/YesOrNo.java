package io.github.ksoes.common.domain;

import lombok.Getter;

@Getter
public enum YesOrNo {
    Y("Yes"),
    N("No");

    private final String description;

    YesOrNo(String description) {
        this.description = description;
    }
}
