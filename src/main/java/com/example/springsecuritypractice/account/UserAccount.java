package com.example.springsecuritypractice.account;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

// 인증된 객체의 속성들을 가져오기 위해 생성한 클래스
public class UserAccount extends User { // User는 UserDetails를 구현한 클래스

    private Account account;

    public UserAccount(Account account) {   // 생성자로 인해서 계정의 정보들을 가져올 수 있다.
        super(account.getUsername(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
