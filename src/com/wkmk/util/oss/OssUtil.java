package com.wkmk.util.oss;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

/**
 * @Description:云存储OSS工具类
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date May 23, 20163:24:34 PM
 */
public class OssUtil {
	private static final String accessKeyId = "7FdfRrgh0PSrioIt";
	private static final String accessKeySecret = "DCrR4cM7WG8p58HLmgRqMG2G8GMaIy";
	//私有正式环境地址
	private static final String endpoint = "http://oss-cn-hangzhou-internal.aliyuncs.com";
	private static final String bucketName = "lmzyb-01";
	//公共测试地址
	//private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	//private static final String bucketName = "lmzyb-test";
	
	private OSSClient getOSSClient(){
		OSSClient ossclient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		return ossclient;
	}

	/**
	 * 流上传文件
	 * @param is 上传的文件流
	 * @param filepathname oss上存放的唯一键值，文件流会按照此指定键值生成对应的文件名称
	 * 如：filepathname=123.txt，则会在oss云存储的根目录中上传文件并且名称为123.txt
	 *    filepathname=1/vwh/2016/05/20/123.txt，则会在oss云存储自动创建相应的文件夹，并在对应的文件夹中上传文件并且名称为123.txt
	 */
	public void ossStreamUpload(InputStream is, String key_filepathname) {
		try {
			OSSClient ossclient = getOSSClient();
			ossclient.putObject(bucketName, key_filepathname, is);
			ossclient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 本地文件上传
	 * */
	public void ossFileUpload(String file, String key_filepathname) {
		File localfile = new File(file);
		ossFileUpload(localfile, key_filepathname);
	}
	
	/**
	 * 本地文件上传
	 * */
	public void ossFileUpload(File localfile, String key_filepathname) {
		OSSClient ossclient = getOSSClient();
		ossclient.putObject(bucketName, key_filepathname, localfile);
		ossclient.shutdown();
	}

	/**
	 * 创建文件夹
	 * */
	public void ossCreateFolder(String foldername) {
		OSSClient ossclient = getOSSClient();
		if (!foldername.endsWith("/")) {
			foldername += "/";
		}
		ossclient.putObject(bucketName, foldername, new ByteArrayInputStream(new byte[0]));
		ossclient.shutdown();
	}

	/**
	 * 流下载
	 */
	public void ossStreamDowmload(String keyname, String filename) {
		byte[] bytes = new byte[1024];
		int length = 0;
		OSSClient ossclient = getOSSClient();
		OSSObject ossobject = ossclient.getObject(bucketName, keyname);
		DataInputStream dis = new DataInputStream(ossobject.getObjectContent());
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename);
			while ((length = dis.read(bytes, 0, bytes.length)) > 0) {
				fos.write(bytes, 0, length);
				fos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos!=null){
					fos.close();					
				}
				if(dis!=null){
					dis.close();					
				}
			} catch (IOException e) {
			}
			ossclient.shutdown();
		}
	}

	/**
	 * 下载到本地
	 * */
	public void ossFileDowmload(String keyname, String filename) {
		OSSClient ossclient = getOSSClient();
		ossclient.getObject(new GetObjectRequest(bucketName, keyname), new File(filename));
		ossclient.shutdown();
	}

	public static void main(String[] args) throws IOException {
		OssUtil ot = new OssUtil();
		//ot.ossFileDowmload("119a374e2fcc26974959d5d46ed96109.mp4", "d:/aaa/123.mp4");
		//ot.ossCreateFolder("1/vwh/2015/");
		ot.ossFileUpload(new File("d:/test/123.png"), "test/123.png");
	}
}
