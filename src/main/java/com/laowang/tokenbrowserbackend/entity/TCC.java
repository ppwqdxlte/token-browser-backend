package com.laowang.tokenbrowserbackend.entity;

import com.laowang.tokenbrowserbackend.enumeration.TokenType;

import java.io.Serializable;
import java.sql.Date;

public class TCC extends BaseToken<TokenType> implements Serializable {

    private static final Long serialVersionUID = 1L;

    private final TokenType TOKEN_TYPE = TokenType.TCC;

    public TCC(){
        setTokenType(TOKEN_TYPE);
    }

    public TCC(String batchNo, String meterNo, String token, Date date) {
        super(batchNo, meterNo, token, date);
        setTokenType(TOKEN_TYPE);
    }

    @Override
    public TokenType getTOKEN_TYPE() {
        return TOKEN_TYPE;
    }
}
