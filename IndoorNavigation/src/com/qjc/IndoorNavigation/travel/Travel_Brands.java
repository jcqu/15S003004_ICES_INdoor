package com.qjc.IndoorNavigation.travel;

import com.qjc.IndoorNavigation.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Travel_Brands extends Activity {
	private Button mBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.brands);
		mBack= (Button)findViewById(R.id.brand_title_back);
		mBack.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			Travel_Brands.this.finish();
    		}
    	});
	}
}
