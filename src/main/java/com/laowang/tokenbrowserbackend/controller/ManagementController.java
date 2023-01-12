package com.laowang.tokenbrowserbackend.controller;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import com.laowang.tokenbrowserbackend.service.Management;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "system user management")
public class ManagementController {

    private Management management = Management.getInstance();

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "change user's password")
    public Result<TokenUser> changePassword(@RequestParam String username, @RequestParam String oldPwd,
                                            @RequestParam String newPwd01, @RequestParam String newPwd02) {
        return management.changePassword(username,oldPwd,newPwd01,newPwd02);
    }
}