package com.example.springsecuritypractice.controller;


import com.example.springsecuritypractice.account.Account;
import com.example.springsecuritypractice.repository.AccountRepository;
import com.example.springsecuritypractice.service.AccountService;
import com.example.springsecuritypractice.account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    // 회원정보 수정
    @PostMapping("/updateCheck")
    public String updateCheck(Account preAccount, @AuthenticationPrincipal UserAccount userAccount) {
        accountService.update(preAccount, userAccount);
        return "redirect:/";
    }

    // 비밀번호 변경
    @GetMapping("/updatePasswordForm")
    public String updatePasswordForm(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("userAccount", userAccount);
        return "updatePasswordForm";
    }

    // 비밀번호 변경 확인
    @PostMapping("/passwordUpdateCheck")
    public String updatePasswordCheck(Account preAccount, @AuthenticationPrincipal UserAccount userAccount)  {
        accountService.updatePassword(preAccount, userAccount);
        return "redirect:/";
    }

    // 마이페이지 이동
    @GetMapping("/myPage")
    public String myPage(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        model.addAttribute("userAccount", userAccount);
        return "myPage";
    }
}


