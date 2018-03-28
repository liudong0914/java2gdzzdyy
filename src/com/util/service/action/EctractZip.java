package com.util.service.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.util.date.DateTime;
import com.util.string.PublicResourceBundle;
import com.util.string.UUID;

/**
 * 解压缩测试
 * 
 * @说明
 * @author cuisuqiang
 * @version 1.0
 * @since
 */
public class EctractZip {

	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) {
		String zipFilePath = PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.fileupload") + "wotemplate.zip";
		String destDir = "E:\\工程\\zyb\\WebRoot\\upload\\unzip\\" + UUID.getNewUUID() + "\\";
		EctractZip.unZip(zipFilePath, destDir);
		System.out.println("********执行成功，路径为destDir=**********" + destDir);
	}

	/** 使用GBK编码可以避免压缩中文文件名乱码 */
	private static final String CHINESE_CHARSET = "GBK";
	/** 文件读取缓冲区大小 */
	private static final int CACHE_SIZE = 1024;

	public static void unZip(String zipFilePath, String destDir) {
		ZipFile zipFile = null;
		try {
			BufferedInputStream bis = null;
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);
			Enumeration<ZipEntry> zipEntries = zipFile.getEntries();
			File file, parentFile;
			ZipEntry entry;
			byte[] cache = new byte[CACHE_SIZE];
			while (zipEntries.hasMoreElements()) {
				entry = (ZipEntry) zipEntries.nextElement();
				if (entry.isDirectory()) {
					new File(destDir + entry.getName()).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				file = new File(destDir + entry.getName());
				parentFile = file.getParentFile();
				if (parentFile != null && (!parentFile.exists())) {
					parentFile.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, CACHE_SIZE);
				int readIndex = 0;
				while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {
					fos.write(cache, 0, readIndex);
				}
				bos.flush();
				bos.close();
				fos.close();
				bis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}