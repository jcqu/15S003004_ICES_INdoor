package com.qjc.IndoorNavigation;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ViewFlipper;
import com.qjc.IndoorNavigation.R;
/**
 * @ClassName: MainActivity 
 * @Description: TODO MainActivity 
 * @author 锦年 
 * @date 2015-3-23 下午4:00:19
 */
public class MainActivity extends ActivityGroup implements OnCheckedChangeListener {

	public static MainActivity instance = null;
	private ViewFlipper container;// 使用ViewFlipper进行屏幕叶卡切换
	private RadioGroup radio_group;//
	private Intent mIntent;
	@SuppressWarnings("unused")
	private RadioButton radio_login;
	@SuppressWarnings("unused")
	private RadioButton radio_traveldiary;
	@SuppressWarnings("unused")
	private RadioButton radio_voice;
	@SuppressWarnings("unused")
	private RadioButton radio_help; 
	//退出时间
	private long mExitTime;
	//退出间隔
	private static final int INTERVAL = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		// 初始化视图
		container = (ViewFlipper) findViewById(R.id.container);
		radio_group = (RadioGroup) findViewById(R.id.radio_group);		
		radio_login=(RadioButton)findViewById(R.id.radio_login);
		radio_traveldiary=(RadioButton)findViewById(R.id.radio_traveldiary);
		radio_voice=(RadioButton)findViewById(R.id.radio_voice);
		radio_help=(RadioButton)findViewById(R.id.radio_help);
		// 根据id区分页卡
		switchPage(0);
		//点击后页卡响应
		radio_group.setOnCheckedChangeListener(this);
	}

	/**
	 * 区分不同的页面 
	 * @param positoon
	 */
	private void switchPage(int positoon) {
		switch (positoon) {
		case 0:
			mIntent = new Intent(this, NavigationActivity.class);
			break;
		case 1:
			mIntent = new Intent(this, TravelDiaryActivity.class);
			break;
		case 2:
			mIntent = new Intent(this, GetapinfoActivity.class);
			break;
		case 3:
			mIntent = new Intent(this, HelpActivity.class);
			break;

		default:
			break;
		}
		
		 container.removeAllViews();
	        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        Window subActivity = getLocalActivityManager().startActivity("subActivity", mIntent);
	        container.addView(subActivity.getDecorView(), new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	                LayoutParams.FILL_PARENT));
	}


	/**
	 * 根据checkedId来判断选定的Radio，从而进行页面的换
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_login:
			
			switchPage(0);
			
			break;
			
		case R.id.radio_traveldiary:
			
			switchPage(1);
			break;
			
		case R.id.radio_voice:
			
			switchPage(2);
			break;
			
		case R.id.radio_help:
			
			switchPage(3);
			break;

		default:
			break;
		}
		
	}
	

	/**
	 * 判断两次返回时间间隔,小于两秒则退出程序
	 */
	public void onBackPressed(){
		
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "再按一次返回键,可直接退出程序", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
