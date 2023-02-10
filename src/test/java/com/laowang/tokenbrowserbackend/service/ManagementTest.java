package com.laowang.tokenbrowserbackend.service;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagementTest {

    Management management;
    @BeforeEach
    void setUp() {
        management = Management.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

//    @Test
    void changePassword() {
        Result<TokenUser> tokenUserResult = management.changePassword("0001", "0000", "0000", "0000");
        System.out.println(tokenUserResult.getMsg());
        System.out.println(tokenUserResult.getData().toString());
    }
}