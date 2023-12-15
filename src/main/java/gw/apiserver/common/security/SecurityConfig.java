package gw.apiserver.common.security;

import gw.apiserver.common.security.access.CustomAccessDeniedHandler;
import gw.apiserver.common.security.access.CustomAuthenticationEntryPoint;
import gw.apiserver.common.security.access.CustomAuthenticationFailureHandler;
import gw.apiserver.common.security.access.CustomAuthenticationSuccessHandler;
import gw.apiserver.common.security.core.JwtTokenProvider;
import gw.apiserver.common.security.core.encoder.ShaEncoder;
import gw.apiserver.common.security.core.userdetails.service.CustomUserDetailsService;
import gw.apiserver.common.security.filter.JwtAuthenticationFilter;
import gw.apiserver.common.security.filter.JwtVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * JWT TOKEN 기반 스프링 시큐리티 설정
 *
 * @author kimgunwoo
 * @since 2023.11.21
 * @version 1.0v
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();                 -> 스프링에서 권장하고 있음 sha256 보다 안전 하다
//        return new MessageDigestPasswordEncoder("SHA-256"); -> 한국 인터넷 진흥원에서 권장함
        return new ShaEncoder();                           // -> dpubad에 이렇게 되어있음.. 추후 위에 두가지중 하나로 변경 필요함
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(
                "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs", "/v3/api-docs",
                "/auth/token", "/auth/reissue", "/error",
                "/api/v1/user/join","/api/v1/user/id-duple-check/**","/api/v1/code/detail/**"
//                "/api/**"
        );
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())// CrossOrigin(인증X), 시리큐리 필터에 등록(인증O)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 권한 없는 사용자에 대한 예외 처리
        http
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증
                .accessDeniedHandler(new CustomAccessDeniedHandler());          // 인가

        http
                .authorizeRequests()
                .anyRequest().access("@authorizationChecker.check(request, authentication)");

//        http
//                .authorizeRequests(authorize -> authorize
//                        .antMatchers("/api/v1/user/**")
//                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/manager/**")
//                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/admin/**")
//                        .access("hasRole('ROLE_ADMIN')")
//                        .anyRequest().permitAll()
//                );

        //                .antMatchers("/api/v1/manager/**")
//                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/api/v1/admin/**")
//                .access("hasRole('ROLE_ADMIN')")



        http
                .apply(new CustomFilterConfigurer());

        return http.build();
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity>{
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider, passwordEncoder());

            jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());

            http
                    // 시큐리티 필터 체인이 모든 필터의 우선 순위를 가진다
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JwtVerificationFilter(authenticationManager, jwtTokenProvider));

        }
    }


}
