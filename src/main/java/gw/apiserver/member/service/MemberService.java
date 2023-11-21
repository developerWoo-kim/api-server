package gw.apiserver.member.service;

import gw.apiserver.member.controller.form.SignupForm;
import gw.apiserver.member.domain.Member;
import gw.apiserver.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupForm form) {
        Member member = new Member().builder()
                .memberId(form.getMemberId())
                .name(form.getName())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();

        member.setRoles("USER");

        memberRepository.save(member);
    }
}
