package com.qjc.IndoorNavigation.login;
 
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.qjc.IndoorNavigation.MainActivity;
import com.qjc.IndoorNavigation.R;
import com.qjc.IndoorNavigation.utils.MyAlertDialog;
import com.qjc.IndoorNavigation.utils.MyAlertDialog.MyDialogInt;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @ClassName: LoginProActivity 
 * @Description: TODO login
 * @author 锦年 
 * @date 2015-3-23 下午4:02:32
 */
public class LoginProActivity extends Activity {
    /** Called when the activity is first created. */
	private static final String PREFS_NAME = "MyUserInfo";
	public static EditText Num,Pwd;
	private static Button Login,mLogin;
	public static CheckBox RememberPwd,AutoLogin;
	private Handler mHandler;
	private Button Regeister;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
      //获得实例对象
        Num=(EditText) findViewById(R.id.et_Num);
        Pwd=(EditText) findViewById(R.id.et_Pwd);
        Login=(Button) findViewById(R.id.btn_Login);
        mLogin=(Button) findViewById(R.id.mlogin);
        Regeister=(Button) findViewById(R.id.btn_More);
        RememberPwd=(CheckBox) findViewById(R.id.checkBox1);
        LoadUserDate();
        mHandler = new MyHandler();
        Login.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			// TODO Auto-generated method stub
        		if(Num.getText().toString().equals("")){
    				Toast.makeText(LoginProActivity.this,"姓名不能为空 ", Toast.LENGTH_SHORT).show();
    				return;
    			}
    			if(Pwd.getText().toString().equals("")){
    				Toast.makeText(LoginProActivity.this,"号码不能为空 ", Toast.LENGTH_SHORT).show();
    				return;
    			}
    			Login.setText("正在登陆...");
				System.out.println("thread--->" + Thread.currentThread().getName());
				Thread t = new NetWorkThread();
				t.start();  			
    		}
    	});
        Regeister.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			// TODO Auto-generated method stub)
    			Intent intent=new Intent(LoginProActivity.this,RegeisterActivity.class);
  				startActivity(intent);
    		}
    	});
        
        mLogin.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			// TODO Auto-generated method stub)
    			Toast.makeText(LoginProActivity.this, "进入免登录模式", Toast.LENGTH_SHORT).show();
    			Intent intent=new Intent(LoginProActivity.this,MainActivity.class);
  				startActivity(intent);
  				LoginProActivity.this.finish();
    		}
    	});
    			
    }

	
  	//退出
  	 public boolean onKeyDown(int keyCode, KeyEvent event)  {  
         if (keyCode == KeyEvent.KEYCODE_BACK )   {  
        	final MyAlertDialog ad=new MyAlertDialog(LoginProActivity.this);
 			ad.setTitle("提示");
 			ad.setMessage("\t\t\t确定要退出吗？");
 			ad.setLeftButton("确定" ,new MyDialogInt(){
 				public void onClick(View view) {
 					ad.dismiss();
 					LoginProActivity.this.finish();
 				}
 			});
 			ad.setRightButton("取消" ,new MyDialogInt(){
 				public void onClick(View view) {
 					ad.dismiss();					
 				}
 			});
         }    
         return false;       
     }
  	 
  	
  	/*
	 * 保存用户信息
	 */
	private void SaveUserDate() {
		// 载入配置文件
		SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
		// 写入配置文件
		Editor spEd = sp.edit();
		if (RememberPwd.isChecked()) {
			spEd.putBoolean("isSave", true);
			spEd.putString("name", Num.getText().toString());
			spEd.putString("password", Pwd.getText().toString());
		} else {
			spEd.putBoolean("isSave", false);
			spEd.putString("name", "");
			spEd.putString("password", "");
		}
		spEd.commit();
	}

	/*
	 * 载入已记住的用户信息
	 */
	private void LoadUserDate() {
		SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
		if (sp.getBoolean("isSave", false)) {
			String username = sp.getString("name", "");
			String userpassword = sp.getString("password", "");
			if (!("".equals(username) && "".equals(userpassword))) {
				Num.setText(username);
				Pwd.setText(userpassword);
				RememberPwd.setChecked(true);
			}
		}
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
				Toast.makeText(LoginProActivity.this, "欢迎回来！", Toast.LENGTH_SHORT).show();
    			SaveUserDate(); 
				Intent intent=new Intent(LoginProActivity.this,MainActivity.class);
  				startActivity(intent);
  				LoginProActivity.this.finish();

			} else
			{
				Toast.makeText(LoginProActivity.this, "道友是不是忘了些什么！", Toast.LENGTH_SHORT).show();	
			}
		}

	}    
	class NetWorkThread extends Thread
	{
		String strResult;
		int state;
		JSONObject mJson;
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			System.out.println("thread--->" + Thread.currentThread().getName());
			String httpUrl = getString(R.string.httpurl) + "/IndoorNavigation/login.php";
			String parms = "name=" + Num.getText().toString() + "&pwd=" + Pwd.getText().toString();
			System.out.println(httpUrl+"?"+parms);
			try
			{
				strResult = mhttpGet(httpUrl, parms);
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

			Message msg = mHandler.obtainMessage();
			msg.arg1 = state;
			mHandler.sendMessage(msg);
		}
	}
	public static String mhttpGet(String url, String params) throws Exception
	{
		String response = null; //返回信息
		//拼接请求URL
		if (null!=params&&!params.equals(""))
		{
			url += "?" + params;
		}
		int timeoutConnection = 1000;  
		int timeoutSocket = 3000;  
		HttpParams httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  	    
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);// Set the default socket timeout (SO_TIMEOUT) 
	    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);  
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
	
}