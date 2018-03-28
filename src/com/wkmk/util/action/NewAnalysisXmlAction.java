package com.wkmk.util.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.CopyFile;
import com.util.file.FileUtil;
import com.util.image.ImageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentQuestion;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsSimilar;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentQuestionManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsSimilarManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.util.common.TwoCodeUtil;

/**
 * 所有题型：单选题，多选题，判断题，填空题，复合题，其他主观题。因为英语学科比较特殊，都是有标准答案的题，所以所有的其他主观题（S）都要改成填空题（E）。
 * 登陆后执行 Windows：http://localhost:8080/newXml.do?changeFile=1&copyFile=1&thumb=1
 * 登陆后执行 Windows：http://localhost:8080/newXml.do?changeFile=1&copyFile=1&thumb=1&isrightans=1 英语
 * Linux：http://localhost:8080/newXml.do?copyFile=1&thumb=1
 * Linux：http://localhost:8080/newXml.do
 * Linux：http://localhost:8080/newXml.do?copyFile=1&thumb=1&isrightans=1 英语
 * Linux：http://localhost:8080/newXml.do?isrightans=1 英语
 * 
 * <tests xmlns:xlink="http://www.w3.org/1999/xlink">
 */
public class NewAnalysisXmlAction extends BaseAction {
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
		// EduGradeInfoManager gradeManager = (EduGradeInfoManager)
		// getBean("eduGradeInfoManager");
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
			// 因为Linux通过软件挂云存储时性能相对较低，所以拷贝文件这块可通过命令提前拷贝好
			String changeFile = Encode.nullToBlank(request.getParameter("changeFile"));
			String copyFile = Encode.nullToBlank(request.getParameter("copyFile"));
			if ("1".equals(changeFile)) {
				// 重命名图片，修改xml图片路径
				Map<String, String> photoMap = new HashMap<String, String>();
				changeFile(new File(imageUrl), photoMap);
				changeXml(new File(mainXmlUlr), photoMap, mainXmlUlr);
			}
			if ("1".equals(copyFile)) {
				// 拷贝图片到指定目录
				CopyFile.copy(imageUrl, targetUrl);
			}
			String isrightans = Encode.nullToBlank(request.getParameter("isrightans"));// 英语课本所有题默认都是有标准答案的

			// 缩略图路径
			int beign = targetUrl.indexOf("/upload");
			String simageurl = targetUrl.substring(beign);
			TkBookInfo bookinfo = bookManager.getTkBookInfo(bookid);
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
			
			// 举一反三map
			Map<String, Integer> similarMap = new HashMap<String, Integer>();
			//专门针对语文单独导入整本的课前预习题而用
			if("【课前预习】".equals(toc.elementText("title"))){
				List<Element> tocdivs = toc.selectNodes("tocdiv");
				List<SearchModel> condition2 = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition2, "bookid", "=", bookid);
				SearchCondition.addCondition(condition2, "parentno", "!=", "0000");
				bookcontent = bookcontentManager.getTkBookContents(condition2, "contentno asc", 0);
				int zindex = 0;
				for (int i = 0; i < tocdivs.size(); i++) {
					Element tocdiv = tocdivs.get(i);
					List<Element> sontocdivs = tocdiv.selectNodes("tocdiv");
					if(sontocdivs != null && sontocdivs.size() > 0){
						for (int k = 0; k < sontocdivs.size(); k++) {
							zindex += 1;
							Element sontocdiv = sontocdivs.get(k);
							TkBookContent sonbookcontent = bookcontent.get(zindex - 1);
							sonbookcontent.setFlags(sontocdiv.element("tocentry").attributeValue("href"));
						}
					}else {
						//有的作业本的检测卷是和章平级的，但是后来录的数据是固定二级结构
						zindex += 1;
						if(bookcontent.size() >= zindex){
							TkBookContent sonbookcontent = bookcontent.get(zindex - 1);
							if(sonbookcontent != null){
								sonbookcontent.setFlags(tocdiv.element("tocentry").attributeValue("href"));
							}
						}
					}
				}
			}else {
				// System.out.println(toc.asXML());
				List<Element> tocdivs = toc.selectNodes("tocdiv");
				List<SearchModel> condition2 = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition2, "bookid", "=", bookid);
				SearchCondition.addCondition(condition2, "parentno", "!=", "0000");
				bookcontent = bookcontentManager.getTkBookContents(condition2, "contentno asc", 0);
				int zindex = 0;
				for (int i = 0; i < tocdivs.size(); i++) {
					// TkBookContent parentbookcontent = new TkBookContent();
					Element tocdiv = tocdivs.get(i);
					// String title = tocdiv.elementText("title");
					// String contentno =
					// bookcontentManager.getMaxContentno(bookid);
					// if ("".equals(contentno)) {
					// parentbookcontent.setContentno("00000100");
					// } else {
					// int temp = Integer.parseInt(contentno);
					// temp += 1;
					// int length = 8 - String.valueOf(temp).length();
					// String nowcontento = String.valueOf(temp);
					// for (int j = 0; j < length; j++) {
					// nowcontento = "0" + nowcontento;
					// }
					// parentbookcontent.setContentno(nowcontento);
					// }
					// parentbookcontent.setBookid(Integer.parseInt(bookid));
					// parentbookcontent.setPaperid(0);
					// parentbookcontent.setTitle(title);
					// parentbookcontent.setParentno("0000");
					// parentbookcontent.setBeforeclass("");
					// parentbookcontent.setTeachingcase("");
					// bookcontentManager.addTkBookContent(parentbookcontent);
					// parentbookcontent.setBeforeclasstwocode(TwoCodeUtil.getbeforeClassTwoCodePath(parentbookcontent,
					// request));
					// parentbookcontent.setTeachingcasetwocode(TwoCodeUtil.getteachingCaseTwoCodePath(parentbookcontent,
					// request));
					// bookcontentManager.updateTkBookContent(parentbookcontent);
					List<Element> sontocdivs = tocdiv.selectNodes("tocdiv");
					if(sontocdivs != null && sontocdivs.size() > 0){
						for (int k = 0; k < sontocdivs.size(); k++) {
							zindex += 1;
							Element sontocdiv = sontocdivs.get(k);
							TkBookContent sonbookcontent = bookcontent.get(zindex - 1);
							sonbookcontent.setFlags(sontocdiv.element("tocentry").attributeValue("href"));
						}
					}else {
						//有的作业本的检测卷是和章平级的，但是后来录的数据是固定二级结构
						zindex += 1;
						if(bookcontent.size() >= zindex){
							TkBookContent sonbookcontent = bookcontent.get(zindex - 1);
							if(sonbookcontent != null){
								sonbookcontent.setFlags(tocdiv.element("tocentry").attributeValue("href"));
							}
						}
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
			}
			
			// 解析题目
			List<Element> charpters = root.selectNodes("chapter");
			for (int z = 0; z < charpters.size(); z++) {
				Element charpter1 = charpters.get(z);
				List<Element> sect1s = charpter1.selectNodes("sect1");
				for (int h = 0; h < sect1s.size(); h++) {
					Element sect1 = sect1s.get(h);
					String falgs = sect1.attributeValue("id");
					
					if(falgs == null || "".equals(falgs)){
						falgs = charpter1.attributeValue("id");
					}
					List<Element> elist = sect1.selectNodes("test");
					for (int i = 0; i < elist.size(); i++) {
						TkQuestionsInfo model = new TkQuestionsInfo();
						String option_count = "0";
						String subjecttype = "";
						String subjectid = "";
						String questiontype = "";//
						String question_content = "";// 题干
						String question_title = "";// 没有图片的标题，在后面统一通过question_content处理获取数据
						String question_answer = "";// 答案
						String question_answercontent = "";// 答案解析
						String question_difficult = "";// 题目难度
						String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
						String gradeid = "";
						int parentid = 0;
						int pointcount = 0;// 填空数
						String similarid = "";
						Element text = (Element) elist.get(i);
						// 判断是否为题如果有存入map中
						if (!"".equals(text.elementText("link_id")) && null != text.elementText("link_id")) {
							similarid = text.elementText("link_id");
						}
						subjecttype = text.elementText("type");
						String testnum = text.elementText("test_num");
						Element test_content = text.element("test_content");
						question_content = getElementHtmlContent(test_content, imageUrl, targetUrl, simageurl, request);
						model.setTitlecontent(question_content);
						// 将titlecontent转换为title
						question_title = question_content.replaceAll("<sub>", "").replaceAll("</sub>", "").replaceAll("<sup>", "").replaceAll("</sup>", "").replaceAll("<p>", "")
								.replaceAll("</p>", "");
						while (question_title.indexOf("</a>") > -1) {
							question_title = question_title.substring(0, question_title.indexOf("<a id=")) + question_title.substring(question_title.indexOf("</a>") + 4);
						}
						model.setTitle(question_title);
						if (model.getTitle().length() > 500) {
							model.setTitle(model.getTitle().substring(0, 500));
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
							model.setDifficult(question_difficult);
						} else {
							model.setDifficult("A");
						}
						if (!"".equals(text.elementText("answer_unicity")) && null != text.elementText("answer_unicity")) {
							String answer_unicity = text.elementText("answer_unicity");
							if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
								model.setIsrightans("1");
							} else {
								if ("否".equals(answer_unicity)) {
									model.setIsrightans("0");
								} else if ("是".equals(answer_unicity)) {
									model.setIsrightans("1");
								}
								if ("0".equals(answer_unicity)) {
									model.setIsrightans("0");
								} else if ("1".equals(answer_unicity)) {
									model.setIsrightans("1");
								}
							}
						} else {
							if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
								model.setIsrightans("1");
							} else if ("1".equals(isrightans)) {
								model.setIsrightans("1");
							} else {
								model.setIsrightans("0");
							}
						}
						if (pointcount > 10) {
							model.setIsrightans("0");// 超过10空设置为没有标准答案
						}
						if (null != text.element("answer")) {
							Element answer = text.element("answer");
							if ("1".equals(model.getIsrightans())) {
								question_answer = answer.asXML().replaceAll("<answer>", "").replaceAll("</answer>", "").replaceAll("<para>", "").replaceAll("</para>", "").trim();
								// xml有bug，有【】结尾字符串
								if (question_answer.endsWith("【】")) {
									question_answer = question_answer.substring(0, question_answer.length() - 2);
								}
								model.setRightans(question_answer);
							} else {
								question_answer = getElementHtmlContent(answer, imageUrl, targetUrl, simageurl, request);
								model.setRightans(question_answer);
								if ("E".equals(subjecttype)) {
									model.setOption1(question_answer);
								}
							}
						}
						Element option_content = text.element("option_content");
						model = getElementHtmlContent(model, option_content, imageUrl, targetUrl, simageurl, request);
						if (!"".equals(text.elementText("value")) && null != text.elementText("value")) {
							if ("1".equals(model.getIsrightans())) {
								if ("E".equals(subjecttype)) {
									String value = text.elementText("value");
									double result;
									double yu;
									if (value.indexOf(".") == -1) {
										value = value + ".0";
									}
									if (value.indexOf(".") > 0) {
										if (pointcount == 1) {
											result = Double.parseDouble(value);
											yu = 0;
										} else {
											// 如果可以除尽则以小数呈现，比如0.5
											double tempsum = Math.floor(Double.valueOf(value).doubleValue());
											result = tempsum / pointcount;
											if ((result + "").length() > 4) {
												double xiaoshu = Double.parseDouble(value.substring(value.indexOf(".")));
												int sum = Double.valueOf(value).intValue();
												result = sum / pointcount;
												yu = sum % pointcount + xiaoshu;
											} else {
												// 可以除尽就没有余数
												yu = Double.parseDouble(value.substring(value.indexOf(".")));
											}
										}
									} else {
										int sum = Integer.parseInt(value);
										result = sum / pointcount;
										yu = sum % pointcount;
									}
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
						} else {
							model.setScore("1");// 如果没有给分值，则默认给1分
						}
						Element answer_analysis = text.element("answer_analysis");
						question_answercontent = getElementHtmlContent(answer_analysis, imageUrl, targetUrl, simageurl, request);
						model.setDescript(question_answercontent);
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
						} else if ("B".equals(subjecttype)) {
							model.setQuestiontype("O");
							questiontype = "B";
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
							if ("T".equals(model.getRightans().trim())) {
								model.setRightans("1");
							} else if ("F".equals(model.getRightans().trim())) {
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
						model.setGradeid(bookinfo.getGradeid());
						// 如果题型为填空题，并且有标准答案
						if (!"A".equals(subjecttype) && !"B".equals(subjecttype) && !"C".equals(subjecttype) && "1".equals(model.getIsrightans())) {
							if(pointcount > 0){
								model.setOptionnum(pointcount);
							}else {
								//英语其他主观题没有写空格数量，用程序自动判断
								String[] pointsize = model.getRightans().split("【】");
								model.setOptionnum(pointsize.length);
							}
						} else {
							model.setOptionnum(Integer.parseInt(option_count));
						}
						model.setDoctype("0");
						model.setCretatdate(DateTime.getDate());
						model.setUpdatetime(model.getCretatdate());
						model.setStatus("1");
						model.setAuthorid(userid);
						model.setAuthorname(username);
						model.setUnitid(unitid);
						replcaeUnderlinde(model);
						manager.addTkQuestionsInfo(model);
						if (!"".equals(similarid)) {
							similarMap.put(similarid, model.getQuestionid());
						}
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
							avgscore = Float.valueOf(model.getScore()).intValue() / sonquestion;
							// 求余
							remscore = Float.valueOf(model.getScore()).intValue() % sonquestion;
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
								papercontentmodel.setScore(model.getTotalScore());
								papercontentManager.addTkPaperContent(papercontentmodel);
								break;
							}
						}
						if (null != text.element("test_children")) {
							List<Element> list = text.element("test_children").elements();
							for (int j = 0; j < list.size(); j++) {
								Element child = list.get(j);
								if (j == list.size() - 1) {
									savechildQuestion(bookinfo.getGradeid(), map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore + remscore);
								} else {
									savechildQuestion(bookinfo.getGradeid(), map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore);
								}
							}

						}
					}
				}
				List<Element> sects = charpters.get(z).selectNodes("sect");
				// 解析练习题和举一反三
				for (int h = 0; h < sects.size(); h++) {
					Element sect1 = sects.get(h);
					String falgs = sect1.attributeValue("id");
					List<Element> elist = sect1.selectNodes("test");
					for (int i = 0; i < elist.size(); i++) {
						TkQuestionsInfo model = new TkQuestionsInfo();
						String option_count = "0";
						String subjecttype = "";
						String subjectid = "";
						String questiontype = "";//
						String question_content = "";// 题干
						String question_title = "";// 没有图片的标题
						String question_answer = "";// 答案
						String question_answercontent = "";// 答案解析
						String question_difficult = "";// 题目难度
						String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
						String gradeid = "";
						int parentid = 0;
						int pointcount = 0;// 填空数
						String testid = "0";
						Element text = (Element) elist.get(i);
						String role = text.attributeValue("role");
						subjecttype = text.elementText("type");
						String testnum = text.elementText("test_num");
						Element test_content = text.element("test_content");
						question_content = getElementHtmlContent(test_content, imageUrl, targetUrl, simageurl, request);
						model.setTitlecontent(question_content);
						// 将titlecontent转换为title
						question_title = question_content.replaceAll("<sub>", "").replaceAll("</sub>", "").replaceAll("<sup>", "").replaceAll("</sup>", "").replaceAll("<p>", "")
								.replaceAll("</p>", "");
						while (question_title.indexOf("</a>") > -1) {
							question_title = question_title.substring(0, question_title.indexOf("<a id=")) + question_title.substring(question_title.indexOf("</a>") + 4);
						}
						model.setTitle(question_title);
						if (model.getTitle().length() > 500) {
							model.setTitle(model.getTitle().substring(0, 500));
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
							model.setDifficult(question_difficult);
						} else {
							model.setDifficult("A");
						}
						if (!"".equals(text.elementText("answer_unicity")) && null != text.elementText("answer_unicity")) {
							String answer_unicity = text.elementText("answer_unicity");
							if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
								model.setIsrightans("1");
							} else {
								if ("否".equals(answer_unicity)) {
									model.setIsrightans("0");
								} else if ("是".equals(answer_unicity)) {
									model.setIsrightans("1");
								}
								if ("0".equals(answer_unicity)) {
									model.setIsrightans("0");
								} else if ("1".equals(answer_unicity)) {
									model.setIsrightans("1");
								}
							}
						} else {
							if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
								model.setIsrightans("1");
							} else if ("1".equals(isrightans)) {
								model.setIsrightans("1");
							} else {
								model.setIsrightans("0");
							}
						}
						if (pointcount > 10) {
							model.setIsrightans("0");// 超过10空设置为没有标准答案
						}
						if (null != text.element("answer")) {
							Element answer = text.element("answer");
							if ("1".equals(model.getIsrightans())) {
								question_answer = answer.asXML().replaceAll("<answer>", "").replaceAll("</answer>", "").replaceAll("<para>", "").replaceAll("</para>", "").trim();
								// xml有bug，有【】结尾字符串
								if (question_answer.endsWith("【】")) {
									question_answer = question_answer.substring(0, question_answer.length() - 2);
								}
								model.setRightans(question_answer);
							} else {
								question_answer = getElementHtmlContent(answer, imageUrl, targetUrl, simageurl, request);
								model.setRightans(question_answer);
								if ("E".equals(subjecttype)) {
									model.setOption1(question_answer);
								}
							}
						}
						Element option_content = text.element("option_content");
						model = getElementHtmlContent(model, option_content, imageUrl, targetUrl, simageurl, request);

						if (!"".equals(text.elementText("value")) && null != text.elementText("value")) {
							if ("1".equals(model.getIsrightans())) {
								if ("E".equals(subjecttype)) {
									String value = text.elementText("value");
									double result;
									double yu;
									if (value.indexOf(".") == -1) {
										value = value + ".0";
									}
									if (value.indexOf(".") > 0) {
										if (pointcount == 1) {
											result = Double.parseDouble(value);
											yu = 0;
										} else {
											// 如果可以除尽则以小数呈现，比如0.5
											double tempsum = Math.floor(Double.valueOf(value).doubleValue());
											result = tempsum / pointcount;
											if ((result + "").length() > 4) {
												double xiaoshu = Double.parseDouble(value.substring(value.indexOf(".")));
												int sum = Double.valueOf(value).intValue();
												result = sum / pointcount;
												yu = sum % pointcount + xiaoshu;
											} else {
												// 可以除尽就没有余数
												yu = Double.parseDouble(value.substring(value.indexOf(".")));
											}
										}
									} else {
										int sum = Integer.parseInt(value);
										result = sum / pointcount;
										yu = sum % pointcount;
									}
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
						} else {
							model.setScore("1");// 如果没有给分值，则默认给1分
						}
						Element answer_analysis = text.element("answer_analysis");
						question_answercontent = getElementHtmlContent(answer_analysis, imageUrl, targetUrl, simageurl, request);
						model.setDescript(question_answercontent);

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
						} else if ("B".equals(subjecttype)) {
							model.setQuestiontype("O");
							questiontype = "B";
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
							if ("T".equals(model.getRightans().trim())) {
								model.setRightans("1");
							} else if ("F".equals(model.getRightans().trim())) {
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
						model.setGradeid(bookinfo.getGradeid());
						// 如果题型为填空题，并且有标准答案
						if (!"A".equals(subjecttype) && !"B".equals(subjecttype) && !"C".equals(subjecttype) && "1".equals(model.getIsrightans())) {
							if(pointcount > 0){
								model.setOptionnum(pointcount);
							}else {
								//英语其他主观题没有写空格数量，用程序自动判断
								String[] pointsize = model.getRightans().split("【】");
								model.setOptionnum(pointsize.length);
							}
						} else {
							model.setOptionnum(Integer.parseInt(option_count));
						}
						model.setDoctype("0");
						model.setCretatdate(DateTime.getDate());
						model.setUpdatetime(model.getCretatdate());
						model.setStatus("1");
						model.setAuthorid(userid);
						model.setAuthorname(username);
						model.setUnitid(unitid);
						replcaeUnderlinde(model);
						manager.addTkQuestionsInfo(model);
						// 判断是否为举一反三题如果有存入map中
						if (!"".equals(text.elementText("test_id")) && null != text.elementText("test_id")) {
							testid = text.elementText("test_id");
							TkQuestionsSimilarManager tksManager = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
							TkQuestionsSimilar tksmodel = new TkQuestionsSimilar();
							if (null != similarMap.get(testid)) {
								int questionid = similarMap.get(testid);
								tksmodel.setQuestionid(questionid);
								tksmodel.setSimilarquestionid(model.getQuestionid());
								tksManager.addTkQuestionsSimilar(tksmodel);
							}
						}
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
							avgscore = Float.valueOf(model.getScore()).intValue() / sonquestion;
							// 求余
							remscore = Float.valueOf(model.getScore()).intValue() % sonquestion;
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
						if ("lianxi".equals(role)) {
							// 课前预习题
							TkBookContentQuestionManager tbcqManager = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
							for (int n = 0; n < bookcontent.size(); n++) {
								TkBookContent bookcontentmodel = bookcontent.get(n);
								if (falgs.equals(bookcontentmodel.getFlags())) {
									TkBookContentQuestion tbcqModel = new TkBookContentQuestion();
									tbcqModel.setBookcontentid(bookcontentmodel.getBookcontentid());
									tbcqModel.setQuestionid(model.getQuestionid());
									tbcqModel.setType("1");
									tbcqManager.addTkBookContentQuestion(tbcqModel);
								}
							}
						}
						if (null != text.element("test_children")) {
							List<Element> list = text.element("test_children").elements();
							for (int j = 0; j < list.size(); j++) {
								Element child = list.get(j);
								if (j == list.size() - 1) {
									savechildQuestion(bookinfo.getGradeid(), map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore + remscore);
								} else {
									savechildQuestion(bookinfo.getGradeid(), map.get("parentid"), imageUrl, targetUrl, simageurl, child, request, response, avgscore);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("试题导入失败!");
			e.printStackTrace();
			String promptinfo = "试题导入失败! 【失败原因】" + e.getMessage();
			request.setAttribute("promptinfo", promptinfo);
			return mapping.findForward("failure");
		}
		System.out.println("试题导入成功!");
		request.setAttribute("promptinfo", "试题导入成功!");
		return mapping.findForward("success");

	}

	// private static List<String> fileList = new ArrayList<String>();
	/**
	 * 生成缩略图
	 * */
	public static void thumbnail(String imageUrl, String targetUrl, int width, int height, String image, HttpServletRequest request) {
		// 是否需要生成缩略图，有可能是提前生成好拷贝到服务器上了
		String thumb = Encode.nullToBlank(request.getParameter("thumb"));
		if ("1".equals(thumb)) {
			if (width > 60)
				width = 60;
			File sourceFile = new File(imageUrl);
			File targeturl = new File(targetUrl);
			File[] files = sourceFile.listFiles();
			//后期修改，固定图片大小120*60
			width = 120;
			height = 60;
			for (int i = 0; i < files.length; i++) {
				try {
					String name = files[i].getName();
					if (image.indexOf(name) != -1) {
						// if(fileList.contains(targeturl + "/s_" +
						// files[i].getName())){
						// System.out.println(image);
						// }else {
						// fileList.add(targeturl + "/s_" + files[i].getName());
						// }
						// if(fileList.size() > 720){
						// System.out.println("fileList.size>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+fileList.size());
						// }
						//ImageUtil.generateThumbnailsSubImage(files[i].toString(), targeturl + "/s_" + files[i].getName(), width, height, true);// 按给定图片大小压缩
						ImageUtil.generateThumbnails(files[i].toString(), targeturl + "/s_" + files[i].getName(), width, height, false);// 按给定图片大小压缩
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析子题
	 * **/
	public void savechildQuestion(int bookgradeid, int eparentid, String imageUrl, String targetUrl, String simageurl, Element element, HttpServletRequest request, HttpServletResponse response,
			int score) {
		String isrightans = Encode.nullToBlank(request.getParameter("isrightans"));
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		// EduGradeInfoManager gradeManager = (EduGradeInfoManager)
		// getBean("eduGradeInfoManager");

		TkQuestionsInfo model = new TkQuestionsInfo();
		String option_count = "0";
		String subjecttype = "";
		String subjectid = "";
		String questiontype = "";//
		String question_content = "";// 题干
		String question_title = "";// 没有图片的标题
		String question_answer = "";// 答案解析
		String question_answercontent = "";// 答案解析
		String question_difficult = "";// 题目难度
		String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
		String gradeid = "";
		int parentid = 0;
		int pointcount = 0;// 填空数
		Element child = element;
		subjecttype = child.elementText("type");
		String testnum = child.elementText("test_num");
		Element test_content = child.element("test_content");
		question_content = getElementHtmlContent(test_content, imageUrl, targetUrl, simageurl, request);
		model.setTitlecontent(question_content);
		// 将titlecontent转换为title
		question_title = question_content.replaceAll("<sub>", "").replaceAll("</sub>", "").replaceAll("<sup>", "").replaceAll("</sup>", "").replaceAll("<p>", "").replaceAll("</p>", "");
		while (question_title.indexOf("</a>") > -1) {
			question_title = question_title.substring(0, question_title.indexOf("<a id=")) + question_title.substring(question_title.indexOf("</a>") + 4);
		}
		model.setTitle(question_title);
		if (model.getTitle().length() > 500) {
			model.setTitle(model.getTitle().substring(0, 500));
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
			if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
				model.setIsrightans("1");
			} else {
				if ("否".equals(answer_unicity)) {
					model.setIsrightans("0");
				} else if ("是".equals(answer_unicity)) {
					model.setIsrightans("1");
				}
				if ("0".equals(answer_unicity)) {
					model.setIsrightans("0");
				} else if ("1".equals(answer_unicity)) {
					model.setIsrightans("1");
				}
			}
		} else {
			if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
				model.setIsrightans("1");
			} else if ("1".equals(isrightans)) {
				model.setIsrightans("1");
			} else {
				model.setIsrightans("0");
			}
		}
		if (pointcount > 10) {
			model.setIsrightans("0");// 超过10空设置为没有标准答案
		}
		if (null != child.element("answer")) {
			Element answer = child.element("answer");
			if ("1".equals(model.getIsrightans())) {
				question_answer = answer.asXML().replaceAll("<answer>", "").replaceAll("</answer>", "").replaceAll("<para>", "").replaceAll("</para>", "").trim();
				// xml有bug，有【】结尾字符串
				if (question_answer.endsWith("【】")) {
					question_answer = question_answer.substring(0, question_answer.length() - 2);
				}
				model.setRightans(question_answer);
			} else {
				question_answer = getElementHtmlContent(answer, imageUrl, targetUrl, simageurl, request);
				model.setRightans(question_answer);
				if ("E".equals(subjecttype)) {
					model.setOption1(question_answer);
				}
			}
		}
		Element option_content = child.element("option_content");
		model = getElementHtmlContent(model, option_content, imageUrl, targetUrl, simageurl, request);

		if (null != child.element("value") && !"".equals(child.elementText("value")) && null != child.elementText("value")) {
			if ("1".equals(model.getIsrightans())) {
				if ("E".equals(subjecttype)) {
					String value = child.elementText("value");
					double result;
					double yu;
					if (value.indexOf(".") == -1) {
						value = value + ".0";
					}
					if (value.indexOf(".") > 0) {
						if (pointcount == 1) {
							result = Double.parseDouble(value);
							yu = 0;
						} else {
							// 如果可以除尽则以小数呈现，比如0.5
							double tempsum = Math.floor(Double.valueOf(value).doubleValue());
							result = tempsum / pointcount;
							if ((result + "").length() > 4) {
								double xiaoshu = Double.parseDouble(value.substring(value.indexOf(".")));
								int sum = Double.valueOf(value).intValue();
								result = sum / pointcount;
								yu = sum % pointcount + xiaoshu;
							} else {
								// 可以除尽就没有余数
								yu = Double.parseDouble(value.substring(value.indexOf(".")));
							}
						}
					} else {
						int sum = Integer.parseInt(value);
						result = sum / pointcount;
						yu = sum % pointcount;
					}
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
		question_answercontent = getElementHtmlContent(answer_analysis, imageUrl, targetUrl, simageurl, request);
		model.setDescript(question_answercontent);

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
		} else if ("B".equals(subjecttype)) {
			model.setQuestiontype("O");
			questiontype = "B";
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
			if ("T".equals(model.getRightans().trim())) {
				model.setRightans("1");
			} else if ("F".equals(model.getRightans().trim())) {
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

		// List<SearchModel> condition = new ArrayList<SearchModel>();
		// SearchCondition.addCondition(condition, "subjectid", "=",
		// Integer.parseInt(subjectid));
		// SearchCondition.addCondition(condition, "cxueduanid", "=",
		// Integer.parseInt(gradeid));
		// EduGradeInfo edugradeinfo = (EduGradeInfo)
		// gradeManager.getEduGradeInfos(condition, "a.gradeid", 0).get(0);
		// model.setGradeid(edugradeinfo.getGradeid());
		model.setGradeid(bookgradeid);
		// 如果题型为填空题，并且有标准答案
		if (!"A".equals(subjecttype) && !"B".equals(subjecttype) && !"C".equals(subjecttype) && "1".equals(model.getIsrightans())) {
			if(pointcount > 0){
				model.setOptionnum(pointcount);
			}else {
				//英语其他主观题没有写空格数量，用程序自动判断
				String[] pointsize = model.getRightans().split("【】");
				model.setOptionnum(pointsize.length);
			}
		} else {
			model.setOptionnum(Integer.parseInt(option_count));
		}
		model.setDoctype("0");
		model.setDifficult(question_difficult);
		model.setCretatdate(DateTime.getDate());
		model.setUpdatetime(model.getCretatdate());
		model.setStatus("1");
		model.setAuthorid(userid);
		model.setAuthorname(username);
		model.setUnitid(unitid);
		replcaeUnderlinde(model);
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

	// 重命名图片
	private static void changeFile(File f, Map<String, String> map) {
		if (f.isFile()) {
			String oldfilename = f.getName();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
			int index = oldfilename.indexOf("_");
			if (index > 0) {
				String replacename = oldfilename.substring(0, index);
				String newname = sdf.format(new Date());
				map.put(oldfilename, newname + oldfilename.substring(index));
				f.renameTo(new File(f.getPath().replace(replacename, newname)));
			} else {
				String newname = sdf.format(new Date()) + "." + FileUtil.getFileExt(oldfilename);
				map.put(oldfilename, newname);
				f.renameTo(new File(f.getPath().replace(oldfilename, newname)));
			}
		} else {
			File[] fs = f.listFiles();
			for (File f3 : fs) {
				changeFile(f3, map);
			}
		}
	}

	// 修改xml
	private static void changeXml(File f, Map<String, String> map, String xmlFile) throws DocumentException, IOException {
		SAXReader saxreader = new SAXReader();
		FileInputStream in = new FileInputStream(f);
		Document document = saxreader.read(in);
		Element root = document.getRootElement();
		Iterator it = map.keySet().iterator();
		String content = root.asXML();
		while (it.hasNext()) {
			String key = it.next().toString();
			content = content.replace(key, map.get(key));
		}
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(xmlFile), "utf-8"));
		writer.write(content);
		writer.close();
	}

	/*
	 * 解析xml富文本标签，返回标签中字符串
	 */
	private String getElementHtmlContent(Element htmlContentElement, String imageUrl, String targetUrl, String simageurl, HttpServletRequest request) {
		// 【特别说明】在导入试题时，如果试题标题内容有题号，则需要自动去掉，平台会自动生成
		String testcontentParaOrderindexTag = "0";
		String htmlContentElementString = htmlContentElement.asXML();
		if (htmlContentElementString.indexOf("<test_content>") != -1) {
			testcontentParaOrderindexTag = "1";
		}
		int testcontentParaOrderindex = 0;// 只判断过滤第一个para标签
		StringBuffer htmlContent = new StringBuffer();
		// 【注意】标签严格规范是里面有para标签的，但如果没有para标签，而是直接跟内容也需要解析出来
		if (htmlContentElementString.indexOf("<para>") != -1) {
			for (Iterator itest_content = htmlContentElement.elementIterator("para"); itest_content.hasNext();) {
				String image = "";
				Element para = (Element) itest_content.next();
				testcontentParaOrderindex++;
				String content = para.asXML();
				for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
					image = "";
					Element element = (Element) mediaobject.next();
					if(mediaobject == null || element.element("imageobject") == null){
						System.out.println(content);
					}
					Element imagedata = element.element("imageobject").element("imagedata");
					String imagetitle = "";
					if (imagedata.element("info") != null) {
						if (imagedata.element("info").element("title") != null) {
							if (imagedata.element("info").element("title").getText() != null) {
								imagetitle = imagedata.element("info").element("title").getText();
							}
						}
						if (imagedata.element("info").element("annotation") != null) {
							if (imagedata.element("info").element("annotation").getText() != null) {
								imagetitle = imagedata.element("info").element("annotation").getText();
							}
						}
					}
					int width = Integer.parseInt(imagedata.attributeValue("width"));
					int height = Integer.parseInt(imagedata.attributeValue("depth"));
					if (width > 60) {
						if (height > 30) {
							height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
							if (height > 60) {
								height = 60;// 控制图片大小不超过60*60，可以点击查看大图
							}
							if (height < 20) {// 控制高度不能太小
								height = 20;
							}
						}
						width = 60;
					} else {
						if (height > 60) {
							height = 60;
						}
					}

					image += imagedata.attributeValue("fileref")
							.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_")
							.replace(".jpg", ".jpg\"/>");
					thumbnail(imageUrl, targetUrl, width, height, image, request);
					if (!"".equals(image) || null != image) {
						image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
								+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "")
								+ "')\">" + image + "</a>";
					}
					image = image + imagetitle;
					content = content.replace(imagedata.asXML(), image);
				}
				for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
					image = "";
					Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
					String imagetitle = "";
					if (imagedata.element("info") != null) {
						if (imagedata.element("info").element("title") != null) {
							if (imagedata.element("info").element("title").getText() != null) {
								imagetitle = imagedata.element("info").element("title").getText();
							}
						}
						if (imagedata.element("info").element("annotation") != null) {
							if (imagedata.element("info").element("annotation").getText() != null) {
								imagetitle = imagedata.element("info").element("annotation").getText();
							}
						}
					}
					int width = Integer.parseInt(imagedata.attributeValue("width"));
					int height = Integer.parseInt(imagedata.attributeValue("depth"));
					if (width > 60) {
						if (height > 30) {
							height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
							if (height > 60) {
								height = 60;// 控制图片大小不超过60*60，可以点击查看大图
							}
							if (height < 20) {// 控制高度不能太小
								height = 20;
							}
						}
						width = 60;
					} else {
						if (height > 60) {
							height = 60;
						}
					}

					image += imagedata.attributeValue("fileref")
							.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_")
							.replace(".jpg", ".jpg\"/>");
					thumbnail(imageUrl, targetUrl, width, height, image, request);
					if (!"".equals(image) || null != image) {
						image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
								+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "")
								+ "')\">" + image + "</a>";
					}
					image = image + imagetitle;
					content = content.replace(imagedata.asXML(), image);
				}
				content = content.replaceAll("<para>", "<p>").replaceAll("</para>", "</p>").replaceAll("<para align=\"center\">", "<p>").replaceAll("<para/>", "");
				if ("1".equals(testcontentParaOrderindexTag) && testcontentParaOrderindex == 1) {
					int indexof = content.indexOf(".");
					if (indexof != -1 && indexof > 3 && indexof < 6) {// 题号默认不超过100题，比如18.
						content = content.substring(indexof + 1);
						// 通过上面的方法截取掉了开始的<p>标签，需求再截取第一次出现的</p>标签
						int pindex = content.indexOf("</p>");
						content = content.substring(0, pindex) + content.substring(pindex + 4);
					}
				}
				htmlContent.append(content);
			}
		} else {
			String image = "";
			String content = htmlContentElementString;
			for (Iterator mediaobject = htmlContentElement.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
				image = "";
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				String imagetitle = "";
				if (imagedata.element("info") != null) {
					if (imagedata.element("info").element("title") != null) {
						if (imagedata.element("info").element("title").getText() != null) {
							imagetitle = imagedata.element("info").element("title").getText();
						}
					}
					if (imagedata.element("info").element("annotation") != null) {
						if (imagedata.element("info").element("annotation").getText() != null) {
							imagetitle = imagedata.element("info").element("annotation").getText();
						}
					}
				}
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth"));
				if (width > 60) {
					if (height > 30) {
						height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
						if (height > 60) {
							height = 60;// 控制图片大小不超过60*60，可以点击查看大图
						}
						if (height < 20) {// 控制高度不能太小
							height = 20;
						}
					}
					width = 60;
				} else {
					if (height > 60) {
						height = 60;
					}
				}

				image += imagedata.attributeValue("fileref")
						.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, width, height, image, request);
				if (!"".equals(image) || null != image) {
					image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
							+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">"
							+ image + "</a>";
				}
				image = image + imagetitle;
				content = content.replace(imagedata.asXML(), image);
			}
			for (Iterator mediaobject = htmlContentElement.elementIterator("mediaobject"); mediaobject.hasNext();) {
				image = "";
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				String imagetitle = "";
				if (imagedata.element("info") != null) {
					if (imagedata.element("info").element("title") != null) {
						if (imagedata.element("info").element("title").getText() != null) {
							imagetitle = imagedata.element("info").element("title").getText();
						}
					}
					if (imagedata.element("info").element("annotation") != null) {
						if (imagedata.element("info").element("annotation").getText() != null) {
							imagetitle = imagedata.element("info").element("annotation").getText();
						}
					}
				}
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth"));
				if (width > 60) {
					if (height > 30) {
						height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
						if (height > 60) {
							height = 60;// 控制图片大小不超过60*60，可以点击查看大图
						}
						if (height < 20) {// 控制高度不能太小
							height = 20;
						}
					}
					width = 60;
				} else {
					if (height > 60) {
						height = 60;
					}
				}

				image += imagedata.attributeValue("fileref")
						.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, width, height, image, request);
				if (!"".equals(image) || null != image) {
					image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
							+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">"
							+ image + "</a>";
				}
				image = image + imagetitle;
				content = content.replace(imagedata.asXML(), image);
			}
		}
		String htmlContentString = htmlContent.toString().replaceAll("<subscript>", "<sub>").replaceAll("</subscript>", "</sub>").replaceAll("<superscript>", "<sup>")
				.replaceAll("</superscript>", "</sup>").replaceAll("<mediaobject>", "").replaceAll("</mediaobject>", "").replaceAll("<imageobject>", "").replaceAll("</imageobject>", "")
				.replaceAll("<inlinemediaobject>", "").replaceAll("</inlinemediaobject>", "");
		return htmlContentString;
	}

	/*
	 * 解析xml选项富文本标签，返回标签中字符串
	 */
	private TkQuestionsInfo getElementHtmlContent(TkQuestionsInfo model, Element htmlContentElement, String imageUrl, String targetUrl, String simageurl, HttpServletRequest request) {
		int option = 1;
		for (Iterator itest_content = htmlContentElement.elementIterator("para"); itest_content.hasNext();) {
			String image = "";
			Element para = (Element) itest_content.next();
			String content = para.asXML();
			for (Iterator mediaobject = para.elementIterator("inlinemediaobject"); mediaobject.hasNext();) {
				image = "";
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				String imagetitle = "";
				if (imagedata.element("info") != null) {
					if (imagedata.element("info").element("title") != null) {
						if (imagedata.element("info").element("title").getText() != null) {
							imagetitle = imagedata.element("info").element("title").getText();
						}
					}
					if (imagedata.element("info").element("annotation") != null) {
						if (imagedata.element("info").element("annotation").getText() != null) {
							imagetitle = imagedata.element("info").element("annotation").getText();
						}
					}
				}
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth"));
				if (width > 60) {
					height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
					if (height > 60) {
						height = 60;// 控制图片大小不超过60*60，可以点击查看大图
					}
					width = 60;
				} else {
					if (height > 60) {
						height = 60;
					}
				}

				image += imagedata.attributeValue("fileref")
						.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, width, height, image, request);
				if (!"".equals(image) || null != image) {
					image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
							+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">"
							+ image + "</a>";
				}
				image = image + imagetitle;
				content = content.replace(imagedata.asXML(), image);
			}
			for (Iterator mediaobject = para.elementIterator("mediaobject"); mediaobject.hasNext();) {
				image = "";
				Element imagedata = ((Element) mediaobject.next()).element("imageobject").element("imagedata");
				String imagetitle = "";
				if (imagedata.element("info") != null) {
					if (imagedata.element("info").element("title") != null) {
						if (imagedata.element("info").element("title").getText() != null) {
							imagetitle = imagedata.element("info").element("title").getText();
						}
					}
					if (imagedata.element("info").element("annotation") != null) {
						if (imagedata.element("info").element("annotation").getText() != null) {
							imagetitle = imagedata.element("info").element("annotation").getText();
						}
					}
				}
				int width = Integer.parseInt(imagedata.attributeValue("width"));
				int height = Integer.parseInt(imagedata.attributeValue("depth"));
				if (width > 60) {
					height = Integer.parseInt(imagedata.attributeValue("depth")) / (width / 60);
					if (height > 60) {
						height = 60;// 控制图片大小不超过60*60，可以点击查看大图
					}
					width = 60;
				} else {
					if (height > 60) {
						height = 60;
					}
				}

				image += imagedata.attributeValue("fileref")
						.replace("Image/", "<img id=\"" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" src=\"" + simageurl + "s_").replace(".jpg", ".jpg\"/>");
				thumbnail(imageUrl, targetUrl, width, height, image, request);
				if (!"".equals(image) || null != image) {
					image = "<a id=\"A_" + imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "\" href=\"javascript:previewPic('"
							+ imagedata.attributeValue("fileref").replace("Image/", "").replace(".jpg", "") + "','" + simageurl + imagedata.attributeValue("fileref").replace("Image/", "") + "')\">"
							+ image + "</a>";
				}
				image = image + imagetitle;
				content = content.replace(imagedata.asXML(), image);
			}
			// 选项里面不要<p>标签
			content = content.replaceAll("<para>", "").replaceAll("</para>", "").replaceAll("<para align=\"center\">", "").replaceAll("<para/>", "").replaceAll("<para role=\"A\">", "")
					.replaceAll("<para role=\"B\">", "").replaceAll("<para role=\"C\">", "").replaceAll("<para role=\"D\">", "");
			content = content.replaceAll("<subscript>", "<sub>").replaceAll("</subscript>", "</sub>").replaceAll("<superscript>", "<sup>").replaceAll("</superscript>", "</sup>")
					.replaceAll("<mediaobject>", "").replaceAll("</mediaobject>", "").replaceAll("<imageobject>", "").replaceAll("</imageobject>", "").replaceAll("<inlinemediaobject>", "")
					.replaceAll("</inlinemediaobject>", "");
			model.setOptionContent(option, content);// 每个para标签为一个选项
			option++;
		}
		return model;
	}

	// 统一替换下划线
	public void replcaeUnderlinde(TkQuestionsInfo model) {
		// model.setTitle(model.getTitle().replaceAll("<emphasis role=\"underline\">",
		// "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>",
		// "</span>"));
		model.setTitle(model.getTitle().replaceAll("<emphasis role=\"underline\">", "").replaceAll("</emphasis>", ""));
		model.setTitlecontent(model.getTitlecontent().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		if (model.getOption1() != null) {
			model.setOption1(model.getOption1().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption2() != null) {
			model.setOption2(model.getOption2().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption3() != null) {
			model.setOption3(model.getOption3().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption4() != null) {
			model.setOption4(model.getOption4().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption5() != null) {
			model.setOption5(model.getOption5().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption6() != null) {
			model.setOption6(model.getOption6().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption7() != null) {
			model.setOption7(model.getOption7().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption8() != null) {
			model.setOption8(model.getOption8().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption9() != null) {
			model.setOption9(model.getOption9().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getOption10() != null) {
			model.setOption10(model.getOption10().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getRightans() != null) {
			model.setRightans(model.getRightans().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
		if (model.getDescript() != null) {
			model.setDescript(model.getDescript().replaceAll("<emphasis role=\"underline\">", "<span style=\"text-decoration:underline\">").replaceAll("</emphasis>", "</span>"));
		}
	}
}
