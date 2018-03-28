<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>${tkBookContent.title }</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/ckplayer/js/offlights.js"></script>
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script>
$(document).ready(function(){
	var filmid = document.getElementById("filmid").value;
	var a = document.getElementById(filmid);
	a.style.color="#1fcc8a";
	
});
function upComment(){
	var Tab1 = document.getElementById("Tab1");
	var b=document.getElementById("b");
	Tab1.style.display="none";
	b.style.display="block";
	CKobject.getObjectById('a1').videoPause();//暂停播放，开启弹窗展示资源
}
function closeComment(){
	getComment(1);
	var Tab1 = document.getElementById("Tab1");
	var b=document.getElementById("b");
	Tab1.style.display="block";
	b.style.display="none";
	CKobject.getObjectById('a1').videoPlay();//继续播放
	document.getElementById('b').reset();
	$("#msg_p").html("");
}
function setTab(name,cursel,n){
	//如果已经加载过内容，无需重新加载，只需显示即可
	if($("#con_two_1").html() == ""){
		getComment(1);
	}
	
	for(var i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		con.style.display=i==cursel?"block":"none";
	}
}
function setTab1(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		menu.className=i==cursel?"hover":"";
	}
	getComment(cursel);
}
function changePlay(imgpath,mp4path,obj){
	var newAddress = "{i->/upload/" + imgpath + "}{html5->http://${computerinfo.ip}:${computerinfo.port}/upload/" + mp4path + "->video/mp4}";
	CKobject.getObjectById('a1').newAddress(newAddress);
	document.getElementById("filmid").value =obj;
	
	var filmid = document.getElementById("filmid").value;
	var a_filmid =null;
	var a_filmids = document.getElementsByName('a_filmid');
	for(var i=0;i<a_filmids.length;i++){
		a_filmid = a_filmids[i].value;
		if(a_filmid == filmid){
			var a = document.getElementById(a_filmid);
			a.style.color="#1fcc8a";
		}else{
			var a = document.getElementById(a_filmid);
			a.style.color="#595757";
		}
	 }
	 //切换视频时候，刷新播放次数	 
	 $.ajax({
        type:"post",
        url:"/weixinVod.app?method=getAjaxCommentHits",
        data:"filmid="+filmid+"&userid=${userid}&bookcontentid=${bookcontentid}&bookid=${bookid }&searchtype=${searchtype }&type=${type }",
        success:function(msg){
        	$("#hitsid").html(msg);
			}
	});
}
function onSubmit(){
	var Tab1 = document.getElementById("Tab1");
	var b=document.getElementById("b");
	var opinionCategory = document.getElementsByName('opinionCategory');
    var opinionCategoryValue = 1;
    for(var i=0;i<opinionCategory.length;i++){
           if(opinionCategory[i].checked){
                         opinionCategoryValue = opinionCategory[i].value;
           }
    }
    var text = document.getElementById("txt").value;
    var anonymouss =null;
    var anonymous=document.getElementById("anonymous");
    if(anonymous.checked){
      //选中了
    	anonymouss ="1";
    }else{
       //没选中
    	anonymouss ="0";
    }
	$.ajax({
        type:"post",
        url:"/weixinVod.app?method=addAjaxComment",
        data:"score=" + opinionCategoryValue + "&content="+ text +"&anonymouss="+anonymouss+"&userid=${userid}&bookcontentid=${bookcontentid}&bookid=${bookid }&searchtype=${searchtype }&type=${type }",
        success:function(msg){
        	if(msg == "1"){
        		getComment(1);
        		CKobject.getObjectById('a1').videoPlay();//继续播放
        		document.getElementById('b').reset();
        		$("#msg_p").html("");
        		Tab1.style.display="block";
        		b.style.display="none";
        	}else{
        		$("#msg_p").html("评价内容不能为空，请重新输入！");
        		Tab1.style.display="none";
        		b.style.display="block";
        	}
			}
	});
getAjaxCommentNum();
}
function getAjaxCommentNum(){
$.ajax({
        type:"post",
        url:"/weixinVod.app?method=getAjaxCommentNum",
        dataType:'json',
        data:"userid=${userid}&bookcontentid=${bookcontentid}&bookid=${bookid }&searchtype=${searchtype }&type=${type }",
        success:function(msg){
        	if(msg){
        		$("#good").html(msg.goodNum);
        		$("#medium").html(msg.mediumNum);
        		$("#poor").html(msg.poorNum);
        		$("#evaluation").html(msg.evaluationNum);
        	}
			}
	});
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="/weixinAccountIndex.app?method=myCourse&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p"><logic:equal value="1" name="type">解题微课</logic:equal><logic:equal value="2" name="type">举一反三微课</logic:equal></p>
    </div>
    <a href="javascript:showDH()" onClick="showtext()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="play">
   <!--   <a href="#" class="play_bg_a">开始学习</a>
	<p class="play_bg"></p>
	<img src="/weixin/images/img11.png" class="play_img"/>
    -->
<div id="a1"></div>
<%
VwhFilmPix model = (VwhFilmPix)request.getAttribute("model"); 
%>
<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
	<script type="text/javascript">
		var flashvars={
			p:1,
			e:1,
			i:'/upload/${filmpix.imgpath}'
			};
		var video=['http://${computerinfo.ip}:${computerinfo.port}/upload/${filmpix.flvpath}->video/mp4'];
		var support=['all'];
		CKobject.embedHTML5('a1','ckplayer_a1','100%','100%',video,flashvars,support);
	</script>
</div>

<div id="Tab1" class="play_menu" style="display:block">
    <div class="Menubox">
    <ul>
    	<li class="play_menu_ul_li"></li>
        <li id="one1" onclick="setTab('one',1,6)" >简介</li>
        <li id="one2" onclick="setTab('one',2,6)" class="hover" >目录</li>
        <li id="one3" onclick="setTab('one',3,6)" >评价</li>
    </ul>
    </div>
    <div class="Contentbox">
        <div id="con_one_1"  style="display:none">
        	<p class="Contentbox_p" style="font-size:10px;color:#ff0000;">说明：当前购买的作业微课有效期为<%=Constants.BUY_END_DAY %>天，请在有效期内观看完微课视频，截止时间：${contentBuy.enddate }。</p>
        	<p class="Contentbox_p02">
        	<c:if test="${type=='1' }">
					[解题微课]
				</c:if>
				<c:if test="${type=='2' }">
					[举一反三]
				</c:if>
        	</p>
            <p class="Contentbox_p02">共<span>${num}</span>个微课</p>
             <p class="Contentbox_p02" >播放次数：<span id="hitsid">${hits}</span>次</p>
            <!--<c:forEach var="tbcf" items="${tkBookContentFilms}"> 
				<p>${tbcf.title}</p>
			</c:forEach>-->
			${keywords}
        </div>
        <div id="con_one_2" class="hover">
        	<div class="play_menu_list"> 
        	<c:forEach var="tbcf" items="${tkBookContentFilms}" varStatus="a"> 
				  <a href="#"  id="${tbcf.filmid }" onclick="changePlay('${tbcf.flago}','${tbcf.flags}','${tbcf.filmid }')">
                    <p class="play_menu_list_p">${tbcf.orderindex }</p>
                    <div class="play_menu_list_moudle">
                       <p class="play_menu_list_moudle_p">${tbcf.title }</p>
                       <img src="/weixin/images/img14.png" />
                    </div>
                </a>   
                <input type="hidden"  name="a_filmid" id="${a.index }" value="${tbcf.filmid }"/>
			</c:forEach>           	
            </div>
        </div>
        <div id="con_one_3" style="display:none">
        	<div id="Tab2">
                <div class="Menubox01">
                    <ul>
                        <a class="Contentbox02_moudle">
                            <li id="two1" onclick="setTab1('two',1,4)"  class="hover">
                                <p  class="Contentbox02_moudle_p">全部</p>
                                <p id="evaluation" class="Contentbox02_moudle_p02">${evaluationNum }</p>
                            </li>
                        </a>
                        <a class="Contentbox02_moudle">
                            <li id="two2" onclick="setTab1('two',2,4)" >
                                <p  class="Contentbox02_moudle_p">好评</p>
                                <p id="good" class="Contentbox02_moudle_p02">${goodNum }</p>
                            </li>
                        </a>
                        <a class="Contentbox02_moudle">
                            <li id="two3" onclick="setTab1('two',3,4)">
                                <p  class="Contentbox02_moudle_p">中评</p>
                                <p id="medium" class="Contentbox02_moudle_p02">${mediumNum }</p>
                            </li>
                        </a>
                        <a class="Contentbox02_moudle">
                            <li id="two4" onclick="setTab1('two',4,4)">
                                <p  class="Contentbox02_moudle_p">差评</p>
                                <p  id="poor" class="Contentbox02_moudle_p02">${poorNum }</p>
                            </li>
                        </a>
                    </ul>
                    
                </div>
                 <div class="Contentbox01">
                    <div id="con_two_1" class="hover"></div>
                    <div class="loading" id="loading">
						<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
					</div>
					 <button class="Menubox01_button" style="border:none;" onclick="upComment()">发表我的评价</button>
                </div>
            </div>
        </div>
    </div>
</div>
<form name="pageForm" method="post" style="display:none" id="b">
<div class="search search_write">
    <a href="#" onclick="closeComment()" class="write_left">关闭</a>
    <div  class="search_wk">
    	<p class="search_wk_p">微课评价</p>
    </div>
    <a href="#" onclick="onSubmit()" class="write">提交</a>
</div>

<div class="write_main">
	<p>评分</p>
    <p class="write_main_p">1分差评，2-3分中评，4分及以上好评</p>
    <div class="write_main_img">
    	 <input type="radio"    name="opinionCategory" value="1" />1分
    	 <input type="radio"    name="opinionCategory" value="2" />2分
     	<input type="radio"    name="opinionCategory" value="3" />3分
     	<input type="radio"    name="opinionCategory" value="4" />4分
    	 <input type="radio"    name="opinionCategory" value="5" checked/>5分
    </div>
</div>
<div class="write_input">
        <textarea type="text"  id="txt" class="write_input01"  placeholder="发表文字评价～&nbsp;您的评价对其他小伙伴很重要哦～" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
</div>
<div class="write_input_nm">
	<input type="checkbox" name="anonymous" id="anonymous" value="1"/>
    <p>匿名评价</p>
    <p id="msg_p" style="color:red;"></p>
</div>

</form>
<script>
function getComment(commenttype){
	var num = 0;
	if(num == 0){
		$.ajax({
	        type:"post",
	        url:"/weixinVod.app?method=getAjaxComment",
	        data:"pagenum=" + num + "&userid=${userid}&bookcontentid=${bookcontentid}&type=${buytype }&commenttype=" + commenttype,
	        success:function(msg){
	        	if(msg != ""){
	        		$("#con_two_1").html(msg);
	        	}else{
	        		$("#con_two_1").html("暂无评价！");
	        	}
   			}
		});
  	  	num++;
	}
	$(document).ready(function(){
		$(document).scroll(function(){
	    	if($("#loading").is(":hidden")){
	      		$("#loading").show();
	      	}
	    	var scrolltop = $(document).scrollTop();
	        var dheight = $(document).height();
	        var wheight = $(window).height();
	        if(scrolltop >= dheight-wheight){
	    		  $.ajax({
			        type:"post",
			        url:"/weixinVod.app?method=getAjaxComment",
			        data:"pagenum=" + num + "&userid=${userid}&bookcontentid=${bookcontentid}&type=${buytype }&commenttype=" + commenttype,
			        success:function(msg){
			        	if(msg != ""){
			        		$("#con_two_1").append(msg);
			        	}else{
			        		$("#loading").html("数据已全部加载完!");
			        	}
	     			}
	 			});
	    	  	num++;
	      	}
		});
	});
}
</script>

<input type="hidden" name="filmid" id="filmid" value="${firstFilmid }"/>
</body>
</html>