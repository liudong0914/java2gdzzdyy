package com.util.file;

import com.util.encrypt.MD5;

/**
 * 转码文件路径转换处理工具
 * 因为转码后的图片与视频或文档存放在一个目录下面，而且名字相同，所有很容易根据图片路径猜出视频或文档路径，上传原始文件的路径是md5加密的。
 * 图片需要在此基础上用md5再次加密一次，而且转码后的视频或文档需要用md5加密二次,这样就无法猜测路径规则。
 */
public class ConvertFilepathUtil {

	/**
	 * 获取文档截图图片路径
	 */
	public static String getSpngpath(String sdocpath){
		String path0 = sdocpath.substring(0,sdocpath.lastIndexOf("/") + 1);
		String path = sdocpath.substring(sdocpath.lastIndexOf("/") + 1, sdocpath.lastIndexOf("."));
		String spngpath = path0 + MD5.getEncryptPwd(path) + ".png";
		return spngpath;
	}
	
	/**
	 * 获取文档转码pdf路径
	 */
	public static String getSpdfpath(String sdocpath){
		String path0 = sdocpath.substring(0,sdocpath.lastIndexOf("/") + 1);
		String path = sdocpath.substring(sdocpath.lastIndexOf("/") + 1, sdocpath.lastIndexOf("."));
		String spdfpath = path0 + MD5.getEncryptPwd(MD5.getEncryptPwd(path)) + ".pdf";
		return spdfpath;
	}
	
	/**
	 * 获取文档转码swf路径
	 */
	public static String getSswfpath(String sdocpath){
		return getSswfpath(sdocpath, "1");
	}
	
	/**
	 * 获取文档转码swf路径
	 */
	public static String getSswfpath(String sdocpath, String converttype){
		String path0 = sdocpath.substring(0,sdocpath.lastIndexOf("/") + 1);
		String path = sdocpath.substring(sdocpath.lastIndexOf("/") + 1, sdocpath.lastIndexOf("."));
		String sswfpath = path0 + MD5.getEncryptPwd(MD5.getEncryptPwd(MD5.getEncryptPwd(path)));
		if(!"2".equals(converttype)){
			sswfpath = sswfpath + ".swf";
		}
		return sswfpath;
	}
	
	/**
	 * 获取视频截取图片路径
	 */
	public static String getImgpath(String srcpath){
		String path0 = srcpath.substring(0,srcpath.lastIndexOf("/") + 1);
		String path = srcpath.substring(srcpath.lastIndexOf("/") + 1, srcpath.lastIndexOf("."));
		String imgpath = path0 + MD5.getEncryptPwd(path) + ".jpg";
		return imgpath;
	}
	
	/**
	 * 获取视频转码路径
	 */
	public static String getFlvpath(String srcpath){
		String path0 = srcpath.substring(0,srcpath.lastIndexOf("/") + 1);
		String path = srcpath.substring(srcpath.lastIndexOf("/") + 1, srcpath.lastIndexOf("."));
		//String flvpath = path0 + MD5.getEncryptPwd(MD5.getEncryptPwd(path)) + ".flv";
		String flvpath = path0 + MD5.getEncryptPwd(MD5.getEncryptPwd(path)) + ".mp4";
		return flvpath;
	}
	
	/**
	 * 获取音频转码路径
	 */
	public static String getMp3path(String srcpath){
		String path0 = srcpath.substring(0,srcpath.lastIndexOf("/") + 1);
		String path = srcpath.substring(srcpath.lastIndexOf("/") + 1, srcpath.lastIndexOf("."));
		String flvpath = path0 + MD5.getEncryptPwd(MD5.getEncryptPwd(path)) + ".mp3";
		return flvpath;
	}
}
