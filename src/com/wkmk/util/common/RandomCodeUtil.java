package com.wkmk.util.common;

import java.util.ArrayList;
import java.util.List;

import com.util.date.DateTime;
import com.util.encrypt.MD5;

/**
 * 随机码生成工具
 */
public class RandomCodeUtil {
	
	/**
	 * 获取班级随机密码
	 * @param list 系统已存在随机密码列表
	 * @param size 需要生成随机密码数量
	 * @return 8位随机字符串
	 */
	public static List<String> getClassPasswords(List<String> list, int size){
		List<String> cardnoList = new ArrayList<String>();
		try {
			if(size > 0){
				String cardno = null;
				if(list != null && list.size() > 0){
					while (true) {
						if(cardnoList.size() == size){
							return cardnoList;
						}
						cardno = getRandomStr(8).toUpperCase();
						if(!list.contains(cardno) && !cardnoList.contains(cardno)){
							cardnoList.add(cardno);
						}
					}
				}else {
					while (true) {
						if(cardnoList.size() == size){
							return cardnoList;
						}
						cardno = getRandomStr(8).toUpperCase();
						if(!cardnoList.contains(cardno)){
							cardnoList.add(cardno);
						}
					}
				}
			}
		} catch (Exception e) {
			cardnoList = null;
			e.printStackTrace();
		}
		return cardnoList;
	}
	
	/**
	 * 获取班级唯一编号
	 * @param list 系统已存在班级编号
	 * @return 10位随机字符串
	 */
	public static String getClassno(List<String> list){
		String cardno = null;
		try {
			if(list != null && list.size() > 0){
				while (true) {
					cardno = getRandomStr(10).toUpperCase();
					if(!list.contains(cardno)){
						return cardno;
					}
				}
			}else {
				cardno = getRandomStr(10).toUpperCase();
			}
		} catch (Exception e) {
			cardno = "0000000000";
			e.printStackTrace();
		}
		return cardno;
	}
	
	/*
	 * 获取随机字符串
	 * @param length 字符串长度
	 * @return
	 */
	private static String getRandomStr(int length) {
	    char hexDigits[] = { '0','1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	    try {
	        byte[] strTemp = MD5.getEncryptPwd(DateTime.getDate("HHmmssS")).getBytes();
	        int j = strTemp.length;
	        char str[] = new char[j * 2];
	        int k = 0;
	        for (int i = 0; i < j; i++) {
	            byte byte0 = strTemp[i];
	            str[k++] = hexDigits[byte0 >>> 2 & 0xf];
	            str[k++] = hexDigits[byte0 & 0xf];
	        }
	        return new String(str).substring(0, length);
	    } catch (Exception e) {
	    }
	    return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(getRandomStr(8));
//		List<String> list = getClassPasswords(null, 100);
//		for(int i=0; i<list.size(); i++){
//			System.out.println(list.get(i));
//		}
//	}
}  