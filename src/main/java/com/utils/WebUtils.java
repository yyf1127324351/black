package com.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.regex.Pattern;

public class WebUtils {

    public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if (forwarded != null) {
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

    public static String getClientIp(HttpServletRequest httpServletRequest) {
        String returnValue = httpServletRequest.getHeader("x-forwarded-for");
        if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
            returnValue = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
            returnValue = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
            returnValue = httpServletRequest.getRemoteAddr();
        }
        if (null != returnValue) {
            String[] ips = returnValue.split(",");
            for (String oneIp : ips) {
                if (!"unknown".equals(oneIp)) {
                    returnValue = oneIp;
                    break;
                }
            }
        }
        return returnValue;
    }

    /**
     * 判断是否是 Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equals("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }

    public static void writeJson(HttpServletResponse response, Object object) {
        response.setContentType("application/json; charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            out.println(JSONObject.toJSONString(object));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
