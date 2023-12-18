package gw.apiserver.common.security.core.userdetails.service;

import gw.apiserver.common.security.core.userdetails.CustomUserDetails;
import gw.apiserver.member.repository.MemberRepository;
import gw.apiserver.oms.auth.domain.AuthGroup;
import gw.apiserver.oms.auth.domain.AuthGroupRole;
import gw.apiserver.oms.auth.domain.AuthGroupUser;
import gw.apiserver.oms.user.domain.User;
import gw.apiserver.oms.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException(""));
        AuthGroup authGroup = user.getAuthGroupUser().getAuthGroup();

        String role = authGroup.getRoleStr();

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getUserId(), user.getPswd(), role);

        return customUserDetails;
    }
}
