package com.qjc.IndoorNavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.qjc.IMU.cap.CapActivity;
import com.qjc.IMU.step.StepMActivity;
import com.qjc.IndoorNavigation.R;
import com.qjc.IndoorNavigation.Json.JsonHttpGet;

/**
 * @ClassName: HelpActivity
 * @Description: TODO ����ҳ��Activity
 * @author ����
 * @date 2015-3-23 ����3:59:52
 */
public class HelpActivity extends Activity {
	private Button mBack;
	private Button btn1;
	private Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help);
		mBack = (Button) findViewById(R.id.title_down);
		btn1 = (Button) findViewById(R.id.btn3);// ָ����
		btn2 = (Button) findViewById(R.id.btn4);// �Ʋ���
		mBack.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				JsonHttpGet.getMapInfo(getString(R.string.httpurl), "sj", "1.0");
			}
		});
		// change the interface to Capѡ��ָ���빦��
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HelpActivity.this, CapActivity.class);
				startActivity(intent);
			}
		});

		// change the interface to Stepѡ������⹦��
		btn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(HelpActivity.this,StepMActivity.class);
				startActivity(intent);

			}
		});
	}
}
