package com.back.controller;

import com.back.model.BackUserDto;
import com.back.service.LoginService;
import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import com.constant.ConfigConstants;
import com.utils.Encodes;
import com.utils.WebUtils;
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
     * @param backUserDto
     * */
    @RequestMapping(value = "/login")
    public ModelAndView login(BackUserDto backUserDto, HttpServletRequest request){
        if (SessionContainer.getSession() != null) {
            return new ModelAndView("redirect:" + ConfigConstants.LOGIN_RETURN_URL);
        }
        ModelAndView mv = new ModelAndView("login/login");
        if (backUserDto == null || StringUtils.isBlank(backUserDto.getLoginName())) {

            String redirectUrl = request.getParameter("redirectUrl");
            if (StringUtils.isBlank(redirectUrl)) {
                redirectUrl = request.getHeader("referer");
            }
            mv.addObject("redirectUrl", StringUtils.isNotBlank(redirectUrl) ? redirectUrl : ConfigConstants.LOGIN_RETURN_URL);
        }else {
            String loginName = backUserDto.getLoginName();
            loginName = loginName.substring(6, loginName.length());
            String password = backUserDto.getPassword();
            password = password.substring(6, password.length());

            backUserDto.setLoginName(Encodes.decodeBase64(loginName));
            backUserDto.setPassword(password);

            Map<String,String> param = new HashMap<>();
            param.put("loginName",backUserDto.getLoginName());
            param.put("password",backUserDto.getPassword());

            UserVo userVo = userService.getLoginUser(param);
            if (null == userVo){
                return mv;
            }else {
                userVo.setRedirectUrl(backUserDto.getRedirectUrl());
                String redirectUrl = loginService.getRedirectUrl(request,userVo);
                mv.setViewName("redirect:" + (StringUtils.isNotBlank(redirectUrl) ? redirectUrl : ConfigConstants.LOGIN_RETURN_URL));
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
    public BaseResponse validateLogin(BackUserDto backUserDto) {
        return BaseResponse.success();
    }


}
