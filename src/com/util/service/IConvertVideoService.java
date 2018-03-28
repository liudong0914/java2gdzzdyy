package com.util.service;

public interface IConvertVideoService {

	/**
	 * 增加待转换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param flvpath 转码后flv视频存放路径
	 * @param imgpath 视频截图路径
	 * @param imgwidth 视频截图宽度
	 * @param imgheight 视频截图高度
	 * @param second 视频截图时间点(单位：秒)
	 * @param level 0~9越大越高
	 * @param linkid 外部链接id
	 * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addVideoFile(String filename, long filesize, String srcpath, String flvpath, String imgpath, int imgwidth, int imgheight, String second, int level, String linkid, String client);
	
	/**
	 * 增加待转换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param imgwidth 视频截图宽度
	 * @param imgheight 视频截图高度
	 * @param second 视频截图时间点(单位：秒)
	 * @param level 0~9越大越高
	 * @param linkid 外部链接id
	 * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addVideoFile(String filename, long filesize, String srcpath, int imgwidth, int imgheight, String second, int level, String linkid, String client);
	
	/**
	 * 增加待转换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param imgwidth 视频截图宽度
	 * @param imgheight 视频截图高度
	 * @param second 视频截图时间点(单位：秒)
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addVideoFile(String filename, long filesize, String srcpath, int imgwidth, int imgheight, String second, String linkid);
	
	/**
	 * 增加待转换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param linkid 外部链接id
	 * * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addVideoFile(String filename, long filesize, String srcpath, String linkid, String client);
	
	/**
	 * 增加待转换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addVideoFile(String filename, long filesize, String srcpath, String linkid);
	
	/**
	 * 删除文档
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean delVideoFile(String linkid);
	
	/**
	 * 删除文档
	 * @param 外部链接id数组
	 * @return boolean
	 */
	public boolean delVideoFile(String[] linkid);
	
	/**
	 * 增加实时换视频文件
	 * @param filename 转换视频名称
	 * @param filesize 文件大小
	 * @param srcpath 源视频文件路径
	 * @param flvpath 转码后flv视频存放路径
	 * @param imgpath 视频截图路径
	 * @param imgwidth 视频截图宽度
	 * @param imgheight 视频截图高度
	 * @param second 视频截图时间点(单位：秒)
	 * @param level 0~9越大越高
	 * @param linkid 外部链接id
	 * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addConvertVideoFile(String filename, long filesize, String srcpath, String flvpath, String imgpath, int imgwidth, int imgheight, String second, int level, String linkid, String client, String userid);
}