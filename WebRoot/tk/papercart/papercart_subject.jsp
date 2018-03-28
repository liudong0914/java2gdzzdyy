<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery.tree.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery-1.4.2.min.js"></script>
<link href="/tk/papercart/css/subject.css" rel="stylesheet" type="text/css"/>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<script type="text/javascript">
 function changeKnopoint(type,subjectid,gradetype){
  this.parent.location='/tkPaperCartAction.do?method=main&type='+type+'&subjectid='+subjectid+'&gradetype='+gradetype;
  changeRightmain("A");
 }
  function changeRightmain(temp){
  if(temp=="A"){
    document.getElementById("RightmainA").className="state-active";
    document.getElementById("RightmainB").className="";
    parent.rfrmright.location='/tkPaperCartAction.do?method=leftmain&gradetype=${gradetype }&subjectid=${subjectid}';
  }else if(temp=="B"){
    document.getElementById("RightmainB").className="state-active";
    document.getElementById("RightmainA").className="";
    var grade=document.getElementById("selected-subject").innerHTML;
    var type="";
    if(grade.indexOf("小学")!=-1){
     type="1";
    }else if(grade.indexOf("初中")!=-1){
      type="2";
    }else if(grade.indexOf("高中")!=-1){
      type="3";
    }
    parent.rfrmright.location='/tkPaperCartAction.do?method=beforeInteligencePaper&gradetype=${gradetype}&subjectid=${subjectid}&type='+type;
  }
}
   function clearAllQuesionts(){
      $.ajax({
         url:"tkPaperCartAction.do?method=clearAllQuestions&gradetype=${gradetype}&subjectid=${subjectid}",
         data:{},
         cache:false,
         async:false,
         dataType:'json'
      });
    parent.rfrmleft.location='/tkPaperCartAction.do?method=subject&type=1&gradetype=${gradetype}&subjectid=${subjectid}';  
    parent.rfrmright.location='/tkPaperCartAction.do?method=leftmain&gradetype=${gradetype }&subjectid=${subjectid}';
   }
   function paperpervier(){
    document.getElementById("RightmainB").className="";
    document.getElementById("RightmainA").className="";  
    var grade=document.getElementById("selected-subject").innerHTML;
    var type="";
    if(grade.indexOf("小学")!=-1){
     type="1";
    }else if(grade.indexOf("初中")!=-1){
      type="2";
    }else if(grade.indexOf("高中")!=-1){
      type="3";
    }
    parent.rfrmright.location='/tkPaperCartAction.do?method=paperPervier&subjectid=${subjectid}&xueduanid=${gradetype}&type'+type;

   }
   function papersubmit(){
   	   window.open("/tkPaperCartAction.do?method=addPaperInfo&subjectid=${subjectid}&xueduanid=${gradetype}","_blank","heignt=600px,width=800px");
   }
   
</script>
  </head>
  <body>
 <div class="sui-wrap"> 
   <div class="sui-aside" style="height:auto">
     <div class="top" >
       <h2 class="fn-left" id="selected-subject">${firstname }</h2>
        <div class="ctrl-change fn-right">
        <span class="ctrl-name">
          <a href="#" style="color:#36a9e0;margin-right:10px;">更换学科</a>
          <div class="second" id="subject" style="display:none;" >
            <dl>
              <dt><a href="javascript:void(0)" >小学</a></dt>
              <dd>
               <c:forEach items="${xsubjectlist }" var="x">
                <a href="#" onclick="changeKnopoint('1','${x.subjectid}','2')">${x.subjectname }</a>
               </c:forEach>
              </dd>
            </dl>
            <dl>
              <dt><a href="javascript:void(0)">初中</a></dt>
              <dd>
              <c:forEach items="${csubjectlist }" var="c">
              <a href="#" onclick="changeKnopoint('1','${c.subjectid}','3')">${c.subjectname }</a>
              </c:forEach>
              </dd>
            </dl>
            <dl>
              <dt><a href="javascript:void(0)">高中</a></dt>
              <dd>
               <c:forEach items="${gsubjectlist }" var="g">
                <a href="#" onclick="changeKnopoint('1','${g.subjectid}','4')">${g.subjectname }</a>
              </c:forEach>
              </dd>
            </dl>
          </div>
        </span>
        <script>
$(function(){
	var sucaijiayuan = 1; // 默认值为0，二级菜单向下滑动显示；值为1，则二级菜单向上滑动显示
	if(sucaijiayuan ==0){
		$('.ctrl-change span').hover(function(){
			$('.second',this).css('bottom','30px').show();
		},function(){
			$('.second',this).hide();
		});
	}else if(sucaijiayuan ==1){
		$('.ctrl-change span').hover(function(){
			$('.second',this).css('top','40px').show();
		},function(){
			$('.second',this).hide();
		});
	}
});

</script>
      </div>
    </div>
    <ul class="chose-types">
    <li><a id="RightmainA" onclick="changeRightmain('A')" class="state-active" style="background-image:url(../../tk/papercart/images/ico_2.png)">知识点选题</a></li>
    <li><a id="RightmainB" onclick="changeRightmain('B')" >智能选题</a></li>
    </ul>
    <div class="preview pd10">
      <a class="preview-btn" href="javascript:paperpervier();">试卷预览</a>
      <a class="preview-btn" href="javascript:papersubmit();" style="margin-top: 25px;">试卷保存</a>
    </div>
     <h3>
      <span>已选题目(</span>
      <span id="question-cart-num" style="border:none;">${questioncount }</span>
      <span style="border:none;">)</span>
      <a class="clear-all" href="javascript:clearAllQuesionts();">全部清空</a>
    </h3>
    <div class="type-table" style="height: 300px;">
    <table id="question-cart">
      <tbody>
         <c:forEach items="${questiontypes }" var="t">
         <tr>   
            <th>${t.typename }</th>   
            <td class="second-col">
              <span class="num"><a href="#">${t.countnum }</a></span>
            </td>   
            <td class="third-col" style="width: 60px;">
              <div style="width:0px;" class="percent-line">
                <div class="percent-text" style="text-align: center;">
                  <span style="text-align: center;">${t.percent }</span>
                </div>
              </div>
            </td>
            <td class="third-col" style="width: 75px;">
              <div style="width:0px;" class="percent-line">
                <div class="percent-text" style="text-align: center;width: 60px;">
                  <span  style="text-align: center;">${t.score }</span>分
                </div>
              </div>
            </td>
          </tr>
          </c:forEach>
      </tbody>
    </table>
   </div>
    </div>
 </div>  
  </body>
</html>
