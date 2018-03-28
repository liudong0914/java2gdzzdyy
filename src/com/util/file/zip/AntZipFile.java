package com.util.file.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 利于ant包来压缩文件 可对空文件夹进行压缩<br>
 * 
 * 注：压缩文件的关键步骤在于对文件的读取，
 * 存放的时特别要注意文件的路径，最好的方式用相对路径，
 */
public class AntZipFile {
	boolean flag = true; // 压缩成功与否标志
	private ZipFile zipFile;
	private ZipEntry zipEntry;
	private ZipOutputStream zipOut; // 压缩Zip
	private static int bufSize; // size of bytes
	private byte[] buf;
	private int readedBytes;

	public AntZipFile() {
		this(1024);
	}

	public AntZipFile(int size) { 
		bufSize = size;
		this.buf = new byte[bufSize];
	}

	public boolean doZip(String srcStr, String targetStr, String targetName) {
		File zipDir = new File(srcStr);
		// 未指定压缩文件名，默认为"ZipFile"
		if (targetName == null || "".equals(targetName))
			targetName = "ZipFile";
		// 添加".zip"后缀
		if (!targetName.endsWith(".zip"))
			targetName += ".zip";
		try {
			this.zipOut = new ZipOutputStream(new FileOutputStream(new File(targetStr + File.separator + targetName)));
			zipOut.setEncoding("gbk");
			// 压缩文件
			compressFile(zipDir, "", zipOut);
			zipOut.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 压缩目录（包含空目录）与文件
	 * 
	 * @param srcFile 待压缩的目录与文件
	 * @param oppositePath 压缩的相对路径，用于按照原文件的目录结构压缩文件
	 * @param zipOut ZipOutputStream压缩流
	 * @throws IOException
	 */
	public void compressFile(File srcFile, String oppositePath, ZipOutputStream zipOut) throws IOException {
		/*
		 * 因为是空目录，所以要在结尾加一个"/"。 不然就会被当作是空文件。 ZipEntry的isDirectory()方法中,目录以"/"结尾.
		 * org.apache.tools.zip.ZipEntry : public boolean isDirectory() { return
		 * getName().endsWith("/"); }
		 */
		if (srcFile.isDirectory()) {// 为目录时
			String newOppositePath = oppositePath + srcFile.getName() + "/"; // 得到相对路径
			compressFolder(srcFile, newOppositePath, zipOut);
		}
		// 如果是文件则需读;如果是空目录则无需读，直接转到zipOut.closeEntry()。
		if (srcFile.isFile()) {// 为文件时
			ZipEntry zipe = new ZipEntry(oppositePath + srcFile.getName());
			try {
				zipOut.putNextEntry(zipe);
				FileInputStream fileIn = new FileInputStream(srcFile);
				while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
					zipOut.write(this.buf, 0, this.readedBytes);
				}
				fileIn.close();
				zipOut.closeEntry();
			} catch (Exception e) {
			}
		}

	}

	/**
	 * 递归完成目录文件读取，由compressFile()方法调用
	 * @param file 要读取的文件目录
	 * @param oppositePath
	 *            相对路径，用于按照原文件的目录结构压缩文件
	 * @param zipOut
	 *            ZipOutputStream压缩流
	 * @throws IOException
	 */
	private void compressFolder(File file, String oppositePath, ZipOutputStream zipOut) throws IOException {
		File[] files = file.listFiles();

		if (files.length == 0) {// 如果目录为空，则单独压缩空目录。
			String fileName = String.valueOf(files);
			/*
			 * 因为是空目录，所以要在结尾加一个"/"。 不然就会被当作是空文件。
			 * ZipEntry的isDirectory()方法中,目录以"/"结尾. org.apache.tools.zip.ZipEntry :
			 * public boolean isDirectory() { return getName().endsWith("/"); }
			 * 这里oppositePath已经为相对路径，故zipOut.putNextEntry(new
			 * ZipEntry(oppositePath));
			 */
			if (file.isDirectory())
				fileName = fileName + "/";// 此处不能用"\\"
			zipOut.putNextEntry(new ZipEntry(oppositePath));
			// 如果是文件则需读;如果是空目录则无需读，直接转到zipOut.closeEntry()。
			if (file.isFile()) {
				FileInputStream fileIn = new FileInputStream(file);
				while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
					zipOut.write(this.buf, 0, this.readedBytes);
				}
				fileIn.close();
			}
			zipOut.closeEntry();
		} else
			// 如果目录不为空,则分别处理目录和文件
			for (File subfile : files) {
				compressFile(subfile, oppositePath, zipOut);
			}
	}

	/**
	 * 解压指定zip文件
	 * @param srcZipFile 待解压的文件（包含路径）
	 * @param targetStr  解压存放的路径
	 * @param targetName 解压的存放的目录
	 */
	public void upZip(String srcZipFile, String targetStr, String targetName) {
		FileOutputStream fileOut;
		File file;
		InputStream inputStream;
		try {
			this.zipFile = new ZipFile(srcZipFile);
			// 遍历每个压缩文件
			for (Enumeration entries = this.zipFile.getEntries(); entries.hasMoreElements();) {
				zipEntry = (ZipEntry) entries.nextElement();
				file = new File(targetStr + "/" + targetName + "/" + zipEntry.getName());

				if (zipEntry.isDirectory()) {// 是目录，则创建之
					file.mkdirs();
				} else {// 是文件
					// 如果指定文件的父目录不存在,则创建之
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					// 写文件
					inputStream = zipFile.getInputStream(zipEntry);
					fileOut = new FileOutputStream(file);
					while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, this.readedBytes);
					}
					fileOut.close();
					inputStream.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置压缩或解压时缓冲区大小。
	 * @param bufSize  缓冲区大小
	 */
	public static void setBufSize(int bufSize) {
		AntZipFile.bufSize = bufSize;
	}

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		AntZipFile test = new AntZipFile();
//
//		test.doZip("c:/乱七八糟", "d:/乱七八糟的", "aa.zip");
//		test.upZip("d:/乱七八糟的/aa.zip", "e:/", "aa");
//
//	}

}
