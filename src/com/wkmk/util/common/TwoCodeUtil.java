package com.wkmk.util.common;

import javax.servlet.http.HttpServletRequest;

import com.util.encrypt.Base64Utils;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ZXingUtil;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.weixin.mp.MpUtil;

/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Mar 1, 20165:00:12 PM
 */
public class TwoCodeUtil {

	/*
	 * 获取作业本二维码图片路径
	 */
	public static String getTwoCodePath(String bookid, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/book/" + bookid + "/" + MD5.getEncryptPwd(bookid) + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookInfo=" + bookid;
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookInfo=" + bookid);// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 获取作业本具体作业课前预习二维码图片路径
	 */
	public static String getbeforeClassTwoCodePath(TkBookContent tkBookContent, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/book/" + tkBookContent.getBookid() + "/" + tkBookContent.getBookcontentid() + "/"
				+ MD5.getEncryptPwd(tkBookContent.getBookcontentid() + "_1") + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + tkBookContent.getBookcontentid() + ":1";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookContent=" + tkBookContent.getBookcontentid() + ":1");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}

	/*
	 * 获取作业本具体作业教学案二维码图片路径
	 */
	public static String getteachingCaseTwoCodePath(TkBookContent tkBookContent, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/book/" + tkBookContent.getBookid() + "/" + tkBookContent.getBookcontentid() + "/"
				+ MD5.getEncryptPwd(tkBookContent.getBookcontentid() + "_2") + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + tkBookContent.getBookcontentid() + ":2";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookContent=" + tkBookContent.getBookcontentid() + ":2");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}

	/*
	 * 获取作业本具体作业教学案二维码图片路径
	 */
	public static String getMp3pathTwoCodePath(TkBookContent tkBookContent, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/book/" + tkBookContent.getBookid() + "/" + tkBookContent.getBookcontentid() + "/"
				+ MD5.getEncryptPwd(tkBookContent.getBookcontentid() + "_3") + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + tkBookContent.getBookcontentid() + ":3";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookContent=" + tkBookContent.getBookcontentid() + ":3");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 获取微课二维码图片路径
	 */
	public static String getFilmTwoCodePath(TkQuestionsInfo tkQuestionsInfo, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/question/" + tkQuestionsInfo.getSubjectid() + "/" + tkQuestionsInfo.getGradeid() + "/" + MD5.getEncryptPwd(tkQuestionsInfo.getQuestionid() + "_1") + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkQuestionsInfo=" + tkQuestionsInfo.getQuestionid() + ":1";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkQuestionsInfo=" + tkQuestionsInfo.getQuestionid() + ":1");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}

	/*
	 * 获取举一反三二维码图片路径
	 */
	public static String getSimilarTwoCodePath(TkQuestionsInfo tkQuestionsInfo, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/question/" + tkQuestionsInfo.getSubjectid() + "/" + tkQuestionsInfo.getGradeid() + "/" + MD5.getEncryptPwd(tkQuestionsInfo.getQuestionid() + "_2") + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkQuestionsInfo=" + tkQuestionsInfo.getQuestionid() + ":2";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkQuestionsInfo=" + tkQuestionsInfo.getQuestionid() + ":2");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 获取班级二维码图片路径
	 */
	public static String getTwoCodePath(TkClassInfo tkClassInfo, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/class/" + tkClassInfo.getUnitid() + "/" + MD5.getEncryptPwd(tkClassInfo.getClassid().toString()) + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkClassInfo=" + tkClassInfo.getClassid();
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkClassInfo=" + tkClassInfo.getClassid());// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 获取微课二维码图片路径
	 */
	public static String getTwoCodePath(VwhFilmInfo vwhFilmInfo, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/video/" + vwhFilmInfo.getEduGradeInfo().getSubjectid() + "/" + MD5.getEncryptPwd(vwhFilmInfo.getFilmid().toString()) + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?VwhFilmInfo=" + vwhFilmInfo.getFilmid();
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("VwhFilmInfo=" + vwhFilmInfo.getFilmid());// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 获取解题微课二维码图片路径
	 */
	public static String getTwoCodePath(TkBookContentFilm tkBookContentFilm, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "twocode/contentfilm/" + tkBookContentFilm.getBookid() + "/" + tkBookContentFilm.getBookcontentid() + "/" + MD5.getEncryptPwd(tkBookContentFilm.getFid().toString()) + ".png";
		String filepath = realpath + "/upload/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContentFilm=" + tkBookContentFilm.getFid();
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("VwhFilmInfo=" + vwhFilmInfo.getFilmid());// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 深圳职业学院童教授职业社会能力微课学习【提前生成二维码】
	 * twocodeno 二维码编号，提前定好内置
	 */
	public static String getBookFilmTwoCodePath(String twocodeno, HttpServletRequest request) {
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String twocodepath = "upload/twocode/course/film/" + twocodeno + ".png";
		String filepath = realpath + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?qrcodeno=" + twocodeno;
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		return twocodepath;
	}
}
