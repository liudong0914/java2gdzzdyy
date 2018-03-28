package com.wkmk.util.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.CopyFile;
import com.util.image.ImageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.util.common.TwoCodeUtil;

public class AnalysisXmlAction extends BaseAction {
	// 默认给出关联用户和单位
	private Integer userid = 2;
	private Integer unitid = 1;
	private String username = "系统管理员";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		TkBookInfoManager bookManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		TkPaperInfoManager paperinfoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		try {
			// 读取配置文件
			String xmlpath = request.getSession().getServletContext().getRealPath("/") + "/upload/xml.txt";
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(xmlpath), "gb2312"));
			String urlcontent = input.readLine();
			String urls[] = urlcontent.split(";");
			String mainXmlUlr = "";// mainxml的路径
			String imageUrl = "";// 图片image存放路径
			String targetUrl = "";// 目标路径
			String bookid = "";
			int sonquestion = 0;// 子题个数;
			int sonindex = 0;// 字体下标
			int avgscore = 0;
			int remscore = 0;
			for (int i = 0; i < urls.length; i++) {
				if (i == 0) {
					mainXmlUlr = urls[i];
				} else if (i == 1) {
					imageUrl = urls[i];
				} else if (i == 2) {
					targetUrl = urls[i];
				} else if (i == 3) {
					bookid = urls[i];
				}
			}
			TkBookInfo bookinfo = bookManager.getTkBookInfo(bookid);
			int beign = targetUrl.indexOf("/upload");
			String simageurl = targetUrl.substring(beign);
			CopyFile.copy(imageUrl, targetUrl);
			// 解析xml
			File file = new File(mainXmlUlr);
			SAXReader saxreader = new SAXReader();
			FileInputStream in = new FileInputStream(file);
			Document document = saxreader.read(in);
			Element root = document.getRootElement();
			// 解析bookcontent
			List<TkBookContent> bookcontent = new ArrayList<TkBookContent>();
			List<TkPaperInfo> paperinfos = new ArrayList<TkPaperInfo>();
			Element toc = root.element("toc");
			List<Element> tocdivs = toc.selectNodes("tocdiv");
			for (int i = 0; i < tocdivs.size(); i++) {
				TkBookContent parentbookcontent = new TkBookContent();
				Element tocdiv = tocdivs.get(i);
				String title = tocdiv.elementText("title");
				String contentno = bookcontentManager.getMaxContentno(bookid);
				if ("".equals(contentno)) {
					parentbookcontent.setContentno("00000100");
				} else {
					int temp = Integer.parseInt(contentno);
					temp += 1;
					int length = 8 - String.valueOf(temp).length();
					String nowcontento = String.valueOf(temp);
					for (int j = 0; j < length; j++) {
						nowcontento = "0" + nowcontento;
					}
					parentbookcontent.setContentno(nowcontento);
				}
				parentbookcontent.setBookid(Integer.parseInt(bookid));
				parentbookcontent.setPaperid(0);
				parentbookcontent.setTitle(title);
				parentbookcontent.setParentno("0000");
				parentbookcontent.setBeforeclass("");
				parentbookcontent.setTeachingcase("");
				bookcontentManager.addTkBookContent(parentbookcontent);
				parentbookcontent.setBeforeclasstwocode(TwoCodeUtil.getbeforeClassTwoCodePath(parentbookcontent, request));
				parentbookcontent.setTeachingcasetwocode(TwoCodeUtil.getteachingCaseTwoCodePath(parentbookcontent, request));
				bookcontentManager.updateTkBookContent(parentbookcontent);
				List<Element> sontocdivs = tocdiv.selectNodes("tocdiv");
				for (int k = 0; k < sontocdivs.size(); k++) {
					Element sontocdiv = sontocdivs.get(k);
					TkBookContent sonbookcontent = new TkBookContent();
					String sontitle = sontocdiv.elementText("title");
					sonbookcontent.setTitle(sontitle);
					String temp = String.valueOf(k + 1);
					int length = temp.length();
					for (int m = 0; m < 4 - length; m++) {
						temp = "0" + temp;
					}
					sonbookcontent.setBeforeclass("");
					sonbookcontent.setTeachingcase("");
					sonbookcontent.setContentno(parentbookcontent.getContentno() + temp);
					sonbookcontent.setParentno(parentbookcontent.getContentno());
					sonbookcontent.setBookid(Integer.parseInt(bookid));
					sonbookcontent.setPaperid(0);
					sonbookcontent.setFlags(sontocdiv.element("tocentry").attributeValue("href"));
					bookcontentManager.addTkBookContent(sonbookcontent);
					sonbookcontent.setBeforeclasstwocode(TwoCodeUtil.getbeforeClassTwoCodePath(sonbookcontent, request));
					sonbookcontent.setTeachingcasetwocode(TwoCodeUtil.getteachingCaseTwoCodePath(sonbookcontent, request));
					bookcontentManager.updateTkBookContent(sonbookcontent);
					bookcontent.add(sonbookcontent);
				}
			}
			// 新建试卷并修改bookcontent的paperid
			for (int i = 0; i < bookcontent.size(); i++) {
				TkPaperInfo papermodel = new TkPaperInfo();
				TkBookContent bookcontenmodel = bookcontent.get(i);
				papermodel.setTitle(bookcontenmodel.getTitle());
				papermodel.setPapertype("1");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				papermodel.setCreatedate(sdf.format(new Date()));
				papermodel.setDescript("");
				papermodel.setStatus("1");
				papermodel.setUserid(2);
				papermodel.setGradeid(bookinfo.getGradeid());
				papermodel.setSubjectid(bookinfo.getSubjectid());
				papermodel.setUnitid(1);
				papermodel.setFlags(bookcontenmodel.getFlags());
				paperinfoManager.addTkPaperInfo(papermodel);
				bookcontenmodel.setPaperid(papermodel.getPaperid());
				bookcontentManager.updateTkBookContent(bookcontenmodel);
				paperinfos.add(papermodel);
			}
			// 解析题目
			List<Element> charpters = root.selectNodes("chapter");
			for (int z = 0; z < charpters.size(); z++) {
				List<Element> sect1s = charpters.get(z).selectNodes("sect1");
				for (int h = 0; h < sect1s.size(); h++) {
					Element sect1 = sect1s.get(h);
					String falgs = sect1.attributeValue("id");
					List<Element> elist = sect1.selectNodes("test");
					for (int i = 0; i < elist.size(); i++) {
						TkQuestionsInfo model = new TkQuestionsInfo();
						String begin = "";
						String option_count = "0";
						String subjecttype = "";
						String subjectid = "";
						String questiontype = "";//
						String question_content = "";// 题干
						String question_title = "";// 没有图片的标题
						String question_answercontent = "";// 答案解析
						String question_difficult = "";// 题目难度
						String question_optioncount = "";// 选项个数
						String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
						String gradeid = "";
						int parentid = 0;
						int pointcount = 0;// 填空数
						Element text = (Element) elist.get(i);
						subjecttype = text.elementText("type");
						String testnum = text.elementText("test_num");
						Element test_content = text.element("test_content");
						for (Iterator itest_content = test_content.elementIterator("para"); itest_content.hasNext();) {
							String image = "";
							String lastcontent = "";
							Element para = (Element) itest_content.next();
							String content = para.asXML();
							content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
							if (content.indexOf("<mediaobject>") > -1 && "".equals(begin)) {
								begin = content.substring(0, content.indexOf("<mediaobject>")).trim();
							} else if (content.indexOf("<inlinemediaobject>") > -1 && "".equals(begin)) {
								begin = content.substring(0, content.indexOf("<inlinemediaobject>")).trim();
							}
							for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
								image = "";
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
								}
								content = content.replace(imagedata.asXML(), image);
								lastcontent = content;
							}
							for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
								image = "";
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
									content = content.replace(imagedata.asXML(), image);
									lastcontent = content;
								}
							}
							if (!"".equals(image)) {
								question_content += lastcontent;
								question_title += begin;
							} else {
								question_content += content;
								question_title += content;
							}
						}
						if (!"".equals(text.elementText("option_count")) && null != text.elementText("option_count")) {
							option_count = text.elementText("option_count");
						}
						if (!"".equals(text.elementText("space_count")) && null != text.elementText("space_count")) {
							pointcount = Integer.parseInt(text.elementText("space_count"));
						} else {
							pointcount = 0;
						}
						if (!"".equals(text.elementText("difficulty")) && null != text.elementText("difficulty")) {
							question_difficult = text.elementText("difficulty");
						}
						if (!"".equals(text.elementText("answer_unicity")) && null != text.elementText("answer_unicity")) {
							String answer_unicity = text.elementText("answer_unicity");
							if ("A".equals(subjecttype) || "B".equals(subjecttype)) {
								model.setIsrightans("1");
							} else {
								if ("否".equals(answer_unicity)) {
									model.setIsrightans("0");
								} else if ("是".equals(answer_unicity)) {
									model.setIsrightans("1");
								}
							}
						} else {
							if ("A".equals(subjecttype) || "B".equals(subjecttype)) {
								model.setIsrightans("1");
							} else {
								model.setIsrightans("0");
							}
						}
						if (null != text.element("answer")) {
							Element answer = text.element("answer");
							for (Iterator itest_content = answer.elementIterator("para"); itest_content.hasNext();) {
								String image = "";
								String lastcontent = "";
								Element para = (Element) itest_content.next();
								String content = para.asXML();
								content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
								for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
									Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
									int width = Integer.parseInt(imagedata.attributeValue("width"));
									int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
									image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
									thumbnail(imageUrl, targetUrl, height, image);
									if (!"".equals(image) || null != image) {
										image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
									}
									content = content.replace(imagedata.asXML(), image);
									lastcontent = content;
								}
								for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
									Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
									int width = Integer.parseInt(imagedata.attributeValue("width"));
									int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
									image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
									thumbnail(imageUrl, targetUrl, height, image);
									if (!"".equals(image) || null != image) {
										image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
									}
									content = content.replace(imagedata.asXML(), image);
									lastcontent = content;
								}
								if (!"".equals(image)) {
									model.setRightans(lastcontent);
								} else {
									model.setRightans(content);
								}
								if ("E".equals(subjecttype)) {
									model.setOption1(model.getRightans());
								}
							}
						}
						Element option_content = text.element("option_content");
						int count = 1;
						for (Iterator itest_content = option_content.elementIterator("para"); itest_content.hasNext();) {
							String image = "";
							String lastcontent = "";
							Element para = (Element) itest_content.next();
							String content = para.asXML();
							content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
							for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
								}
								content = content.replace(imagedata.asXML(), image);
								lastcontent = content;
							}
							for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
								}
								content = content.replace(imagedata.asXML(), image);
								lastcontent = content;
							}
							if (!"".equals(image)) {
								model.setOptionContent(count, lastcontent);
							} else {
								model.setOptionContent(count, content);
							}
							count += 1;
						}
						if (!"".equals(text.elementText("value")) && null != text.elementText("value")) {
							if ("1".equals(model.getIsrightans())) {
								if ("E".equals(subjecttype)) {
									String value = text.elementText("value");
									double sum = Double.parseDouble(value);
									double result = sum / pointcount;
									double yu = sum % pointcount;
									String sresult = "";
									for (int k = 0; k < pointcount; k++) {
										if (k == pointcount - 1) {
											sresult += (yu + result);
										} else {
											sresult += result + "【】";
										}
									}
									model.setScore(sresult);
								} else {
									model.setScore(text.elementText("value"));
								}
							} else {
								model.setScore(text.elementText("value"));
							}
						}
						Element answer_analysis = text.element("answer_analysis");
						for (Iterator itest_content = answer_analysis.elementIterator("para"); itest_content.hasNext();) {
							String lastcontent = "";
							String image = "";
							Element para = (Element) itest_content.next();
							String content = para.asXML();
							content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
							for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
								}
								content = content.replace(imagedata.asXML(), image);
								lastcontent = content;
							}
							for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
								Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
								int width = Integer.parseInt(imagedata.attributeValue("width"));
								int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
								image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
								thumbnail(imageUrl, targetUrl, height, image);
								if (!"".equals(image) || null != image) {
									image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
								}
								content = content.replace(imagedata.asXML(), image);
								lastcontent = content;
							}
							if (!"".equals(image)) {
								question_answercontent = lastcontent;
							} else {
								question_answercontent = content;
							}
						}
						if (!"".equals(text.elementText("area")) && null != text.elementText("area")) {
							model.setArea(text.elementText("area"));
						} else {
							model.setArea("全国");
						}
						if (!"".equals(text.elementText("year")) && null != text.elementText("year")) {
							model.setTheyear(text.elementText("year"));
						} else {
							model.setTheyear(DateTime.getDateYear());
						}
						if (!"".equals(text.elementText("subject")) && null != text.elementText("subject")) {
							subjectid = text.elementText("subject");
							model.setSubjectid(Integer.parseInt(text.elementText("subject")));
						}
						if (!"".equals(text.elementText("grade")) && null != text.elementText("grade")) {
							gradeid = text.elementText("grade");
						}
						if (!"".equals(text.elementText("source")) && null != text.elementText("source")) {
							model.setTag(text.elementText("source"));
							if (!"".equals(testnum) && null != testnum) {
								model.setTag(model.getTag() + "【第" + testnum + "题】");
							}
						}
						if (null != text.element("test_children")) {
							subjecttype = "M";
							List<Element> list = text.element("test_children").elements();
							sonquestion = list.size();
						}
						if (null != text.element("题号")) {
							ischildquestion = "1";
							sonindex += 1;
						}
						// 往数据库插入数据
						if ("A".equals(subjecttype)) {
							model.setQuestiontype("O");
							questiontype = "A";// 单选题
						} else if ("E".equals(subjecttype)) {
							model.setQuestiontype("S");
							questiontype = "E";
						} else if ("M".equals(subjecttype)) {
							questiontype = "M";
							model.setQuestiontype("S");
						} else if ("S".equals(subjecttype)) {
							questiontype = "S";
							model.setQuestiontype("S");
						} else if ("C".equals(subjecttype)) {
							questiontype = "C";
							model.setQuestiontype("O");
							if ("T".equals(model.getRightans())) {
								model.setRightans("1");
							} else if ("F".equals(model.getRightans())) {
								model.setRightans("0");
							}
						}
						// 查询题型
						TkQuestionsType typemodel = typeManager.getTkQuestionsType(questiontype, Integer.parseInt(subjectid));
						if (null == typemodel) {
							typemodel = new TkQuestionsType();
							int typeno = (manager.getMaxTypeno() + 1);
							if ("A".equals(subjecttype)) {
								typemodel.setTypename("单选题");
							} else if ("B".equals(subjecttype)) {
								typemodel.setTypename("多选题");
							} else if ("C".equals(subjecttype)) {
								typemodel.setTypename("判断题");
							} else if ("E".equals(subjecttype)) {
								typemodel.setTypename("填空题");
							} else if ("F".equals(subjecttype)) {
								typemodel.setTypename("英语完型填空题");
							} else if ("M".equals(subjecttype)) {
								typemodel.setTypename("复合题");
							} else if ("S".equals(subjecttype)) {
								typemodel.setTypename("其他主观题");
							}
							typemodel.setTypeno(String.valueOf(typeno));
							typemodel.setType(questiontype);
							typemodel.setSubjectid(Integer.parseInt(subjectid));
							typemodel.setUnitid(1);
							typeManager.addTkQuestionsType(typemodel);
						}
						model.setTkQuestionsType(typemodel);
						// 查找题库中questionid最大值
						// int maxquestionid = manager.getMaxQuestionid();
						// model.setQuestionid(maxquestionid + 1);

						List<SearchModel> condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
						SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.parseInt(gradeid));
						EduGradeInfo edugradeinfo = (EduGradeInfo) gradeManager.getEduGradeInfos(condition, "a.gradeid", 0).get(0);
						model.setGradeid(edugradeinfo.getGradeid());
						question_title = question_title.replaceAll("<subscript>", "").replaceAll("</subscript>", "").replaceAll("<superscript>", "").replaceAll("</superscript>", "");
						model.setTitle(question_title);
						model.setTitlecontent(question_content);
						// 如果题型为填空题，并且有标准答案
						if ("E".equals(subjecttype) && "1".equals(model.getIsrightans())) {
							model.setOptionnum(pointcount);
						} else {
							model.setOptionnum(Integer.parseInt(option_count));
						}
						model.setDoctype("0");
						model.setDescript(question_answercontent);
						model.setDifficult(question_difficult);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						model.setCretatdate(sdf.format(new java.util.Date()));
						model.setUpdatetime(sdf.format(new java.util.Date()));
						model.setStatus("1");
						model.setAuthorid(userid);
						model.setAuthorname(username);
						model.setUnitid(unitid);
						manager.addTkQuestionsInfo(model);
						model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, request));
						model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, request));
						String questionno = "";
						for (int j = model.getQuestionid().toString().length(); j < 10; j++) {
							questionno += "0";
						}
						questionno += model.getQuestionid();
						model.setQuestionno(questionno);
						if ("M".equals(subjecttype)) {
							map.put("parentid", model.getQuestionid());
							// 求平局分的商
							avgscore = Integer.parseInt(model.getScore()) / sonquestion;
							// 求余
							remscore = Integer.parseInt(model.getScore()) % sonquestion;
						} else if (!"M".equals(subjecttype) && "0".equals(ischildquestion)) {
							map.remove("parentid");
						}
						if (!"M".equals(subjecttype) && "1".equals(ischildquestion)) {
							model.setParentid(map.get("parentid"));
							if (sonquestion == sonindex) {
								model.setScore(String.valueOf(avgscore + remscore));
								sonquestion = 0;
							} else {
								model.setScore(String.valueOf(avgscore));
							}
						} else {
							model.setParentid(0);
						}
						manager.updateTkQuestionsInfo(model);
						// 新增papercontent表
						for (int m = 0; m < paperinfos.size(); m++) {
							TkPaperInfo tkpaperinfo = paperinfos.get(m);
							if (falgs.equals(tkpaperinfo.getFlags())) {
								TkPaperContent papercontentmodel = new TkPaperContent();
								int orderindex = papercontentManager.getMaxOrderindex(tkpaperinfo.getPaperid());
								papercontentmodel.setPaperid(tkpaperinfo.getPaperid());
								papercontentmodel.setQuestionid(model.getQuestionid());
								papercontentmodel.setOrderindex((orderindex + 1));
								papercontentmodel.setScore(Float.parseFloat(model.getScore()));
								papercontentManager.addTkPaperContent(papercontentmodel);
								break;
							}
						}
						if (null != text.element("test_children")) {
							List<Element> list = text.element("test_children").elements();
							for (int j = 0; j < list.size(); j++) {
								Element child = list.get(j);
								if (j == list.size() - 1) {
									savechildQuestion(map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore + remscore);
								} else {
									savechildQuestion(map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore);
								}

							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String promptinfo = "试题导入失败! 【失败原因】" + e.getMessage();
			request.setAttribute("promptinfo", promptinfo);
			return mapping.findForward("failure");
		}
		request.setAttribute("promptinfo", "试题导入成功!");
		return mapping.findForward("success");

	}

	/**
	 * 生成缩略图
	 * */
	public static void thumbnail(String imageUrl, String targetUrl, int height, String image) {
		File sourceFile = new File(imageUrl);
		File targeturl = new File(targetUrl);
		File[] files = sourceFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			try {
				String name = files[i].getName();
				if (image.indexOf(name) != -1) {
					ImageUtil.generateThumbnailsSubImage(files[i].toString(), targeturl + "/s_" + files[i].getName(), 60, height, false);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析子题
	 * **/
	public void savechildQuestion(int eparentid, String imageUrl, String targetUrl, String simageurl, Element element, HttpServletRequest request, HttpServletResponse response, int score) {
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");

		TkQuestionsInfo model = new TkQuestionsInfo();
		String option_count = "0";
		String subjecttype = "";
		String subjectid = "";
		String questiontype = "";//
		String question_content = "";// 题干
		String question_title = "";// 没有图片的标题
		String question_answercontent = "";// 答案解析
		String question_difficult = "";// 题目难度
		String question_optioncount = "";// 选项个数
		String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
		String gradeid = "";
		int parentid = 0;
		int pointcount = 0;// 填空数
		Element child = element;
		subjecttype = child.elementText("type");
		String testnum = child.elementText("test_num");
		Element test_content = child.element("test_content");
		for (Iterator itest_content = test_content.elementIterator("para"); itest_content.hasNext();) {
			String begin = "";
			String lastcontent = "";
			String image = "";
			Element para = (Element) itest_content.next();
			String content = para.asXML();
			content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
			if (content.indexOf("<mediaobject>") > -1) {
				begin += content.substring(0, content.indexOf("<mediaobject>")).trim();
			}
			if (content.indexOf("<inlinemediaobject>") > -1) {
				begin += content.substring(0, content.indexOf("<inlinemediaobject>")).trim();
			}
			for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
			}
			for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
				content = content.replace(imagedata.asXML(), image);
				lastcontent = content;
			}
			if (!"".equals(image)) {
				question_content += lastcontent;
				question_title += begin;
			} else {
				question_content += content;
				question_title += content;
			}
		}
		if (!"".equals(child.elementText("option_count")) && null != child.elementText("option_count")) {
			option_count = child.elementText("option_count");
		}
		if (!"".equals(child.elementText("space_count")) && null != child.elementText("space_count")) {
			pointcount = Integer.parseInt(child.elementText("space_count"));
		} else {
			pointcount = 0;
		}
		if (!"".equals(child.elementText("difficulty")) && null != child.elementText("difficulty")) {
			question_difficult = child.elementText("difficulty");
		}
		if (!"".equals(child.elementText("answer_unicity")) && null != child.elementText("answer_unicity")) {
			String answer_unicity = child.elementText("answer_unicity");
			if ("A".equals(subjecttype) || "B".equals(subjecttype)) {
				model.setIsrightans("1");
			} else {
				if ("否".equals(answer_unicity)) {
					model.setIsrightans("0");
				} else if ("是".equals(answer_unicity)) {
					model.setIsrightans("1");
				}
			}
		} else {
			if ("A".equals(subjecttype) || "B".equals(subjecttype)) {
				model.setIsrightans("1");
			} else {
				model.setIsrightans("0");
			}
		}
		if (null != child.element("answer")) {
			Element answer = child.element("answer");
			for (Iterator itest_content = answer.elementIterator("para"); itest_content.hasNext();) {
				String lastcontent = "";
				String image = "";
				Element para = (Element) itest_content.next();
				String content = para.asXML();
				content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
				for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
					Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
					int width = Integer.parseInt(imagedata.attributeValue("width"));
					int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
					image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
					thumbnail(imageUrl, targetUrl, height, image);
					if (!"".equals(image) || null != image) {
						image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
					}
					content = content.replace(imagedata.asXML(), image);
					lastcontent = content;
				}
				for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
					Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
					int width = Integer.parseInt(imagedata.attributeValue("width"));
					int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
					image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
					thumbnail(imageUrl, targetUrl, height, image);
					if (!"".equals(image) || null != image) {
						image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
					}
					content = content.replace(imagedata.asXML(), image);
					lastcontent = content;
				}
				if (!"".equals(image)) {
					model.setRightans(lastcontent);
				} else {
					model.setRightans(content);
				}
				if ("E".equals(subjecttype)) {
					model.setOption1(model.getRightans());
				}
			}
		}
		Element option_content = child.element("option_content");
		int count = 1;
		for (Iterator itest_content = option_content.elementIterator("para"); itest_content.hasNext();) {
			String lastcontent = "";
			String image = "";
			Element para = (Element) itest_content.next();
			String content = para.asXML();
			content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
			for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
				content = content.replace(imagedata.asXML(), image);
				lastcontent = content;
			}
			for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
				content = content.replace(imagedata.asXML(), image);
				lastcontent = content;
			}
			if (!"".equals(image)) {
				model.setOptionContent(count, lastcontent);
			} else {
				model.setOptionContent(count, content);
			}
			count += 1;
		}
		if (null != child.element("value") && !"".equals(child.elementText("value")) && null != child.elementText("value")) {
			if ("1".equals(model.getIsrightans())) {
				if ("E".equals(subjecttype)) {
					String value = child.elementText("value");
					double sum = Double.parseDouble(value);
					double result = sum / pointcount;
					double yu = sum % pointcount;
					String sresult = "";
					for (int k = 0; k < pointcount; k++) {
						if (k == pointcount - 1) {
							sresult += (yu + result);
						} else {
							sresult += result + "【】";
						}
					}
					model.setScore(sresult);
				} else {
					model.setScore(child.elementText("value"));
				}
			} else {
				model.setScore(child.elementText("value"));
			}
		} else {
			model.setScore(String.valueOf(score));
		}
		Element answer_analysis = child.element("answer_analysis");
		for (Iterator itest_content = answer_analysis.elementIterator("para"); itest_content.hasNext();) {
			String lastcontent = "";
			String image = "";
			Element para = (Element) itest_content.next();
			String content = para.asXML();
			content = content.replaceAll("<para>", "").replace("</para>", "").replaceAll("<para align=\"center\">", "");
			for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
				content = content.replace(imagedata.asXML(), image);
				lastcontent = content;
			}
			for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
				image += imagedata.attributeValue("fileref").replace("Image/", "<img src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, height, image);
				if (!"".equals(image) || null != image) {
					image = "<a href=\"javascript:previewPic('" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">" + image + "</a>";
				}
				content = content.replace(imagedata.asXML(), image);
				lastcontent = content;
			}
			if (!"".equals(image)) {
				question_answercontent = lastcontent;
			} else {
				question_answercontent = content;
			}
		}
		if (!"".equals(child.elementText("area")) && null != child.elementText("area")) {
			model.setArea(child.elementText("area"));
		} else {
			model.setArea("全国");
		}
		if (!"".equals(child.elementText("year")) && null != child.elementText("year")) {
			model.setTheyear(child.elementText("year"));
		} else {
			model.setTheyear(DateTime.getDateYear());
		}
		if (!"".equals(child.elementText("subject")) && null != child.elementText("subject")) {
			subjectid = child.elementText("subject");
			model.setSubjectid(Integer.parseInt(child.elementText("subject")));
		}
		if (!"".equals(child.elementText("grade")) && null != child.elementText("grade")) {
			gradeid = child.elementText("grade");
		}
		if (!"".equals(child.elementText("source")) && null != child.elementText("source")) {
			model.setTag(child.elementText("source"));
		}
		// 往数据库插入数据
		if ("A".equals(subjecttype)) {
			model.setQuestiontype("O");
			questiontype = "A";// 单选题
		} else if ("E".equals(subjecttype)) {
			model.setQuestiontype("S");
			questiontype = "E";
		} else if ("M".equals(subjecttype)) {
			questiontype = "M";
			model.setQuestiontype("S");
		} else if ("S".equals(subjecttype)) {
			questiontype = "S";
			model.setQuestiontype("S");
		} else if ("C".equals(subjecttype)) {
			questiontype = "C";
			model.setQuestiontype("O");
			if ("T".equals(model.getRightans())) {
				model.setRightans("1");
			} else if ("F".equals(model.getRightans())) {
				model.setRightans("0");
			}
		}
		// 查询题型
		TkQuestionsType typemodel = typeManager.getTkQuestionsType(questiontype, Integer.parseInt(subjectid));
		if (null == typemodel) {
			typemodel = new TkQuestionsType();
			int typeno = (manager.getMaxTypeno() + 1);
			if ("A".equals(subjecttype)) {
				typemodel.setTypename("单选题");
			} else if ("B".equals(subjecttype)) {
				typemodel.setTypename("多选题");
			} else if ("C".equals(subjecttype)) {
				typemodel.setTypename("判断题");
			} else if ("E".equals(subjecttype)) {
				typemodel.setTypename("填空题");
			} else if ("F".equals(subjecttype)) {
				typemodel.setTypename("英语完型填空题");
			} else if ("M".equals(subjecttype)) {
				typemodel.setTypename("复合题");
			} else if ("S".equals(subjecttype)) {
				typemodel.setTypename("其他主观题");
			}
			typemodel.setTypeno(String.valueOf(typeno));
			typemodel.setType(questiontype);
			typemodel.setSubjectid(Integer.parseInt(subjectid));
			typemodel.setUnitid(1);
			typeManager.addTkQuestionsType(typemodel);
		}
		model.setTkQuestionsType(typemodel);
		// 查找题库中questionid最大值
		// int maxquestionid = manager.getMaxQuestionid();
		// model.setQuestionid(maxquestionid + 1);

		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.parseInt(gradeid));
		EduGradeInfo edugradeinfo = (EduGradeInfo) gradeManager.getEduGradeInfos(condition, "a.gradeid", 0).get(0);
		model.setGradeid(edugradeinfo.getGradeid());
		question_title = question_title.replaceAll("<subscript>", "").replaceAll("</subscript>", "").replaceAll("<superscript>", "").replaceAll("</superscript>", "");
		model.setTitle(question_title);
		model.setTitlecontent(question_content);
		// 如果题型为填空题，并且有标准答案
		if ("E".equals(subjecttype) && "1".equals(model.getIsrightans())) {
			model.setOptionnum(pointcount);
		} else {
			model.setOptionnum(Integer.parseInt(option_count));
		}
		model.setDoctype("0");
		model.setDescript(question_answercontent);
		model.setDifficult(question_difficult);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.setCretatdate(sdf.format(new java.util.Date()));
		model.setUpdatetime(sdf.format(new java.util.Date()));
		model.setStatus("1");
		model.setAuthorid(userid);
		model.setAuthorname(username);
		model.setUnitid(unitid);
		manager.addTkQuestionsInfo(model);
		model.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(model, request));
		model.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(model, request));
		String questionno = "";
		for (int j = model.getQuestionid().toString().length(); j < 10; j++) {
			questionno += "0";
		}
		questionno += model.getQuestionid();
		model.setQuestionno(questionno);
		model.setParentid(eparentid);
		manager.updateTkQuestionsInfo(model);
	}
}
