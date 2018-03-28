package com.util.service;

public interface IConvertFileService {

	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param spdfpath 生成pdf路径
	 * @param sswfpath 生成swf路径
	 * @param spngpath 生成png路径
	 * @param converttype 转换类型
	 * @param level 0~9越大越高
	 * @param linkid 外部链接id
	 * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, String spdfpath, String sswfpath, String spngpath, String converttype, int level, String linkid, String client);
	
	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param spdfpath 生成pdf路径
	 * @param sswfpath 生成swf路径
	 * @param spngpath 生成png路径
	 * @param converttype 转换类型
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, String spdfpath, String sswfpath, String spngpath, String converttype, String linkid);
	
	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param converttype 转换类型
	 * @param linkid 外部链接id
	 * @param client 客户端信息
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, String converttype, String linkid, String client);
	
	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param level 0~9越大越高
	 * @param converttype 转换类型
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, int level, String converttype, String linkid);
	
	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param converttype 转换类型
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, String converttype, String linkid);
	
	/**
	 * 增加待转换文档
	 * @param filename 转换文档名称
	 * @param filesize 文件大小
	 * @param sdocpath 源文件路径
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean addDocFile(String filename, long filesize, String sdocpath, String linkid);
	
	/**
	 * 删除文档
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean delDocFile(String linkid);
	
	/**
	 * 删除文档
	 * @param linkid 外部链接id
	 * @return boolean
	 */
	public boolean delDocFile(String[] linkid);
	
	
}