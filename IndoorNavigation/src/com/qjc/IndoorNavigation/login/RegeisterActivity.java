package com.qjc.IndoorNavigation.login;

import com.qjc.IndoorNavigation.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @ClassName: RegeisterActivity 
 * @Description: TODO 注册页面
 * @author 锦年 
 * @date 2015-3-23 下午4:03:02
 */
public class RegeisterActivity extends Activity{
		private EditText mynum,mypwd;
	   private Button btnAdd;
	  // private DBHelper dbHelper;
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regeist);
		mynum=(EditText) findViewById(R.id.regist_Num);
		mypwd=(EditText) findViewById(R.id.regist_Pwd);
		btnAdd=(Button) findViewById(R.id.btn_regist);
		//dbHelper=new DBHelper(this);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mynum.getText().toString().equals("")){
					Toast.makeText(RegeisterActivity.this,"号码不能为空 ", Toast.LENGTH_SHORT).show();
					return;
				}
				if(mypwd.getText().toString().equals("")){
					Toast.makeText(RegeisterActivity.this,"密码不能为空 ", Toast.LENGTH_SHORT).show();
					return;
				}
				//dbHelper.insert(mynum.getText().toString(), mypwd.getText().toString());
				//SqlLiteLoginInfoActivity.cursor=dbHelper.select(); 
				Toast.makeText(RegeisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(RegeisterActivity.this,LoginProActivity.class);
				startActivity(intent);
				RegeisterActivity.this.finish();
			}
		});
	}
}
