import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * @Description:
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Dec 17, 201610:39:56 AM
 */
public class ThreadTest {

	public static void main(String[] args) {
		for(int i=0; i<=1000; i++){
			MyThread m1 = new MyThread();  
	        MyThread m2 = new MyThread();  
	        m1.start();  
	        m2.start();
		}
    }
}

final class MyThread extends Thread {  
  
    public MyThread() {  
    }  
    
    public void print(){
		String uriAPI = "http://127.0.0.1:8080/weixinHelp.app?method=answer&userid=4EDD693B60E8CF2A&questionid=1";
		/*建立HTTPost对象*/
		HttpPost httpRequest = new HttpPost(uriAPI);
		
		try {
			/* 添加请求参数到请求对象*/
			/*发送请求并等待响应*/
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest); 
			/*若状态码为200 ok*/
			if(httpResponse.getStatusLine().getStatusCode() == 200) { 
				/*读返回数据*/
				String strResult = EntityUtils.toString(httpResponse.getEntity()); 
				System.out.println("》》》》》》》》》》》》strResult=="+strResult);
			} else { 
				System.out.println("strResult=="+"Error Response: "+httpResponse.getStatusLine().toString());
			}
		} catch (Exception e) {	
			e.printStackTrace();
		}
	} 
  
    public void run() {  
        print();  
    }  
}