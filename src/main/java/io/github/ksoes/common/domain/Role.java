package io.github.ksoes.common.domain;

public enum Role {
    ADMIN("10"),
    USER("20");

    private final String code;

    Role(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
