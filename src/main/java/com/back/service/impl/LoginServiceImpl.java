package com.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.back.service.LoginService;
import com.back.service.RedisService;
import com.back.vo.UserVo;
import com.constant.Constants;
import com.utils.CodeUtils;
import com.utils.DateUtils;
import com.utils.DesEncryptUtils;
import com.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisService redisService;

    @Override
    public String getRedirectUrl(HttpServletRequest request, UserVo userVo) {
        String ip = WebUtils.getClientIp(request);
        userVo.setLoginTime(DateUtils.formatDateToString(new Date()));
        userVo.setIp(ip);
        userVo.setPassword(null);
        //生成有个 TokenKey
        String tokenKey = generateTokenKey(userVo);
        //失效时长（有效时长为从登录到当天24点）
        Integer expirationTime = DateUtils.getTodayRemainTimes();
        //将用户信息添加至redis缓存（使用json字符串）
        redisService.set(Constants.TOKEN + ":" + tokenKey, JSONObject.toJSONString(userVo), expirationTime);

        // 根据tokenKey生成临时TOKEN:tmpTokenKey
        String tmpTokenKey = tokenKey + CodeUtils.getRandomCodes(6);
        redisService.set(Constants.TMP_TOKEN + ":" + tmpTokenKey, JSONObject.toJSONString(userVo), 7200);

        //重定向地址
        String redirectUrl = userVo.getRedirectUrl();
        if (!StringUtils.isNotBlank(redirectUrl)) {
            return redirectUrl;
        }
        // 添加token参数
        if (redirectUrl.contains("?")) {
            redirectUrl = redirectUrl.substring(0, redirectUrl.indexOf("?"));
        }
        redirectUrl += ("?" + Constants.TOKEN + "=" + tokenKey);
        redirectUrl += ("&" + Constants.TMP_TOKEN + "=" + tmpTokenKey);
        return redirectUrl;
    }

    private String generateTokenKey(UserVo userVo) {
        String sb = "sso" + "_" + userVo.getUserId() + "_" + userVo.getLoginName() + "_" + userVo.getIp() + "_" + DateUtils.formatDateToString3(new Date());
        return DesEncryptUtils.encrypt(sb);
    }

}
