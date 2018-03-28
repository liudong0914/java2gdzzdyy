package com.wkmk.tk.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkBookContentTask;

/**
 *<p>Description: 作业任务信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentTaskActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private TkBookContentTask tkBookContentTask = new TkBookContentTask();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public TkBookContentTask getTkBookContentTask(){
		return this.tkBookContentTask;
	}

	public void setTkBookContentTask(TkBookContentTask tkBookContentTask){
		this.tkBookContentTask=tkBookContentTask;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}