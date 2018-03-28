package com.wkmk.util.file;

import java.io.File;

import com.util.file.FileUtil;
import com.util.image.ImageUtil;

/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Jul 13, 20163:19:51 PM
 */
public class ImageFile {

	public static void main(String[] args) throws Exception{
		File sourceFile0 = null;
		File[] files0 = null;
		File file0 = null;
		
		File sourceFile = new File("d:/lmzyb-img(120X60)");
		File[] files = sourceFile.listFiles();
		File file = null;
		for (int i = 0; i < files.length; i++) {
			file = files[i];
			if(file.isDirectory()){
				sourceFile0 = new File("d:/lmzyb-img(120X60)/" + file.getName());
				files0 = sourceFile0.listFiles();
				for (int m = 0; m < files0.length; m++) {
					file0 = files0[m];
					try {
						ImageUtil.generateThumbnails(sourceFile0.getPath() + "/" + file0.getName(), sourceFile0.getPath() + "/s_" + file0.getName(), 120, 60, false);
						FileUtil.deleteFile(file0.getPath());
					} catch (Exception e) {
						System.out.println("文件名：" + file0.getPath());
						//e.printStackTrace();
					}
					
				}
			}
		}
		System.out.println("完成");
	}
}
