package com.wkmk.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.util.string.PublicResourceBundle;

/**
 * 数据常量
 */
public class Constants {
	//产品版本号规范标记：当前版本号_时间，如：V1.0.0.1_20170227130000
	public static final String PRODUCT_VERSION = "V1.0.0.0";
	
	public static final String PRODUCT_NAME = "职业核心能力课程平台";
	
	// 对象类型
	public static final Integer DEFAULT_UNITID = 1;
	public static final String DEFAULT_TYPENO = "codetype";// 默认码表类型
	public static final String LINKTYPE_TYPENO = "linktype";// 友情链接码表类型
	public static final String BULLETINTYPE_TYPENO = "bulletintype";// 公告码表类型
	public static final String WEBSERVICE_USERNAME = "WKMK";
	public static final String WEBSERVICE_PASSWORD = "WKMK-BJ";

	public static String[] CODETYPE_BLANK = {};

	// 系统初始化角色id
	public static final Integer SYS_ROLEID_0 = 1;// 产品管理员
	public static final Integer DEFAULT_COMPUTERID = 1;// 默认视频服务器id
	public static final Integer DEFAULT_COLUMNID = 1;// 默认教材目录id
	
	public static final Integer BUY_END_DAY = 180;//默认购买解题微课有效天数是180天，半年
	
	//用户变更手机号码每天允许发送短信数量
	public static final int CODETYPE_MOBILE_SMSCOUNT = 10;
	
	//用户修改支付密码每天允许发送短信数量
	public static final int CODETYPE_PAYPASSWORD_SMSCOUNT = 10;
	
	public static final Integer PAYPASSWORD_ERROR_COUNT = 3;//默认每天支付密码输入错误数限制
	//在线答疑提问和回答
	public static final Integer UPLOAD_IMAGE_COUNT = 4;//默认上传图片数量
	public static final Integer UPLOAD_AUDIO_COUNT = 2;//默认上传音频数量
	public static final Integer UPLOAD_VIDEO_COUNT = 1;//默认教师回复答疑上传视频数量
	public static final Float BUY_QUESTION_DISCOUNT = 5.0F;//默认购买已回复答疑折扣，计算方式：总价*折扣/10
	//教师在线回答提问，平台需要提出，教师可以得到的比例为80%，20%留平台 
	public static final Float ANSWER_QUESTION_PROPORTION = 0.8F;
	//学生购买已回复答疑分成比例，提问作者获得30%，解答教师获得50%，平台预留20%
	public static final Float SELL_QUESTION_PROPORTION = 0.3F;//提问作者卖题获取的提成
	public static final Float SELL_QUESTION_ANSWER_PROPORTION = 0.5F;//提问作者卖题，解答老师获取的提成
	//老师回答提问，学生没有确认，默认自动扣款给老师打钱时间为7天
	public static final Integer AUTO_PAY_DAY = 7;
	
	public static final String IS_DEBUG = "1";//是否开启在线答疑调试，可把过程时间设置小点，方便测试。AUTO_PAY_DAY等于15分钟

	// 状态
	public static String CODETYPE_STATUS1 = "status1";
	public static String[] CODETYPE_STATUS1_ID = { "0", "1" };
	public static String[] CODETYPE_STATUS1_NAME = { "禁用", "开通" };

	public static String CODETYPE_STATUS2 = "status2";
	public static String[] CODETYPE_STATUS2_ID = { "0", "1" };
	public static String[] CODETYPE_STATUS2_NAME = { "禁用", "正常" };

	public static String CODETYPE_STATUS3 = "status3";
	public static String[] CODETYPE_STATUS3_ID = { "0", "1", "2", "3" };
	public static String[] CODETYPE_STATUS3_NAME = { "申请", "正常", "禁用", "删除" };

	public static String CODETYPE_STATUS4 = "status4";
	public static String[] CODETYPE_STATUS4_ID = { "1", "2" };
	public static String[] CODETYPE_STATUS4_NAME = { "正常", "禁用" };

	// 性别
	public static String CODETYPE_SEX = "sex";
	public static String[] CODETYPE_SEX_ID = { "0", "1" };
	public static String[] CODETYPE_SEX_NAME = { "男", "女" };

	// 是否
	public static String CODETYPE_BOOLEAN = "boolean";
	public static String[] CODETYPE_BOOLEAN_ID = { "0", "1" };
	public static String[] CODETYPE_BOOLEAN_NAME = { "否", "是" };

	// 显示,隐藏
	public static String CODETYPE_VIEW = "view";
	public static String[] CODETYPE_VIEW_ID = { "0", "1" };
	public static String[] CODETYPE_VIEW_NAME = { "隐藏", "显示" };

	// 审核,未审核
	public static String CODETYPE_CHECK = "check";
	public static String[] CODETYPE_CHECK_ID = { "0", "1" };
	public static String[] CODETYPE_CHECK_NAME = { "待审核", "已审核" };

	// 56个民族
	public static String CODETYPE_NATION = "nation";
	public static String[] CODETYPE_NATION_ID = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
			"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
			"50", "51", "52", "53", "54", "55", "56", "99" };
	public static String[] CODETYPE_NATION_NAME = { "汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族",
			"满族", "侗族", "瑶族", "白族", "土家族", "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族",
			"纳西族", "景颇族", "柯尔克孜族", "土族", "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族", "塔吉克族",
			"怒族", "乌孜别克族", "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族",
			"基诺族", "其他" };

	// 教材版本
	public static String CODETYPE_VERSION = "version";
//	public static String[] CODETYPE_VERSION_ID = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
//			"14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31",
//			"32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
//			"50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67",
//			"68", "69", "70", "71", "72", "73", "74", "98", "99" };
//	public static String[] CODETYPE_VERSION_NAME = { "全国通用", "人教版", "人教新课标", "人教新目标", "人教新起点", "人教版PEP", "北师大版",
//			"华师大版", "苏教版", "浙教版", "上教版", "豫教版", "冀教版", "湘教版", "岳麓版", "鲁教版", "鄂教版", "粤教版", "陕教版", "川教版", "闽教版", "人民版",
//			"苏科版", "教科版", "浙科版", "上科版", "牛津版", "外研版", "外研版(一年级起点)", "外研版(三年级起点)", "深港版", "语文版", "沪教版", "中图版", "大象版",
//			"广州版", "江苏版", "青岛版", "长春版", "济南版", "上海版", "湘少版", "仁爱英语", "沪科版", "北京课改版", "中华书局版", "标准实验版", "人教旧大纲",
//			"商务星球版", "上海教育版", /*"牛津译林版"*/"译林版", "牛津深圳版", "牛津上海版", "牛津沈阳版", "牛津苏教版", "牛津广州版", "广东版", "西师大版", "新世纪版", "新蕾快乐英语",
//			"辽师大版", "科教版", "首师大版", "山东人民版", "商务印书馆", "地质出版社", "山东科技版", "红旗出版社", "江苏少儿版", "泰山出版社", "山东教育版", "人民美术出版社",
//			"辽海版", "沪粤版", "通用版", "其它" };
	public static String[] CODETYPE_VERSION_ID = { "1", "2", "7", "8", "16", "24", "28", "32", "33", "37", "39", "44", "51", "74", "98" };
	public static String[] CODETYPE_VERSION_NAME = { "QG", "R", "BS", "HS", "LJ", "JK", "WY", "YW", "HJ", "JS", "CC", "HK", "YL", "HY", "通用版" };

	// 产品类型
	public static String CODETYPE_PRODUCTTYPE = "producttype";
	public static String[] CODETYPE_PRODUCTTYPE_ID = { "1", "2", "3" };
	public static String[] CODETYPE_PRODUCTTYPE_NAME = { "自主产品", "合作产品", "第三方产品" };

	// 用户类型
	public static String CODETYPE_USERTYPE = "usertype";
	public static String[] CODETYPE_USERTYPE_ID = { "0", "1", "2", "3" };
	public static String[] CODETYPE_USERTYPE_NAME = { "系统用户", "老师", "学生", "家长" };

	// 证件类型
	public static String CODETYPE_CARDTYPE = "cardtype";
	public static String[] CODETYPE_CARDTYPE_ID = { "1", "2", "9" };
	public static String[] CODETYPE_CARDTYPE_NAME = { "身份证", "护照", "其他" };

	// 学历
	public static String CODETYPE_EDUCATION = "education";
	public static String[] CODETYPE_EDUCATION_ID = { "1", "2", "3", "4", "9" };
	public static String[] CODETYPE_EDUCATION_NAME = { "博士及以上", "硕士", "本科", "大专", "其他" };

	// 职称
	public static String CODETYPE_JOBTITLE = "jobtitle";
	public static String[] CODETYPE_JOBTITLE_ID = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	public static String[] CODETYPE_JOBTITLE_NAME = { "中学高级", "中学中级(一级)", "中学初级(二、三级)", "小学特技教师(小高高)", "小学高级", "小学一级",
			"小学二级", "小学三级", "其它" };

	// 允许评论
	public static String CODETYPE_CANCOMMENTTYPE = "cancomment";
	public static String[] CODETYPE_CANCOMMENTTYPE_ID = { "0", "1" };
	public static String[] CODETYPE_CANCOMMENTTYPE_NAME = { "不允许评论", "允许评论" };

	// 转码状态
	public static String CODETYPE_CONVERTSTATUS = "convertstatus";
	public static String[] CODETYPE_CONVERTSTATUS_ID = { "0", "1", "2" };
	public static String[] CODETYPE_CONVERTSTATUS_NAME = { "等待转换", "转换成功", "转换失败" };

	// 学校性质
	public static String CODETYPE_SCHOOLTYPE = "schooltype";
	// public static String[] CODETYPE_SCHOOLTYPE_ID = { "1", "2", "3", "4" };
	// public static String[] CODETYPE_SCHOOLTYPE_NAME = { "基础教育", "职业教育",
	// "高等教育", "继续教育" };
	public static String[] CODETYPE_SCHOOLTYPE_ID = { "1" };
	public static String[] CODETYPE_SCHOOLTYPE_NAME = { "基础教育" };

	// 知识点类型
	public static String CODETYPE_KNOPOINTTYPE = "knopointtype";
	public static String[] CODETYPE_KNOPOINTTYPE_ID = { "0", "1" };
	public static String[] CODETYPE_KNOPOINTTYPE_NAME = { "知识点大纲", "具体知识点" };

	// 题难易程度
	public static String CODETYPE_DIFFICULT = "difficult";
	//public static String[] CODETYPE_DIFFICULT_ID = { "1", "2", "3", "4", "5" };
	public static String[] CODETYPE_DIFFICULT_ID = { "A", "B", "C", "D", "E" };
	public static String[] CODETYPE_DIFFICULT_NAME = { "容易", "较易", "一般", "较难", "很难" };

	// 固定题型【后期可增加】
	public static String CODETYPE_QUESTIONSTYPE = "questionstype";
	//public static String[] CODETYPE_QUESTIONSTYPE_ID = { "A", "B", "C", "D", "E", "F", "M", "S" };
	//public static String[] CODETYPE_QUESTIONSTYPE_NAME = { "单选题", "多选题", "判断题", "连线题", "填空题", "英语完型填空题", "复合题", "其它主观题" };
	public static String[] CODETYPE_QUESTIONSTYPE_ID = { "A", "B", "C", "E", "M", "S" };
	public static String[] CODETYPE_QUESTIONSTYPE_NAME = { "单选题", "多选题", "判断题", "填空题", "复合题", "其它主观题" };

	// 课本版本
	public static String CODETYPE_USINGTYPE = "usingtype";
	public static String[] CODETYPE_USINGTYPE_ID = { "0", "1", "2" };
	public static String[] CODETYPE_USINGTYPE_NAME = { "全册", "上册", "下册" };
    
	// 用户所属学段
	public static String CODETYPE_XUEDUAN = "xueduan";
	//public static String[] CODETYPE_XUEDUAN_ID = { "1", "2", "3" };
	//public static String[] CODETYPE_XUEDUAN_NAME = { "小学", "初中", "高中" };
	public static String[] CODETYPE_XUEDUAN_ID = { "2", "3" };
	public static String[] CODETYPE_XUEDUAN_NAME = { "初中", "高中" };
	
	// 教师认证
	public static String CODETYPE_AUTHENTICATION = "authentication";
	public static String[] CODETYPE_AUTHENTICATION_ID = { "0", "1" };
	public static String[] CODETYPE_AUTHENTICATION_NAME = { "未认证", "已认证" };
	

	// 定价类型
    public static String CODETYPE_PRICETYPE = "pricetype";
	public static String[] CODETYPE_PRICETYPE_ID = { "1", "2" };
	public static String[] CODETYPE_PRICETYPE_NAME = { "解题微课定价", "举一反三微课定价" };
	

	// 作业本配套微课开放状态
	public static String CODETYPE_VODSTATE = "vodstate";
	public static String[] CODETYPE_VODSTATE_ID = { "0", "1", "2", "3" };
	public static String[] CODETYPE_VODSTATE_NAME = { "不开放", "开放解题微课", "开放举一反三微课", "全部开放" };
	
	
	//龙币活动类型
	public static String CODETYPE_GIVETYPE = "givetype";
	public static String[] CODETYPE_GIVETYPE_ID = { "1", "2", "3" };
	public static String[] CODETYPE_GIVETYPE_NAME = { "注册赠送", "充值赠送", "充值打折" };
	
	//龙币活动类型
	public static String CODETYPE_COURSETYPEID = "coursetypeid";
	public static String[] CODETYPE_COURSETYPEID_ID = { "1", "2", "3" };
	public static String[] CODETYPE_COURSETYPEID_NAME = { "院校企业", "退役军人", "医护行业" };
	
	// 允许创建课程
	public static String CODETYPE_CANADDCOURSE = "canaddcourse";
	public static String[] CODETYPE_CANADDCOURSE_ID = { "0", "1" };
	public static String[] CODETYPE_CANADDCOURSE_NAME = { "不允许", "允许" };
	
	//资源类型coursefiletype
	public static String COURSE_FILETYPE = "coursefiletype";
    public static String[] COURSE_FILETYPE_ID = { "1", "2","3","4" };
    public static String[] COURSE_FILETYPE_NAME = { "教案", "课件","习题","素材" };
	
	
	/**
	 * 获取码表id数组
	 * 
	 * @param name
	 *            String
	 * @return String[]
	 */
	public static String[] getCodeTypeid(String codetype) {
		if (CODETYPE_STATUS1.equals(codetype)) {
			return CODETYPE_STATUS1_ID;
		} else if (CODETYPE_STATUS2.equals(codetype)) {
			return CODETYPE_STATUS2_ID;
		} else if (CODETYPE_STATUS3.equals(codetype)) {
			return CODETYPE_STATUS3_ID;
		} else if (CODETYPE_STATUS4.equals(codetype)) {
			return CODETYPE_STATUS4_ID;
		} else if (CODETYPE_SEX.equals(codetype)) {
			return CODETYPE_SEX_ID;
		} else if (CODETYPE_BOOLEAN.equals(codetype)) {
			return CODETYPE_BOOLEAN_ID;
		} else if (CODETYPE_VIEW.equals(codetype)) {
			return CODETYPE_VIEW_ID;
		} else if (CODETYPE_CHECK.equals(codetype)) {
			return CODETYPE_CHECK_ID;
		} else if (CODETYPE_NATION.equals(codetype)) {
			return CODETYPE_NATION_ID;
		} else if (CODETYPE_PRODUCTTYPE.equals(codetype)) {
			return CODETYPE_PRODUCTTYPE_ID;
		} else if (CODETYPE_USERTYPE.equals(codetype)) {
			return CODETYPE_USERTYPE_ID;
		} else if (CODETYPE_CARDTYPE.equals(codetype)) {
			return CODETYPE_CARDTYPE_ID;
		} else if (CODETYPE_EDUCATION.equals(codetype)) {
			return CODETYPE_EDUCATION_ID;
		} else if (CODETYPE_JOBTITLE.equals(codetype)) {
			return CODETYPE_JOBTITLE_ID;
		} else if (CODETYPE_CANCOMMENTTYPE.equals(codetype)) {
			return CODETYPE_CANCOMMENTTYPE_ID;
		} else if (CODETYPE_CONVERTSTATUS.equals(codetype)) {
			return CODETYPE_CONVERTSTATUS_ID;
		} else if (CODETYPE_VERSION.equals(codetype)) {
			return CODETYPE_VERSION_ID;
		} else if (CODETYPE_SCHOOLTYPE.equals(codetype)) {
			return CODETYPE_SCHOOLTYPE_ID;
		} else if (CODETYPE_KNOPOINTTYPE.equals(codetype)) {
			return CODETYPE_KNOPOINTTYPE_ID;
		} else if (CODETYPE_DIFFICULT.equals(codetype)) {
			return CODETYPE_DIFFICULT_ID;
		} else if (CODETYPE_QUESTIONSTYPE.equals(codetype)) {
			return CODETYPE_QUESTIONSTYPE_ID;
		} else if (CODETYPE_USINGTYPE.equals(codetype)) {
			return CODETYPE_USINGTYPE_ID;
		} else if (CODETYPE_XUEDUAN.equals(codetype)) {
			return CODETYPE_XUEDUAN_ID;
		} else if (CODETYPE_AUTHENTICATION.equals(codetype)) {
			return CODETYPE_AUTHENTICATION_ID;
		} else if (CODETYPE_PRICETYPE.equals(codetype)) {
			return CODETYPE_PRICETYPE_ID;
		} else if (CODETYPE_VODSTATE.equals(codetype)) {
			return CODETYPE_VODSTATE_ID;
		} else if (CODETYPE_GIVETYPE.equals(codetype)) {
			return CODETYPE_GIVETYPE_ID;
		} else if (CODETYPE_COURSETYPEID.equals(codetype)) {
			return CODETYPE_COURSETYPEID_ID;
		} else if (CODETYPE_CANADDCOURSE.equals(codetype)) {
			return CODETYPE_CANADDCOURSE_ID;
		}else if (COURSE_FILETYPE.equals(codetype)) {
            return COURSE_FILETYPE_ID;
        }
		return CODETYPE_BLANK;
	}

	/**
	 * 获取码表值数组
	 * 
	 * @param name
	 *            String
	 * @return String[]
	 */
	public static String[] getCodeTypename(String codetype) {
		if (CODETYPE_STATUS1.equals(codetype)) {
			return CODETYPE_STATUS1_NAME;
		} else if (CODETYPE_STATUS2.equals(codetype)) {
			return CODETYPE_STATUS2_NAME;
		} else if (CODETYPE_STATUS3.equals(codetype)) {
			return CODETYPE_STATUS3_NAME;
		} else if (CODETYPE_STATUS4.equals(codetype)) {
			return CODETYPE_STATUS4_NAME;
		} else if (CODETYPE_SEX.equals(codetype)) {
			return CODETYPE_SEX_NAME;
		} else if (CODETYPE_BOOLEAN.equals(codetype)) {
			return CODETYPE_BOOLEAN_NAME;
		} else if (CODETYPE_VIEW.equals(codetype)) {
			return CODETYPE_VIEW_NAME;
		} else if (CODETYPE_CHECK.equals(codetype)) {
			return CODETYPE_CHECK_NAME;
		} else if (CODETYPE_NATION.equals(codetype)) {
			return CODETYPE_NATION_NAME;
		} else if (CODETYPE_PRODUCTTYPE.equals(codetype)) {
			return CODETYPE_PRODUCTTYPE_NAME;
		} else if (CODETYPE_USERTYPE.equals(codetype)) {
			return CODETYPE_USERTYPE_NAME;
		} else if (CODETYPE_CARDTYPE.equals(codetype)) {
			return CODETYPE_CARDTYPE_NAME;
		} else if (CODETYPE_EDUCATION.equals(codetype)) {
			return CODETYPE_EDUCATION_NAME;
		} else if (CODETYPE_JOBTITLE.equals(codetype)) {
			return CODETYPE_JOBTITLE_NAME;
		} else if (CODETYPE_CANCOMMENTTYPE.equals(codetype)) {
			return CODETYPE_CANCOMMENTTYPE_NAME;
		} else if (CODETYPE_CONVERTSTATUS.equals(codetype)) {
			return CODETYPE_CONVERTSTATUS_NAME;
		} else if (CODETYPE_VERSION.equals(codetype)) {
			return CODETYPE_VERSION_NAME;
		} else if (CODETYPE_SCHOOLTYPE.equals(codetype)) {
			return CODETYPE_SCHOOLTYPE_NAME;
		} else if (CODETYPE_KNOPOINTTYPE.equals(codetype)) {
			return CODETYPE_KNOPOINTTYPE_NAME;
		} else if (CODETYPE_DIFFICULT.equals(codetype)) {
			return CODETYPE_DIFFICULT_NAME;
		} else if (CODETYPE_QUESTIONSTYPE.equals(codetype)) {
			return CODETYPE_QUESTIONSTYPE_NAME;
		} else if (CODETYPE_USINGTYPE.equals(codetype)) {
			return CODETYPE_USINGTYPE_NAME;
		} else if (CODETYPE_XUEDUAN.equals(codetype)) {
			return CODETYPE_XUEDUAN_NAME;
		} else if (CODETYPE_AUTHENTICATION.equals(codetype)) {
			return CODETYPE_AUTHENTICATION_NAME;
		} else if (CODETYPE_PRICETYPE.equals(codetype)) {
			return CODETYPE_PRICETYPE_NAME;
		} else if (CODETYPE_VODSTATE.equals(codetype)) {
			return CODETYPE_VODSTATE_NAME;
		} else if (CODETYPE_GIVETYPE.equals(codetype)) {
			return CODETYPE_GIVETYPE_NAME;
		} else if (CODETYPE_COURSETYPEID.equals(codetype)) {
			return CODETYPE_COURSETYPEID_NAME;
		} else if (CODETYPE_CANADDCOURSE.equals(codetype)) {
			return CODETYPE_CANADDCOURSE_NAME;
		}else if (COURSE_FILETYPE.equals(codetype)) {
            return COURSE_FILETYPE_NAME;
        }
		return CODETYPE_BLANK;
	}

	/**
	 * 获取码表值
	 * 
	 * @param name
	 *            String
	 * @param id
	 *            String
	 * @return String
	 */
	public static String getCodeTypevalue(String codetype, String id) {
		String data = "";
		String[] objid = getCodeTypeid(codetype);
		String[] objname = getCodeTypename(codetype);
		for (int i = 0; i < objid.length; i++) {
			if (id.equals(objid[i])) {
				data = objname[i];
				break;
			}
		}
		return data;
	}

	/**
	 * 获取码表值
	 * 
	 * @param name
	 *            String
	 * @param id
	 *            String
	 * @return String
	 */
	public static String getCodeTypeId(String codetype, String name) {
		String data = "";
		String[] objid = getCodeTypeid(codetype);
		String[] objname = getCodeTypename(codetype);
		for (int i = 0; i < objname.length; i++) {
			if (name.equals(objname[i])) {
				data = objid[i];
				break;
			}
		}
		return data;
	}

	/**
	 * 获取列表
	 * 
	 * @param codetype
	 *            String
	 * @return List
	 */
	public static List getCodeType(String codetype) {
		List<HashMap> lst = new ArrayList<HashMap>();
		String[] objid = getCodeTypeid(codetype);
		String[] objname = getCodeTypename(codetype);
		for (int i = 0; i < objid.length; i++) {
			if (!"".equals(objid[i])) {
				HashMap hm = new HashMap();
				hm.put("id", objid[i]);
				hm.put("name", objname[i]);
				lst.add(hm);
			}
		}
		return lst;
	}

	/**
	 * 当前系统不可修改的角色列表
	 */
	public static List<Integer> getSysRoleids() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);

		return list;
	}

	// ===================================读取配置文件信息=============================================
	/*
	 * 系统默认单位id
	 */
	public static Integer getDefaultUnitid() {
		String unitid = PublicResourceBundle.getProperty("system", "default.unitid");
		if (unitid == null || "".equals(unitid)) {
			return DEFAULT_UNITID;
		} else {
			Integer uid = Integer.valueOf(unitid);
			return uid;
		}
	}

//	/*
//	 * 系统主页
//	 */
//	public static String getHomepage() {
//		String homepage = PublicResourceBundle.getProperty("system", "homepage");
//		return homepage;
//	}
}
