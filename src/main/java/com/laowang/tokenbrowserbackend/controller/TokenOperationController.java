package com.laowang.tokenbrowserbackend.controller;

import com.laowang.tokenbrowserbackend.entity.BaseToken;
import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.enumeration.TokenType;
import com.laowang.tokenbrowserbackend.service.TokenOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "operations about token")
public class TokenOperationController {

    private TokenOperation operation = TokenOperation.getINSTANCE();

    @RequestMapping(value = "/queryAll",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "query all tokens including TCC and KCT")
    public Result<List<BaseToken>> queryAll() {
        return operation.queryAll();
    }

    @RequestMapping(value = "/queryAllTCC",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "query all tokens of TCC")
    public Result<List<BaseToken>> queryAllTCC() {
        return operation.queryAllTCC();
    }

    @RequestMapping(value = "/queryAllKCT",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "query all tokens of KCT")
    public Result<List<BaseToken>> queryAllKCT() {
        return operation.queryAllKCT();
    }

    @RequestMapping(value = "/queryTokenListbyMeterStr",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "query tokens by TokenType and meternumber string")
    public Result<List<BaseToken>> queryTokenListbyMeterStr(@RequestParam String tokenType, @RequestParam String meterStr) {
        return operation.queryTokenListbyMeterStr(tokenType.contains("TCC")?TokenType.TCC:TokenType.KCT,meterStr);
    }
}