package com.example.springsecuritypractice.controller;


import com.example.springsecuritypractice.Account.Account;
import com.example.springsecuritypractice.Account.AccountRepository;
import com.example.springsecuritypractice.Account.UserAccount;
import com.example.springsecuritypractice.Auth.PrincipalDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("userAccount", userAccount);
        return "index";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/signUpForm")
    public String signUpForm() {
        return "signUpForm";
    }

    @PostMapping("/signUpCheck")
    public String signUpCheck(Account account) {
        System.out.println("로그인 하기");
        account.setRole("ROLE_USER");
        String password = account.getPassword();
        String bcPassword = bCryptPasswordEncoder.encode(password);
        account.setPassword(bcPassword);
        accountRepository.save(account);
        return "redirect:/";
    }


    @PostMapping("/loginCheck")
    public void sample(@AuthenticationPrincipal Account account, Model model) {
        model.addAttribute("account", account);
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }

}
