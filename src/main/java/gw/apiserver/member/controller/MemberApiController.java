package gw.apiserver.member.controller;

import gw.apiserver.member.controller.form.SignupForm;
import gw.apiserver.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "홈";
    }


    @PostMapping("/members/signup")
    public String signup(@RequestBody SignupForm form) {

        memberService.signup(form);

        return "회원가입 완료";
    }
}
