$(function () {
    $(".payment_time_title01").click(function () {
        $("#bg01").css({
            display: "block", height: $(document).height()
        });
        var $box = $('.payment_time_mask01');
        $box.css({
            display: "block",
        });
    });
    //点击关闭按钮的时候，遮罩层关闭
    $(".payment_time_mask01 .wk_buy_a").on('click',function () {
        $("#bg01,.payment_time_mask01").css("display", "none");
       
    });
});