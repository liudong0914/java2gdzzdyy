package com.wkmk.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录用户过滤
 */
public class SessionFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String requestURI = httpRequest.getRequestURI().toString();
	    if(!(requestURI.indexOf("admin.action") != -1 || requestURI.indexOf("plogin.do") != -1 || requestURI.indexOf("sysUserLoginAction.do") != -1 || requestURI.indexOf("vurl.action") != -1 || requestURI.indexOf("clientService.action") != -1 || requestURI.indexOf("appService.action") != -1 || requestURI.indexOf("appWeikeService.action") != -1 || requestURI.indexOf("tkQuestionToolsService.action") != -1 || requestURI.indexOf("payWxpayNotifyAction.do") != -1 || requestURI.indexOf("automationOrder.action") != -1)){
	    	HttpSession session = httpRequest.getSession();
	    	if(session.getAttribute("s_userid") == null){
	    		//不过滤fileUploadAction.do这个是因为在360浏览器中上传报http上传错误，和浏览器权限设置有关，通过此设置可以使其正常。
	    	    if(requestURI.indexOf("fileUploadAction.do") != -1){
	    	    	String queryStr = httpRequest.getQueryString();//访问此路径必须带参数unitid，否则视为违法请求
	    	    	if(!(queryStr.indexOf("unitid") != -1)){
	    	    		httpResponse.sendRedirect("/index_do.jsp");
						return;
	    	    	}
	    	    }else {
	    	    	if(requestURI.indexOf("courseCenter.do") != -1){
		    			String redirecturl = httpRequest.getServletPath();
		    			String queryStr = httpRequest.getQueryString();
		    			if(queryStr != null && !"".equals(queryStr)){
		    				redirecturl = redirecturl + "?" + queryStr;
		    			}
		    			redirecturl = java.net.URLEncoder.encode(redirecturl, "UTF-8");
		    			httpResponse.sendRedirect("/plogin.do?method=slogin&redirecturl=" + redirecturl);
						return;
		    		}else {
		    			httpResponse.sendRedirect("/index_do.jsp");
						return;
					}
				}
	    	}
	    }
	    chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
