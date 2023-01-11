package com.laowang.tokenbrowserbackend.service;

import com.laowang.tokenbrowserbackend.entity.BaseToken;
import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.enumeration.TokenType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenOperationTest {

    TokenOperation operation;

    @BeforeEach
    void setUp() {
        operation = TokenOperation.getINSTANCE();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void queryAll() {
        Result<List<BaseToken>> listResult = operation.queryAll();
        System.out.println(listResult.getMsg());
        for (BaseToken datum : listResult.getData()) {
            System.out.println(datum.toString());
        }
    }

    @Test
    void queryAllTCC() {
        Result<List<BaseToken>> listResult = operation.queryAllTCC();
        System.out.println(listResult.getMsg());
        for (BaseToken datum : listResult.getData()) {
            System.out.println(datum.toString());
        }
    }

    @Test
    void queryAllKCT() {
        Result<List<BaseToken>> listResult = operation.queryAllKCT();
        System.out.println(listResult.getMsg());
        for (BaseToken datum : listResult.getData()) {
            System.out.println(datum.toString());
        }
    }

    @Test
    void queryTokenListbyMeterStr() {
        Result<List<BaseToken>> listResult = operation.queryTokenListbyMeterStr(TokenType.TCC,"0150000854516");
        System.out.println(listResult.getMsg());
        for (BaseToken datum : listResult.getData()) {
            System.out.println(datum.toString());
        }
    }
}