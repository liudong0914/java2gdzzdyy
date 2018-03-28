package com.wkmk.edu.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.des.DesUtil;
import com.util.encrypt.DES;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.web.form.EduCourseFilmActionForm;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.util.common.ArithUtil;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;

/**
 *<p>Description: 课程视频</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFilmAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	    EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
	       VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
	       
	       String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
	       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	       String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
	       httpServletRequest.setAttribute("courseid", courseid);
	       httpServletRequest.setAttribute("columnid", columnid);
	       httpServletRequest.setAttribute("courseclassid", courseclassid);
	       
	       String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
	       httpServletRequest.setAttribute("createdate", createdate);
	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	       
	       try {
	           //课程视频
	           PageUtil pageUtil = new PageUtil(httpServletRequest);
	           List<SearchModel> condition = new ArrayList<SearchModel>();
	           SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
	           SearchCondition.addCondition(condition, "courseid", "=", courseid);
	           SearchCondition.addCondition(condition, "status", "=", "1");
	           if(!"".equals(createdate)){
	               SearchCondition.addCondition(condition, "createdate", "like", "%"+createdate+"%");
	           }
	           
	           PageList pageList = filmManager.getPageEduCourseFilms(condition, "orderindex desc", pageUtil.getStartCount(), pageUtil.getPageSize());
	           ArrayList datalist = pageList.getDatalist();
	           if(datalist !=null && datalist.size()>0){
	               for(int i=0;i<datalist.size();i++){
	                   EduCourseFilm model = (EduCourseFilm) datalist.get(i);
	                   Integer filmid = model.getFilmid();
	                   VwhFilmInfo vwhFilmInfo = filmInfoManager.getVwhFilmInfo(filmid);
	                   //model.setFlagl(vwhFilmInfo.getTitle());//视频名称
	                   model.setFlago(vwhFilmInfo.getActor());//主讲人
	                   Date parse = formatter.parse(model.getCreatedate());
	                     model.setCreatedate(formatter.format(parse));
	               }
	           }
	           httpServletRequest.setAttribute("pagelist", pageList);
	       }catch (Exception e){
	       }
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
		httpServletRequest.setAttribute("act", "uploadSave");
		
		 String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
         String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
         String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
         httpServletRequest.setAttribute("courseid", courseid);
         httpServletRequest.setAttribute("columnid", columnid);
         httpServletRequest.setAttribute("courseclassid", courseclassid);
         
         httpServletRequest.setAttribute("price", "2");
         httpServletRequest.setAttribute("discount", "5");
         httpServletRequest.setAttribute("sellprice", "1");
         
         //微课视频信息
         SysUserInfo sysUserInfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
         VwhFilmInfo model = new VwhFilmInfo();
         model.setSketch("default.gif");
         model.setActor(sysUserInfo.getUsername());
         httpServletRequest.setAttribute("model", model);
         httpServletRequest.setAttribute("act", "uploadSave");
         
		saveToken(httpServletRequest);
		httpServletRequest.setAttribute("tag", "3");
		
		 EduCourseFilm ecf = new EduCourseFilm();
	     httpServletRequest.setAttribute("modelECF", ecf);
		return actionMapping.findForward("edit");
	}
	
	/**
	 * 微课上传保存
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-17 上午10:29:57 
	* @lastModified ; 2017-2-17 上午10:29:57 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward uploadSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	    EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
        String qrcodeno = Encode.nullToBlank(httpServletRequest.getParameter("qrcodeno"));//二维码编号
        String price = Encode.nullToBlank(httpServletRequest.getParameter("price"));//标价
        String coursecolumnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));//所属目录
        String discount = Encode.nullToBlank(httpServletRequest.getParameter("discount"));//折扣
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        
        VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        
        HttpSession session = httpServletRequest.getSession();
        SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
        Integer unitid = (Integer) session.getAttribute("s_unitid");
        try {
            EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
            String gradeid = "1";
            EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
            
            VwhFilmInfo model = form.getVwhFilmInfo();
            model.setSketchimg(model.getSketch());
            model.setHits(0);
            model.setStatus("0");//待审核
            model.setOrderindex(1);
            model.setCreatedate(DateTime.getDate());
            model.setUpdatetime(model.getCreatedate());
            model.setEduGradeInfo(egi);
            model.setSysUserInfo(sysUserInfo);
            model.setComputerid(Constants.DEFAULT_COMPUTERID);//默认用户上传不能选择服务器，只有管理员管理视频时可以改
            model.setUnitid(unitid);
            manager.addVwhFilmInfo(model);
            addLog(httpServletRequest,"增加了一个微课程【" + model.getTitle() + "】信息");
            model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model, httpServletRequest));
            manager.updateVwhFilmInfo(model);
            
            //视频影片
            VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
            String[] filename = httpServletRequest.getParameterValues("filename");
            String[] filepath = httpServletRequest.getParameterValues("filepath");
            String[] filesize = httpServletRequest.getParameterValues("filesize");
            //视频截屏参数
            String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
            String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
            String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
            
            if(filename != null && filename.length > 0){
                String fext = null;
                String fname = null;
                VwhFilmPix pix = null;
                String[] paths = null;
                for(int i=0, size=filename.length; i<size; i++){
                    if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
                        continue;
                    }
                    if(filepath[i].indexOf("exist_") != -1){
                        //可以重复上传，只给用户提示，除非用户自己删除
                        filepath[i] = filepath[i].substring(6);
                    }
                    paths = filepath[i].split(";");
                    fext = FileUtil.getFileExt(filename[i]).toLowerCase();
                    fname = filename[i].substring(0,filename[i].lastIndexOf("."));
                    
                    pix = new VwhFilmPix();
                    pix.setName(fname);
                    pix.setSrcpath(paths[0]);
                    //转码后的路径统一通过接口修改
                    //pix.setFlvpath(filepath[i].substring(0,filepath[i].lastIndexOf(".")) + ".mp4");
                    pix.setImgpath("convert.png");
                    pix.setConvertstatus("0");
                    pix.setImgwidth(Integer.valueOf(imgwidth));
                    pix.setImgheight(Integer.valueOf(imgheight));
                    pix.setSecond(second);
                    pix.setFilesize(Long.valueOf(filesize[i]));
                    pix.setFileext(fext);
                    pix.setTimelength("0");
                    pix.setResolution("0");
                    pix.setOrderindex(i+1);
                    pix.setCreatedate(DateTime.getDate());
                    pix.setFilmid(model.getFilmid());
                    pix.setUnitid(unitid);
                    pix.setUpdateflag("1");
                    pix.setUpdatetime(DateTime.getDate());
                    pix.setMd5code(paths[1]);
                    vfpm.addVwhFilmPix(pix);
                    
                    //开始转码
                    ConvertFile.convertVod(pix, "add", 0);
                    vfpm.updateVwhFilmPix(pix);
                }
            }
            
            //课程微课
            EduCourseFilm courseFilm = new EduCourseFilm();
            courseFilm.setTitle(model.getTitle());
            courseFilm.setQrcodeno(qrcodeno);
            courseFilm.setFilmid(model.getFilmid());
            courseFilm.setCoursecolumnid(Integer.valueOf(coursecolumnid));
            courseFilm.setCourseid(Integer.valueOf(courseid));
            courseFilm.setOrderindex(0);
            courseFilm.setStatus("1");
            courseFilm.setCreatedate(DateTime.getDate());
            courseFilm.setPrice(Float.valueOf(price));
            courseFilm.setDiscount(Float.valueOf(discount));
            courseFilm.setSellprice(ArithUtil.round(Float.valueOf(price)*Float.valueOf(discount)));
            courseFilm.setSellcount(0);
            courseFilm.setHits(0);
            filmManager.addEduCourseFilm(courseFilm);
            
            //生成课程视频二维码
            TwoCodeUtil.getBookFilmTwoCodePath(qrcodeno, httpServletRequest);
            
            //清空缓存
            CacheUtil.deleteObject("EduCourseFilm_Map_" + courseid);
            CacheUtil.deleteObject("EduCourseFilm_List_" + courseid);
             
        }catch (Exception e){
            e.printStackTrace();
            httpServletRequest.setAttribute("promptinfo", "微课上传失败,请重试!");
            return actionMapping.findForward("failure");
        }

        return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
		EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseFilm model = form.getEduCourseFilm();
				manager.addEduCourseFilm(model);
				addLog(httpServletRequest,"增加了一个课程视频");
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
    public ActionForward adminBeforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        
          //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("columnid", columnid);
        try {
            EduCourseFilm model = manager.getEduCourseFilm(objid);
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("act", "adminUpdateSave");
            Integer filmid = model.getFilmid();
            VwhFilmInfo filmInfo = filmInfoManager.getVwhFilmInfo(filmid);
            httpServletRequest.setAttribute("filminfo", filmInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        saveToken(httpServletRequest);
        return actionMapping.findForward("updateedit");
    }

    /**
     *修改时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminUpdateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        if (isTokenValid(httpServletRequest, true)) {
            try {
                EduCourseFilm model = form.getEduCourseFilm();
                manager.updateEduCourseFilm(model);
                
                HttpSession session = httpServletRequest.getSession();
                SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
                VwhFilmInfo vwhFilmInfo = form.getVwhFilmInfo();
                EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
                String gradeid = "1";
                EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
                vwhFilmInfo.setEduGradeInfo(egi);
                vwhFilmInfo.setUpdatetime(DateTime.getDate());
                vwhFilmInfo.setSysUserInfo(sysUserInfo);
                filmInfoManager.updateVwhFilmInfo(vwhFilmInfo);
                
                addLog(httpServletRequest,"修改了一个课程视频【" + model.getTitle() + "】");
                
              //生成课程视频二维码
                TwoCodeUtil.getBookFilmTwoCodePath(model.getQrcodeno(), httpServletRequest);
                
                //清空缓存
                CacheUtil.deleteObject("EduCourseFilm_Map_" + model.getCourseid());
                CacheUtil.deleteObject("EduCourseFilm_List_" + model.getCourseid());
            }catch (Exception e){
            	e.printStackTrace();
            }
        }
        httpServletRequest.setAttribute("reloadtree", "1");
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
    public ActionForward adminDelBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");

        EduCourseFilm model = null;
        for (int i = 0; i < checkids.length; i++) {
            model = manager.getEduCourseFilm(checkids[i]);
            //清空缓存
            CacheUtil.deleteObject("EduCourseFilm_Map_" + model.getCourseid());
            CacheUtil.deleteObject("EduCourseFilm_List_" + model.getCourseid());
            manager.delEduCourseFilm(model);
            addLog(httpServletRequest,"删除了一个课程视频【" + model.getTitle() + "】");
        }
        return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }	
	   /**
     * 跳转到主工作区
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward main(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
            return actionMapping.findForward("main");
    }
    /**
     * 树形选择器
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward tree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
            EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
            EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
            String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
            String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
            httpServletRequest.setAttribute("courseid", courseid);
            httpServletRequest.setAttribute("courseclassid", courseclassid);
            //课程信息
            EduCourseInfo model = manager.getEduCourseInfo(courseid);
            Integer userid = model.getSysUserInfo().getUserid();
            httpServletRequest.setAttribute("userid", userid.toString());
            httpServletRequest.setAttribute("model", model);
            //课程目录
            List lst = columnManager.getEduCourseColumnByCourseid(model.getCourseid(), model.getUnitid());
            String tree = "";
            String url = "";
            EduCourseColumn cnc = null;
            String no = "";// 自身ID
            String pno = "";// 父ID
            String text = "";// 树节点显示名称
            String hint = "";
            String target = "";
            for (int i = 0; i < lst.size(); i++) {
                cnc = (EduCourseColumn) lst.get(i);
                no = cnc.getColumnno().trim();// 自身ID
                pno = cnc.getParentno().trim();// 父ID
                text = cnc.getTitle();// 树节点显示名称
                  
                 
                url = "eduCourseFilmAction.do?method=list&columnid=" + cnc.getColumnid()+"&courseid="+cnc.getCourseid()+"&courseclassid="+courseclassid;// onclick树节点后执行
                target = "rfrmright";// 页面在打开位置
                tree += "\n" + "tree" + ".nodes[\"" + pno + "_" + no + "\"]=\"";
                if (text != null && text.trim().trim().length() > 0) {
                    tree += "text:" + text + ";";
                }
                if (hint != null && hint.trim().length() > 0) {
                    tree += "hint:" + hint + ";";
                }
                tree += "url:" + url + ";";
                tree += "target:" + target + ";";
                tree += "\";";
            }
            String rooturl = "/sysLayoutAction.do?method=welcome";
            httpServletRequest.setAttribute("treenode", tree);
            httpServletRequest.setAttribute("rooturl", rooturl);
            httpServletRequest.setAttribute("modulename", "课程视频");
            return actionMapping.findForward("tree");
    }
    
    
    //======================================以下为个人中心微课程管理==============================================

    /**
     * 微课管理
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-20 上午9:41:08 
    * @lastModified ; 2017-2-20 上午9:41:08 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
    * @throws Exception
     */
    public ActionForward filmMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("filmmain");
    }
    
    /**
     * 微课管理
     */
    public ActionForward filmTree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            HttpSession session = httpServletRequest.getSession();
            String courseid = (String) session.getAttribute("courseid");
            
            EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
            List list = manager.getEduCourseColumns(condition, "columnno asc", 0);
            
            StringBuffer tree = new StringBuffer();
            String text = null;
            String target="rfrmright";
            String url = null;
            String hint = null;
            String no = null;
            String pno = null;
            EduCourseColumn ecc = null;
            for (int i = 0; i < list.size(); i++) {
                ecc = (EduCourseColumn) list.get(i);
                no = ecc.getColumnno().trim();// 自身ID
                pno = ecc.getParentno().trim();// 父ID
                text = ecc.getTitle();// 树节点显示名称
                 
                url = "/eduCourseFilmAction.do?method=filmList&columnid=" + ecc.getColumnid()+"&courseid="+ecc.getCourseid();
                tree.append("\n").append("tree").append(".nodes[\"").append(pno).append("_").append(no).append("\"]=\"");
                if(text !=null && text.trim().length() > 0) {
                    tree.append("text:").append(text).append(";");
                }
                if(hint!=null && hint.trim().length() > 0) {
                    tree.append("hint:").append(hint).append(";");
                }
                tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
            }
            String rooturl = "javascript:;";
            httpServletRequest.setAttribute("treenode", tree.toString());
            httpServletRequest.setAttribute("rooturl", rooturl);
            httpServletRequest.setAttribute("modulename", "微课管理");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("filmtree");
    }
    
    /**
     * 微课管理
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-20 上午9:46:47 
    * @lastModified ; 2017-2-20 上午9:46:47 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward filmList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
            EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
           
           String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
           String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
           httpServletRequest.setAttribute("courseid", courseid);
           httpServletRequest.setAttribute("columnid", columnid);
           
           String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
           String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
           httpServletRequest.setAttribute("title", title);
           httpServletRequest.setAttribute("status", status);
           
           try {
               //课程视频
               PageUtil pageUtil = new PageUtil(httpServletRequest);
               PageList pageList = filmManager.getEduCourseFilmsOfPage(columnid, courseid, status, title, "a.orderindex asc",  pageUtil.getStartCount(), pageUtil.getPageSize());
               httpServletRequest.setAttribute("pagelist", pageList);
           }catch (Exception e){
           }
           return actionMapping.findForward("filmlist");
    }
    
    /**
     * 微课新增前
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-20 上午10:05:13 
    * @lastModified ; 2017-2-20 上午10:05:13 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward beforeAddFilm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("columnid", columnid);
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        VwhFilmInfo model = new VwhFilmInfo();
        model.setSketch("default.gif");
        httpServletRequest.setAttribute("model", model);
        httpServletRequest.setAttribute("act", "addSaveFilm");
        httpServletRequest.setAttribute("price", "2");
        httpServletRequest.setAttribute("discount", "5");
        httpServletRequest.setAttribute("sellprice", "1");
        
        
        EduCourseFilm ecf = new EduCourseFilm();
        httpServletRequest.setAttribute("modelECF", ecf);
        
        return actionMapping.findForward("filmedit");
    }

    /**
     *增加时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward addSaveFilm(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
        VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("columnid", columnid);
        
        String qrcodeno = Encode.nullToBlank(httpServletRequest.getParameter("qrcodeno"));
        String orderindex = Encode.nullToBlank(httpServletRequest.getParameter("orderindex"));
        String discount = Encode.nullToBlank(httpServletRequest.getParameter("discount"));//折扣
        String sellprice = Encode.nullToBlank(httpServletRequest.getParameter("sellprice"));//售价
        String price = Encode.nullToBlank(httpServletRequest.getParameter("price"));//标价

        
        HttpSession session = httpServletRequest.getSession();
        SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
        Integer unitid = (Integer) session.getAttribute("s_unitid");
        try {
            EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
            String gradeid = "1";
            EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
            
            VwhFilmInfo model = form.getVwhFilmInfo();
            model.setSketchimg(model.getSketch());
            model.setHits(0);
            model.setStatus("1");//已审核
            model.setActor(sysUserInfo.getUsername());
            model.setOrderindex(1);
            model.setCreatedate(DateTime.getDate());
            model.setUpdatetime(model.getCreatedate());
            model.setEduGradeInfo(egi);
            model.setSysUserInfo(sysUserInfo);
            model.setComputerid(Constants.DEFAULT_COMPUTERID);//默认用户上传不能选择服务器，只有管理员管理视频时可以改
            model.setUnitid(unitid);
            manager.addVwhFilmInfo(model);
            addLog(httpServletRequest,"增加了一个微课程【" + model.getTitle() + "】信息");
            model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model, httpServletRequest));
            manager.updateVwhFilmInfo(model);
            
            //视频影片
            VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
            String[] filename = httpServletRequest.getParameterValues("filename");
            String[] filepath = httpServletRequest.getParameterValues("filepath");
            String[] filesize = httpServletRequest.getParameterValues("filesize");
            //视频截屏参数
            String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
            String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
            String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
            
            if(filename != null && filename.length > 0){
                String fext = null;
                String fname = null;
                VwhFilmPix pix = null;
                String[] paths = null;
                for(int i=0, size=filename.length; i<size; i++){
                    if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
                        continue;
                    }
                    if(filepath[i].indexOf("exist_") != -1){
                        //可以重复上传，只给用户提示，除非用户自己删除
                        filepath[i] = filepath[i].substring(6);
                    }
                    paths = filepath[i].split(";");
                    fext = FileUtil.getFileExt(filename[i]).toLowerCase();
                    fname = filename[i].substring(0,filename[i].lastIndexOf("."));
                    
                    pix = new VwhFilmPix();
                    pix.setName(fname);
                    pix.setSrcpath(paths[0]);
                    //转码后的路径统一通过接口修改
                    //pix.setFlvpath(filepath[i].substring(0,filepath[i].lastIndexOf(".")) + ".mp4");
                    pix.setImgpath("convert.png");
                    pix.setConvertstatus("0");
                    pix.setImgwidth(Integer.valueOf(imgwidth));
                    pix.setImgheight(Integer.valueOf(imgheight));
                    pix.setSecond(second);
                    pix.setFilesize(Long.valueOf(filesize[i]));
                    pix.setFileext(fext);
                    pix.setTimelength("0");
                    pix.setResolution("0");
                    pix.setOrderindex(i+1);
                    pix.setCreatedate(DateTime.getDate());
                    pix.setFilmid(model.getFilmid());
                    pix.setUnitid(unitid);
                    pix.setUpdateflag("0");
                    pix.setUpdatetime(DateTime.getDate());
                    pix.setMd5code(paths[1]);
                    vfpm.addVwhFilmPix(pix);
                    
                    //开始转码
                    ConvertFile.convertVod(pix, "add", 0);
                    vfpm.updateVwhFilmPix(pix);
                }
            }
            
            //课程微课
            EduCourseFilm courseFilm = new EduCourseFilm();
            courseFilm.setFilmid(model.getFilmid());
            courseFilm.setTitle(model.getTitle());
            courseFilm.setQrcodeno(qrcodeno);
            courseFilm.setCoursecolumnid(Integer.valueOf(columnid));
            courseFilm.setCourseid(Integer.valueOf(courseid));
            courseFilm.setOrderindex(Integer.valueOf(orderindex));
            courseFilm.setStatus("1");
            courseFilm.setCreatedate(DateTime.getDate());
            courseFilm.setPrice(Float.valueOf(price));
            courseFilm.setDiscount(Float.valueOf(discount));
            courseFilm.setSellprice(Float.valueOf(sellprice));
            courseFilm.setHits(0);
            courseFilm.setSellcount(0);
            filmManager.addEduCourseFilm(courseFilm);
            
            //生成课程视频二维码
            TwoCodeUtil.getBookFilmTwoCodePath(qrcodeno, httpServletRequest);
            
          //清空缓存
            CacheUtil.deleteObject("EduCourseFilm_Map_" + courseid);
            CacheUtil.deleteObject("EduCourseFilm_List_" + courseid);
        }catch (Exception e){
            e.printStackTrace();
        }

        return filmList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        
          //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("columnid", columnid);
        try {
            EduCourseFilm model = manager.getEduCourseFilm(objid);
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("act", "updateSave");
            Integer filmid = model.getFilmid();
            VwhFilmInfo filmInfo = filmInfoManager.getVwhFilmInfo(filmid);
            httpServletRequest.setAttribute("filminfo", filmInfo);
        }catch (Exception e){
            e.printStackTrace();
        }

        saveToken(httpServletRequest);
        return actionMapping.findForward("filmupdateedit");
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
        EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
        if (isTokenValid(httpServletRequest, true)) {
            try {
                EduCourseFilm model = form.getEduCourseFilm();
                manager.updateEduCourseFilm(model);
                
                HttpSession session = httpServletRequest.getSession();
                SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
                VwhFilmInfo vwhFilmInfo = form.getVwhFilmInfo();
                EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
                String gradeid = "1";
                EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
                vwhFilmInfo.setEduGradeInfo(egi);
                vwhFilmInfo.setUpdatetime(DateTime.getDate());
                vwhFilmInfo.setSysUserInfo(sysUserInfo);
                filmInfoManager.updateVwhFilmInfo(vwhFilmInfo);
                
                addLog(httpServletRequest,"修改了一个课程视频【" + model.getTitle() + "】");
                
                //生成课程视频二维码
                TwoCodeUtil.getBookFilmTwoCodePath(model.getQrcodeno(), httpServletRequest);
                
              //清空缓存
                CacheUtil.deleteObject("EduCourseFilm_Map_" + model.getCourseid());
                CacheUtil.deleteObject("EduCourseFilm_List_" + model.getCourseid());
            }catch (Exception e){
            }
        }
        httpServletRequest.setAttribute("reloadtree", "1");
        return filmList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");

        EduCourseFilm model = null;
        for (int i = 0; i < checkids.length; i++) {
            model = manager.getEduCourseFilm(checkids[i]);
          //清空缓存
            CacheUtil.deleteObject("EduCourseFilm_Map_" + model.getCourseid());
            CacheUtil.deleteObject("EduCourseFilm_List_" + model.getCourseid());
            manager.delEduCourseFilm(model);
            addLog(httpServletRequest,"删除了一个课程视频【" + model.getTitle() + "】");
        }
        return filmList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }   
    /**
     * 微课视频列表
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-21 上午10:29:48 
    * @lastModified ; 2017-2-21 上午10:29:48 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward videoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
        String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
        String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
        String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "filmid", "=", filmid);
        if(!"".equals(name)){
            SearchCondition.addCondition(condition, "name", "like", "%" + name + "%");  
        }
        if(!"".equals(convertstatus)){
            SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);   
        }
        
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "orderindex asc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
        PageList page = manager.getPageVwhFilmPixs(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
        
        httpServletRequest.setAttribute("pagelist", page);
        httpServletRequest.setAttribute("filmid", filmid);
        httpServletRequest.setAttribute("name", name);
        httpServletRequest.setAttribute("convertstatus", convertstatus);
        httpServletRequest.setAttribute("flag", flag);
        httpServletRequest.setAttribute("mark", mark);
    	
        return actionMapping.findForward("videolist");
    }
    
    /**
     *增加前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward beforeAddVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
        String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
        String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
        String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);
        httpServletRequest.setAttribute("filmid", filmid);
        httpServletRequest.setAttribute("name", name);
        httpServletRequest.setAttribute("convertstatus", convertstatus);
        httpServletRequest.setAttribute("flag", flag);
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        VwhFilmPix model = new VwhFilmPix();
        httpServletRequest.setAttribute("model", model);
        httpServletRequest.setAttribute("act", "addSaveVideo");
        
        saveToken(httpServletRequest);
        return actionMapping.findForward("addvideo");
    }

    /**
     *增加时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward addSaveVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (isTokenValid(httpServletRequest, true)) {
            VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
            try {
                String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
                HttpSession session = httpServletRequest.getSession();
                Integer unitid = (Integer) session.getAttribute("s_unitid");
                
                String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
                String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
                httpServletRequest.setAttribute("mark", mark);
                //视频影片
                String[] filename = httpServletRequest.getParameterValues("filename");
                String[] filepath = httpServletRequest.getParameterValues("filepath");
                String[] filesize = httpServletRequest.getParameterValues("filesize");
                //视频截屏参数
                String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
                String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
                String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
                
                if(filename != null && filename.length > 0){
                    String fext = null;
                    String fname = null;
                    VwhFilmPix pix = null;
                    String[] paths = null;
                    for(int i=0, size=filename.length; i<size; i++){
                        if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
                            continue;
                        }
                        if(filepath[i].indexOf("exist_") != -1){
                            //可以重复上传，只给用户提示，除非用户自己删除
                            filepath[i] = filepath[i].substring(6);
                        }
                        paths = filepath[i].split(";");
                        fext = FileUtil.getFileExt(filename[i]).toLowerCase();
                        fname = filename[i].substring(0,filename[i].lastIndexOf("."));
                        
                        pix = new VwhFilmPix();
                        pix.setName(fname);
                        pix.setSrcpath(paths[0]);
                        //转码后的路径统一通过接口修改
                        //pix.setFlvpath(filepath[i].substring(0,filepath[i].lastIndexOf(".")) + ".mp4");
                        pix.setImgpath("convert.png");
                        pix.setConvertstatus("0");
                        pix.setImgwidth(Integer.valueOf(imgwidth));
                        pix.setImgheight(Integer.valueOf(imgheight));
                        pix.setSecond(second);
                        pix.setFilesize(Long.valueOf(filesize[i]));
                        pix.setFileext(fext);
                        pix.setTimelength("0");
                        pix.setResolution("0");
                        pix.setOrderindex(i+1);
                        pix.setCreatedate(DateTime.getDate());
                        pix.setFilmid(Integer.valueOf(filmid));
                        pix.setUnitid(unitid);
                        pix.setUpdateflag("1");
                        pix.setUpdatetime(DateTime.getDate());
                        pix.setMd5code(paths[1]);
                        manager.addVwhFilmPix(pix);
                        addLog(httpServletRequest,"增加了一个微课程视频【" + pix.getName() + "】");
                        
                        //开始转码
                        ConvertFile.convertVod(pix, "add", 0);
                        manager.updateVwhFilmPix(pix);
                    }
                    
                    if(!"1".equals(flag)){
                        //重新上传影片，视频需要重新审核
                        VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
                        VwhFilmInfo vfi = vfim.getVwhFilmInfo(filmid);
                        vfi.setStatus("0");
                        vfi.setUpdatetime(DateTime.getDate());
                        vfim.updateVwhFilmInfo(vfi);
                    }
                }
            }catch (Exception e){
            }
        }

        return videoList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    /**
     *修改前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward beforeUpdateVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
        String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
        String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
        String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);
        httpServletRequest.setAttribute("filmid", filmid);
        httpServletRequest.setAttribute("name", name);
        httpServletRequest.setAttribute("convertstatus", convertstatus);
        httpServletRequest.setAttribute("flag", flag);
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        try {
            VwhFilmPix model = manager.getVwhFilmPix(objid);
            httpServletRequest.setAttribute("act", "updateSaveVideo");
            httpServletRequest.setAttribute("model", model);
        }catch (Exception e){
        }

        saveToken(httpServletRequest);
        return actionMapping.findForward("editvideo");
    }

    /**
     *修改时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward updateSaveVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFilmActionForm form = (EduCourseFilmActionForm)actionForm;
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);
        if (isTokenValid(httpServletRequest, true)) {
            try {
                String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
                VwhFilmPix model = form.getVwhFilmPix();
                VwhFilmPix vfp = manager.getVwhFilmPix(model.getPixid());
                if("0".equals(vfp.getUpdateflag())){
                    vfp.setUpdateflag("2");//只修改了影片属性值
                    vfp.setUpdatetime(DateTime.getDate());
                }
                vfp.setImgpath(model.getImgpath());
                vfp.setName(model.getName());
                vfp.setOrderindex(model.getOrderindex());
                manager.updateVwhFilmPix(vfp);
                addLog(httpServletRequest,"修改了一个微课程视频【" + model.getName() + "】");
                
                if(!"1".equals(flag)){
                    //重新修改影片属性值，视频需要重新审核
                    VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
                    VwhFilmInfo vfi = vfim.getVwhFilmInfo(vfp.getFilmid());
                    vfi.setStatus("0");
                    vfi.setUpdatetime(DateTime.getDate());
                    vfim.updateVwhFilmInfo(vfi);
                }
            }catch (Exception e){
            }
        }

        return videoList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     *批量删除
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward delBatchRecordVideo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);

        VwhFilmPix model = null;
        for (int i = 0; i < checkids.length; i++) {
            model = manager.getVwhFilmPix(checkids[i]);
            //如果视频还没有转码成功，则需要告知转码服务器已删除此视频
            if(!"1".equals(model.getConvertstatus())){
                ConvertFile.convertVod(model, "delete", 0);
            }
            manager.delVwhFilmPix(model);
            addLog(httpServletRequest,"删除了一个微课程视频【" + model.getName() + "】");
        }
        return videoList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    /**
     *设置封面
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward setPhoto(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("filmid", mark);

        if(checkids != null && checkids.length == 1){
            VwhFilmPix model = manager.getVwhFilmPix(checkids[0]);
            VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
            vfi.setSketch(model.getImgpath());
            vfi.setSketchimg(vfi.getSketch());
            vfim.updateVwhFilmInfo(vfi);
            httpServletRequest.setAttribute("msg", "微课封面设置成功!");
        }
        return videoList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    /**
     *影片预览
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward preview(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
    	try {
			
        VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
        VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
        String pixid = Encode.nullToBlank(httpServletRequest.getParameter("pixid"));
        VwhFilmPix model = manager.getVwhFilmPix(pixid);
        VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
        httpServletRequest.setAttribute("model", model);

        StringBuffer vurl = new StringBuffer();
        StringBuffer vurl1 = new StringBuffer();
        if("swf".equals(model.getFileext().toLowerCase())){
            VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
            VwhComputerInfo computerInfo = vcim.getVwhComputerInfo(vfi.getComputerid());
            vurl.append("http://").append(computerInfo.getIp()).append(":").append(computerInfo.getPort()).append("/upload/").append(model.getSrcpath());
            httpServletRequest.setAttribute("playurl", DesUtil.encrypt(vurl.toString(), "cd4b635b494306cddf6a6e74a7b0b4d8"));
        }else {
        	String playurl=getUrl(model.getPixid().toString(), vfi.getComputerid().toString());
        	httpServletRequest.setAttribute("playurl", DesUtil.encrypt(playurl, "cd4b635b494306cddf6a6e74a7b0b4d8"));
        }
        httpServletRequest.setAttribute("vurl", vurl.toString());
        httpServletRequest.setAttribute("vurl1", vurl1.toString());
        
        return actionMapping.findForward("preview");
    	} catch (Exception e) {
    		e.printStackTrace();
		}
		return null;
    }
    
    public String  getUrl(String pixid,String computerid){
    	StringBuffer vurl = new StringBuffer();
			VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
			VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
			VwhFilmPix pix = vfpm.getVwhFilmPix(pixid);
			if(pix.getFlvpath()==null?false:pix.getFlvpath().startsWith("http://")){
				vurl.append(pix.getFlvpath());
			}else {
				VwhComputerInfo computer = vcim.getVwhComputerInfo(computerid);
				
				vurl.append("http://").append(computer.getIp());
				if(!"80".equals(computer.getPort())){
					vurl.append(":").append(computer.getPort());
				}
				vurl.append("/upload/").append(pix.getFlvpath());
			}
			if (pix.getFlvpath()==null) {
				return "";
			}
			return vurl.toString();
    }
    
    /**
     * 检测二维码编号是否存在
     */
    public ActionForward checkQrcodeno(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String qrcodeno = Encode.nullToBlank(httpServletRequest.getParameter("qrcodeno"));
        
        EduCourseFilmManager manager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "qrcodeno", "=", qrcodeno);
        
        List lst = manager.getEduCourseFilms(condition, "", 0);

        PrintWriter pw = null;
        try {
            pw = httpServletResponse.getWriter();
            if(lst != null && lst.size() > 0){
                pw.write("1");
            }
        } catch (IOException ex) {
        } finally {
            if (pw != null) {
                pw.close();
            }
            pw = null;
        }

        return null;
    }
    //===========================================================微课播放和购买统计========================================================
    /**
     * 跳转到主工作区
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward statisticalMain(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
            return actionMapping.findForward("statisticalmain");
    }
    /**
     * 树形选择器
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward statisticalTree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
            EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
          
            HttpSession session = httpServletRequest.getSession();
            Integer unitid = (Integer) session.getAttribute("s_unitid");
            //课程信息
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "status", "=", "1");
            SearchCondition.addCondition(condition, "unitid", "=", unitid);
            
            List lst = manager.getEduCourseInfos(condition, "courseno asc", 0);
            String tree = "";
            String url = "";
            EduCourseInfo cnc = null;
            String no = "";// 自身ID
            String text = "";// 树节点显示名称
            String hint = "";
            String target = "";
            for (int i = 0; i < lst.size(); i++) {
                cnc = (EduCourseInfo) lst.get(i);
                no = cnc.getCourseno().trim();// 自身ID
                text = cnc.getTitle();// 树节点显示名称
                  
                 
                url = "eduCourseFilmAction.do?method=statisticalList&courseid="+cnc.getCourseid();// onclick树节点后执行
                target = "rfrmright";// 页面在打开位置
                tree += "\n" + "tree" + ".nodes[\"0000_" + no + "\"]=\"";
                if (text != null && text.trim().trim().length() > 0) {
                    tree += "text:" + text + ";";
                }
                if (hint != null && hint.trim().length() > 0) {
                    tree += "hint:" + hint + ";";
                }
                tree += "url:" + url + ";";
                tree += "target:" + target + ";";
                tree += "\";";
            }
            String rooturl = "/sysLayoutAction.do?method=welcome";
            httpServletRequest.setAttribute("treenode", tree);
            httpServletRequest.setAttribute("rooturl", rooturl);
            httpServletRequest.setAttribute("modulename", "课程微课");
            return actionMapping.findForward("statisticaltree");
    }
    
    public ActionForward statisticalList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
           VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
           
           String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
           httpServletRequest.setAttribute("courseid", courseid);
           
           String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
           httpServletRequest.setAttribute("title", title);
           
           try {
               //课程视频
               PageUtil pageUtil = new PageUtil(httpServletRequest);
               List<SearchModel> condition = new ArrayList<SearchModel>();
               SearchCondition.addCondition(condition, "courseid", "=", courseid);
               SearchCondition.addCondition(condition, "status", "=", "1");
               if(!"".equals(title)){
                   SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
               }
               
               PageList pageList = filmManager.getPageEduCourseFilms(condition, "orderindex desc", pageUtil.getStartCount(), pageUtil.getPageSize());
               httpServletRequest.setAttribute("pagelist", pageList);
           }catch (Exception e){
               e.printStackTrace();
           }
           return actionMapping.findForward("statisticallist");
    }
}