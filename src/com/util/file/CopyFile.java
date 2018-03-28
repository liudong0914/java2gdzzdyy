package com.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 拷贝文件
 */
public class CopyFile {

	/**
	 * 文件拷贝
	 * @param source 源文件（路径）
	 * @param dest 目标文件（路径）
	 */
	public static void copy(String source, String dest) {
		File sourceFile = new File(source);
		if (sourceFile.isFile())
			copyFile(source, dest);
		if (sourceFile.isDirectory())
			copyDict(source, dest);
	}

	private static void copyFile(String source, String dest) {
		FileInputStream inFile = null;
		FileOutputStream outFile = null;
		try {
			String path = dest.substring(0, dest.lastIndexOf("/")) + "/";
			//检查文件夹是否存在,如果不存在,新建一个
 	        if (!FileUtil.isExistDir(path)) {
 	        	FileUtil.creatDir(path);
 	        }
 	        
			File in = new File(source);
			File out = new File(dest);
			inFile = new FileInputStream(in);
			outFile = new FileOutputStream(out);
			byte[] buffer = new byte[1024];
			int i = 0;
			while ((i = inFile.read(buffer)) != -1) {
				outFile.write(buffer, 0, i);
			}
			inFile.close();
			outFile.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (inFile != null)
					inFile.close();
				if (outFile != null)
					outFile.close();
			} catch (IOException e) {
			}
			System.out.println("文件拷贝失败！");
		}
	}

	private static void copyDict(String source, String dest) {
		String path = dest.substring(0, dest.lastIndexOf("/")) + "/";
		//检查文件夹是否存在,如果不存在,新建一个
        if (!FileUtil.isExistDir(path)) {
          FileUtil.creatDir(path);
        }
	        
		String source1;
		String dest1;

		new File(dest).mkdirs();
		File[] file = (new File(source)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				source1 = source + "/" + file[i].getName();
				dest1 = dest + "/" + file[i].getName();
				copyFile(source1, dest1);
			}
			if (file[i].isDirectory()) {
				source1 = source + "/" + file[i].getName();
				dest1 = dest + "/" + file[i].getName();
				File dest2 = new File(dest1);
				dest2.mkdir();
				copyDict(source1, dest1);
			}
		}
	}
}
