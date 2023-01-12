package com.laowang.tokenbrowserbackend.controller;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import com.laowang.tokenbrowserbackend.service.Login;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Log in")
public class LoginController {

    private Login login = Login.getInstance();

    /**
     * @param username system login username
     * @param password system login password
     * @return result with TokenUser data
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "login check")
    public Result<TokenUser> submit(@RequestParam String username, @RequestParam String password) {
        return login.submit(username, password);
    }
}
