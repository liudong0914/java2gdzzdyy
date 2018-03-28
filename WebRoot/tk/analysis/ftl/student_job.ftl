<html xmlns:v="urn:schemas-microsoft-com:vml"
xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 12">
<meta name=Originator content="Microsoft Word 12">
<link rel=File-List href="1.files/filelist.xml">
<link rel=themeData href="1.files/themedata.thmx">
<link rel=colorSchemeMapping href="1.files/colorschememapping.xml">
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:普通表格;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-priority:99;
	mso-style-qformat:yes;
	mso-style-parent:"";
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman","serif";
	mso-bidi-font-family:"Times New Roman";
	mso-bidi-theme-font:minor-bidi;}
table.MsoTableGrid
	{mso-style-name:网格型;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-priority:59;
	mso-style-unhide:no;
	border:solid black 1.0pt;
	mso-border-themecolor:text1;
	mso-border-alt:solid black .5pt;
	mso-border-themecolor:text1;
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-border-insideh:.5pt solid black;
	mso-border-insideh-themecolor:text1;
	mso-border-insidev:.5pt solid black;
	mso-border-insidev-themecolor:text1;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.5pt;
	mso-bidi-font-size:11.0pt;
	font-family:"Calibri","sans-serif";
	mso-ascii-font-family:Calibri;
	mso-ascii-theme-font:minor-latin;
	mso-hansi-font-family:Calibri;
	mso-hansi-theme-font:minor-latin;
	mso-font-kerning:1.0pt;}
</style>
</head>

<body lang=ZH-CN style='tab-interval:21.0pt;text-justify-trim:punctuation'>

<div class=Section1 style='layout-grid:15.6pt'>
<#list userQuestions as uq>
<p class=MsoNormal>${uq_index+1}<span style='font-family:宋体;mso-ascii-font-family:
Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:
minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>、</span><span
lang=EN-US>${uq.question.titlecontent}<span style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span><span
style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;
mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:
Calibri;mso-hansi-theme-font:minor-latin'>（</span><span lang=EN-US> ${uq.question.tkQuestionsType.typename}</span><span style='font-family:宋体;mso-ascii-font-family:
Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:
minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>：</span><span
lang=EN-US>${uq.question.score}分</span><span style='font-family:
宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:
宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;
mso-hansi-theme-font:minor-latin'>）</span></p>
<#if (uq.question.tkQuestionsType.type == 'A'||uq.question.tkQuestionsType.type == 'B')>
<#if (uq.question.option1?exists&&uq.question.option1!="")>
<p class=MsoNormal>A:${uq.question.option1}</p>
</#if><#if (uq.question.option2?exists&&uq.question.option2!="")>
<p class=MsoNormal>B:${uq.question.option2}</p>
</#if>
<#if (uq.question.option3?exists&&uq.question.option3!="")>
<p class=MsoNormal>C:${uq.question.option3}</p>
</#if>
<#if (uq.question.option4?exists&&uq.question.option4!="")>
<p class=MsoNormal>D:${uq.question.option4}</p>
</#if>
<#if (uq.question.option5?exists&&uq.question.option5!="")>
<p class=MsoNormal>E:${uq.question.option5}</p>
</#if>
<#if (uq.question.option6?exists&&uq.question.option6!="")>
<p class=MsoNormal>F:${uq.question.option6}</p>
</#if>
<#if (uq.question.option7?exists&&uq.question.option7!="")>
<p class=MsoNormal>G:${uq.question.option7}</p>
</#if>
<#if (uq.question.option8?exists&&uq.question.option8!="")>
<p class=MsoNormal>H:${uq.question.option8}</p>
</#if>
<#if (uq.question.option9?exists&&uq.question.option9!="")>
<p class=MsoNormal>I:${uq.question.option9}</p>
</#if>
<#if (uq.question.option10?exists&&uq.question.option10!="")>
<p class=MsoNormal>J:${uq.question.option10}</p>
</#if>
</#if>
<#if (uq.userAnswer?exists&&uq.userAnswer?size>0)>
<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:
 solid black .5pt;mso-border-themecolor:text1;mso-yfti-tbllook:1184;mso-padding-alt:
 0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>是否答对</span></p>
  </td>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>我的答案</span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>得分</span></p>
  </td>
  <td width=178 valign=top style='width:130.55pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>作答时间</span></p>
  </td>
 </tr>
 
 <#list uq.userAnswer as ua>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-top:none;mso-border-top-alt:solid black .5pt;
  mso-border-top-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <#if ua.isright =="1">
  <p class=MsoNormal><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:
  Calibri;color:red'>对</span></p>
  <#else>
<p class=MsoNormal><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:
  Calibri;color:red'>错</span></p>
  </#if>
  </td>
  <td width=178 valign=top style='width:106.5pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>
  <#if ua.answer=="1">
  	对
  <#elseif ua.answer =="0">
  	错
  <#else>
  ${ua.answer}
  </#if>
  </span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp; </span>${ua.score}</span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>${ua.createdate}</span></p>
  </td>
 </tr>
 </#list>
</table>
<#else>
<#if (uq.childQuesitons?size<=0)>
<p class=MsoNormal><span
style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;
mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:
Calibri;mso-hansi-theme-font:minor-latin;color:red'>尚未作答</span></p>
</#if>
</#if>

<p class=MsoNormal><span lang=EN-US><o:p>&nbsp;</o:p></span></p>

<#if uq.childQuesitons ?exists> 
<#list uq.childQuesitons as child>
<p class=MsoNormal>${child_index+1}</span><span style='font-family:宋体;mso-ascii-font-family:
Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:
minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>、</span><span
lang=EN-US>${child.que.titlecontent}<span style='mso-spacerun:yes'>&nbsp;&nbsp; </span></span><span
style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;
mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:
Calibri;mso-hansi-theme-font:minor-latin'>（</span><span lang=EN-US> ${child.que.tkQuestionsType.typename}</span><span style='font-family:宋体;mso-ascii-font-family:
Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:
minor-fareast;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>：</span><span
lang=EN-US>${child.que.score}分</span><span style='font-family:
宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;mso-fareast-font-family:
宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:Calibri;
mso-hansi-theme-font:minor-latin'>）</span></p>
<#if (child.que.tkQuestionsType.type == 'A'||child.que.tkQuestionsType.type == 'B')>
<#if (child.que.option1?exists&&child.que.option1!="")>
<p class=MsoNormal>A:${child.que.option1}</p>
</#if>
<#if (child.que.option2?exists&&child.que.option2!="")>
<p class=MsoNormal>B:${child.que.option2}</p>
</#if>
<#if (child.que.option3?exists&&child.que.option3!="")>
<p class=MsoNormal>C:${child.que.option3}</p>
</#if>
<#if (child.que.option4?exists&&child.que.option4!="")>
<p class=MsoNormal>D:${child.que.option4}</p>
</#if>
<#if (child.que.option5?exists&&child.que.option5!="")>
<p class=MsoNormal>E:${child.que.option5}</p>
</#if>
<#if (child.que.option6?exists&&child.que.option6!="")>
<p class=MsoNormal>F:${child.que.option6}</p>
</#if>
<#if (child.que.option7?exists&&child.que.option7!="")>
<p class=MsoNormal>G:${child.que.option7}</p>
</#if>
<#if (child.que.option8?exists&&child.que.option8!="")>
<p class=MsoNormal>H:${child.que.option8}</p>
</#if>
<#if (child.que.option9?exists&&child.que.option9!="")>
<p class=MsoNormal>I:${child.que.option9}</p>
</#if>
<#if (child.que.option10?exists&&child.que.option10!="")>
<p class=MsoNormal>J:${child.que.option10}</p>
</#if>
</#if>
<#if (child.childAnswer?exists&&child.childAnswer?size>0)>
<table class=MsoTableGrid border=1 cellspacing=0 cellpadding=0
 style='border-collapse:collapse;border:none;mso-border-alt:
 solid black .5pt;mso-border-themecolor:text1;mso-yfti-tbllook:1184;mso-padding-alt:
 0cm 5.4pt 0cm 5.4pt'>
 <tr style='mso-yfti-irow:0;mso-yfti-firstrow:yes'>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>是否答对</span></p>
  </td>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>我的答案</span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>得分</span></p>
  </td>
  <td width=178 valign=top style='width:130.55pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-left:none;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:
  minor-latin;mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;
  mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin'>作答时间</span></p>
  </td>
 </tr>
 <#list child.childAnswer as a>
 <tr style='mso-yfti-irow:1;mso-yfti-lastrow:yes'>
  <td width=178 valign=top style='width:106.5pt;border:solid black 1.0pt;
  mso-border-themecolor:text1;border-top:none;mso-border-top-alt:solid black .5pt;
  mso-border-top-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <#if a.isright =="1">
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:
  Calibri;color:red'>对</span></p>
  <#else>
<p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span
  style='font-family:宋体;mso-ascii-font-family:Calibri;mso-hansi-font-family:
  Calibri;color:red'>错</span></p>
  </#if>
  </td>
  <td width=178 valign=top style='width:106.5pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>
  <#if a.answer=="1">
  	对
  <#elseif a.answer =="0">
  	错
  <#else>
  ${a.answer}
  </#if>
  </span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;</span><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>${a.score}</span></p>
  </td>
  <td width=178 valign=top style='width:106.55pt;border-top:none;border-left:
  none;border-bottom:solid black 1.0pt;mso-border-bottom-themecolor:text1;
  border-right:solid black 1.0pt;mso-border-right-themecolor:text1;mso-border-top-alt:
  solid black .5pt;mso-border-top-themecolor:text1;mso-border-left-alt:solid black .5pt;
  mso-border-left-themecolor:text1;mso-border-alt:solid black .5pt;mso-border-themecolor:
  text1;padding:0cm 5.4pt 0cm 5.4pt'>
  <p class=MsoNormal><span lang=EN-US><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp; </span>${a.createdate}</span></p>
  </td>
 </tr>
 </#list>
</table>
<#else>
<p class=MsoNormal><span
style='font-family:宋体;mso-ascii-font-family:Calibri;mso-ascii-theme-font:minor-latin;
mso-fareast-font-family:宋体;mso-fareast-theme-font:minor-fareast;mso-hansi-font-family:
Calibri;mso-hansi-theme-font:minor-latin;color:red'>尚未作答</span></p>
</#if>
</#list>
</#if>
</#list>
</div>

</body>

</html>
