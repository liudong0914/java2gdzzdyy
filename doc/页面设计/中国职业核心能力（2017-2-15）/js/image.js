﻿// JavaScript Document
<!--//--><![CDATA[//><!--
//图片滚动列表 mengjia 070816
var Speed = 10; //速度(毫秒)
var Space = 5; //每次移动(px)
var PageWidth = 125; //翻页宽度
var fill = 0; //整体移位
var MoveLock = false;
var MoveTimeObj;
var Comp = 0;
var AutoPlayObj = null;
GetObj("List2").innerHTML = GetObj("List1").innerHTML;
GetObj('JS_Cont').scrollLeft = fill;
GetObj("JS_Cont").onmouseover = function(){clearInterval(AutoPlayObj);}
GetObj("JS_Cont").onmouseout = function(){AutoPlay();}
AutoPlay();
function GetObj(objName){if(document.getElementById){return eval('document.getElementById("'+objName+'")')}else{return eval('document.all.'+objName)}}
function AutoPlay(){ //自动滚动
 clearInterval(AutoPlayObj);
 AutoPlayObj = setInterval('JS_GoDown();JS_StopDown();',5000); //间隔时间
}
function JS_GoUp(){ //上翻开始
 if(MoveLock) return;
 clearInterval(AutoPlayObj);
 MoveLock = true;
 MoveTimeObj = setInterval('JS_ScrUp();',Speed);
}
function JS_StopUp(){ //上翻停止
 clearInterval(MoveTimeObj);
 if(GetObj('JS_Cont').scrollLeft % PageWidth - fill != 0){
  Comp = fill - (GetObj('JS_Cont').scrollLeft % PageWidth);
  CompScr();
 }else{
  MoveLock = false;
 }
 AutoPlay();
}
function JS_ScrUp(){ //上翻动作
 if(GetObj('JS_Cont').scrollLeft <= 0){GetObj('JS_Cont').scrollLeft = GetObj('JS_Cont').scrollLeft + GetObj('List1').offsetWidth}
 GetObj('JS_Cont').scrollLeft -= Space ;
}


function JS_GoDown(){ //下翻
 clearInterval(MoveTimeObj);
 if(MoveLock) return;
 clearInterval(AutoPlayObj);
 MoveLock = true;
 JS_ScrDown();
 MoveTimeObj = setInterval('JS_ScrDown()',Speed);
}
function JS_StopDown(){ //下翻停止
 clearInterval(MoveTimeObj);
 if(GetObj('JS_Cont').scrollLeft % PageWidth - fill != 0 ){
  Comp = PageWidth - GetObj('JS_Cont').scrollLeft % PageWidth + fill;
  CompScr();
 }else{
  MoveLock = false;
 }
 AutoPlay();
}
function JS_ScrDown(){ //下翻动作
 if(GetObj('JS_Cont').scrollLeft >= GetObj('List1').scrollWidth){GetObj('JS_Cont').scrollLeft = GetObj('JS_Cont').scrollLeft - GetObj('List1').scrollWidth;}
 GetObj('JS_Cont').scrollLeft += Space ;
}

function CompScr(){
 var num;
 if(Comp == 0){MoveLock = false;return;}
 if(Comp < 0){ //上翻
  if(Comp < -Space){
   Comp += Space;
   num = Space;
  }else{
   num = -Comp;
   Comp = 0;
  }
  GetObj('JS_Cont').scrollLeft -= num;
  setTimeout('CompScr()',Speed);
 }else{ //下翻
  if(Comp > Space){
   Comp -= Space;
   num = Space;
  }else{
   num = Comp;
   Comp = 0;
  }
  GetObj('JS_Cont').scrollLeft += num;
  setTimeout('CompScr()',Speed);
 }
}
//--><!]]>