package kr.co.lotte.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // 로그인 설정
        httpSecurity.formLogin(login -> login //Form 로그인 인증 기능이 작동함
                .loginPage("/member/login") // 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동페이지
                .failureUrl("/member/login?success=100") // 로그인 실패 후 이동 페이지
                .usernameParameter("uid") //아이디 파라미터명 설정
                .passwordParameter("pass")); // 패스워드 파라미터 설정

        // 로그아웃 설정
        httpSecurity.logout(logout -> logout
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login?success=300"));

        /*
            인가 설정
             - Spring Security는 존재하지 않는 요청 주소에 대해 기본적으로 login 페이지로 redirect를 수행
             - 자원 요청의 추가 인가 처리 확장과 redirect 기본 해제를 위해 마지막에 .anyRequest().permitAll() 설정
         */
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/article/**").permitAll()
                .requestMatchers("/market/list").permitAll()
                .requestMatchers("/market/view").permitAll()
                .requestMatchers("/market/**").authenticated()
                .requestMatchers("/admin/**").permitAll()
                .requestMatchers("/manager/**").hasAnyAuthority("ADMIN", "MANAGER")
                .anyRequest().permitAll());

        // 사이트 위변조 방지 설정
        httpSecurity.csrf(CsrfConfigurer::disable);

        //OAuth 설정
        httpSecurity.oauth2Login(config -> config.loginPage("/user/login")
                .defaultSuccessUrl("/"));


        return httpSecurity.build();
    }

    // Security 인증 암호화 인코더 설정
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}