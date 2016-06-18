package com.qjc.IndoorNavigation;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.qjc.IndoorNavigation.R;
/**
 * @ClassName: GetapinfoActivity 
 * @Description: TODO 获取AP信息
 * @author 锦年 
 * @date 2015-3-23 下午3:59:36
 */
public class GetapinfoActivity extends Activity {
	private TextView text1; 
	private TextView numtxt;
	private TextView destxt;
	private TextView xtxt;
	private TextView ytxt;
	private Button btn1;
	private Button btn2;
	private String apmac;
	private String aplev;
	private Handler mHandler1;
	//固定（稳定）mac表
	///////////////////应从服务器下载，本地解析////////////////////
	public static String[] macarray5={"b6:14:4b:74:d4:ec","a8:15:4d:fe:22:53","c6:14:4b:74:d4:ed","6c:e8:73:fe:04:19",
			"a0:21:b7:ae:70:98","60:c5:47:99:7b:f8"}; 
	public static String[] macarray4={"56:14:4b:5f:1f:f6","d6:14:4b:5f:22:ae","e6:14:4b:5f:22:af","d6:14:4b:5f:22:6e","fe:85:de:03:d9:78","00:23:45:67:89:ab",
		"22:f8:da:56:05:74","3c:e5:a6:20:6b:71","3c:e5:a6:20:6b:71"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getapinfo);
		text1 = (TextView) findViewById(R.id.txt1);//显示ap信息
		numtxt= (TextView) findViewById(R.id.numtxt);//posid
		destxt= (TextView) findViewById(R.id.destxt);//desc
		xtxt = (TextView) findViewById(R.id.xtxt);//x坐标
		ytxt = (TextView) findViewById(R.id.ytxt);//x坐标
		btn1 = (Button)findViewById(R.id.btn1);//扫描
		btn2 = (Button)findViewById(R.id.btn2);//上传
		mHandler1 = new MyHandler();
		btn1.setOnClickListener(new Button.OnClickListener() {
            @SuppressWarnings("unchecked")
			public void onClick(View v) {
            	text1.setText(null);
				apmac="";
				aplev="";
            	WifiManager wm = (WifiManager) getSystemService (Context.WIFI_SERVICE);
	        	wm.startScan ();  
	        	ComparatorUser c = new ComparatorUser();   
	        	List<ScanResult> results = wm.getScanResults (); 
	        	try {
	        		Collections.sort (results,c); 
	        		int max=Math.min (30,results.size ());
		        	for(int i=0;i<max;i++) {//去掉测试服务器
		        		ScanResult one = results.get(i); 
		        		if(i<9) text1.append (one.SSID+"\t\t"+one.BSSID+"\t\t"+one.level+"\n");
		        		for (int j = 0; j < macarray4.length; j++) {
		        			if (one.BSSID.equals(macarray4[j])) {
		        				apmac+=(j+",");
		        				aplev+=one.level;
							}
						}
		        	}
		        	
				} catch (Exception e) {
					// TODO: handle exception
				}	  
            }
		  });
		btn2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	Thread t = new NetWorkThread1();
				t.start(); 
				int i=Integer.parseInt(numtxt.getText().toString())+1;
				numtxt.setText(i+"");
            }
		});
	}
	
	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.arg1 == 1)
			{
				Toast.makeText(GetapinfoActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();

			} else
			{
				Toast.makeText(GetapinfoActivity.this, "上传失败！", Toast.LENGTH_SHORT).show();				
			}
		}
	}  
	
	class NetWorkThread1 extends Thread
	{
		String strResult;
		int state;
		JSONObject mJson;
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			System.out.println("thread--->" + Thread.currentThread().getName());
			String httpUrl = getString(R.string.httpurl) + "/IndoorNavigation/apinfoget.php";
			String parms = "&desc=" + destxt.getText().toString()+"&x=" + xtxt.getText().toString()+
					"&y=" + ytxt.getText().toString()+"&apmac=" + apmac + "&aplev=" + aplev+"&mapid="+1+"&floor="+4;
			System.out.println(httpUrl+"?"+parms);
			try
			{
				strResult = mhttpGet1(httpUrl, parms);
				System.out.println(strResult);				
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (strResult != null && strResult.startsWith("\ufeff"))
			{
				strResult = strResult.substring(1);
			}
			try
			{
				mJson = new JSONObject(strResult);
				state = mJson.getInt("state");
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = mHandler1.obtainMessage();
			msg.arg1 = state;
			mHandler1.sendMessage(msg);
		}
	}
	
	public static String mhttpGet1(String url, String params) throws Exception
	{
		String response = null; //返回信息
		//拼接请求URL
		if (null!=params&&!params.equals(""))
		{
			url += "?" + params;
		}
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  	    
		// 构造HttpClient的实例
		HttpClient httpClient = new DefaultHttpClient(httpParameters);  
		// 创建GET方法的实例
		HttpGet httpGet = new HttpGet(url);
		try
		{
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) //SC_OK = 200
			{
				// 获得返回结果
				response = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
			}
			else
			{
				response = "false";
			}
		} catch (Exception e)
		{
			response = "9";
		} 
		return response;
	}
	
	@SuppressWarnings("rawtypes")
	class ComparatorUser implements Comparator{ 
		  
        @Override  
        public int compare(Object arg0, Object arg1) { 
        ScanResult temp1 = (ScanResult) arg0;  
        ScanResult temp2 = (ScanResult) arg1;  
        if(temp1.level>temp2.level)return -1;  
        else if(temp1.level<temp2.level)return 1;  
        else if(temp1.level==temp2.level)return 0;  
        return 0;  
        }  
          
    };  
}
