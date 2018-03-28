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

/**
 * 前台页面过滤
 * @author Administrator
 *
 */
public class IndexFilter implements Filter {
	
	//以下路径不过滤直接放行
	private static final String[] filterurl = {".htm", "weixinService.app", "weixinMenu.app", "weixinRegister.app", "weixinAccountIndex.app", "weixinTeacher.app", "weixinStudent.app", "weixinScanQRCode.app", "twoCode.app", "weixinVod.app", "weixinPay.app", "zyb.bo", "weixinScanTwoCode.app", "weixinHelp.app", "weixinPaper.app", "weixinLoginPc.app", "weixinLoginPcConfirm.app", "weixinCourse.app", "course.bo","weixinTextBook.app"};

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
	    String requestURI = httpRequest.getRequestURI().toString();
	    String result = "0";
	    for(int i=0, size=filterurl.length; i<size; i++){
	    	if(requestURI.indexOf(filterurl[i]) != -1){
	    		result = "1";
	    		break;
	    	}
	    }
	    
	    if("1".equals(result)){
	    	chain.doFilter(request, response);
	    }else {
	    	HttpServletResponse httpResponse = (HttpServletResponse) response;
	    	httpResponse.sendRedirect("/index.html");
			return ;
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}
}
