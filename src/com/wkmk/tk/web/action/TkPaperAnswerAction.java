package com.wkmk.tk.web.action;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.util.action.BaseAction;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.bo.TkPaperAnswer;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkClassInfoManager;
import com.wkmk.tk.service.TkPaperAnswerManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.web.form.TkPaperAnswerActionForm;
import com.wkmk.util.common.ExportWordHelper;

/**
 * <p>
 * Description: 试卷作答信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
public class TkPaperAnswerAction extends BaseAction {

	/**
	 * 列表显示
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
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkPaperAnswers(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
	}

	/**
	 * 增加前
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
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswer model = new TkPaperAnswer();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 增加时保存
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
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswerActionForm form = (TkPaperAnswerActionForm) actionForm;
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperAnswer model = form.getTkPaperAnswer();
				manager.addTkPaperAnswer(model);
				addLog(httpServletRequest, "增加了一个试卷作答信息");
			} catch (Exception e) {
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 修改前
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkPaperAnswer model = manager.getTkPaperAnswer(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswerActionForm form = (TkPaperAnswerActionForm) actionForm;
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkPaperAnswer model = form.getTkPaperAnswer();
				manager.updateTkPaperAnswer(model);
				addLog(httpServletRequest, "修改了一个试卷作答信息");
			} catch (Exception e) {
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
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
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		for (int i = 0; i < checkids.length; i++) {
			manager.delTkPaperAnswer(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
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
	public ActionForward getStudentsByClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));

		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		List<SysUserInfo> users = manager.getStudentsByClass(classid);
		List<TkPaperContent> paperContents = manager.getPaperContent(paperid);

		List userPaperInfo = new ArrayList();
		for (SysUserInfo user : users) {
			List userPaperAnswer = new ArrayList();
			for (TkPaperContent pc : paperContents) {
				Map m = new HashMap();
				String isright = manager.getPaperAnswer(classid, user.getUserid(), bookcontentid, pc.getContentid());
				m.put("isright", isright);
				userPaperAnswer.add(m);
			}
			Map m = new HashMap();
			m.put("userid", user.getUserid());
			m.put("username", user.getUsername());
			m.put("userPaperAnswer", userPaperAnswer);
			userPaperInfo.add(m);
		}
		httpServletRequest.setAttribute("paperContents", paperContents);
		httpServletRequest.setAttribute("userPaperInfo", userPaperInfo);
		httpServletRequest.setAttribute("paperid", paperid);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		return actionMapping.findForward("jobanalysis");
	}

	/**
	 * 跳转到工作区
	 */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("classid", httpServletRequest.getParameter("classid"));
		httpServletRequest.setAttribute("bookcontentid", httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("paperid", httpServletRequest.getParameter("paperid"));
		return actionMapping.findForward("main");
	}

	/**
	 * 用户作业完成明细
	 */
	public ActionForward getUserQuestionsInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		Integer userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
		List userQuestions = getUserQuestions(classid, bookcontentid, paperid, userid, state);
		httpServletRequest.setAttribute("userQuestions", userQuestions);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("paperid", paperid);
		httpServletRequest.setAttribute("state", state);
		httpServletRequest.setAttribute("userid", userid);
		return actionMapping.findForward("userquestions");
	}

	private List getUserQuestions(Integer classid, Integer bookcontentid, Integer paperid, Integer userid, String state) {

		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		List<TkQuestionsInfo> ques = manager.getQuestionsByPaper(paperid);
		List userQuestions = new ArrayList();
		for (TkQuestionsInfo q : ques) {
			List userAnswer = new ArrayList();
			List childAnswer = new ArrayList();
			if ("填空题".equals(q.getTkQuestionsType().getTypename())) {
				if (!"".equals(q.getScore()) && q.getScore() != null) {
					String[] s = q.getScore().split("【】");
					BigDecimal score = new BigDecimal("0");
					for (String str : s) {
						score = score.add(new BigDecimal(str));
					}
					DecimalFormat fmt = new DecimalFormat("###.##");
					q.setScore(fmt.format(score));
				}
			}
			List<TkQuestionsInfo> childs = manager.getChildQuestions(q.getQuestionid());
			List childQuesitons = new ArrayList();
			Map m = new HashMap();
			for (TkQuestionsInfo que : childs) {
				if ("填空题".equals(que.getTkQuestionsType().getTypename())) {
					if (!"".equals(que.getScore()) && que.getScore() != null) {
						String[] s = que.getScore().split("【】");
						BigDecimal score = new BigDecimal("0");
						for (String str : s) {
							score = score.add(new BigDecimal(str));
						}
						DecimalFormat fmt = new DecimalFormat("###.##");
						que.setScore(fmt.format(score));
					}
				}
				Map map = new HashMap();
				childAnswer = manager.getChildAnswer(classid, userid, bookcontentid, que.getQuestionid());
				map.put("childAnswer", childAnswer);
				map.put("que", que);
				childQuesitons.add(map);
			}
			if (childs.size() <= 0) {
				userAnswer = manager.getQuestionsAnswer(classid, userid, bookcontentid, q.getQuestionid());
				m.put("userAnswer", userAnswer);
			}
			m.put("question", q);
			m.put("childQuesitons", childQuesitons);
			if (state.equals("1")) {
				userQuestions.add(m);
			} else {
				int errorchilde = 0;
				int usererror = 0;
				for (int i = 0; i < userAnswer.size(); i++) {
					Map map = (Map) userAnswer.get(i);
					if (map.get("isright").toString().equals("0")) {
						usererror += 1;
					}
				}
				for (int i = 0; i < childAnswer.size(); i++) {
					Map map = (Map) childAnswer.get(i);
					if (map.get("isright").toString().equals("0")) {
						errorchilde += 1;
					}
				}
				if (errorchilde != 0 || usererror != 0) {
					userQuestions.add(m);
				}
			}
		}
		return userQuestions;
	}

	/**
	 * 作业完成情况统计
	 */

	public ActionForward getStatQuestions(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		Integer contentid = Integer.parseInt(httpServletRequest.getParameter("contentid"));
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		Map jobStat = manager.getStatQuestions(classid, bookcontentid, contentid, state);
		if (jobStat.get("questionid") != null) {
			List<TkQuestionsInfo> childQuestions = manager.getChildQuestions(Integer.parseInt(jobStat.get("questionid").toString()));
			httpServletRequest.setAttribute("childQuestions", childQuestions);
		}
		httpServletRequest.setAttribute("jobStat", jobStat);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("state", state);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("contentid", contentid);
		return actionMapping.findForward("jobstat");
	}

	/**
	 * 作业分析 饼状图统计试题答错次数
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward getPiechartStat(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		TkClassInfo clazz = ((TkClassInfoManager) getBean("tkClassInfoManager")).getTkClassInfo(classid);
		Integer bookid = ((TkBookContentManager) getBean("tkBookContentManager")).getTkBookContent(bookcontentid).getBookid();
		Integer subjectid = ((TkBookInfoManager) getBean("tkBookInfoManager")).getTkBookInfo(bookid).getSubjectid();
		List list = manager.getPiechartStat(classid, subjectid, bookcontentid, paperid);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object object : list) {
			Map m = (Map) object;
			dataset.setValue(m.get("typename").toString(), Integer.parseInt(m.get("errorCount").toString()));
		}
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart("错题统计图", dataset, true, true, false);
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setLabelFont(new Font("宋书", Font.PLAIN, 15));
		// 设置饼图是圆的（true），还是椭圆的（false）；默认为true
		pieplot.setCircular(true);
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);
		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setLabelGap(0.02D);

		String filename;
		try {
			filename = ServletUtilities.saveChartAsJPEG(chart, 800, 500, httpServletRequest.getSession());
			String graphURL = httpServletRequest.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
			UUID uuid = UUID.randomUUID();
			String fileUrl = httpServletRequest.getRealPath("/") + "/upload/" + clazz.getUnitid() + "/" + clazz.getClassid() + "/statistic/";
			if (!FileUtil.isExistDir(fileUrl)) {
				FileUtil.creatDir(fileUrl);
			}
			String chartname = fileUrl + uuid + ".jpeg";
			FileOutputStream fos = new FileOutputStream(chartname);
			ChartUtilities.writeChartAsJPEG(fos, chart, 800, 500);
			fos.close();
			httpServletRequest.setAttribute("chartname", chartname);
			httpServletRequest.setAttribute("graphURL", graphURL);
			httpServletRequest.setAttribute("classid", classid);
			httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("piechart");
	}

	/**
	 * 作业分析 饼状图统计试题首次作答答错次数
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward getFirstPiechartStat(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		TkClassInfo clazz = ((TkClassInfoManager) getBean("tkClassInfoManager")).getTkClassInfo(classid);

		Integer bookid = ((TkBookContentManager) getBean("tkBookContentManager")).getTkBookContent(bookcontentid).getBookid();
		Integer subjectid = ((TkBookInfoManager) getBean("tkBookInfoManager")).getTkBookInfo(bookid).getSubjectid();
		List list = manager.getFirstPiechartStat(classid, subjectid, bookcontentid, paperid);
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Object object : list) {
			Map m = (Map) object;
			dataset.setValue(m.get("typename").toString(), Integer.parseInt(m.get("errorCount").toString()));
		}
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);
		// 通过工厂类生成JFreeChart对象
		JFreeChart chart = ChartFactory.createPieChart("首次作答错题统计图", dataset, true, true, false);
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setLabelFont(new Font("宋书", Font.PLAIN, 15));
		// 设置饼图是圆的（true），还是椭圆的（false）；默认为true
		pieplot.setCircular(true);
		StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})", NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
		pieplot.setLabelGenerator(standarPieIG);
		// 没有数据的时候显示的内容
		pieplot.setNoDataMessage("无数据显示");
		pieplot.setLabelGap(0.02D);
		String filename;
		try {
			filename = ServletUtilities.saveChartAsJPEG(chart, 800, 500, httpServletRequest.getSession());
			String graphURL = httpServletRequest.getContextPath() + "/servlet/DisplayChart?filename=" + filename;
			UUID uuid = UUID.randomUUID();
			String fileUrl = httpServletRequest.getRealPath("/") + "/upload/" + clazz.getUnitid() + "/" + clazz.getClassid() + "/statistic/";
			if (!FileUtil.isExistDir(fileUrl)) {
				FileUtil.creatDir(fileUrl);
			}
			String chartname = fileUrl + uuid + ".jpeg";
			FileOutputStream fos = new FileOutputStream(chartname);
			ChartUtilities.writeChartAsJPEG(fos, chart, 800, 500);
			fos.close();
			httpServletRequest.setAttribute("chartname", chartname);
			httpServletRequest.setAttribute("graphURL", graphURL);
			httpServletRequest.setAttribute("classid", classid);
			httpServletRequest.setAttribute("first", 1);
			httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("piechart");
	}

	/**
	 * 下载jfreechart图
	 */
	public void dowload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
		String name = httpServletRequest.getParameter("filename");
		String first = httpServletRequest.getParameter("first");
		String classid = Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String classname = "";
		if (!"".equals(classid)) {
			classname = ((TkClassInfoManager) getBean("tkClassInfoManager")).getTkClassInfo(classid).getClassname();
		}
		String bcname = "";
		if (!"".equals(bookcontentid)) {
			bcname = ((TkBookContentManager) getBean("tkBookContentManager")).getTkBookContent(bookcontentid).getTitle();
			bcname = bcname.replace(" ", "");
		}
		String filename = "";
		if (!"".equals(name) && !"".equals(classname)) {
			if (first == null || "".equals(first)) {
				filename = classname + "_" + bcname + "_错题统计图.jpeg";
			} else {
				filename = classname + "_" + bcname + "_首次作答错题统计图.jpeg";
			}
			httpServletResponse.setContentType("application/x-download");
			httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			OutputStream output = null;
			FileInputStream fis = null;
			try {
				output = httpServletResponse.getOutputStream();
				fis = new FileInputStream(name);
				byte[] buffer = new byte[1024];
				int i = 0;
				while ((i = fis.read(buffer)) > 0) {
					output.write(buffer, 0, i);
				}
				output.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(fis!=null){
					fis.close();					
				}
				if(output!=null){
					output.close();					
				}
			}
		}
	}

	public void downloadUserBookContent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		Integer userid = Integer.parseInt(httpServletRequest.getParameter("userid"));
		String username = ((SysUserInfoManager) getBean("sysUserInfoManager")).getSysUserInfo(userid).getUsername();
		String filename = username + "_作业详情.doc";
		List userQuestions = getUserQuestions(classid, bookcontentid, paperid, userid, state);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("userQuestions", userQuestions);
		ExportWordHelper.toPreview(httpServletRequest, "student_job.ftl", dataMap);
		try {
			File previewFile = new File(httpServletRequest.getSession().getServletContext().getRealPath(ExportWordHelper.PREVIEW_DOC));
			InputStream is = new FileInputStream(previewFile);
			httpServletResponse.reset();
			httpServletResponse.setContentType("application/vnd.ms-word;charset=UTF-8");
			httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) > 0) {
				httpServletResponse.getOutputStream().write(b, 0, len);
			}
			is.close();
			httpServletResponse.getOutputStream().flush();
			httpServletResponse.getOutputStream().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ActionForward downloadJobStat(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		Integer contentid = Integer.parseInt(httpServletRequest.getParameter("contentid"));
		String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		Map jobStat = manager.getStatQuestions(classid, bookcontentid, contentid, state);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String questionno = "";
		if (jobStat.get("questionid") != null) {
			List<TkQuestionsInfo> childQuestions = manager.getChildQuestions(Integer.parseInt(jobStat.get("questionid").toString()));
			dataMap.put("childQuestions", childQuestions);
			questionno = ((TkQuestionsInfoManager) getBean("tkQuestionsInfoManager")).getTkQuestionsInfo(jobStat.get("questionid").toString()).getQuestionno();
			String filename = "试题" + questionno + "统计.doc";
			dataMap.put("jobStat", jobStat);
			ExportWordHelper.toPreview(httpServletRequest, "question_stat.ftl", dataMap);
			try {
				File previewFile = new File(httpServletRequest.getSession().getServletContext().getRealPath(ExportWordHelper.PREVIEW_DOC));
				InputStream is = new FileInputStream(previewFile);
				httpServletResponse.reset();
				httpServletResponse.setContentType("application/vnd.ms-word;charset=UTF-8");
				httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) > 0) {
					httpServletResponse.getOutputStream().write(b, 0, len);
				}
				is.close();
				httpServletResponse.getOutputStream().flush();
				httpServletResponse.getOutputStream().close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return getStatQuestions(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}
		return null;
	}

	/**
	 * 错题统计（按每个题统计）
	 * */
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward getErrorStatistics(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		Integer classid = Integer.parseInt(httpServletRequest.getParameter("classid"));
		Integer bookcontentid = Integer.parseInt(httpServletRequest.getParameter("bookcontentid"));
		Integer paperid = Integer.parseInt(httpServletRequest.getParameter("paperid"));
		TkPaperContentManager tpcManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		TkPaperAnswerManager answerManager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		List contentids = tpcManager.getContentids(paperid);
		List list = new ArrayList();
		DecimalFormat df = new DecimalFormat("###.00");
		for (int i = 0; i < contentids.size(); i++) {
			Map map = new HashMap();
			int contentid = (Integer) contentids.get(i);
			int sumcounts = answerManager.getNumberQuestions(contentid, bookcontentid, classid);
			int errorcount = answerManager.getErrorNumberQuestions(contentid, bookcontentid, classid);
			int tent = i + 1;
			map.put("title", "第" + tent + "道题");
			if (sumcounts != 0) {
				map.put("errorpeople", errorcount);
				if (errorcount == 0) {
					map.put("result", 0.00);
				} else {
					map.put("result", df.format(Double.parseDouble(String.valueOf(errorcount)) / Double.valueOf(String.valueOf(sumcounts)) * 100));
				}
			} else {
				map.put("errorpeople", 0);
				map.put("result", 0.00);
			}
			list.add(map);
		}
		if (list.size() > 0) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				dataset.addValue(Double.parseDouble(map.get("result").toString()), "错题统计", map.get("title").toString() + "(错误个数：" + map.get("errorpeople").toString() + ",百分比："
						+ map.get("result").toString() + "%)");
				count = i;
			}
			// 生成jfree图
			JFreeChart chart = ChartFactory.createLineChart("错题统计", "题号", "百分比", dataset, PlotOrientation.VERTICAL, true, false, false);
			chart.getTitle().setFont(new Font("黑体 ", Font.BOLD, 18));// 设置图表标题字体样式
			Font font = new Font("黑体", Font.PLAIN, 14);
			chart.getCategoryPlot().getDomainAxis().setLabelFont(font);// 设置图表横坐标轴标题字体样式
			chart.getCategoryPlot().getRangeAxis().setLabelFont(font);// 设置图表纵坐标轴标题字体样式
			chart.getCategoryPlot().getDomainAxis().setTickLabelFont(font);// 设置图表横坐标轴目录字体样式
			chart.getLegend().setItemFont(font);// 设置图示字体样式
			chart.getCategoryPlot();
			// 设置x轴
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 11)); // 设置横轴字体
			domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 11));// 设置坐标轴标尺值字体
			domainAxis.setLowerMargin(0.01);// 左边距 边框距离
			domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
			domainAxis.setMaximumCategoryLabelLines(10);
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴
																					// lable
																					// 的位置
																					// 横轴上的
																					// Lable
																					// 45度倾斜
																					// DOWN_45

			plot.setDomainGridlinesVisible(true);
			plot.setRangeGridlinesVisible(true);
			java.util.UUID uuid = java.util.UUID.randomUUID();

			TkClassInfoManager tcim = (TkClassInfoManager) getBean("tkClassInfoManager");
			TkClassInfo tci = tcim.getTkClassInfo(classid);
			String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
			String chartpath = "/upload/" + tci.getUnitid() + "/class/" + tci.getClassid() + "/statistic/";
			;
			if (!FileUtil.isExistDir(realpath + chartpath)) {
				FileUtil.creatDir(realpath + chartpath);
			}
			String chartname = chartpath + uuid + ".jpeg";
			try {
				FileOutputStream fos = new FileOutputStream(realpath + chartname);
				ChartUtilities.writeChartAsJPEG(fos, chart, count * 150, 520);
				fos.close();
				httpServletRequest.setAttribute("charname", chartname);
				httpServletRequest.setAttribute("classid", classid);
				httpServletRequest.setAttribute("bookcontentid", bookcontentid);
				httpServletRequest.setAttribute("filename", realpath + chartname);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			httpServletRequest.setAttribute("errortext", "该错题统计没有数据");
		}
		return actionMapping.findForward("error_questions");
	}
}