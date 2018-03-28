<%@ page contentType="text/html; charset=utf-8"%>
<script type="text/javascript">
function g(objid){
	return document.getElementById(objid);
}

function selectSubject(){
	var diag = new top.Dialog();
	diag.Title = "选择学科";
	diag.URL = '/eduSelectAction.do?method=selectSubject';
	diag.Width = 300;
	diag.Height = 400;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('selectobjid')){
			var id = diag.innerFrame.contentWindow.document.getElementById('selectobjid').value;
			var name = diag.innerFrame.contentWindow.document.getElementById('selectobjname').value;
			g('subjectid').value = id;
			g('subjectname').value = name;
			
			g('gradeid').value = "";
			g('gradename').value = "点击选择年级";
			g('knopointidupdate').value = "1";
			g('knopointid').value = "";
			g('knopointname').value = "点击选择知识点";
		}
		diag.close();
	};
	diag.show();
}

function selectGrade(){
	var subjectid = g("subjectid").value;
	if(subjectid == ""){
		top.Dialog.alert("请先选择学科！");
		return;
	}
	var diag = new top.Dialog();
	diag.Title = "选择年级";
	diag.URL = '/eduSelectAction.do?method=selectGrade&subjectid=' + subjectid;
	diag.Width = 300;
	diag.Height = 400;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('selectobjid')){
			var id = diag.innerFrame.contentWindow.document.getElementById('selectobjid').value;
			var name = diag.innerFrame.contentWindow.document.getElementById('selectobjname').value;
			g('gradeid').value = id;
			g('gradename').value = name;
			
			g('knopointidupdate').value = "1";
			g('knopointid').value = "";
			g('knopointname').value = "点击选择知识点";
		}
		diag.close();
	};
	diag.show();
}

function selectKnopoint(){
	var subjectid = g("subjectid").value;
	var gradeid = g("gradeid").value;
	if(subjectid == "" || gradeid == ""){
		top.Dialog.alert("请先选择学科和年级！");
		return;
	}
	var knopointid = g('knopointid').value;
	var diag = new top.Dialog();
	diag.Title = "选择知识点";
	diag.URL = '/eduSelectAction.do?method=selectKnopoint&subjectid=' + subjectid + '&gradeid=' + gradeid + '&knopointid=' + knopointid;
	diag.Width = 400;
	diag.Height = 500;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('selectobjid')){
			var id = diag.innerFrame.contentWindow.document.getElementById('selectobjid').value;
			var name = diag.innerFrame.contentWindow.document.getElementById('selectobjname').value;
			g('knopointid').value = id;
			g('knopointidupdate').value = "1";
			if(name == ""){
				g('knopointname').value = "点击选择知识点";
			}else{
				g('knopointname').value = name;
			}
		}
		diag.close();
	};
	diag.show();
}

/*屏蔽所有的js错误*/ 
//function killerrors() {
//	return true; 
//} 
//window.onerror = killerrors; 
</script>