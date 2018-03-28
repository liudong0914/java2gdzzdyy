package com.wkmk.util.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.util.action.BaseAction;
import com.util.encrypt.DES;
import com.util.string.Encode;

/**
 * <p>Description: 获取视频连接地址</p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @Date Dec 12, 2012 3:08:38 PM
 * @author 张学栋
 * @version 1.0
 */
public class VideoUrlAction extends BaseAction {

	/**
	 * 获取视频播放地址
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		StringBuffer vurl = new StringBuffer();
		try {
			String id = Encode.nullToBlank(request.getParameter("id"));
			//ids[0]影片id，ids[1]类型，1为影片id，ids[2]视频服务器id，
			String[] ids = id.split("!");
			if(ids != null && ids.length == 3){
				if("1".equals(DES.getDecryptPwd(ids[1]))){
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
					VwhFilmPix pix = vfpm.getVwhFilmPix(DES.getDecryptPwd(ids[0]));
					if(pix.getFlvpath().startsWith("http://")){
						vurl.append(pix.getFlvpath());
					}else {
						VwhComputerInfo computer = vcim.getVwhComputerInfo(DES.getDecryptPwd(ids[2]));
						
						vurl.append("http://").append(computer.getIp());
						if(!"80".equals(computer.getPort())){
							vurl.append(":").append(computer.getPort());
						}
						vurl.append("/upload/").append(pix.getFlvpath());
					}
				}
			}
			out = response.getWriter();
			out.print(vurl.toString());
		} catch (Exception e) {
		}
		
		return null;
	}
}
