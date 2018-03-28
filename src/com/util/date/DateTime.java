package com.util.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {

  public static String getDate(String sMask) {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat(sMask);
      return formatter.format(new Date());
    } catch (Exception e) {
    }
    return "";
  }

  public static String getDate() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return formatter.format(new Date());
    } catch (Exception e) {
    }
    return "";
  }

  public static String getDateYMD() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("yyyy-MM-dd");
      return formatter.format(new Date());
    } catch (Exception e) {
    }
    return "";
  }

  public static String getDateCN() {
    SimpleDateFormat formatter;
    try
    {
      formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	  return formatter.format(new Date());
	} catch (Exception e) {
	}
	return "";
  }

  public static String getDateYMDCN() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("yyyy年MM月dd日");
	  return formatter.format(new Date());
	} catch (Exception e) {
	}
	return "";
  }

  public static String getDateYMDWCN() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("yyyy年MM月dd日 E");
	  return formatter.format(new Date());
	} catch (Exception e) {
	}
	return "";
  }

  public static String getDateYear() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("yyyy");
	  return formatter.format(new Date());
	} catch (Exception e) {
	}
	return "";
  }

  public static String getDateMonth() {
    SimpleDateFormat formatter;
    try {
      formatter = new SimpleDateFormat("MM");
	  return formatter.format(new Date());
	} catch (Exception e) {
	}
	return "";
  }

  public static String getDateCN(String sDate, String DelimeterChar) {
    String restr = "";
	String[] tmpArr = sDate.split(DelimeterChar);
	String[] dArr = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
	for (int i = 0; i < 10; ++i) {
	  Integer x = new Integer(i);
	  String temp = x.toString();
	  tmpArr[0] = tmpArr[0].replaceAll(temp, dArr[i]);
	}
	tmpArr[0] = tmpArr[0] + "年";
	if (tmpArr[1].length() == 1) {
	  tmpArr[1] = dArr[Integer.parseInt(tmpArr[1])] + "月";
	}
	else if (tmpArr[1].substring(0, 1).equals("0")) {
	  tmpArr[1] = dArr[Integer.parseInt(tmpArr[1].substring(tmpArr[1].length() - 1, tmpArr[1].length()))] + "月";
	}
	else {
	  tmpArr[1] = "十" + dArr[Integer.parseInt(tmpArr[1].substring(tmpArr[1].length() - 1, tmpArr[1].length()))] + "月";
	  tmpArr[1] = tmpArr[1].replaceAll("零", "");
	}
	
	if (tmpArr[2].length() == 1) {
	  tmpArr[2] = dArr[Integer.parseInt(tmpArr[2])] + "日";
	}
	else if (tmpArr[2].substring(0, 1).equals("0")) {
	  tmpArr[2] = dArr[Integer.parseInt(tmpArr[2].substring(tmpArr[2].length() - 1, tmpArr[2].length()))] + "日";
	}
	else {
	  tmpArr[2] = dArr[Integer.parseInt(tmpArr[2].substring(0, 1))] + "十" + dArr[Integer.parseInt(tmpArr[2].substring(tmpArr[2].length() - 1, tmpArr[2].length()))] + "日";
	  tmpArr[2] = tmpArr[2].replaceAll("零", "");
	}

    return tmpArr[0] + tmpArr[1] + tmpArr[2];
  }

  public static int getYearOfDate() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(1);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getMonthOfYear() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return (1 + calendar.get(2));
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getDayOfMonth() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(5);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getDayOfYear() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(6);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getDayOfWeek() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(7);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getWeekOfYear() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(3);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getHouseOfDay() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(11);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getMinuteOfHouse() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(12);
    } catch (Exception e) {
    }
    return 0;
  }

  public static int getSecondOfMinute() {
    Calendar calendar;
    try {
      calendar = Calendar.getInstance();
      Date curTime = new Date();
      calendar.setTime(curTime);
      return calendar.get(13);
    } catch (Exception e) {
    }
    return 0;
  }
  
  /**
	 * 两个日期相比较
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDateCal(String date1,String date2) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date _date1=df.parse(date1);
			Date _date2=df.parse(date2);
			
		    long day = (_date1.getTime()-_date2.getTime())/(24*60*60*1000);
		    return day;
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * 两个日期比较
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getCompareTo(String date1,String date2) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date _date1=df.parse(date1);
			Date _date2=df.parse(date2);
			
			if(_date1.compareTo(_date2) < 0){
				return -1;
			}
			else if(_date1.compareTo(_date2) ==0){
				return 0;
			}
			else{
				return 1;
			}
		} catch (Exception e) {
			return -2;
		}
	}
	
	/**
	 * 两个日期比较
	 * @param date1
	 * @param date2
	 * @return 1表示date1>date2
	 */
	public static int getCompareToDate(String date1,String date2) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date _date1=df.parse(date1);
			Date _date2=df.parse(date2);
			//比较两个日期的顺序【如果参数 Date 等于此 Date，则返回值 0；如果此 Date 在 Date 参数之前，则返回小于 0 的值；如果此 Date 在 Date 参数之后，则返回大于 0 的值。】
			if(_date1.compareTo(_date2) < 0){
				return -1;
			}
			else if(_date1.compareTo(_date2) ==0){
				return 0;
			}
			else{
				return 1;
			}
		} catch (Exception e) {
			return -2;
		}
	}

//  public static void main(String[] args) {
//    System.out.println(getDate());
//    System.out.println(getDayOfWeek());
//    System.out.println(getDateCN(getDate(), "-"));
//  }
}
