package com.back.controller;

import com.back.model.UserDto;
import com.back.service.UserService;
import com.common.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("goUserPage")
    public String goUserPage(){
        return "login/login";
    }

    @RequestMapping("/getUserList")
    @ResponseBody
    public BaseResponse getUserList(){
        UserDto userDto = userService.getUserList();
        return BaseResponse.success(userDto);
    }
}
