package com.wkmk.tk.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserInfoActionForm;
import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkTextBookBuyManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.tk.web.form.TkTextBookBuyActionForm;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookBuyAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		
		String textbookname = Encode.nullToBlank(httpServletRequest.getParameter("textbookname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String recipientname = Encode.nullToBlank(httpServletRequest.getParameter("recipientname"));
		String isdelivery = Encode.nullToBlank(httpServletRequest.getParameter("isdelivery"));
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "a.createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getTkTextBookBuysOfPage(textbookname, username, createdate, recipientname, isdelivery,sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("textbookname", textbookname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("recipientname", recipientname);
		httpServletRequest.setAttribute("isdelivery", isdelivery);
		return actionMapping.findForward("list");
	}

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuy model = new TkTextBookBuy();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuyActionForm form = (TkTextBookBuyActionForm)actionForm;
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkTextBookBuy model = form.getTkTextBookBuy();
				manager.addTkTextBookBuy(model);
				addLog(httpServletRequest,"增加了一个教材基本信息表");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		TkTextBookInfoManager ttbim = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		SysUserInfoManager suim = (SysUserInfoManager)getBean("sysUserInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			//分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			
			//订购记录
			TkTextBookBuy model = manager.getTkTextBookBuy(objid);
			String isdelivery = model.getIsdelivery();
			if("".equals(isdelivery)){
				isdelivery ="0";
			}
			httpServletRequest.setAttribute("isdelivery", isdelivery);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			//教材详情
			Integer textbookid = model.getTextbookid();
			TkTextBookInfo textBookInfo = ttbim.getTkTextBookInfo(textbookid);
			String bookStatus ="";
			String status = textBookInfo.getStatus();
			if("1".equals(status)){
				bookStatus="正常";
			}else{
				bookStatus="禁用";
			}
			httpServletRequest.setAttribute("textBookInfo", textBookInfo);
			httpServletRequest.setAttribute("bookStatus", bookStatus);
			//购买人
			Integer buyuserid = model.getBuyuserid();
			SysUserInfo sysUserInfo = suim.getSysUserInfo(buyuserid);
			httpServletRequest.setAttribute("sysUserInfo", sysUserInfo);
			
		}catch (Exception e){
			e.printStackTrace();
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkTextBookBuyActionForm form = (TkTextBookBuyActionForm)actionForm;
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String textbookbuyid = Encode.nullToBlank(httpServletRequest.getParameter("textbookbuyid"));
				String isdelivery = Encode.nullToBlank(httpServletRequest.getParameter("isdelivery"));
//				TkTextBookBuy model = form.getTkTextBookBuy();
				TkTextBookBuy model = manager.getTkTextBookBuy(textbookbuyid);
				model.setIsdelivery(isdelivery);
				manager.updateTkTextBookBuy(model);
				addLog(httpServletRequest,"修改了一个教材基本信息表");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *批量删除
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkTextBookBuy(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 
	    * @Title: userExport
	    * @Description: 导出教材订购记录到excel
	    * @param @param actionMapping
	    * @param @param actionForm
	    * @param @param httpServletRequest
	    * @param @param httpServletResponse
	    * @param @return    参数
	    * @return ActionForward    返回类型
	    * @throws
	 */
  public ActionForward exportOrders(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	  TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
	  
	  String idArray = Encode.nullToBlank(httpServletRequest.getParameter("idArray"));//7,6,5,4,2,1
	  List<SearchModel> condition = new ArrayList<SearchModel>();
	  String textbookbuyids = "";
		if(!"".equals(idArray)){
			String[] ids = idArray.split(",");
			if(!"".equals(ids) && ids.length>0){
				for (int i = 0; i < ids.length; i++) {
					String textbookbuyid = ids[i];
					textbookbuyids = textbookbuyids + textbookbuyid + ",";
				}
			}
		}
	  if(!"".equals(textbookbuyids)){
		  textbookbuyids  = textbookbuyids.substring(0, textbookbuyids.length()-1);
		  SearchCondition.addCondition(condition, "textbookbuyid", "in", textbookbuyids);
	  }
        List list = manager.getTkTextBookBuys(textbookbuyids, "a.createdate desc");
        
        String rootpath = httpServletRequest.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
        String targetfile = "教材订购记录表.xls";//输出的excel文件名   
        String worksheet = "教材订购记录";//输出的excel文件工作表名   
        //String[] title = {"登录名","用户姓名"};//excel工作表的标题   
        WritableWorkbook workbook;   
        try {   
            //创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下   
            OutputStream os=new FileOutputStream(rootpath+"/"+targetfile);    
            workbook=Workbook.createWorkbook(os);    
            WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表   
            jxl.write.Label label;   
            label = new jxl.write.Label(0, 0, "序号");
            sheet.addCell(label); 
            label = new jxl.write.Label(1, 0, "教材名称");
            sheet.addCell(label); 
            label = new jxl.write.Label(2, 0, "单价");
            sheet.addCell(label); 
            label = new jxl.write.Label(3, 0, "折扣");
            sheet.addCell(label); 
            label = new jxl.write.Label(4, 0, "售价");
            sheet.addCell(label); 
            label = new jxl.write.Label(5, 0, "订购人姓名");
            sheet.addCell(label); 
            label = new jxl.write.Label(6, 0, "订购总数");
            sheet.addCell(label); 
            label = new jxl.write.Label(7, 0, "订单总额");
            sheet.addCell(label); 
            label = new jxl.write.Label(8, 0, "收件人姓名");
            sheet.addCell(label); 
            label = new jxl.write.Label(9, 0, "收件人电话");
            sheet.addCell(label); 
            label = new jxl.write.Label(10, 0, "收件人地址");
            sheet.addCell(label); 
            label = new jxl.write.Label(11, 0, "支付状态");
            sheet.addCell(label); 
            label = new jxl.write.Label(12, 0, "发货状态");
            sheet.addCell(label); 
            label = new jxl.write.Label(13, 0, "订购日期");
            sheet.addCell(label); 
            if(list !=null && list.size()>0){
            	Object[] obj = null;
			    TkTextBookBuy bookBuy = null;
			    TkTextBookInfo bookInfo = null;
			    SysUserInfo userInfo = null;
			    for (int i=0; i<list.size(); i++){   
					obj = (Object[])list.get(i);
					bookBuy = (TkTextBookBuy)obj[0];
					bookInfo = (TkTextBookInfo)obj[1];
					userInfo = (SysUserInfo)obj[2];
					String status = bookBuy.getStatus();
					String payStatus = "";
					if("0".equals(status)){
						payStatus = "未付款";
					}else{
						payStatus = "已付款";
					}
					String isdelivery = bookBuy.getIsdelivery();
					String postStatus = "";
					if("0".equals(isdelivery)){
						postStatus = "未发货";
					}else{
						postStatus = "已发货";
					}
		            //Label(列号,行号 ,内容 )   
		            label = new jxl.write.Label(0, i+1, i+1+""); //第一列，序号
		            sheet.addCell(label);   
		            label = new jxl.write.Label(1, i+1, bookInfo.getTextbookname()); //第二列，教材名称
		            sheet.addCell(label); 
		            label = new jxl.write.Label(2, i+1, bookInfo.getPrice().toString());
		            sheet.addCell(label);
		            label = new jxl.write.Label(3, i+1, bookInfo.getDiscount().toString());
		            sheet.addCell(label);
		            label = new jxl.write.Label(4, i+1, bookInfo.getSellprice().toString());
		            sheet.addCell(label);
		            label = new jxl.write.Label(5, i+1, userInfo.getLoginname());
		            sheet.addCell(label);
		            label = new jxl.write.Label(6, i+1, bookBuy.getTotalnum().toString());
		            sheet.addCell(label);
		            label = new jxl.write.Label(7, i+1, bookBuy.getTotalprice().toString());
		            sheet.addCell(label);
		            label = new jxl.write.Label(8, i+1, bookBuy.getRecipientname());
		            sheet.addCell(label);
		            label = new jxl.write.Label(9, i+1, bookBuy.getRecipientphone());
		            sheet.addCell(label);
		            label = new jxl.write.Label(10, i+1, bookBuy.getRecipientaddress());
		            sheet.addCell(label);
		            label = new jxl.write.Label(11, i+1, payStatus);
		            sheet.addCell(label);
		            label = new jxl.write.Label(12, i+1, postStatus);
		            sheet.addCell(label);
		            label = new jxl.write.Label(13, i+1, bookBuy.getCreatedate());
		            sheet.addCell(label);
	           }
            } 
            workbook.write();    
            workbook.close();   
        }catch(Exception e)  {    
            e.printStackTrace();    
        }    
        
        try
        {
            BufferedInputStream bis = null; 
            BufferedOutputStream bos = null; 
            OutputStream fos = null; 
            InputStream fis = null; 
            File uploadFile = new File(rootpath+"/"+targetfile); 
            fis = new FileInputStream(uploadFile); 
            bis = new BufferedInputStream(fis); 
            fos = httpServletResponse.getOutputStream(); 
            bos = new BufferedOutputStream(fos); 
            //这个就是弹出下载对话框的关键代码 
            httpServletResponse.setHeader("Content-disposition", 
                               "attachment;filename=" + 
                               URLEncoder.encode(targetfile, "utf-8")); 
            int bytesRead = 0; 
            //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流 
            byte[] buffer = new byte[8192]; 
            while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) { 
                bos.write(buffer, 0, bytesRead); 
            } 
            bos.flush(); 
            fis.close(); 
            bis.close(); 
            fos.close(); 
            bos.close(); 
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        
        return null;
    }
}