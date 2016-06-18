package com.qjc.IndoorNavigation.travel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.qjc.IndoorNavigation.R;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Travel_Message extends Activity {
	private Button mMenu;
	private ListView mDisplay;
	private String[] mTitles = {"好友消息", "系统消息", "评论", "圈子"};
	private Context mContext;
	MessageAdapter myAdapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.message);
		myAdapter = new MessageAdapter(this);
		mMenu = (Button)findViewById(R.id.message_title_back);
		mMenu.setOnClickListener(new OnClickListener()
    	{
    		public void onClick(View v)
    		{
    			Travel_Message.this.finish();
    		}
    	});
		mDisplay = (ListView) findViewById(R.id.message_display);
		mDisplay.setAdapter(myAdapter); 
	}	

	class MessageAdapter extends BaseAdapter {

	public int getCount() {
		return mTitles.length;
	}

	public Object getItem(int position) {
		return mTitles[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public MessageAdapter(Context context) {
		mContext = context;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.message_item_title);
			holder.messageCount = (TextView) convertView
					.findViewById(R.id.message_item_messagecount);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(mTitles[position]);
		holder.messageCount.setText("0条新");
		return convertView;
	}

	class ViewHolder {
		TextView title;
		TextView messageCount;
	}
 }
	
}
