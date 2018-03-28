<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>设置教学</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var subjectid="${!empty(teaching)?teaching.subjectid:''}";
	var gradeid="${!empty(teaching)?teaching.gradeid:''}";
	
	function getGrade(sid){
		$(".res_alert").hide();
		subjectid=sid;
		$.ajax({
			type:"post",
	        url:"weixinAccountIndex.app?method=getGradeByAjax",
	        data:"userid=${userid}&subjectid="+sid,
	        success:function(data){
	        	if(data){
	        		$("#g").show();
	        		$("#gg").html(data);
	        		
	        	}
	        }
		});
		$("#s").find("a").attr("class","");
		$("#s"+sid).attr("class","resource_top_s");
	}
	
	function selectGrade(gid){
		$(".res_alert").hide();
		gradeid=gid;
		$("#gg").find("a").attr("class","");
		$("#g"+gid).attr("class","resource_top_s");
		$.ajax({
			type:"post",
	        url:"weixinAccountIndex.app?method=gradeExistsByAjax",
	        data:"userid=${userid}&subjectid="+subjectid+"&gradeid="+gradeid,
	        success:function(data){
	        	if(data==1){
	        		$(".res_alert").html("该年级已经设置");
	    			$(".res_alert").show();
	    			$("#sub").attr("href","javascript:");
	        	}else{
	        		$("#sub").attr("href","javascript:saveTeaching()");
	        	}
	        }
		});
		
	}
	function saveTeaching(){
		if(subjectid==""){
			$(".res_alert").html("请选择学科");
			$(".res_alert").show();
			return
		}
		if(gradeid==""){
			$(".res_alert").html("请选择年级");
			$(".res_alert").show();
			return
		}
		location.href="/weixinAccountIndex.app?method=saveTeaching&teachingid=${teachingid}&userid=${userid}&subjectid="+subjectid+"&gradeid="+gradeid;
	}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=setUserTeaching&userid=${userid}">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">设置教学</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="resource">
<div class="res_alert"></div>
	<div class="resource_top">
	    	<div class="resource_top_p">
	    		<p>学科</p>   		
	       </div>
	       <div class="resource_top_a" id="s">
	           <logic:iterate id="s" name="subjectlist">
	           		<a href="javascript:getGrade(${s.subjectid})" id="s${s.subjectid}" <c:if test="${!empty(teaching)&&s.subjectid eq teaching.subjectid}">class="resource_top_s"</c:if>>${s.subjectname}</a>
	           </logic:iterate>
	       </div>	  
	</div>
	<div class="resource_top" id="g" <logic:empty name="gradelist">style="display:none;"</logic:empty>>
    	<div class="resource_top_p">
    		<p>年级</p>
       </div>
       <div class="resource_top_a" id="gg">
	       	<c:forEach items="${gradelist}" var="g">
       			<a href="javascript:selectGrade(${g.gradeid})" id="g${g.gradeid}" <c:if test="${g.gradeid eq teaching.gradeid}">class="resource_top_s"</c:if>>${g.gradename}</a>
       		</c:forEach>
       </div>	  
    </div>
</div>

<div  class="school_footer_bottom_sx">
	<a href="/weixinAccountIndex.app?method=getSubjectInfo&teachingid=${teachingid}&userid=${userid}" class="school_footer_bottom1">重置</a>
	<a href="javascript:saveTeaching()" id="sub" class="school_footer_bottom2">确定</a>
</div>
</body>
</html>