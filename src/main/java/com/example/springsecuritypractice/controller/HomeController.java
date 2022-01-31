package com.example.springsecuritypractice.controller;


import com.example.springsecuritypractice.account.Account;
import com.example.springsecuritypractice.account.AccountRepository;
import com.example.springsecuritypractice.account.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    private AccountRepository accountRepository;

    // 메인 페이지
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserAccount userAccount) {
        model.addAttribute("userAccount", userAccount);
        return "index";
    }

    // 로그인 양식
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }


    // 로그인 처리
    @PostMapping("/login")
    public String loginCheck(@AuthenticationPrincipal Account account, Model model) {
        model.addAttribute("account", account);
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/";
    }



}
