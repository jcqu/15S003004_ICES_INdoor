package com.qjc.IndoorNavigation.travel;

import java.io.InputStream;
import org.json.JSONArray;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.qjc.IndoorNavigation.utils.PhotoUtil;
import com.qjc.result.FriendsBirthdayResult;
import com.qjc.result.GiftResult;
import com.qjc.IndoorNavigation.utils.MyGridView;
import com.qjc.IndoorNavigation.utils.MyListView;
import com.qjc.IndoorNavigation.utils.TextUtil;
import com.qjc.IndoorNavigation.ComApplication;
import com.qjc.IndoorNavigation.R;

public class Travel_Gifts extends Activity{
	private Context mContext;
	private ComApplication mComApplication;
	private Button mMenu;
	private MyGridView mDisplay;
	private MyListView mFriendsList;
	private Bitmap mDefault_Avatar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gifts);
		mComApplication = new ComApplication();
		mMenu = (Button) findViewById(R.id.gift_title_back);
		mMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
    		{
    			Travel_Gifts.this.finish();
    		}
		});
		mContext=this;
		mDisplay = (MyGridView) findViewById(R.id.gifts_display);
		mFriendsList = (MyListView)findViewById(R.id.gifts_friends_list);
		mDefault_Avatar = PhotoUtil.toRoundCorner(
				BitmapFactory.decodeResource(getResources(), R.drawable.head),
				15);
		// 获取礼物数据
		getGifts();
		// 获取好友生日数据
		getFriendsBirthday();
		// 添加适配器
		mDisplay.setAdapter(new GiftAdapter(this));
		mFriendsList.setAdapter(new BirthdayAdapter(this));
	}

	/**
	 * 获取礼物数据
	 */
	private void getGifts() {
		if (mComApplication.mGiftResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open("data/gifts.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				GiftResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new GiftResult();
					result.setGid(array.getJSONObject(i).getString("gid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setContent(array.getJSONObject(i).getString(
							"content"));
					mComApplication.mGiftResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取好友生日数据
	 */
	private void getFriendsBirthday() {
		if (mComApplication.mMyFriendsBirthdayResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open(
						"data/my_friendsbirthday.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				FriendsBirthdayResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new FriendsBirthdayResult();
					result.setUid(array.getJSONObject(i).getString("uid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setAvatar(array.getJSONObject(i).getInt("avatar"));
					result.setBirthday_date(array.getJSONObject(i).getString(
							"birthday_date"));
					mComApplication.mMyFriendsBirthdayResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class GiftAdapter extends BaseAdapter {
		
		public GiftAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return 8;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gifts_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.gifts_item_image);
				holder.title = (TextView) convertView
						.findViewById(R.id.gifts_item_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			GiftResult result = mComApplication.mGiftResults.get(position);
			holder.image.setImageBitmap(mComApplication.getGift(result.getGid()
					+ ".jpg",mContext));
			holder.title.setText(result.getName());
			return convertView;
		}

		class ViewHolder {
			ImageView image;
			TextView title;
		}
	}

	private class BirthdayAdapter extends BaseAdapter {

		public BirthdayAdapter(Context context) {
			mContext = context;
		}
		
		public int getCount() {
			return 2;
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.gifts_birthday_item, null);
				holder = new ViewHolder();
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.gifts_birthday_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.gifts_birthday_item_name);
				holder.date = (TextView) convertView
						.findViewById(R.id.gifts_birthday_item_date);
				holder.send = (Button) convertView
						.findViewById(R.id.gifts_birthday_item_send);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			FriendsBirthdayResult result = mComApplication.mMyFriendsBirthdayResults
					.get(position);
			holder.avatar.setImageBitmap(mDefault_Avatar);
			holder.name.setText(result.getName());
			holder.date.setText(result.getBirthday_date());
			holder.send.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

				}
			});
			return convertView;
		}

		class ViewHolder {
			ImageView avatar;
			TextView name;
			TextView date;
			Button send;
		}
	}

}
