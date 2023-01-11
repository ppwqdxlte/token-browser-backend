package com.laowang.tokenbrowserbackend.service;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginTest {

    Login login;
    @BeforeEach
    void setUp() {
        login = Login.getInstance();
    }

    @Test
    void submit() {
        Result<TokenUser> submit = login.submit("0001", "123");
        System.out.println(submit.getMsg());
        System.out.println(submit.getData().toString());
    }
}