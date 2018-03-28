package com.wkmk.util.oss;

import com.util.file.FileUtil;

/**
 * 拷贝文件
 */
public class OssCopyFile {

	/**
	 * oss存储上的文件拷贝到本地磁盘
	 * @param source 源文件（路径）
	 * @param dest 目标文件（路径）
	 */
	public static void copyOssToDisk(String keyname, String dest) {
		try {
			String path = dest.substring(0, dest.lastIndexOf("/")) + "/";
			//检查文件夹是否存在,如果不存在,新建一个
 	        if (!FileUtil.isExistDir(path)) {
 	        	FileUtil.creatDir(path);
 	        }
 	        
 	        OssUtil ossUtil = new OssUtil();
 	        ossUtil.ossFileDowmload(keyname, dest);
 	        
		} catch (Exception ex) {
			System.out.println("文件拷贝失败！");
		}
	}
	
	/**
	 * oss存储上的文件拷贝到本地磁盘
	 * @param source 源文件（路径）
	 * @param dest 目标文件（路径）
	 */
	public static void copyDiskToOss(String source, String keyname) {
		try {
 	        OssUtil ossUtil = new OssUtil();
 	        ossUtil.ossFileUpload(source, keyname);
 	        
		} catch (Exception ex) {
			System.out.println("文件拷贝失败！");
		}
	}
}
