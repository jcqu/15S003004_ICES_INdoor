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
 * @author ���� 
 * @date 2015-3-23 ����4:00:19
 */
public class MainActivity extends ActivityGroup implements OnCheckedChangeListener {

	public static MainActivity instance = null;
	private ViewFlipper container;// ʹ��ViewFlipper������ĻҶ���л�
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
	//�˳�ʱ��
	private long mExitTime;
	//�˳����
	private static final int INTERVAL = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		// ��ʼ����ͼ
		container = (ViewFlipper) findViewById(R.id.container);
		radio_group = (RadioGroup) findViewById(R.id.radio_group);		
		radio_login=(RadioButton)findViewById(R.id.radio_login);
		radio_traveldiary=(RadioButton)findViewById(R.id.radio_traveldiary);
		radio_voice=(RadioButton)findViewById(R.id.radio_voice);
		radio_help=(RadioButton)findViewById(R.id.radio_help);
		// ����id����ҳ��
		switchPage(0);
		//�����ҳ����Ӧ
		radio_group.setOnCheckedChangeListener(this);
	}

	/**
	 * ���ֲ�ͬ��ҳ�� 
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
	 * ����checkedId���ж�ѡ����Radio���Ӷ�����ҳ��Ļ�
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
	 * �ж����η���ʱ����,С���������˳�����
	 */
	public void onBackPressed(){
		
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "�ٰ�һ�η��ؼ�,��ֱ���˳�����", Toast.LENGTH_SHORT).show();
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
