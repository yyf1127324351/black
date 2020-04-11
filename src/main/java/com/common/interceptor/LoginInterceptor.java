package com.common.interceptor;

import com.back.service.UserService;
import com.back.vo.UserVo;
import com.common.session.SessionContainer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截器
 * 取用户信息放入ThreadLocal中
 */
public class LoginInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	@Autowired UserService userService;

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
//		String servletPath = request.getServletPath();
//		String requestUrl  = request.getRequestURL().toString();
//		String requestUri =  request.getRequestURI();
//		SessionContainer.setSession(null);
//		UserVo ov = null;
//		String token_temp = request.getParameter(Constants.sso_token_temp);
//		String cookie = WebUtil.getCookieValue(request,Constants.sso_token);
//		//SSO的key
//		String ssoKey = "";
//		if(StringUtils.isNotEmpty(token_temp)){
//			//验证该临时token是否有效
//			OperatorVo temp = userService.getOperatorBySsoKey(token_temp);
//			if(temp != null){//验证通过
//				ssoKey = token_temp.substring(0, token_temp.length()-6);
//				userService.clearTokenBykey(token_temp);//清空临时TOKEN
//			}else if(StringUtils.isNotEmpty(cookie)){
//				ssoKey = cookie;
//			}else{
//				//验证未通过
//				log.info(token_temp + ":the token has already used!");
//				SessionLoginContainer.setSession(null);
//				return true;
//			}
//		}else if(StringUtils.isNotEmpty(cookie)){
//			ssoKey = cookie;
//		}
//		if (StringUtils.isNotBlank(ssoKey)) {
//			ov = userService.getOperatorBySsoKey(ssoKey);
//		}
//		if (ov != null && StringUtils.isNotBlank(token_temp)) {
//			System.out.println(WebUtil.getDomain(request));
//			WebUtil.setCookie(response, Constants.sso_token, ssoKey,WebUtil.getDomain(request),WebUtil.getTodayCookieTimeOut());
//		}
//		if(null==ov){
//			String redirectUrl = this.genRedirectUrl(request, response);
////			String redirectUrl = "http://localhost:8080/hr/goHr";
//			if(isAjaxRequest(request)) {
//				Map<String, Object> ret = new HashMap<String, Object>();
//				ret.put("redirectUrl", redirectUrl);
//				ret.put("msg", "登录信息失效，Ajax请求需要自己跳转到登录页面！");
//				ret.put("total", 0);
//				ret.put("rows", new ArrayList<Object>());
//				this.writeJson(response, ret);
//			} else {
//				response.sendRedirect(redirectUrl);
//			}
//			return false;
//		}
//		SessionLoginContainer.setSession(ov);
		return true;
	}
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		SessionLoginContainer.clear();
	}


	/**
	 * @return
	 * @throws IOException
	 */
//	private String genRedirectUrl(HttpServletRequest request,HttpServletResponse response) throws IOException {
//		String queryString = WebUtil.getQueryString(request);
//		StringBuffer url = new StringBuffer();
//		//组装参数
//		url.append(Constants.login_sso_url).append("/login");
//		url.append("?").append(Constants.redirectUrl).append("=");
//		String reqFullUrl = request.getRequestURL() + queryString;
//		if(this.isAjaxRequest(request)) {
//			reqFullUrl = "";//ajax请求无法获得要跳转的url
//		}
//		//全局配置的登录拦截返回的url
//		if(StringUtils.isNotBlank(Constants.login_return_url)) {
//			reqFullUrl = Constants.login_return_url;
//		}
//		//重定向的url encode
//		url.append(Encodes.urlEncode(reqFullUrl));
//		return url.toString();
//	}
//
//	private boolean isAjaxRequest(HttpServletRequest request) {
//	    String requestType = request.getHeader("X-Requested-With");
//	    if (requestType != null && requestType.equals("XMLHttpRequest")) {
//	        return true;
//	    } else {
//	        return false;
//	    }
//	}
//
//	private void writeJson(HttpServletResponse response, Object ar) {
//		response.addHeader("REQUIRES_AUTH", "1");
//		response.setContentType("application/json; charset=UTF-8");
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//			out.println(JsonUtil.toJson(ar));
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}
