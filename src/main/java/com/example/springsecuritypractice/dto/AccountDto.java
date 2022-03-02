package com.example.springsecuritypractice.dto;

import com.example.springsecuritypractice.account.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String phone;
    private String role;

    public Account toEntity() {
        Account account = Account.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .phone(phone)
                .role(role)
                .build();
        return account;
    }

    @Builder
    public AccountDto(Long id, String username, String password, String email, String name, String phone, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
