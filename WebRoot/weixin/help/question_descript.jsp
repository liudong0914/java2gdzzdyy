<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>答疑规则</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="javascript:history.back();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">答疑规则</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<logic:equal value="1" name="usertype">
<div class="service">
    <p class="service_p service_p01" style="line-height:22px;">
    1、任何教师都可回答免费提问问题，只有经过<a href="/weixinHelp.app?method=answertips&userid=${userid }" style="color:#0000ff;">《教师资格认证》</a>通过的老师才可以回答付费答疑提问，挣取学币并可提现。<br />
    2、不可恶意抢单不回答问题（必须在提问设置的有效期内完成回答），超过3次抢单成功未回复，禁用账号1周，每增加一次抢单成功但不回答问题，就多禁用1周。也不可乱回答问题，否则被投诉后，账号可能会被禁用，多次被投诉会导致账号永久禁用。<br />
    3、对于付费答疑，认证教师回答后，学生在<%=Constants.AUTO_PAY_DAY %>天内未进行确认付款，也未投诉回答内容，提问设置的学币金额的80%将会在<%=Constants.AUTO_PAY_DAY %>后自动打款给回答提问的老师，平台提成20%。<br />
    4、付费答疑交易完成（即确认付款）后，当前答疑可进行售卖，价格为“提问金额”的一半，任何人想要查看问题的回答内容，需要支付购买才可查看。假设“问题金额”为10学币，交易完成后，售卖价格为5学币，每售卖一次，自己可从中获得的收益如下（公式）：<br />
    &nbsp;&nbsp;&nbsp;&nbsp;10(原价)*<%=Constants.BUY_QUESTION_DISCOUNT/10 %>(折扣)*<%=Constants.SELL_QUESTION_ANSWER_PROPORTION %>(分成比例) = <%=10*(Constants.BUY_QUESTION_DISCOUNT/10)*Constants.SELL_QUESTION_ANSWER_PROPORTION %>(学币)
    </p>
</div>
</logic:equal>

<logic:equal value="2" name="usertype">
<div class="service">
    <p class="service_p service_p01" style="line-height:22px;">
    1、发布提问时选择的“问题金额”，即老师回答问题获得的报酬。默认为1学币，可根据自己的实际情况选择其他金额，也可选择免费，即发布免费答疑。<br />
    &nbsp;&nbsp;&nbsp;&nbsp;1)免费答疑，任何老师都可以回答提问，回答内容格式有文字、图片、语音、解题微课，并且任何人都可对当前提问进行评论回复。<br />
    &nbsp;&nbsp;&nbsp;&nbsp;2)付费答疑，只有经过教师资格认证通过（专业教师）的老师才可以回答提问，回答内容格式有文字、图片、语音、解题微课。只能是自己、回答提问教师、购买当前答疑提问的人才可对当前提问和答案发表评价。<br />
    2、发布提问时选择的“回答最佳时限”，即提交发布问题后开始计时，老师必须在此设置时间前回答问题，否则此问题将失效。付费答疑提问失效或未完成交易的，学币将自动退回您当前账户余额中。当发布的付费答疑无人回答时，将自动转化成免费答疑供大家评论交流。<br />
    3、对于付费答疑，认证教师回答后，需在<%=Constants.AUTO_PAY_DAY %>天内进行确认付款，或投诉回答内容，如未进行任何操作，提问设置的学币金额将会在<%=Constants.AUTO_PAY_DAY %>后自动打款给回答提问的老师，交易完成后不可投诉。<br />
    4、投诉教师回答内容时，需根据实际情况投诉反馈，如恶意投诉，经发现将会禁用当前账号，多次恶意投诉会导致账号永久禁用。<br />
    5、付费答疑交易完成（即确认付款）后，当前答疑可进行售卖，价格为“问题金额”的一半，任何人想要查看问题的回答内容，需要支付购买才可查看。假设“问题金额”为10学币，交易完成后，售卖价格为5学币，每售卖一次，自己可从中获得的收益如下（公式）：<br />
    &nbsp;&nbsp;&nbsp;&nbsp;10(原价)*<%=Constants.BUY_QUESTION_DISCOUNT/10 %>(折扣)*<%=Constants.SELL_QUESTION_PROPORTION %>(分成比例) = <%=10*(Constants.BUY_QUESTION_DISCOUNT/10)*Constants.SELL_QUESTION_PROPORTION %>(学币)
    </p>
    
</div>
</logic:equal>
</body>
</html>