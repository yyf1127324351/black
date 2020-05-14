package com.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.back.service.RedisService;
import com.back.vo.UserVo;
import com.common.session.SessionContainer;
import com.constant.Constants;
import com.utils.CookieUtils;
import com.utils.DateUtils;
import com.utils.WebUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截器
 * 取用户信息放入ThreadLocal中
 */
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        SessionContainer.clear();
        String servletPath = request.getServletPath();
        String requestUrl = request.getRequestURL().toString();
//        String requestUri = request.getRequestURI();
        String redirectUrl = requestUrl.replace(servletPath, "");

        String tmpToken = request.getParameter(Constants.TMP_TOKEN);
        String cookieValue = CookieUtils.getCookieValue(request, Constants.TOKEN);

        String token = "";
        if (StringUtils.isNotBlank(tmpToken)) {
            String redisTmpTokenKey = Constants.TMP_TOKEN + ":" + tmpToken;
            //验证临时token
            UserVo userVo = checkRedisTokenKey(redisTmpTokenKey);
            if (null != userVo) {
                //临时token验证 通过
                token = tmpToken.substring(0, tmpToken.length() - 6);
                redisService.del(redisTmpTokenKey);//清空临时token
            } else if (StringUtils.isNotBlank(cookieValue)) {
                token = cookieValue;
            } else {
                //临时token验证 未通过
                log.error("the token has already used:" + tmpToken);
                SessionContainer.clear();
                response.sendRedirect(redirectUrl);
                return false;
            }
        } else if (StringUtils.isNotBlank(cookieValue)) {
            token = cookieValue;
        }

        if (StringUtils.isNotBlank(token)) {
            String redisTokenKey = Constants.TOKEN + ":" + token;
            UserVo userVo = checkRedisTokenKey(redisTokenKey);
            if (null != userVo && StringUtils.isNotBlank(tmpToken)) {
                CookieUtils.setCookie(response, Constants.TOKEN, token, request.getServerName(), DateUtils.getTodayRemainTimes());
            }
            SessionContainer.setSession(userVo);
        } else {
            if (WebUtils.isAjaxRequest(request)) {
                Map<String, Object> ret = new HashMap<>();
                ret.put("redirectUrl", redirectUrl);
                ret.put("msg", "登录信息失效，Ajax请求需要自己跳转到登录页面！");
                WebUtils.writeJson(response, ret);
            } else {
                response.sendRedirect(redirectUrl);
            }
            return false;
        }

        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        SessionContainer.clear();
    }

    private UserVo checkRedisTokenKey(String redisKey) {
        UserVo userVo = null;
        try {
            String value = redisService.get(redisKey);
            if (StringUtils.isNotBlank(value)) {
                userVo = JSONObject.parseObject(value, UserVo.class);
            }
        } catch (Exception e) {
            log.error(redisKey + "缓存取值ERROR", e);
        }
        return userVo;
    }
}
