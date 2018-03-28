// 全选/全不选 的函数
function setState(flag) {
if(num>0){
 if (num>1){
   for(i=0;i<num;i++)	{
     if(document.pageForm.checkid[i].disabled!=true){
       document.pageForm.checkid[i].checked = flag;
     }
   }
 }
 else{
   if(document.pageForm.checkid.disabled!=true){
     document.pageForm.checkid.checked = flag;
   }
 }
}
else{
	try{
		Dialog.alert("未找到可操作记录!");
	}catch(e){
		alert("未找到可操作记录!");
	}
}
}

function setState1(flag) {
if(num>0){
 if (num>1){
   for(i=0;i<num;i++)	{
     if(document.pageForm.checkid[i].disabled!=true){
       document.pageForm.checkid[i].checked = flag;
     }
   }
 }
 else{
   if(document.pageForm.checkid.disabled!=true){
     document.pageForm.checkid.checked = flag;
   }
 }
}
else{
    parent.Dialog.alert("未找到可操作记录!")
}
}


function setState2(flag) {
if(num>0){
 if (num>1){
   for(i=0;i<num;i++)	{
     if(document.pageForm.checkid[i].disabled!=true){
       document.pageForm.checkid[i].checked = flag;
     }
   }
 }
 else{
   if(document.pageForm.checkid.disabled!=true){
     document.pageForm.checkid.checked = flag;
   }
 }
}
else{
    parent.parent.Dialog.alert("未找到可操作记录!")
}
}


function batchRecord(url,title){
   if(num>0){
		 var str=title;
		 var checkids="";
		 if (num>1){
			 for(i=0;i<num;i++){
			   if (document.pageForm.checkid[i].checked==true){
				  checkids=checkids+document.pageForm.checkid[i].value+",";
			   }
			 }
		}
		else{
		   if (document.pageForm.checkid.checked==true)	{
			checkids=document.pageForm.checkid.value;
		   }
		}

		if (checkids=="") {
			try{
				Dialog.alert("您还没有选择要操作的记录呢!");
			}catch(e){
				alert("您还没有选择要操作的记录呢!");
			}
		}
		else{
			try{
				Dialog.confirm(str,
						   function(){
			                  document.pageForm.action=url;
					          document.pageForm.submit();  
					       }
					   );
			}catch(e){
				if(window.confirm(str)){
					document.pageForm.action=url;
			        document.pageForm.submit();  
				}
			}
		   		
		}
	}
	else{
		try{
			Dialog.alert("未找到可操作记录!");
		}catch(e){
			alert("未找到可操作记录!");
		}
	}
}

function batchRecord1(url,title){
   if(num>0){
		 var str=title;
		 var checkids="";
		 if (num>1){
			 for(i=0;i<num;i++){
			   if (document.pageForm.checkid[i].checked==true){
				  checkids=checkids+document.pageForm.checkid[i].value+",";
			   }
			 }
		}
		else{
		   if (document.pageForm.checkid.checked==true)	{
			checkids=document.pageForm.checkid.value;
		   }
		}

		if (checkids=="") {
			  parent.Dialog.alert("您还没有选择要操作的记录呢!");
		}
		else{
		   parent.Dialog.confirm(str,
			   function(){
                  document.pageForm.action=url;
		          document.pageForm.submit();  
		       }
		   );		
		}
	}
	else{
	   parent.Dialog.alert("未找到可操作记录!")
	}
}

function batchRecord2(url,title){
   if(num>0){
		 var str=title;
		 var checkids="";
		 if (num>1){
			 for(i=0;i<num;i++){
			   if (document.pageForm.checkid[i].checked==true){
				  checkids=checkids+document.pageForm.checkid[i].value+",";
			   }
			 }
		}
		else{
		   if (document.pageForm.checkid.checked==true)	{
			checkids=document.pageForm.checkid.value;
		   }
		}

		if (checkids=="") {
			  parent.parent.Dialog.alert("您还没有选择要操作的记录呢!");
		}
		else{
		   parent.parent.Dialog.confirm(str,
			   function(){
                  document.pageForm.action=url;
		          document.pageForm.submit();  
		       }
		   );		
		}
	}
	else{
	   parent.parent.Dialog.alert("未找到可操作记录!")
	}
}

function editThisRecord(url,objid){
    document.pageForm.action=url+"?method=beforeUpdate&objid="+objid;
    document.pageForm.submit();
}

function gotoPage(url){
  document.pageForm.action=url+document.all.gopage.value;
  document.pageForm.submit();
}

function gotoPageTxt(url){
//  if(num>0){
    var gopage = new Number(document.getElementById('gopage').value);//要跳往的页数
	var totalPages = new Number(document.getElementById('totalPages').value);//总分页数
	var page_size = new Number(document.getElementById('page_size').value);//每页显示数量
	if(!isNaN(gopage) && gopage != ""){
		if(gopage > totalPages){
			 alert("您输入的页数过大.");
			return ;
		}else{
            var startcount = (gopage-1)*page_size;
			document.pageForm.action=url+startcount;
  			document.pageForm.submit();
		}
	}else{
		return;
	}
//  }
}

function gotoPageTxt2(url){
//  if(num>0){
    var gopage = new Number(document.getElementById('gopage2').value);//要跳往的页数
	var totalPages = new Number(document.getElementById('totalPages2').value);//总分页数
	var page_size = new Number(document.getElementById('page_size2').value);//每页显示数量
	if(!isNaN(gopage) && gopage != ""){
		if(gopage > totalPages){
			 alert("您输入的页数过大.");
			return ;
		}else{
            var startcount = (gopage-1)*page_size;
			document.pageForm.action=url+startcount;
  			document.pageForm.submit();
		}
	}else{
		return;
	}
//  }
}

function gotoPageTxt3(url){
//  if(num>0){
    var gopage = new Number(document.getElementById('gopage3').value);//要跳往的页数
	var totalPages = new Number(document.getElementById('totalPages3').value);//总分页数
	var page_size = new Number(document.getElementById('page_size3').value);//每页显示数量
	if(!isNaN(gopage) && gopage != ""){
		if(gopage > totalPages){
			 alert("您输入的页数过大.");
			return ;
		}else{
            var startcount = (gopage-1)*page_size;
			document.pageForm.action=url+startcount;
  			document.pageForm.submit();
		}
	}else{
		return;
	}
//  }
}

function turnPage(url){
	if(document.getElementById('pageForm') != null){
		document.getElementById('pageForm').action=url;
  		document.getElementById('pageForm').submit();
	}else{
		document.pageForm.action=url;
  		document.pageForm.submit();
	}
}



function chgTDbg(obj,status){
  if (status=="on"){
      tdBgColor="#ededed"
  }
  else{
      tdBgColor="#ffffff"
  }
  obj.style.background=tdBgColor
  obj.style.cursor="pointer"
}
