package com.qjc.IndoorNavigation.login;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import com.qjc.IndoorNavigation.R;
import com.qjc.IndoorNavigation.utils.MyAlertDialog;
import com.qjc.IndoorNavigation.utils.MyAlertDialog.MyDialogInt;
import com.qjc.IndoorNavigation.viewPage.ViewPagerActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * @ClassName: LOGO
 * @Description: TODO 启动页面
 * @author 锦年
 * @date 2015-3-23 下午4:02:49
 */
@SuppressLint({ "HandlerLeak", "ShowToast" })
public class Logo extends Activity {
	private SharedPreferences Isfirst;
	private int userstate;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.logo);
		mHandler = new MyHandler();
		Isfirst = getSharedPreferences("isft", MODE_PRIVATE);
		userstate = Isfirst.getInt("userstate", 0);
		/**
		 * millisInFuture:从开始调用start()到倒计时完成并onFinish()方法被调用的毫秒数
		 * countDownInterval:接收onTick(long)回调的间隔时间
		 */
		new CountDownTimer(3500, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				if (userstate == 0) {
					Intent intent = new Intent(Logo.this,
							ViewPagerActivity.class);
					startActivity(intent);
					SharedPreferences.Editor editor = Isfirst.edit();
					editor.putInt("userstate", 1);
					editor.commit();
				} else if (userstate == 1) {
					Thread t = new NetWorkThread();// 检测新版本
					t.start();
				}
			}
		}.start();
	}

	/*
	 * 进入程序的主界面
	 */
	private void ToLogin() {
		Intent intent = new Intent(Logo.this, LoginProActivity.class);
		startActivity(intent);
		// 结束掉当前的activity
		this.finish();
	}

	/*
	 * 获取当前程序的版本号
	 */
	private String getVersionName() throws Exception {
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		return packInfo.versionName;
	}

	/*
	 * 获取服务器程序的版本号
	 */
	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 0:
				ToLogin();
				break;
			case 1:
				final MyAlertDialog ad = new MyAlertDialog(Logo.this);
				ad.setTitle("发现新版本");
				ad.setMessage("\t\t\t要升级到新版本吗？");
				ad.setLeftButton("确定", new MyDialogInt() {
					public void onClick(View view) {
						downLoadApk();
						ad.dismiss();
					}
				});
				ad.setRightButton("取消", new MyDialogInt() {
					public void onClick(View view) {
						ad.dismiss();
						ToLogin();
					}
				});
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", 1)
						.show();
				ToLogin();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();
				ToLogin();
				break;
			}
		}

	}

	/*
	 * 线程访问服务器
	 */
	class NetWorkThread extends Thread {
		String strResult;
		String verstr;
		int state = -1;
		JSONObject mJson;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			System.out.println("thread--->" + Thread.currentThread().getName());
			String httpUrl = getString(R.string.httpurl)
					+ "/IndoorNavigation/getversion.php";
			String parms = null;
			try {
				strResult = mhttpGet(httpUrl, parms);
				if (strResult != null && strResult.startsWith("\ufeff")) {
					strResult = strResult.substring(1);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Message msg = mHandler.obtainMessage();
				msg.arg1 = 2;
				mHandler.sendMessage(msg);
				e.printStackTrace();
				return;
			}
			try {
				mJson = new JSONObject(strResult);
				verstr = mJson.getString("version");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println("version=" + verstr);
				String verString = getVersionName();
				if (verString.equals(verstr)) {
					state = 0;
				} else if (verstr==null) {
					state = 2;
				} else {
					state = 1;
				}
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = mHandler.obtainMessage();
			msg.arg1 = state;
			System.out.println(state);
			mHandler.sendMessage(msg);
		}
	}

	public static String mhttpGet(String url, String params) throws Exception {
		String response = null; // 返回信息
		// 拼接请求URL
		if (null != params && !params.equals("")) {
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
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) // SC_OK = 200
			{
				// 获得返回结果
				response = EntityUtils.toString(httpResponse.getEntity(),
						"utf-8");
			} else {
				response = "false";
			}
		} catch (Exception e) {
			response = "9";
		}
		return response;
	}

	@SuppressLint("SimpleDateFormat")
	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			SimpleDateFormat  formatter=new SimpleDateFormat ("yyyy-MM-dd-HH:mm");     
			Date  curDate = new Date(System.currentTimeMillis());//获取当前时间     
			String  datestr=formatter.format(curDate);  
		    System.out.println(datestr);
			File file = new File(Environment.getExternalStorageDirectory()
					+ "/IndoorMap/", "updata_"+datestr+".apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					String urlString = getString(R.string.httpurl)
							+ "/IndoorNavigation/apk/IndoorNavigation.apk";
					File file = getFileFromServer(urlString, pd);
					sleep(2500);
					installApk(file);
					ToLogin();
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					Message msg = mHandler.obtainMessage();
					msg.arg1 = 3;
					mHandler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装APK到本地
	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
