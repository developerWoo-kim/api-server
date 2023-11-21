package gw.apiserver.member.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupForm {
    private String memberId;
    private String name;
    private String password;
}
