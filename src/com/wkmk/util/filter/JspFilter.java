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
 * jsp页面过滤，系统中所有jsp页面都需要通过action跳转，除个别jsp文件外
 * @author Administrator
 *
 */
public class JspFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String requestURI = httpRequest.getRequestURI().toString();
	    if(!(requestURI.indexOf("/index.jsp") != -1 || requestURI.indexOf("/index_do.jsp") != -1 || requestURI.indexOf("/dog.jsp") != -1 || requestURI.indexOf("/tip.jsp") != -1 || requestURI.indexOf("/sys/comm/randomcode.jsp") != -1)){
	    	if(requestURI.indexOf("/kindeditor/config/upload.jsp") != -1){
	    		HttpSession session = httpRequest.getSession();
		    	if(session.getAttribute("s_userid") == null){
	    			httpResponse.sendRedirect("/index_do.jsp");
					return;
		    	}
	    	}else {
	    		httpResponse.sendRedirect("/index_do.jsp");
				return;
			}
	    }
	    chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
