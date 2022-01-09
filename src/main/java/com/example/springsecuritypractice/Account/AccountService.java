package com.example.springsecuritypractice.Account;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void update(Account account, @AuthenticationPrincipal UserAccount userAccount) {

        Account newAccount = accountRepository.findByUsername(account.getUsername());

        // DB 데이터 변동
        // account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setEmail(account.getEmail());
        account.setPhone(account.getPhone());


        // 인증 객체 변동
        // userAccount.getAccount().setPassword(account.getPassword());
        userAccount.getAccount().setEmail(account.getEmail());
        userAccount.getAccount().setPhone(account.getPhone());

        accountRepository.save(newAccount);

    }




}
