package com.wkmk.zx.web.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.zx.bo.ZxHelpOrder;

/**
 *<p>Description: 在线答疑订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpOrderActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHelpOrder zxHelpOrder = new ZxHelpOrder();
	private List<FormFile> files = new ArrayList<FormFile>();

    public FormFile getFile(int i) {
        return files.get(i);
    }

    public void setFile(int i,FormFile file) {
        files.add(file);
    }
    
    public List getFiles(){
        return files;
    }

    public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHelpOrder getZxHelpOrder(){
		return this.zxHelpOrder;
	}

	public void setZxHelpOrder(ZxHelpOrder zxHelpOrder){
		this.zxHelpOrder=zxHelpOrder;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}