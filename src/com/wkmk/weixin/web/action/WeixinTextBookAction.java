package com.wkmk.weixin.web.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DESUtil;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseBuy;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduCourseBuyManager;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysPayPassword;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.service.SysPayPasswordManager;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentBuy;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkPaperFileDownload;
import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkBookContentBuyManager;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperFileDownloadManager;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkPaperTypeManager;
import com.wkmk.tk.service.TkTextBookBuyManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 教材订购
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked"})
public class WeixinTextBookAction extends BaseAction {
	
	/**
	 * 教材列表首页
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				List<SearchModel> condition = new ArrayList<SearchModel>();
				
				String keywords=Encode.nullToBlank(request.getParameter("keywords"));//搜索条件
				TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
				
				SearchCondition.addCondition(condition, "status", "=", "1");
				if(!"".equals(keywords)){
					SearchCondition.addCondition(condition, "textbookname", "like", "%"+keywords+"%");//教材名称
				}
				List textbooklist = manager.getTkTextBookInfos(condition, "orderindex asc,createdate desc", 0);
				//List textbooklist = manager.getPageTkTextBookInfos(condition, "orderindex asc,createdate desc", 0, 10).getDatalist();
				
				request.setAttribute("textbooklist", textbooklist);
				request.setAttribute("keywords", keywords);
				return mapping.findForward("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/*
	 * 列表页刷新ajax获取数据
	 */
	public void getTextBookByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			String userid = MpUtil.getUserid(request);
			String desUserid=Encode.nullToBlank(request.getParameter("userid"));
			if(!"1".equals(userid)){
				TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
				String keywords=Encode.nullToBlank(request.getParameter("keywords"));
				String pn=Encode.nullToBlank(request.getParameter("pagenum"));
				int pagesize=10;
				int pagenum="".equals(pn)?0:Integer.parseInt(pn);
				int pagecount=pagesize*pagenum;
				
				//
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "status", "=", "1");
				if(!"".equals(keywords)){
					SearchCondition.addCondition(condition, "textbookname", "like", "%"+keywords+"%");//教材名称
				}
				
				List textbooklist=manager.getPageTkTextBookInfos(condition, "orderindex asc,createdate desc", pagecount, pagesize).getDatalist();
				StringBuffer result=new StringBuffer();
				String subjectname="";
				TkTextBookInfo textBookInfo=null;
				for ( int i=0 ; i<textbooklist.size() ; i++ ) {
					textBookInfo=(TkTextBookInfo)textbooklist.get(i);
				      result.append("<a href=\"/weixinTextBook.app?method=getTextBookDetail&userid="+desUserid+"&textbookid="+textBookInfo.getTextbookid()+"\"><div class=\"listen_main_01\">"+
                              "<img src=\"/upload/"+textBookInfo.getSketch()+"\" onerror=\"javascript:this.src='/weixin/images/img07.png'\"/>"+
                              "<p>"+textBookInfo.getTextbookname()+"</p>"+
                        "</div></a>");
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * ajax,获取教材详情
	 */
	public ActionForward getTextBookDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String textbookid=Encode.nullToBlank(request.getParameter("textbookid"));
				TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
				TkTextBookInfo textBookInfo = manager.getTkTextBookInfo(textbookid);
				request.setAttribute("textBookInfo", textBookInfo);
			}
			return mapping.findForward("detail");
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward getTextBookBuy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
	                SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
	                request.setAttribute("sysUserInfo", sysUserInfo);
	                
				 //通过userid获取openid，支付需要，如果没有，则退出登录
                SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
                SysUserAttention sysUserAttention = suam.getSysUserAttentionByUserid(Integer.valueOf(userid));
                if(sysUserAttention == null){
                    //用户没有对应的openid，需要重新注册
                    return mapping.findForward("welcome");
                }
                request.setAttribute("openid", sysUserAttention.getOpenid());
                
                //教材信息
				String textbookid=Encode.nullToBlank(request.getParameter("textbookid"));
				TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
				TkTextBookInfo textBookInfo = manager.getTkTextBookInfo(textbookid);
				request.setAttribute("textBookInfo", textBookInfo);
				
				//获取用户当天支付密码输入错误次数
                SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
                int errorcount = sppm.getCountSysPayPassword(userid);
                if(errorcount < Constants.PAYPASSWORD_ERROR_COUNT){
                    request.setAttribute("paypassword", "1");
                }else {
                    request.setAttribute("paypassword", "0");
                }
			}
			return mapping.findForward("buy");
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/*
	 * 支付
	 */
	public ActionForward pay(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
        PrintWriter pw = null;
        String result = "0";
        try {
            String userid = MpUtil.getUserid(request);
            String textbookid = Encode.nullToBlank(request.getParameter("textbookid"));
            String paypwd = Encode.nullToBlank(request.getParameter("paypwd"));
            String totalnum = Encode.nullToBlank(request.getParameter("totalnum"));//订购教材总数
            String money = Encode.nullToBlank(request.getParameter("money"));//订购教材总价
            Float moneyFloat =Float.parseFloat(money);
            String recipientname = Encode.nullToBlank(request.getParameter("recipientname"));//收件人姓名
            String recipientphone = Encode.nullToBlank(request.getParameter("recipientphone"));//收件人电话
            String recipientaddress = Encode.nullToBlank(request.getParameter("recipientaddress"));//收件人地址
            
            TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
			TkTextBookInfo textBookInfo = manager.getTkTextBookInfo(textbookid);
            
            //验证支付密码
            SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
            if(MD5.getEncryptPwd(paypwd).equals(sysUserInfo.getPaypassword()) || textBookInfo.getSellprice() == 0){
            	
                //1、修改用户余额
                sysUserInfo.setMoney(sysUserInfo.getMoney() - moneyFloat);
                suim.updateSysUserInfo(sysUserInfo);
                //2、记录交易详情
                SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
                SysUserMoney sysUserMoney = new SysUserMoney();
                String title = "订购了教材：《" + textBookInfo.getTextbookname()+"》" ;
                sysUserMoney.setTitle(title);
                sysUserMoney.setChangemoney(moneyFloat);
                sysUserMoney.setChangetype(-1);
                sysUserMoney.setLastmoney(sysUserInfo.getMoney());
                sysUserMoney.setUserid(Integer.valueOf(userid));
                sysUserMoney.setCreatedate(DateTime.getDate());
                sysUserMoney.setUserip(IpUtil.getIpAddr(request));
                sysUserMoney.setDescript(title);
                summ.addSysUserMoney(sysUserMoney);
                //记录教材购买记录
                TkTextBookBuyManager ttbbm=(TkTextBookBuyManager)getBean("tkTextBookBuyManager");
                TkTextBookBuy ttbb = new TkTextBookBuy();
                ttbb.setTextbookid(textBookInfo.getTextbookid());
                ttbb.setPrice(textBookInfo.getPrice());
                ttbb.setDiscount(textBookInfo.getDiscount());
                ttbb.setSellprice(textBookInfo.getSellprice());
                ttbb.setCreatedate(DateTime.getDate());
                ttbb.setTotalnum(Integer.valueOf(totalnum));
                ttbb.setTotalprice(moneyFloat);
                ttbb.setBuyuserid(Integer.valueOf(userid));
                ttbb.setRecipientname(recipientname);
                ttbb.setRecipientphone(recipientphone);
                ttbb.setRecipientaddress(recipientaddress);
                ttbb.setIsdelivery("0");
                ttbb.setStatus("1");
                ttbbm.addTkTextBookBuy(ttbb);
                //记录教材销量
                textBookInfo.setSellcount(textBookInfo.getSellcount()+Integer.valueOf(totalnum));
                manager.updateTkTextBookInfo(textBookInfo);
               
                result = "ok";
                
                addLog(request, title, sysUserInfo);
            }else {
                //支付密码输入错误
                SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
                SysPayPassword spp = new SysPayPassword();
                spp.setUserid(Integer.valueOf(userid));
                spp.setUserip(IpUtil.getIpAddr(request));
                spp.setCreatedate(DateTime.getDate());
                spp.setPassword(paypwd);
                sppm.addSysPayPassword(spp);
                
                int errorcount = sppm.getCountSysPayPassword(userid);
                result = errorcount + "";
            }
            
            pw = response.getWriter();
            pw.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            pw = null;
        }

        return null;
    }
    
    /*
     * 系统日志记录
     */
    private void addLog(HttpServletRequest request, String descript, SysUserInfo sysUserInfo) {
        SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
        Integer unitid = sysUserInfo.getUnitid();
    
        SysUserLog model = new SysUserLog();
        model.setCreatedate(DateTime.getDate());
        model.setDescript(descript);
        model.setUserip(IpUtil.getIpAddr(request));
        model.setSysUserInfo(sysUserInfo);
        model.setUnitid(unitid);
        model.setLogtype("0");
        
        manager.addSysUserLog(model);
    }
    
	public ActionForward paySuccess(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			return mapping.findForward("success");
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
    }
	
	/**
	 * 
	  * 方法描述：教材订购记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-5-15 下午2:23:48
	 */
	public ActionForward myTextBookOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String userid=MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("mytextbookorder");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public void getMyTextBookOrderByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String searchuserid=MpUtil.getUserid(request);
			String desUserid=Encode.nullToBlank(request.getParameter("userid"));
			String type=Encode.nullToBlank(request.getParameter("type"));//发货状态，1已发货，0未发货
			if("1".equals(type)){
				type ="1";
			}else{
				type="0";
			}
			int pagesize=10;int pagenum=Integer.parseInt(Encode.nullToBlank(request.getParameter("pagenum")));int pagestat=pagesize*pagenum;
			TkTextBookBuyManager ttbbm = (TkTextBookBuyManager) getBean("tkTextBookBuyManager");
			TkTextBookInfoManager ttbim = (TkTextBookInfoManager) getBean("tkTextBookInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "isdelivery", "=", type);
			SearchCondition.addCondition(condition, "status", "=", "1");
			SearchCondition.addCondition(condition, "buyuserid", "=", Integer.valueOf(searchuserid));
			List<TkTextBookBuy> list =ttbbm.getPageTkTextBookBuys(condition, "createdate desc", pagestat, pagesize).getDatalist();
			StringBuffer result=new StringBuffer();
			for (TkTextBookBuy buy : list) {//循环购买记录查询相关数据
				TkTextBookInfo textBookInfo = ttbim.getTkTextBookInfo(buy.getTextbookid());
				String typename="1".equals(type)?"已发货":"未发货";
				result.append("<a href=\"/weixinTextBook.app?method=getTextBookBuyDetail&userid="+desUserid+"&textbookbuyid="+buy.getTextbookbuyid()+"\">"+
								"<div class=\"buy_main_moudle\">"+
							    	"<img src=\"/upload/"+textBookInfo.getSketch()+"\" />"+
							        "<div class=\"buy_main_moudle_font\">"+
							        	"<p  class=\"buy_main_moudle_font_p\">【"+textBookInfo.getTextbookname()+"】</p>"+
							            "<p class=\"buy_main_moudle_font_p01\">"+textBookInfo.getAuthor()+"</p>"+
							            "<p class=\"buy_main_moudle_font_p02\">订购数量：<span>"+buy.getTotalnum()+"</span></p>"+
							        "</div>"+
							    "</div>"+
							 "</a>");
			}
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	  * 方法描述：已订购教材详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-5-15 下午2:44:19
	 */
	public ActionForward getTextBookBuyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String textbookbuyid=Encode.nullToBlank(request.getParameter("textbookbuyid"));
				TkTextBookInfoManager manager=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
				TkTextBookBuyManager ttbbm = (TkTextBookBuyManager) getBean("tkTextBookBuyManager");
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				TkTextBookBuy textBookBuy = ttbbm.getTkTextBookBuy(textbookbuyid);
				String isdelivery = textBookBuy.getIsdelivery();
				if("0".equals(isdelivery)){
					isdelivery="未发货";
				}else{
					isdelivery="已发货";
				}
				TkTextBookInfo textBookInfo = manager.getTkTextBookInfo(textBookBuy.getTextbookid());
				SysUserInfo userInfo = suim.getSysUserInfo(textBookBuy.getBuyuserid());
				request.setAttribute("userInfo", userInfo);
				request.setAttribute("textBookBuy", textBookBuy);
				request.setAttribute("textBookInfo", textBookInfo);
				request.setAttribute("isdelivery", isdelivery);
			}
			return mapping.findForward("buydetail");
	    }catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}