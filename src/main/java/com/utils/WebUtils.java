package com.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class WebUtils {
  public static String getRequestFullUrl(HttpServletRequest httpServletRequest){
    String returnValue = null;
    StringBuilder sb = new StringBuilder();
    StringBuffer requestURL = httpServletRequest.getRequestURL();
    sb.append(requestURL);
    String queryString = httpServletRequest.getQueryString();
    if (StringUtils.isNotBlank(queryString)) {
      sb.append("?").append(queryString);
    }
    returnValue = sb.toString();
    return returnValue;
  }
  
  public static String getClientIp(HttpServletRequest httpServletRequest){
    String returnValue = null;
    returnValue = httpServletRequest.getHeader(ProxyClientIPHttpHeaderNameEnum.XFORWARDEDFOR.getHeaderName());
    if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
      returnValue = httpServletRequest.getHeader(ProxyClientIPHttpHeaderNameEnum.PROXYCLIENTIP.getHeaderName());
    }
    if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
      returnValue = httpServletRequest.getHeader(ProxyClientIPHttpHeaderNameEnum.WLPROXYCLIENTIP.getHeaderName());
    }
    if ((StringUtils.isBlank(returnValue)) || ("unknown".equalsIgnoreCase(returnValue))) {
      returnValue = httpServletRequest.getRemoteAddr();
    }
    if (null != returnValue)
    {
      String[] ips = returnValue.split(",");
      for (String oneIp : ips) {
        if (!"unknown".equals(oneIp))
        {
          returnValue = oneIp;
          break;
        }
      }
    }
    return returnValue;
  }
  
  public static String getRequestHeadersInfo(HttpServletRequest httpServletRequest){
    String returnValue = null;
    Enumeration headerNames = httpServletRequest.getHeaderNames();
    StringBuilder sb = new StringBuilder();
    sb.append("Request Headers:");
    sb.append("[");
    if (null != headerNames)
    {
      Object headerName = null;
      String headerValue = null;
      while (headerNames.hasMoreElements())
      {
        headerName = headerNames.nextElement();
        headerValue = httpServletRequest.getHeader(headerName.toString());
        
        sb.append(headerName).append("=").append(headerValue).append(headerNames.hasMoreElements() ? "," : "");
      }
    }
    sb.append("]");
    returnValue = sb.toString();
    return returnValue;
  }
  
  public static String getRequestParamtersInfo(HttpServletRequest httpServletRequest){
    String returnValue = null;
    Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
    StringBuilder sb = new StringBuilder();
    sb.append("Request Parameters:");
    sb.append("[");
    String parameterName = null;
    String parameterValue = null;
    while (parameterNames.hasMoreElements())
    {
      parameterName = (String)parameterNames.nextElement();
      parameterValue = httpServletRequest.getParameter(parameterName);
      sb.append(parameterName).append("=").append(parameterValue).append(parameterNames.hasMoreElements() ? "," : "");
    }
    sb.append("]");
    returnValue = sb.toString();
    return returnValue;
  }
  
  public static String getRequestMethodInfo(HttpServletRequest httpServletRequest){
    String returnValue = null;
    StringBuilder sb = new StringBuilder();
    sb.append("Request method:");
    sb.append(httpServletRequest.getMethod());
    returnValue = sb.toString();
    return returnValue;
  }
  public static String getCookieValue(HttpServletRequest httpServletRequest, String cookieName){
    String returnValue = null;
    Cookie[] cookies = httpServletRequest.getCookies();
    if (ArrayUtils.isNotEmpty(cookies)) {
      for (Cookie oneCookie : cookies) {
        if (StringUtils.equalsIgnoreCase(cookieName, oneCookie.getName()))
        {
          returnValue = oneCookie.getValue();
          break;
        }
      }
    }
    return returnValue;
  }
  
  /**
	 * 设置cookie
	 * @param name
	 * @param value
	 * @param domain
	 * @param expire
	 */
	public static void setCookie(HttpServletResponse response,String name, String value, String domain, int expire) {
		Cookie cookie = new Cookie(name, value);
		cookie.setDomain(domain);
		cookie.setPath("/");
		if (expire >= 0) {
			cookie.setMaxAge(expire);
		}
		response.addCookie(cookie);
	}
	/**
	 * 设置cookie
	 * @param name
	 * @param value
	 * @param response
	 * @param expire
	 */
	public static void setNoDomainCookie(HttpServletResponse response,String name, String value,int expire) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (expire >= 0) {
			cookie.setMaxAge(expire);
		}
		response.addCookie(cookie);
	}

  private static enum ProxyClientIPHttpHeaderNameEnum {
    XFORWARDEDFOR("x-forwarded-for"),  PROXYCLIENTIP("Proxy-Client-IP"),  WLPROXYCLIENTIP("WL-Proxy-Client-IP");
    
    private final String headerName;
    
    String getHeaderName()
    {
      return this.headerName;
    }
    
    private ProxyClientIPHttpHeaderNameEnum(String headerName)
    {
      this.headerName = headerName;
    }
    
    public String toString()
    {
      return this.headerName;
    }
  }
  public static  String getHostIP1(){
  	InetAddress addr = null;
  	String localIp = "";
		try {
			addr = InetAddress.getLocalHost();
			if(addr!=null){
				localIp = addr.getHostAddress().toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return localIp;
  }
  public static  String getHostIp2(){
  	Enumeration allNetInterfaces;
  	InetAddress ip = null;
  	String hostip="";
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			while (allNetInterfaces.hasMoreElements())
	    	{
	    	NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	    	Enumeration addresses = netInterface.getInetAddresses();
	    	while (addresses.hasMoreElements()){
		    	ip = (InetAddress) addresses.nextElement();
			    	if (ip != null && ip instanceof Inet4Address){
			    	 hostip+=ip.getHostAddress()+",";
			    	} 
	    	}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
  	return hostip;
  }
  public static String getQueryString(HttpServletRequest request) {
		Map<String, String[]> params = request.getParameterMap();
		String queryString = "";
		for (Entry<String, String[]> entry : params.entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
		if (StringUtils.isBlank(queryString)) {
			return "";
		}
		// 去掉最后一个空格
		queryString = queryString.substring(0, queryString.length() - 1);
		return "?" + queryString;
	}
  
  public static String getDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (domain.contains("lebbay") || domain.contains("digi800")) {
			return domain;
		}
		Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
		if (pattern.matcher(domain).matches()) {
			return domain;
		} else {
			return domain.replaceAll(".*\\.(?=.*\\.)", "");
		}
	}
  /**
	 * cookie失效时间，只今天，剩余多少秒
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static int getTodayCookieTimeOut() {
		String nowDayStr = DateFormatUtils.format(new Date(), "yyyy-MM-dd");// 获取当前日期
		Date nowDayEndStr = null;
		try {
			nowDayEndStr = org.apache.commons.lang.time.DateUtils.parseDate(nowDayStr + " 23:59:59", new String[] { "yyyy-MM-dd HH:mm:ss" });
		} catch (ParseException e) {
			e.printStackTrace();
		} // 当天最后1秒时间
		int cookieTimeOut = Long.valueOf((nowDayEndStr.getTime() - new Date().getTime()) / 1000).intValue();
		return cookieTimeOut;
	}
	/**
	 * 去除特殊参数
	 * @param url
	 * @return
	 */
	public static String handleSpeccialUrlParam(String url) {
		url = url.replaceAll("sso_token=[^&]*", "").replaceAll("[&]{2,}+", "&");
		url = url.replaceAll("sso_token_temp=[^&]*", "").replaceAll("[&]{2,}+", "&");
		url = url.replaceAll("sso_token2=[^&]*", "").replaceAll("[&]{2,}+", "&");
		if(url.lastIndexOf("&") == url.length() -1) {
			url = url.substring(0, url.length() - 1);
		}
		if(url.lastIndexOf("?") == url.length() -1) {
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}
}
