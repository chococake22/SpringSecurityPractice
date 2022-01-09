package com.example.springsecuritypractice.controller;


import com.example.springsecuritypractice.Account.Account;
import com.example.springsecuritypractice.Account.AccountRepository;
import com.example.springsecuritypractice.Account.AccountService;
import com.example.springsecuritypractice.Account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 회원가입 양식
    @GetMapping("/signUpForm")
    public String signUpForm() {
        return "signUpForm";
    }

    // 회원가입 처리
    @PostMapping("/signUpCheck")
    public String signUpCheck(Account account) {
        System.out.println("회원가입 하기");
        account.setRole("ROLE_USER");
        String password = account.getPassword();
        String bcPassword = bCryptPasswordEncoder.encode(password);
        account.setPassword(bcPassword);
        accountRepository.save(account);
        return "redirect:/";
    }

    // 회원정보 양식
    @GetMapping("/updateForm")
    public String updateForm(Model model, @AuthenticationPrincipal UserAccount userAccount ) {
        model.addAttribute("userAccount", userAccount);
        return "updateForm";
    }

    @PostMapping("/updateCheck")
    public String updateCheck(Account preAccount, @AuthenticationPrincipal UserAccount userAccount) {


        System.out.println(userAccount.getAccount().getName());
        System.out.println(userAccount.getAccount().getPhone());

        accountService.update(preAccount, userAccount);


        return "redirect:/";
    }
}
