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

        newAccount.setEmail(account.getEmail());
        newAccount.setPhone(account.getPhone());

        System.out.println(newAccount.getPassword());

        // 인증 객체 변동

        userAccount.getAccount().setEmail(account.getEmail());
        userAccount.getAccount().setPhone(account.getPhone());

        System.out.println(userAccount.getAccount().getPassword());

        accountRepository.save(newAccount);

    }

    @Transactional
    public void updatePassword(Account account, @AuthenticationPrincipal UserAccount userAccount) {

        Account newAccount = accountRepository.findByUsername(userAccount.getAccount().getUsername());

        newAccount.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));

        userAccount.getAccount().setPassword(account.getPassword());


        accountRepository.save(newAccount);
    }




}
