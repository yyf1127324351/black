package com.back.controller;

import com.back.model.User;
import com.back.service.LoginService;
import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import com.constant.ConfigConstants;
import com.utils.Encodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;


    /**
     * 登录
     * @param user
     * */
    @RequestMapping(value = "/login")
    public ModelAndView login(User user, HttpServletRequest request){
        if (SessionContainer.getSession() != null) {
            return new ModelAndView("redirect:" + ConfigConstants.LOGIN_REDIRECT_URL);
        }
        ModelAndView mv = new ModelAndView("/login/login");
        if (user == null || StringUtils.isBlank(user.getLoginName())) {
            return mv;
        }else {
            String loginName = user.getLoginName();
            loginName = loginName.substring(6, loginName.length());
            String password = user.getPassword();
            password = password.substring(6, password.length());

            user.setLoginName(Encodes.decodeBase64(loginName));
            user.setPassword(password);

            Map<String,String> param = new HashMap<>();
            param.put("loginName",user.getLoginName());
            param.put("password",user.getPassword());

            UserVo userVo = userService.getLoginUser(param);
            if (null == userVo){
                return mv;
            }else {
//                userVo.setRedirectUrl(user.getRedirectUrl());
                String redirectUrl = loginService.getRedirectUrl(request,userVo);
                mv.setViewName("redirect:" + (StringUtils.isNotBlank(redirectUrl) ? redirectUrl : ConfigConstants.LOGIN_REDIRECT_URL));
            }
        }
        return mv;
    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        String returnUrl = this.loginService.getLogoutUrl(request, response);
//        return "redirect:" + (StringUtils.isNotBlank(returnUrl) ? WebUtils.handleSpeccialUrlParam(returnUrl) : ConfigConstants.LOGIN_RETURN_URL);
        return null;
    }







    @RequestMapping(value = "/goEditPassword")
    public ModelAndView goEditPassword(){
        ModelAndView mv = new ModelAndView("login/editPassword");
        return mv;
    }

    @RequestMapping(value = "/validateLogin")
    @ResponseBody
    public BaseResponse validateLogin(User user) {
        return BaseResponse.success();
    }


}
