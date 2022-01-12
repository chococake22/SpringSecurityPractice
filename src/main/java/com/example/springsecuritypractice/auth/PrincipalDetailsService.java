package com.example.springsecuritypractice.auth;

import com.example.springsecuritypractice.account.Account;
import com.example.springsecuritypractice.account.AccountRepository;
import com.example.springsecuritypractice.account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// SecurityConfig에서 loginProcessUrl("/login")으로 설정함
// /login요청이 올 경우 자동으로 UserDetailsService타입으로 컨테이너에 있는 loadUserByUsername메서드 실행

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    // DB에서 해당 아이디를 가진 유저가 있는지 판단하고 있으면 가져오고 없으면 예외를 보냄
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if(account == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserAccount(account);
        // Authentication 안에 UserDetails(PrincipalDetails) 객체를 넣어줌
    }
}
