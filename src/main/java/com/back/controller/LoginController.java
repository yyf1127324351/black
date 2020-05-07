package com.back.controller;

import com.back.service.LoginService;
import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.BaseResponse;
import com.common.session.SessionContainer;
import com.constant.ConfigConstants;
import com.utils.EncodeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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
     *
     * @param user 用户信息
     */
    @RequestMapping(value = "/login")
    public ModelAndView login(UserVo user, HttpServletRequest request) {
        if (SessionContainer.getSession() != null) {
            return new ModelAndView("redirect:" + ConfigConstants.LOGIN_REDIRECT_URL);
        }
        ModelAndView mv = new ModelAndView("/login/login");
        if (user == null || StringUtils.isBlank(user.getLoginName())) {
            String redirectUrl = request.getParameter("redirectUrl");
            if (StringUtils.isBlank(redirectUrl)) {
                redirectUrl = request.getHeader("referer");
                if (redirectUrl.substring(redirectUrl.length() - 1, redirectUrl.length()).equals("/")) {
                    redirectUrl = redirectUrl.substring(0, redirectUrl.length() - 1);
                }
                redirectUrl = redirectUrl + ConfigConstants.LOGIN_REDIRECT_URL;
            }
            mv.addObject("redirectUrl", redirectUrl);
            return mv;
        } else {
            //解码用户名密码
            decodeLoginNamePassword(user);

            Map<Object, String> param = new HashMap<>();
            param.put("loginName", user.getLoginName());
            param.put("password", user.getPassword());

            UserVo userVo = userService.getLoginUserByMap(param);
            if (null == userVo) {
                return mv;
            } else {
                userVo.setRedirectUrl(user.getRedirectUrl());
                //获取重定向地址
                String redirectUrl = loginService.getRedirectUrl(request, userVo);
                mv.setViewName("redirect:" + (StringUtils.isNotBlank(redirectUrl) ? redirectUrl : ConfigConstants.LOGIN_REDIRECT_URL));
            }
        }
        return mv;
    }

    @RequestMapping("logout")
    public String logout() {
//        String returnUrl = this.loginService.getLogoutUrl(request, response);
        return "redirect:" + "/login/login";
    }


    @RequestMapping(value = "/validateLogin")
    @ResponseBody
    public BaseResponse validateLogin(UserVo user) {
        if (StringUtils.isBlank(user.getLoginName())) {
            return BaseResponse.paramError("用户名不能为空！");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return BaseResponse.paramError("密码不能为空！");
        }
        //解密用户名密码
        decodeLoginNamePassword(user);

        Map<Object, String> param = new HashMap<>();
        param.put("loginName", user.getLoginName());
        param.put("password", user.getPassword());
        //校验 登录名，密码
        UserVo userVo = userService.getLoginUserByMap(param);
        if (null != userVo) {
            return BaseResponse.success();
        } else {
            //如果为空，校验密码是否正确，用户名是否存在
            List<UserVo> userList = userService.getLoginUserByLoginName(user.getLoginName());
            if (CollectionUtils.isEmpty(userList)) {
                return BaseResponse.paramError("该用户名不存在！");
            } else {
                return BaseResponse.paramError("用户名或密码错误！");
            }
        }
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse updatePassword(String userId, String oldPassword, String newPassword, String newPassword2) {
        if (StringUtils.isBlank(oldPassword)) {
            return BaseResponse.paramError("原密码不能为空！");
        }
        if (StringUtils.isBlank(newPassword) || StringUtils.isBlank(newPassword2)) {
            return BaseResponse.paramError("新密码不能为空！");
        }
        String encOldPassword = oldPassword.substring(6, oldPassword.length()); //编码后的密码
        String encNewPassword = newPassword.substring(6, newPassword.length()); //编码后的新密码
        newPassword = EncodeUtils.decodeBase64(newPassword.substring(6, newPassword.length()));
        newPassword2 = EncodeUtils.decodeBase64(newPassword2.substring(6, newPassword2.length()));
        if (!newPassword.equals(newPassword2)) {
            return BaseResponse.paramError("两次输入的新密码不一致！");
        }

        Map<Object, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("loginPassword", encOldPassword);
        map.put("newPassword", encNewPassword);
        //校验原密码是否正确
        if (!userService.checkPassword(map)) {
            // 如果原密码不正确，返回
            return BaseResponse.paramError("原密码错误，请重新输入！");
        } else {
            userService.updateLoginPassword(map);
        }
        return BaseResponse.success("密码修改成功，请重新登录");
    }



    private void decodeLoginNamePassword(UserVo user) {
        //decode登录名
        String loginName = user.getLoginName().substring(6, user.getLoginName().length());
        loginName = EncodeUtils.decodeBase64(loginName);
        user.setLoginName(loginName);

        String password = user.getPassword().substring(6, user.getPassword().length());
        user.setPassword(password);
    }


}
