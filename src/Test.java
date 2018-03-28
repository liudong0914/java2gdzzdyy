import java.util.ArrayList;
import java.util.List;

import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ZXingUtil;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.weixin.mp.MpUtil;


/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Mar 1, 20163:33:10 PM
 */
public class Test {

	public static void main(String[] args) {
		//ZXingUtil.encodeQRCodeImage("http://lmzyb.wkmk.com/weixin/tip/tips.html", null, "D:/123.png", 300, 300, null);
		/*
		for(int i=1; i<=29; i++){
			getTwoCodePath(i+"");
		}
		for(int i=1; i<=74; i++){
			getbeforeClassTwoCodePath(i+"");
			getteachingCaseTwoCodePath(i+"");
		}
		*/
		/*第一批*/
//		List<String> list = new ArrayList<String>();
//		list.add("SH0");
//		for(int i=1; i<=32; i++){
//			list.add("JL" + i);
//		}
//		for(int i=1; i<=32; i++){
//			list.add("HZ" + i);
//		}
//		for(int i=1; i<=32; i++){
//			list.add("JJ" + i);
//		}
//		for(int i=0, size=list.size(); i<size; i++){
//			getBookFilmTwoCodePath(list.get(i));
//		}
		/*第二批*/
		List<String> list = new ArrayList<String>();
		list.add("FF0");
		for(int i=1; i<=32; i++){
			list.add("ZX" + i);
		}
		for(int i=1; i<=32; i++){
			list.add("XX" + i);
		}
		for(int i=1; i<=21; i++){
			list.add("SZ" + i);
		}
		for(int i=0, size=list.size(); i<size; i++){
			getBookFilmTwoCodePath(list.get(i));
		}
	}
	
	/*
	 * 获取作业本二维码图片路径
	 */
	public static String getTwoCodePath(String bookid) {
		String twocodepath = "twocode/book/" + bookid + "/" + MD5.getEncryptPwd(bookid) + ".png";
		String filepath = "D:/" + twocodepath; // 全路径
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
	public static String getbeforeClassTwoCodePath(String bookcontentid) {
		String twocodepath = "twocode/book/1/" + bookcontentid + "/"
				+ MD5.getEncryptPwd(bookcontentid + "_1") + ".png";
		String filepath = "D:/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + bookcontentid + ":1";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookContent=" + tkBookContent.getBookcontentid() + ":1");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}

	/*
	 * 获取作业本具体作业教学案二维码图片路径
	 */
	public static String getteachingCaseTwoCodePath(String bookcontentid) {
		String twocodepath = "twocode/book/1/" + bookcontentid + "/"
				+ MD5.getEncryptPwd(bookcontentid + "_2") + ".png";
		String filepath = "D:/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + bookcontentid + ":2";
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		//String base64Encoder = Base64Utils.base64Encoder("TkBookContent=" + tkBookContent.getBookcontentid() + ":2");// 加密
		//ZXingUtil.encodeQRCodeImage(base64Encoder, null, filepath, 300, 300, null);
		return twocodepath;
	}
	
	/*
	 * 深圳职业学院童教授职业社会能力微课学习【提前生成二维码】
	 * twocodeno 二维码编号，提前定好内置
	 */
	public static String getBookFilmTwoCodePath(String twocodeno) {
		String twocodepath = "twocode/" + twocodeno + ".png";
		String filepath = "D:/" + twocodepath; // 全路径
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		String url = "http://www.hxnlmooc.com/twoCode.app?qrcodeno=" + twocodeno;
		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
		return twocodepath;
	}
}
