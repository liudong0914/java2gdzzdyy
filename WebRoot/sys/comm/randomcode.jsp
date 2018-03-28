<%@ page pageEncoding="utf-8"%>
<%@page contentType="image/jpeg"%>
<%@page import="java.awt.Color"%>
<%@page import="java.util.Random"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.awt.Graphics"%>
<%@page import="java.awt.Font"%>
<%@page import="javax.imageio.ImageIO"%>
<%!
//产生随机颜色函数 getRandColor
public Color getRandColor(int fc, int bc) {
	Random r = new Random();
    if (fc > 255) fc = 255;
    if (bc > 255) bc = 255;
    int red = fc + r.nextInt(bc - fc); //红
    int green = fc + r.nextInt(bc - fc); //绿
    int blue = fc + r.nextInt(bc - fc); //蓝
    return new Color(red, green, blue);
}
%>
<%
//设置页面不缓存
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);

//在内存中创建图象，宽为width,高为height
int width = 60;
int height = 20;
BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//获取图形上下文环境
Graphics gc = pic.getGraphics();

//创建随机类
Random r = new Random();

//设置背景色并进行填充
gc.setColor(getRandColor(200, 250));
gc.fillRect(0, 0, width, height);

//设置图形上下文环境字体
gc.setFont(new Font("Times New Roman", Font.PLAIN, 18));

//随机产生200条干扰直线，是图象中的认证码不易被其他分析程序探测到
gc.setColor(getRandColor(160, 200));
for (int i = 0; i < 200; i++) {
	int x1 = r.nextInt(width);
  	int y1 = r.nextInt(height);
  	int x2 = r.nextInt(15);
  	int y2 = r.nextInt(15);
  	gc.drawLine(x1, y1, x1 + x2, y1 + y2);
}

//随即产生100个干扰点，是图象中的验证码不易被其他分析程序探测到
gc.setColor(getRandColor(120, 240));
for (int i = 0; i < 100; i++) {
	int x = r.nextInt(width);
  	int y = r.nextInt(height);
  	gc.drawOval(x, y, 0, 0);
}

//随即产生4为数字的验证码
StringBuffer rs = new StringBuffer();
String rn = ""; //生成数字
String litter = ""; //生成字母
String litters[] = {
    				"a", "B", "c", "D", "e", "F", "g", "H", "i", "J", "k", "L", "m", "N", "o", "P", "q", "R", "s", "T", "u", "V", "w", "X", "y", "Z",
    				"A", "b", "C", "d", "E", "f", "G", "h", "I", "j", "K", "l", "M", "n", "O", "p", "Q", "r", "S", "t", "U", "v", "W", "x", "Y", "z"
    			   };
int temp = 0;
int i = 0;//产生随机位数的数字和字母
while(true){
	i++;
	if(rs.length() < 4){
		gc.setColor(new Color(20 + r.nextInt(110), 20 + r.nextInt(110), 20 + r.nextInt(110)));
	  	//产生10以内随机数字rn
	  	if (i % 2 == 0) {
	  		int index = r.nextInt(12);
	  		if(index < 10){
	  			rn = String.valueOf(index);
		    	rs.append(rn);
		    	//将认证码用 drawString 函数显示到图象里
		    	gc.drawString(rn.trim(), 13 * temp + 6, 16);
		    	temp++;
	  		}
	  	} else {
	    	int index = r.nextInt(55);
	    	if (index < 52) {
	      		litter = litters[index];
	      		rs.append(litter);
	      		//将认证码用 drawString 函数显示到图象里
	      		gc.drawString(litter.trim(), 13 * temp + 6, 16);
	      		temp++;
	    	}
	  	}
	}else{
		break;
	}
}

//释放图形上下文环境
gc.dispose();

//将认证码rs 存入session 中共享
session.setAttribute("randomcode", rs.toString());

try {
	//输出生成的验证码图象到页面
  	ImageIO.write(pic, "JPEG", response.getOutputStream());
  	out.clear();
  	out = pageContext.pushBody();
} catch(Exception e){
	System.out.println("生成验证码出错!");
	//e.printStackTrace();
}
%>