package com.wkmk.util.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.util.action.BaseAction;
import com.util.file.CopyFile;
import com.util.image.ImageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.util.common.TwoCodeUtil;

public class AnalysisPaperXmlAction extends BaseAction {
	/**
	 * 解析试卷Xml
	 * */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		// 默认给出关联用户和单位
		Integer userid = 2;
		Integer unitid = 1;
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo userInfo = suim.getSysUserInfo(userid);

		Map<String, Integer> map = new HashMap<String, Integer>();
		TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		// 获得dom解析工厂
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			// 读取配置文件
			String xmlpath = request.getSession().getServletContext().getRealPath("/") + "/upload/xml.txt";
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(xmlpath), "gb2312"));
			String urlcontent = input.readLine();
			String urls[] = urlcontent.split(";");
			String mainXmlUlr = "";// mainxml的路径
			String imageUrl = "";// 图片image存放路径
			String targetUrl = "";// 目标路径
			int sonquestion = 0;// 字体个数;
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
				}
			}
			int beign = targetUrl.indexOf("/upload");
			String simageurl = targetUrl.substring(beign);
			CopyFile.copy(imageUrl, targetUrl);
			// 获得dom解析器
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(mainXmlUlr));
			Element element = (Element) document.getDocumentElement();
			NodeList list = element.getElementsByTagName("test");
			for (int i = 0; i < list.getLength(); i++) {
				TkQuestionsInfo model = new TkQuestionsInfo();
				String question_title = "";// 没有图片的标题
				String option_count = "0";
				String subjecttype = "";
				String subjectid = "";
				String questiontype = "";//
				String question_content = "";// 题干
				String question_answercontent = "";// 答案解析
				String question_difficult = "";// 题目难度
				String question_optioncount = "";// 选项个数
				String ischildquestion = "0";// 是否为子题 1是子题 0不是子题
				String gradeid = "";
				int parentid = 0;
				int pointcount = 0;// 填空数
				Node booktitlemement = (Node) list.item(i);
				NodeList chidlist = booktitlemement.getChildNodes();
				for (int j = 0; j < chidlist.getLength(); j++) {
					if (chidlist.item(j).getNodeType() == Node.ELEMENT_NODE) {
						Node aa = chidlist.item(j);
						System.out.println(aa.getNodeValue());
						System.out.println(aa.toString());

						if ("type".equals(chidlist.item(j).getNodeName())) {
							subjecttype = chidlist.item(j).getFirstChild().getNodeValue();// 获取题型
						}
						if ("test_num".equals(chidlist.item(j).getNodeName())) {
							String test_num = chidlist.item(j).getFirstChild().getNodeValue();// 获取xml中题号
						}
						if ("test_content".equals(chidlist.item(j).getNodeName())) {
							NodeList paralist = chidlist.item(j).getChildNodes();
							String content = "";
							String title="";
							for (int k = 0; k < paralist.getLength(); k++) {
								if (paralist.item(k).getNodeType() == Node.ELEMENT_NODE) {
									if ("para".equals(paralist.item(k).getNodeName())) {
										String para = paralist.item(k).getFirstChild().getNodeValue().trim();
										String temptitle=para;
										Element imgobject = (Element) paralist.item(k);
										NodeList imglist = imgobject.getElementsByTagName("imagedata");
										for (int b = 0; b < imglist.getLength(); b++) {
											int begin = imglist.item(b).getAttributes().getNamedItem("fileref").toString().lastIndexOf("/");
											int end = imglist.item(b).getAttributes().getNamedItem("fileref").toString().length() - 1;
											String image = imglist.item(b).getAttributes().getNamedItem("fileref").toString().substring(begin + 1, end);
											int widht = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("width").getNodeValue());
											int height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue());
											if (widht > 60) {
												height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue()) / (widht / 60);
												if (height > 60) {
													height = 60;// 控制图片大小不超过60*60，可以点击查看大图
												}
												widht = 60;
											} else {
												if (height > 60) {
													height = 60;
												}
											}
											String imageparas = "";
											imageparas += imglist.item(b).getAttributes().getNamedItem("fileref").toString().replace("Image/", simageurl + "s_").replace("fileref=\"", "<img src=\"")
													.replace(".jpg\"", ".jpg\" />");// 获取图片路径
											if (!"".equals(imageparas) && null != imageparas) {
												imageparas = "<a href=\"javascript:previewPic('" + simageurl + image + "')\">" + imageparas + "</a>";
											}
											para = para + imageparas;
											temptitle=para.replace(imageparas, "");
											thumbnail(imageUrl, targetUrl, widht, height, image);
										}
										content += para;
										title+=temptitle;
									}
								}
							}
							question_content = content;
							question_title=title;
							
						}
						if ("option_count".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								option_count = chidlist.item(j).getFirstChild().getNodeValue();// 获取选项个数
							}
						}

						if ("space_count".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String space_count = chidlist.item(j).getFirstChild().getNodeValue();// 获取空格数
								pointcount = Integer.parseInt(space_count);
							} else {
								pointcount = 0;
							}
						}
						if ("difficulty".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String difficult = chidlist.item(j).getFirstChild().getNodeValue();// 获取难易度
								question_difficult = difficult;
							}
						}
						if ("answer_unicity".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String answer_unicity = chidlist.item(j).getFirstChild().getNodeValue();// 获取是否有标砖
								if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
									model.setIsrightans("1");
								} else {
									if ("否".equals(answer_unicity)) {
										model.setIsrightans("0");
									} else if ("是".equals(answer_unicity)) {
										model.setIsrightans("1");
									}
								}
							} else {
								if ("A".equals(subjecttype) || "B".equals(subjecttype) || "C".equals(subjecttype)) {
									model.setIsrightans("1");
								} else {
									model.setIsrightans("0");
								}
							}
						}
						if ("answer".equals(chidlist.item(j).getNodeName())) {
							NodeList paralist = chidlist.item(j).getChildNodes();
							String content = "";
							for (int k = 0; k < paralist.getLength(); k++) {
								if (paralist.item(k).getNodeType() == Node.ELEMENT_NODE) {
									if ("para".equals(paralist.item(k).getNodeName())&&paralist.item(k).getFirstChild()!=null) {
										if ("1".equals(model.getIsrightans()) && "E".equals(subjecttype)) {
											String para = paralist.item(k).getFirstChild().getNodeValue().replaceAll(";", "【】");// 获取答案
											content = para;
											String[] options = content.split("【】");
											for (int m = 0; m < options.length; m++) {
												model.setOptionContent((m + 1), options[m]);
											}
										} else {
											String para = paralist.item(k).getFirstChild().getNodeValue().trim();
											Element imgobject = (Element) paralist.item(k);
											NodeList imglist = imgobject.getElementsByTagName("imagedata");
											for (int b = 0; b < imglist.getLength(); b++) {
												int begin = imglist.item(b).getAttributes().getNamedItem("fileref").toString().lastIndexOf("/");
												int end = imglist.item(b).getAttributes().getNamedItem("fileref").toString().length() - 1;
												String image = imglist.item(b).getAttributes().getNamedItem("fileref").toString().substring(begin + 1, end);
												int widht = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("width").getNodeValue());
												int height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue());
												if (widht > 60) {
													height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue()) / (widht / 60);
													if (height > 60) {
														height = 60;// 控制图片大小不超过60*60，可以点击查看大图
													}
													widht = 60;
												} else {
													if (height > 60) {
														height = 60;
													}
												}
												String imageparas = "";
												imageparas = imglist.item(b).getAttributes().getNamedItem("fileref").toString().replace("Image/", simageurl + "s_")
														.replace("fileref=\"", "<img src=\"").replace(".jpg\"", ".jpg\" />");// 获取图片路径
												if (!"".equals(imageparas) && null != imageparas) {
													imageparas = "<a href=\"javascript:previewPic('" + simageurl + image + "')\">" + imageparas + "</a>";
												}
												para = para + imageparas;
												thumbnail(imageUrl, targetUrl, widht, height, image);
											}
											content += para;
											if ("E".equals(subjecttype)) {
												model.setOption1(content);
											}
										}
									}
								}
								model.setRightans(content);
							}

							// if (chidlist.item(j).getFirstChild() != null) {
							// if ("1".equals(model.getIsrightans())) {
							// String answer =
							// chidlist.item(j).getFirstChild().getNodeValue().replaceAll(";",
							// "【】");// 获取答案
							// model.setRightans(answer);
							// if ("E".equals(subjecttype)) {
							// String answers[] = answer.split("【】");
							// for (int k = 0; k < answers.length; k++) {
							// model.setOptionContent((k + 1), answers[k]);
							// }
							// }
							// } else {
							// String answer =
							// chidlist.item(j).getFirstChild().getNodeValue();
							// model.setOption1(answer);
							// if ("E".equals(subjecttype)) {
							// model.setRightans(answer);
							// }
							// }
							// }
						}
						if ("option_content".equals(chidlist.item(j).getNodeName())) {

							NodeList optionlist = chidlist.item(j).getChildNodes();
							int count = 1;
							for (int k = 0; k < optionlist.getLength(); k++) {
								if (optionlist.item(k).getNodeType() == Node.ELEMENT_NODE) {
									if ("para".equals(optionlist.item(k).getNodeName())) {
										String para = optionlist.item(k).getFirstChild().getNodeValue().trim();
										Element imgobject = (Element) optionlist.item(k);
										NodeList imglist = imgobject.getElementsByTagName("imagedata");
										for (int b = 0; b < imglist.getLength(); b++) {
											int begin = imglist.item(b).getAttributes().getNamedItem("fileref").toString().lastIndexOf("/");
											int end = imglist.item(b).getAttributes().getNamedItem("fileref").toString().length() - 1;
											String image = imglist.item(b).getAttributes().getNamedItem("fileref").toString().substring(begin + 1, end);
											int widht = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("width").getNodeValue());
											int height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue());
											if (widht > 60) {
												height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue()) / (widht / 60);
												if (height > 60) {
													height = 60;// 控制图片大小不超过60*60，可以点击查看大图
												}
												widht = 60;
											} else {
												if (height > 60) {
													height = 60;
												}
											}
											String imagespara = "";
											imagespara = imglist.item(b).getAttributes().getNamedItem("fileref").toString().replace("Image/", simageurl + "s_").replace("fileref=\"", "<img src=\"")
													.replace(".jpg\"", ".jpg\"  />");// 获取图片路径
											if (!"".equals(imagespara) && null != imagespara) {
												imagespara = "<a href=\"javascript:previewPic('" + simageurl + image + "')\">" + imagespara + "</a>";
											}
											para = para + imagespara;
											thumbnail(imageUrl, targetUrl, widht, height, image);
										}
										model.setOptionContent(count, para);
										count += 1;
									}
								}
							}
						}
						if ("value".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								if ("1".equals(model.getIsrightans())) {
									if ("E".equals(subjecttype)) {
										String value = chidlist.item(j).getFirstChild().getNodeValue();// 获取分值
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
										model.setScore(chidlist.item(j).getFirstChild().getNodeValue());
									}
								} else {
									model.setScore(chidlist.item(j).getFirstChild().getNodeValue());
								}
							}
						}
						if ("answer_analysis".equals(chidlist.item(j).getNodeName())) {
							NodeList answeparalist = chidlist.item(j).getChildNodes();
							String content = "";
							for (int k = 0; k < answeparalist.getLength(); k++) {
								if (answeparalist.item(k).getNodeType() == Node.ELEMENT_NODE) {
									if ("para".equals(answeparalist.item(k).getNodeName())) {
										if (answeparalist.item(k).getFirstChild() != null) {
											String para = answeparalist.item(k).getFirstChild().getNodeValue().trim();
											Element imgobject = (Element) answeparalist.item(k);
											NodeList imglist = imgobject.getElementsByTagName("imagedata");
											for (int b = 0; b < imglist.getLength(); b++) {
												int begin = imglist.item(b).getAttributes().getNamedItem("fileref").toString().lastIndexOf("/");
												int end = imglist.item(b).getAttributes().getNamedItem("fileref").toString().length() - 1;
												String image = imglist.item(b).getAttributes().getNamedItem("fileref").toString().substring(begin + 1, end);
												int widht = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("width").getNodeValue());
												int height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue());
												if (widht > 60) {
													height = Integer.parseInt(imglist.item(b).getAttributes().getNamedItem("depth").getNodeValue()) / (widht / 60);
													if (height > 60) {
														height = 60;// 控制图片大小不超过60*60，可以点击查看大图
													}
													widht = 60;
												} else {
													if (height > 60) {
														height = 60;
													}
												}
												String imagepara = "";
												imagepara += imglist.item(b).getAttributes().getNamedItem("fileref").toString().replace("Image/", simageurl + "s_")
														.replace("fileref=\"", "<img src=\"").replace(".jpg\"", ".jpg\" />");// 获取图片路径
												if (!"".equals(imagepara) || null != imagepara) {
													imagepara = "<a href=\"javascript:previewPic('" + simageurl + image + "')\">" + imagepara + "</a>";
												}
												para = para + imagepara;
												thumbnail(imageUrl, targetUrl, widht, height, image);
											}
											content += para;
											question_answercontent = content;
										}
									}
								}
							}
						}
						if ("area".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String area = chidlist.item(j).getFirstChild().getNodeValue();// 获取地域
								model.setArea(area);
							}
						}
						if ("year".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String year = chidlist.item(j).getFirstChild().getNodeValue();// 获取年份
								model.setTheyear(year);
							}
						}
						if ("subject".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								subjectid = chidlist.item(j).getFirstChild().getNodeValue();// 获取学科
								model.setSubjectid(Integer.parseInt(subjectid));
							}
						}
						if ("grade".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String grade = chidlist.item(j).getFirstChild().getNodeValue();// 获取年级
								gradeid = grade;
							}
						}
						if ("source".equals(chidlist.item(j).getNodeName())) {
							if (chidlist.item(j).getFirstChild() != null) {
								String source = chidlist.item(j).getFirstChild().getNodeValue();// 获取标签
								model.setTag(source);
							}
						}
						if ("test_children".equals(chidlist.item(j).getNodeName())) {
							subjecttype = "M";
							NodeList testchildrens = chidlist.item(j).getChildNodes();
							for (int k = 0; k < testchildrens.getLength(); k++) {
								if (testchildrens.item(k).getNodeType() == Node.ELEMENT_NODE) {
									sonquestion += 1;
								}
							}
						}
						if ("题号".equals(chidlist.item(j).getNodeName())) {
							ischildquestion = "1";
							sonindex += 1;
						}
					}
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
				if(question_title.length()>500){
					question_title= question_title.substring(0, 500);
				}
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
				model.setUnitid(unitid);
				model.setAuthorid(userid);
				model.setAuthorname(userInfo.getUsername());
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
	public static void thumbnail(String imageUrl, String targetUrl, int width, int height, String image) {
		if (width > 60)
			width = 60;
		File sourceFile = new File(imageUrl);
		File targeturl = new File(targetUrl);
		File[] files = sourceFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			try {
				String name = files[i].getName();
				if (name.equals(image)) {
					ImageUtil.generateThumbnailsSubImage(files[i].toString(), targeturl + "/s_" + files[i].getName(), width, height, false);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
