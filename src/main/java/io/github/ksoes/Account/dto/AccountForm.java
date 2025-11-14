package io.github.ksoes.Account.dto;

import lombok.Getter;
import lombok.Setter;

//import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AccountForm {
//    @NotBlank
    public String id;
    public String password;
}
