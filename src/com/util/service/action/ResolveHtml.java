/**
 * Project Name:java2zyb
 * File Name:ResolveHtml.java
 * Package Name:com.util.service.action
 * Date:2016-11-14下午3:40:58
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.util.service.action;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.date.DateTime;

/**
 * ClassName:ResolveHtml <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-11-14 下午3:40:58 <br/>
 * @author   Administrator
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class ResolveHtml {
	/** 
     * 提取HTML文件的文本内容 
     *  
     * @author Jeelon 
     * @param html 
     *            提取的html文件名 
     * @return 返回提取内容String 
     */  
    public static String getDocument(File html,String newUUID) {  
        String text = "";
        try {  
            // 设置编码集  
            org.jsoup.nodes.Document doc = Jsoup.parse(html, "GBK");
            Elements imgs = doc.getElementsByTag("img");  
            
            for(Element img :imgs){  
                String src = img.attr("src");  
                  
                img.attr("src","/upload/unzip/"+DateTime.getDateYMD()+"/"+newUUID+"/"+src);  
            }
            text =doc.html();
            
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        return text;  
    }  
    
    public static void main(String[] args) {
		File example = new File("E:\\工程\\zyb\\WebRoot\\upload\\unzip\\0001_A.htm");
		String text = getDocument(example,"");
		System.out.println(text);
	}
}
