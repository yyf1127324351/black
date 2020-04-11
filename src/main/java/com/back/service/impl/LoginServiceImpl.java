package com.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.back.service.LoginService;
import com.back.service.RedisService;
import com.back.vo.UserVo;
import com.constant.Constants;
import com.utils.DesEncryptUtils;
import com.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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

        // 有效时长为从登录到当天24点
        Integer expirationTime = WebUtils.getTodayCookieTimeOut();
        String ip = WebUtils.getClientIp(request);
        // 用户信息的cookie
        String ssoKey = generateSsoKey(userVo, ip);
        userVo.setLoginTime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        userVo.setIp(ip);
        // 将用户信息添加至redis缓存（使用json字符串）
        redisService.set(ssoKey, JSONObject.toJSONString(userVo), expirationTime);

        // 根据SSOKEY生成临时TOKEN
        String tmpKey = "";
        if (StringUtils.isNotEmpty(ssoKey)) {
            tmpKey = ssoKey + getCodes(6);
            redisService.set(tmpKey, JSONObject.toJSONString(userVo), 7200);
        }
        String redirectUrl = userVo.getRedirectUrl();
        if (StringUtils.isNotBlank(redirectUrl)) {
            // 简单消除如果有参数sso_token的影响
            redirectUrl = WebUtils.handleSpeccialUrlParam(redirectUrl);
            // 添加token参数
            if (redirectUrl.indexOf("?") >= 0) {
                redirectUrl += ("&" + Constants.SSO_TOKEN + "=" + ssoKey);
                if (StringUtils.isNotEmpty(tmpKey)) {
                    redirectUrl += ("&" + Constants.SSO_TOKEN_TEMP + "=" + tmpKey);
                }
            } else {
                redirectUrl += ("?" + Constants.SSO_TOKEN + "=" + ssoKey);
                if (StringUtils.isNotEmpty(tmpKey)) {
                    redirectUrl += ("&" + Constants.SSO_TOKEN_TEMP + "=" + tmpKey);
                }
            }
        }
        return redirectUrl;
    }

    private String generateSsoKey(UserVo userVo, String ip) {
        StringBuilder sb = new StringBuilder();
        sb.append("sso");
        sb.append("_");
        sb.append(userVo.getUserId());
        sb.append("_");
        sb.append(userVo.getLoginName());
        sb.append("_");
        sb.append(ip);
        sb.append("_").append(DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"));
        String ssoKey = DesEncryptUtils.encrypt(sb.toString());
        return ssoKey;
    }
    private String getCodes(int length) {
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer rands = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands.append(a.charAt(rand));
        }
        return rands.toString();
    }
}
