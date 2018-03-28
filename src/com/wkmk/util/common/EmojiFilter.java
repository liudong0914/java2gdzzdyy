package com.wkmk.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:表情字符过滤器，因为表情字符插入数据库报错
 * @version 1.0
 * @Date Dec 7, 201611:08:11 AM
 */
public class EmojiFilter {

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     */
    public static String filterEmoji(String source) {
    	 String resultSource = "";
    	try {
    		CharSequence sourceCharSequence = source.subSequence(0, source.length());
        	//String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        	String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]|[\ue000-\uefff]";
            String replaceString = "";//特殊字符被替换字符串，默认为空字符串
            Pattern emoji = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);  
            Matcher emojiMatcher = emoji.matcher(sourceCharSequence);  
            resultSource = emojiMatcher.replaceAll(replaceString);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return resultSource;
    }
}
