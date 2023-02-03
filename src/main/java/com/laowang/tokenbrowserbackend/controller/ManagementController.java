package com.laowang.tokenbrowserbackend.controller;

import com.laowang.tokenbrowserbackend.entity.Result;
import com.laowang.tokenbrowserbackend.entity.TokenUser;
import com.laowang.tokenbrowserbackend.service.Management;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "system user management")
public class ManagementController {

    private Management management = Management.getInstance();

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "change user's password")
    public Result<TokenUser> changePassword(@RequestParam String username, @RequestParam String oldPwd,
                                            @RequestParam String newPwd01, @RequestParam String newPwd02) {
        return management.changePassword(username, oldPwd, newPwd01, newPwd02);
    }

    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "query user list")
    public Result<List<TokenUser>> queryUserList() {
        Result<List<TokenUser>> result = new Result<>();
        result.setData(management.queryUserList());
        return result;
    }

    /**
     * @param username username
     * @param password pwd
     * @param permisstionIndex 0-administrator对应数据库 1，1 反而对应数据库的0
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "create new user")
    public Result<TokenUser> createUser(@RequestParam String username, @RequestParam String password, @RequestParam Integer permisstionIndex){
        return management.createUser(username, password, permisstionIndex);
    }
}
