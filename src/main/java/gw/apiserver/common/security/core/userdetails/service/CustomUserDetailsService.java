package gw.apiserver.common.security.core.userdetails.service;

import gw.apiserver.common.security.core.userdetails.CustomUserDetails;
import gw.apiserver.member.repository.MemberRepository;
import gw.apiserver.oms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }
}
