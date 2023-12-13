package gw.apiserver.common.security.core.userdetails;

import gw.apiserver.member.domain.Member;
import gw.apiserver.oms.user.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {
    private String memberId;
    private String password;
    private String roles;

    public CustomUserDetails(String memberId, String password, String roles) {
        this.memberId = memberId;
        this.password = password;
        this.roles = roles;
    }

    public CustomUserDetails(String memberId, String roles) {
        this.memberId = memberId;
        this.roles = roles;
    }

    public static CustomUserDetails of(String memberId, String roles) {
        return new CustomUserDetails(memberId, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        List<String> roleList = new ArrayList<>();
        if(!this.roles.isEmpty()) {
            roleList = Arrays.asList(this.roles.split(","));
        }

        roleList.forEach(r -> {
                    authorities.add(()->r);
                });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
