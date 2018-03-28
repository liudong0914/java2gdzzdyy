package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserTeaching;
import com.wkmk.sys.service.SysUserTeachingManager;

import com.util.action.BaseAction;
import com.util.string.Encode;

/**
 *<p>Description: 系统用户教学设置</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserTeachingAction extends BaseAction {

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserTeachingManager manager = (SysUserTeachingManager) getBean("sysUserTeachingManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		List list = manager.getSysUserTeachings(userid);
		
		EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
		EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		EduSubjectInfo esi = null;
		EduGradeInfo egi = null;
		SysUserTeaching sut = null;
		for(int i=0, size=list.size(); i<size; i++){
			sut = (SysUserTeaching) list.get(i);
			esi = esim.getEduSubjectInfo(sut.getSubjectid());
			egi = egim.getEduGradeInfo(sut.getGradeid());
			sut.setFlags(esi.getSubjectname());
			sut.setFlago(egi.getGradename());
		}
		httpServletRequest.setAttribute("list", list);
		if(list != null && list.size() > 0){
			httpServletRequest.setAttribute("rowcount", list.size());
		}else {
			httpServletRequest.setAttribute("rowcount", 1);
		}

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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserTeachingManager manager = (SysUserTeachingManager)getBean("sysUserTeachingManager");
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			manager.delSysUserTeachingByUserid(userid);//先删除
			
			String subjectid = null;
			String gradeid = null;
			String tempstr = null;
			SysUserTeaching teaching = null;
			List<String> teachgradeids = new ArrayList<String>();
			for(int i=1; i<=10; i++){
				subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid" + i));
				gradeid = Encode.nullToBlank(httpServletRequest.getParameter("gradeid" + i));
				tempstr = subjectid + "_" + gradeid;
				if(!"".equals(subjectid) && !"".equals(gradeid) && !teachgradeids.contains(tempstr)){
					teachgradeids.add(tempstr);
					teaching = new SysUserTeaching();
					teaching.setSubjectid(Integer.valueOf(subjectid));
					teaching.setGradeid(Integer.valueOf(gradeid));
					teaching.setUserid(userid);
					manager.addSysUserTeaching(teaching);
					addLog(httpServletRequest,"增加了一个系统用户教学设置");
				}
			}
		}catch (Exception e){
		}

		httpServletRequest.setAttribute("promptinfo", "用户教学设置修改成功!");
		return actionMapping.findForward("success");
	}
}