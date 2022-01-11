package com.example.springsecuritypractice.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 이거 없어eh JpaRepository를 상속받았기 때문에 자동으로 빈으로 등록이 된다.
public interface AccountRepository extends JpaRepository <Account, Long> {

    // select * from account where username = ?(username)
    public Account findByUsername(String username);

}
