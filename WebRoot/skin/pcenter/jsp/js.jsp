<script>
$(document).ready(function(){
	var mark=document.getElementById("mark").value;
	for(i=1;i<=5;i++){
		if(document.getElementById("list"+i)){
			var menu=document.getElementById("list"+i);
			menu.className=i==mark?"information_line":"";
		}
	}
});
$(function(){
	function select_simulated(select_style,bomb_con_style){
		$(document).click(function(){
			$(bomb_con_style).hide();
			})
		$(select_style).live('click',function(e){
			var thisinput=$(this);
			var local=$(this).position();
			var bomb_con=$(bomb_con_style);
				$(this).parents("li").siblings().find(bomb_con_style).hide();
				$(this).parent().find(bomb_con_style).width($(this).width());
				$(this).parent().find(bomb_con_style).show();
				e?e.stopPropagation():event.cancelBubble = true;
				bomb_con.find("dd").click(function(e){
				var bomb_text=$(this).text();
				$(this).addClass("selected").siblings().removeClass("selected");
				$(this).parents(bomb_con_style).hide();
				$(this).parents("li").find(select_style).val(bomb_text);
				e?e.stopPropagation():event.cancelBubble = true;
				
		});	
		});
		 return false;
	}
	select_simulated(".provin_select",".provin_con");
	
	  
	$(".local").focus(function(){
		$(this).addClass("local3");
	});
	$(".local").blur(function(){
		$(this).removeClass("local3");
	});
	})
function init(){
  <logic:present name="promptinfo">
      alert("<bean:write name="promptinfo"/>")
  </logic:present>   
}
</script>
