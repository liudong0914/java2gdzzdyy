<%@ page contentType="text/html; charset=utf-8" %>
<div class="information">
		<logic:equal value="1" name="s_sysuserinfo" property="usertype" scope="session">
     	<ul>
        	<a href="/pcenter.do?method=showUserInfo&mark=1"  ><li id="list1" class="information_line">
            	<img src="/skin/pcenter/images/icon01.png" />
                <span>个人信息</span>
            </li></a>
            <!--  
            <a href="/pcenter.do?method=modifyPassword&mark=2"  ><li id="list2">
            	<img src="/skin/pcenter/images/icon02.png" />
                <span>密码修改</span>
            </li></a>
            <a href="#"><li>
            	<img src="/skin/pcenter/images/icon03.png" />
                <span>手机绑定</span>
            </li></a>-->
            <a href="/pcenter.do?method=main&mark=3"  ><li id="list3">
            	<img src="/skin/pcenter/images/icon04.png" />
                <span>教师认证</span>
            </li></a>
            <a href="/pcenter.do?method=orderList&status=1&mark=4&point=1" ><li id="list4">
            	<img src="/skin/pcenter/images/icon05.png" />
                <span>我的答疑</span>
            </li></a>
            <a href="/pcenter.do?method=mycontentfilmlist&mark=5&type=1"  ><li id="list5">
            	<img src="/skin/pcenter/images/icon06.png" />
                <span>我的微课</span>
            </li></a>
            <a href="/pcenter.do?method=paperList"  ><li id="list5">
            	<img src="/skin/pcenter/images/icon03.png" />
                <span>试卷下载</span>
            </li></a>
        </ul>
        </logic:equal>
        <logic:equal value="2" name="s_sysuserinfo" property="usertype" scope="session">
     	<ul>
        	<a href="/pcenter.do?method=showUserInfo&mark=1"  ><li id="list1" class="information_line">
            	<img src="/skin/pcenter/images/icon01.png" />
                <span>个人信息</span>
            </li></a>
            <!--  
            <a href="/pcenter.do?method=modifyPassword&mark=2"  ><li id="list2">
            	<img src="/skin/pcenter/images/icon02.png" />
                <span>密码修改</span>
            </li></a>
            <a href="#"><li>
            	<img src="/skin/pcenter/images/icon03.png" />
                <span>手机绑定</span>
            </li></a>-->
            <a href="/pcenter.do?method=mycontentfilmlist&mark=5&type=1"  ><li id="list5">
            	<img src="/skin/pcenter/images/icon06.png" />
                <span>我的微课</span>
            </li></a>
            <a href="/pcenter.do?method=paperList"  ><li id="list5">
            	<img src="/skin/pcenter/images/icon03.png" />
                <span>试卷下载</span>
            </li></a>
        </ul>
        </logic:equal>
</div><!----information-e---->
<input type="hidden" name="mark" id="mark" value="${mark }" />