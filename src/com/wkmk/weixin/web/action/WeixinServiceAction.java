package com.wkmk.weixin.web.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.util.common.EmojiFilter;
import com.wkmk.weixin.bo.MpMessageInfo;
import com.wkmk.weixin.bo.MpMessageInfoEvent;
import com.wkmk.weixin.mp.MpUtil;
import com.util.action.BaseAction;
import com.util.date.DateTime;

/**
 * 微信服务接口入口
 * @version 1.0
 */
public class WeixinServiceAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			request.setCharacterEncoding("UTF-8");
			// 校验
			if (MpUtil.checkSignature(request)) {
				//---------------公众号接口配置验证方法返回值--------------
//				String echostr = request.getParameter("echostr");
//				System.out.println("echostr=" + echostr);
//				response.getWriter().write(echostr);
				//------------------------------------------------
				// 校验数据通过
				String messagexml = MpUtil.readStreamParameter(request.getInputStream());
				//System.out.println(messagexml);
				PrintWriter out = response.getWriter();
				if (messagexml != null && !"".equals(messagexml)) {
					MpMessageInfo mmi = MpUtil.getMessageInfo(messagexml);
					// 根据操作的不同事件做响应
					String resultStr = "";
					if ("event".equals(mmi.getMsgType())) {
						MpMessageInfoEvent mmii = MpUtil.getMessageInfoEvent(messagexml);
						if ("subscribe".equals(mmii.getEvent())) {
							//获取关注公众号客户端用户信息
							String access_token = MpUtil.getAccessToken(MpUtil.APPID, MpUtil.APPSECRET);
							String userjson = MpUtil.getUserInfo0(access_token, mmi.getFromUserName());
							
							//可以存入数据库中，记录统计关注人数
							String contentStr = "欢迎关注" + MpUtil.APPNAME + "！";
							resultStr = MpUtil.getXMLText(mmi.getFromUserName(), mmi.getToUserName(), contentStr);
							//欢迎语改为图文消息
							//String descript = "微课慕课网(http://www.wkmk.com)是中国最大的微课慕课“汇聚”、“分享”平台，是教育部-北京师范大学-中国移动联合实验室“移动学习”研究成果展示平台，是中国教育学会全国微课程大赛的官方网站。微课慕课网汇集全国各省市一线教师的微课、慕课和教学文档资源，可供各层次学校，各年级、各学科的教师分享和使用。";
							//resultStr = MpUtil.getXMLOneNews(mmi.getFromUserName(), mmi.getToUserName(), "感谢您的关注，欢迎访问微课慕课网！", descript, "http://www.wkmk.com/weixin/images/welcome.jpg", "http://mp.weixin.qq.com/s?__biz=MzIwMTUyNDQ3MQ==&mid=400413264&idx=1&sn=7e4fa606a8640401d45c59848e95f2ef&scene=0#rd");
							
							//绑定用户
							SysUserAttentionManager manager = (SysUserAttentionManager) getBean("sysUserAttentionManager");
							SysUserAttention userAttention = manager.getSysUserAttentionByOpenid(mmi.getFromUserName());
							if(userAttention == null){
								userAttention = new SysUserAttention();
								String nickname = MpUtil.getUserInfoValue(userjson, "nickname");
								userAttention.setNickname(EmojiFilter.filterEmoji(nickname));
								userAttention.setOpenid(mmi.getFromUserName());
								userAttention.setSex(MpUtil.getUserInfoValue(userjson, "sex"));
								userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
								userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
								userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
								userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
								userAttention.setHeadimgurl(MpUtil.getUserInfoValue(userjson, "headimgurl"));
								userAttention.setCreatedate(DateTime.getDate());
								userAttention.setDeletedate("");
								userAttention.setRegistertime("");
								userAttention.setUnregistertime("");
								userAttention.setUserid(1);//默认游客用户
								userAttention.setUnitid(1);
								manager.addSysUserAttention(userAttention);
							}else {
								userAttention.setNickname(EmojiFilter.filterEmoji(MpUtil.getUserInfoValue(userjson, "nickname")));
								userAttention.setOpenid(mmi.getFromUserName());
								userAttention.setSex(MpUtil.getUserInfoValue(userjson, "sex"));
								userAttention.setLanguage(MpUtil.getUserInfoValue(userjson, "language"));
								userAttention.setCity(MpUtil.getUserInfoValue(userjson, "city"));
								userAttention.setProvince(MpUtil.getUserInfoValue(userjson, "province"));
								userAttention.setCountry(MpUtil.getUserInfoValue(userjson, "country"));
								userAttention.setHeadimgurl(MpUtil.getUserInfoValue(userjson, "headimgurl"));
								userAttention.setCreatedate(DateTime.getDate());
								manager.updateSysUserAttention(userAttention);
							}
							
						} else if ("unsubscribe".equals(mmii.getEvent())) {
							//取消绑定用户
							SysUserAttentionManager manager = (SysUserAttentionManager) getBean("sysUserAttentionManager");
							SysUserAttention userAttention = manager.getSysUserAttentionByOpenid(mmi.getFromUserName());
							if(userAttention != null){
								userAttention.setDeletedate(DateTime.getDate());
								manager.updateSysUserAttention(userAttention);
							}
						}
					} else {
						String content = "温馨提示：无法识别您的请求，请点击下面的“菜单按钮”进行操作，或直接点击【<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + MpUtil.APPID + "&redirect_uri=" + MpUtil.HOMEPAGE + "/weixinMenu.app&response_type=code&scope=snsapi_userinfo&state=K1001_0000#wechat_redirect\">首页</a>】进入！";
						resultStr = MpUtil.getXMLText(mmi.getFromUserName(), mmi.getToUserName(), content);
					}
					try {
						out.print(resultStr);
					} catch (Exception e) {
						out.print("");
					} finally {
						if (out != null) {
							out.flush();
							out.close();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}