$(function () {
    $(".payment_time_title a").click(function () {
        $("#bg").css({
            display: "block", height: $(document).height()
        });
        var $box = $('.payment_time_mask');
        $box.css({
            display: "block",
        });
        for(var i=1; i<=6; i++){
	    	document.getElementById("pwd" + i).value = "";
	    }
	    document.getElementById("hiddenpwdid").value = "1";
    });
    $(".payment_time_mask .wk_buy_a").on('click',function () {
        $("#bg,.payment_time_mask").css("display", "none");
       
    });
});