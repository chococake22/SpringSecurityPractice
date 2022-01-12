package com.example.springsecuritypractice.auth;

// 시큐리티가 /login 주소 요청을 낚아 채간다.
// 로그인 완료가 되면 Security ContextHolder에 session을 만들어 준다.
// 그 세션은 Authentication 타입의 객체가 있는데 이것은 UserDetails 타입이어야 한다.

import com.example.springsecuritypractice.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Account account;

    public PrincipalDetails(Account account) {
        this.account = account;
    }

    // 해당 회원이 어떠한 권한을 가지고 있는가
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        // account.getRole(); -> String 타입
        // 바로 String 타입으로 리턴 불가 -> Collection<GrantedAuthority>을 만들고
        // Override을 통해 String 타입으로 리턴할 수 있게 바꿔준다.
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return account.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
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
