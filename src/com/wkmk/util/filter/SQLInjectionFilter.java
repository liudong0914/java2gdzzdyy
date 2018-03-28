package com.wkmk.util.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wkmk.util.file.TxtLogsUtil;

/**
 * @Description:
 * @author Administrator
 */
public class SQLInjectionFilter implements Filter {
	
	private static Map<String, List<String>> filterMap = null;
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response; 
		String queryString = httpRequest.getQueryString();
		boolean judge = false;
		if (queryString != null && queryString.trim().length() > 0) {
			judge = isJudge(httpRequest, queryString.toLowerCase());
		} else {
			judge = false;
		} 
		if (judge) {
			httpResponse.sendRedirect("/index_do.jsp");
			return;
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	private boolean isJudge(HttpServletRequest httpRequest, String queryString) {
		//String inj_str = "'| and | or |exec |exec(|exec (|insert |delete |update |chr(|chr (|truncate |declare |sleep(|sleep (";
		String inj_str = "'|and|or|exec|insert|delete|update|chr|truncate|declare|sleep|drop|select|union|script|alert|prompt";
		
		String substring = queryString.substring(0, 1);
		if(!substring.equals("?")){
			queryString = "?"+ queryString;
		}
		if (queryString.indexOf("?method") <= -1) {
			String replaceAll = queryString.replaceAll(".*([;]+|(--)+).*", " ");
			String[] inj_stra = inj_str.split("\\|");
			for (int i = 0; i < inj_stra.length; i++) {
				//初步判断
				if (replaceAll.indexOf(inj_stra[i]) >= 0) {
					//判断是否为真的攻击
					Map<String, List<String>> filterMap = getFilterMap();
					List<String> filterList = filterMap.get(inj_stra[i]);
					for(int m=0, n=filterList.size(); m<n; m++){
						if (replaceAll.indexOf(filterList.get(m)) >= 0) {
							String requestURL = httpRequest.getRequestURI().toString() + "?" + httpRequest.getQueryString() + " {拦截字符串：-inj_stra[" + i + "]-【" + filterList.get(m) + "】}";
							TxtLogsUtil.getInstance().addSQLTxt(httpRequest, requestURL);
							return true;
						}
					}
				}
			}
			return false;
		} else {
			if (queryString.indexOf("&") > -1) {
				if (queryString.indexOf("method") > -1) {
					queryString = queryString.substring((queryString.indexOf("&") + 1));
				}
				String replaceAll = queryString.replaceAll(".*([;]+|(--)+).*", " ");
				String[] inj_stra = inj_str.split("\\|");
				for (int i = 0; i < inj_stra.length; i++) {
					//初步判断
					if (replaceAll.indexOf(inj_stra[i]) >= 0) {
						//判断是否为真的攻击
						Map<String, List<String>> filterMap = getFilterMap();
						List<String> filterList = filterMap.get(inj_stra[i]);
						for(int m=0, n=filterList.size(); m<n; m++){
							if (replaceAll.indexOf(filterList.get(m)) >= 0) {
								String requestURL = httpRequest.getRequestURI().toString() + "?" + httpRequest.getQueryString() + " {拦截字符串：-inj_stra[" + i + "]-【" + filterList.get(m) + "】}";
								TxtLogsUtil.getInstance().addSQLTxt(httpRequest, requestURL);
								return true;
							}
						}
					}
				}
			}
			return false;
		}
	}
	
	private Map<String, List<String>> getFilterMap(){
		if(filterMap == null){
			filterMap = new HashMap<String, List<String>>();
			List<String> filterList = new ArrayList<String>();
			filterList.add("'");
			filterMap.put("'", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add(" and ");
			filterList.add("%20and ");
			filterList.add(" and%20");
			filterList.add("%20and%20");
			filterMap.put("and", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add(" or ");
			filterList.add("%20or ");
			filterList.add(" or%20");
			filterList.add("%20or%20");
			filterMap.put("or", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("exec ");
			filterList.add("exec%20");
			filterList.add("exec(");
			filterMap.put("exec", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("insert ");
			filterList.add("insert%20");
			filterMap.put("insert", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("delete ");
			filterList.add("delete%20");
			filterMap.put("delete", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("update ");
			filterList.add("update%20");
			filterMap.put("update", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("chr(");
			filterList.add("chr (");
			filterList.add("chr%20(");
			filterMap.put("chr", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("truncate ");
			filterList.add("truncate%20");
			filterMap.put("truncate", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("declare ");
			filterList.add("declare%20");
			filterMap.put("declare", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("sleep(");
			filterList.add("sleep (");
			filterList.add("sleep%20(");
			filterMap.put("sleep", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("drop table");
			filterList.add("drop%20table");
			filterMap.put("drop", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("select ");
			filterList.add("select%20");
			filterMap.put("select", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("union ");
			filterList.add("union%20");
			filterMap.put("union", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("<script");
			filterList.add("%3cscript");
			filterMap.put("script", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("alert(");
			filterList.add("alert%20(");
			filterMap.put("alert", filterList);
			
			filterList = new ArrayList<String>();
			filterList.add("prompt(");
			filterList.add("prompt%20(");
			filterMap.put("prompt", filterList);
		}
		return filterMap;
	}
}
