package com.wkmk.util.action;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.image.ZXingUtil;
import com.util.string.Encode;
import com.util.string.UUID;

/**
 * <p>Description: 二维码生成工具</p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @Date Dec 12, 2012 3:08:38 PM
 * @author 张学栋
 * @version 1.0
 */
public class QrcodeToolsAction extends BaseAction {

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("edit");
	}
	
	public ActionForward getQrcodeUrl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			String linkurl = Encode.nullToBlank(request.getParameter("linkurl"));
			
			String realpath = request.getSession().getServletContext().getRealPath("/");
			String twocodepath = "/upload/qrocode/linkurl/" + DateTime.getDate("yyyyMM") + "/" + UUID.getNewUUID() + ".png";
			String filepath = realpath + twocodepath; // 全路径
			if (!FileUtil.isExistDir(filepath)) {
				FileUtil.creatDir(filepath);
			}
			ZXingUtil.encodeQRCodeImage(linkurl, null, filepath, 300, 300, null);
			
			out = response.getWriter();
			out.print(twocodepath);
		} catch (Exception e) {
		}
		
		return null;
	}
	
	public ActionForward downloadQrcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String qrcodepath = Encode.nullToBlank(request.getParameter("qrcodepath"));
		try {
			String realpath = request.getSession().getServletContext().getRealPath("/");
			InputStream is = new FileInputStream(realpath + qrcodepath);
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setContentType("text/html; charset=utf-8");
			response.setContentType("bin");
			String name = "二维码图片_" + DateTime.getDate("yyyyMMddHHmmss") + ".png";
			response.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
