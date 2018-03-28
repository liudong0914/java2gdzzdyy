<%@ page contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
function goBack1(){
	document.pageForm.action = "/weixinStudent.app?method=doBeforeClass";
	document.pageForm.submit();
}
function goBack2(){
	document.pageForm.action = "/weixinStudent.app?method=viewLianxiMain";
	document.pageForm.submit();
}
function goBack3(){
	document.pageForm.action = "/weixinStudent.app?method=errorQuestionInfo";
	document.pageForm.submit();
}
function goBack4(){
	document.pageForm.action = "/weixinStudent.app?method=errorQuestionInfo2";
	document.pageForm.submit();
}
function goBack5(){
	document.pageForm.action = "/weixinTeacher.app?method=viewLianxiMain";
	document.pageForm.submit();
}
function goBack6(){
	document.pageForm.action = "/weixinTeacher.app?method=preparationQuestion";
	document.pageForm.submit();
}
function goBack7(){
	document.pageForm.action = "/weixinTeacher.app?method=preparationQuestion2";
	document.pageForm.submit();
}
function goBack8(){
	document.pageForm.action = "/weixinTeacher.app?method=detaiQuestion";
	document.pageForm.submit();
}
</script>
	<logic:equal value="1" name="vtype">
	<a href="javascript:goBack1();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
	<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
	<input type="hidden" name="index" value="<bean:write name="index"/>"/>
	<input type="hidden" name="next" value="<bean:write name="next"/>"/>
	<input type="hidden" name="show" value="<bean:write name="show"/>"/>
    </logic:equal>
    <logic:equal value="2" name="vtype">
    <a href="javascript:goBack2();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
	<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
	<input type="hidden" name="index" value="<bean:write name="index"/>"/>
	<input type="hidden" name="next" value="<bean:write name="next"/>"/>
    </logic:equal>
    <logic:equal value="3" name="vtype">
    <a href="javascript:goBack3();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="questionid" value="<bean:write name="questionid"/>"/>
	<input type="hidden" name="uerrorid" value="<bean:write name="uerrorid"/>"/>
    </logic:equal>
    <logic:equal value="4" name="vtype">
    <a href="javascript:goBack4();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="questionid" value="<bean:write name="questionid"/>"/>
	<input type="hidden" name="uerrorid" value="<bean:write name="uerrorid"/>"/>
    </logic:equal>
    <logic:equal value="5" name="vtype">
    <a href="javascript:goBack5();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="index" value="<bean:write name="index"/>"/>
	<input type="hidden" name="next" value="<bean:write name="next"/>"/>
    </logic:equal>
    <logic:equal value="6" name="vtype">
    <a href="javascript:goBack6();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="index" value="<bean:write name="index"/>"/>
	<input type="hidden" name="next" value="<bean:write name="next"/>"/>
	<input type="hidden" name="show" value="<bean:write name="show"/>"/>
    </logic:equal>
    <logic:equal value="7" name="vtype">
    <a href="javascript:goBack7();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="index" value="<bean:write name="index"/>"/>
	<input type="hidden" name="next" value="<bean:write name="next"/>"/>
	<input type="hidden" name="show" value="<bean:write name="show"/>"/>
    </logic:equal>
    <%
    String vtype = (String)request.getAttribute("vtype");
    if("8".equals(vtype) || "9".equals(vtype) || "10".equals(vtype)){
    %>
    <a href="javascript:goBack8();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
	<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
	<input type="hidden" name="questionid" value="<bean:write name="questionid"/>"/>
	<input type="hidden" name="type" value="<bean:write name="type"/>"/>
    <%}%>