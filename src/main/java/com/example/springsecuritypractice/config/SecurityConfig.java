package com.example.springsecuritypractice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean   // 메서드를 통해 리턴되는 객체를 컨테이너에 등록함
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    };

    // js, css, image 등 보안 필터를 적용할 필요가 없는 리소스를 설정한다
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // login 없이 접근 허용 하는 url
                .antMatchers("/", "/signUpForm","/signUpCheck","/logout","/list", "/boardDetail/**").permitAll()
                // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
                // 그 외 모든 요청은 인증과정 필요
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/loginForm")    // 로그인하는 페이지 .html
                .loginProcessingUrl("/login")   // 아이디, 비밀번호 입력하고 확인 누르면 처리하는 url
                // 시큐리티가 /login을 낚아채서 로그인을 진행한다(따로 Controller에서 /login을 만들지 않아도 된다)
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .permitAll();

    }
}
