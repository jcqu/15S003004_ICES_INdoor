package com.qjc.IndoorNavigation.travel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.qjc.IndoorNavigation.ComApplication;
import com.qjc.IndoorNavigation.R;
import com.qjc.result.NearbyPeopleResult;
import com.qjc.result.NearbyPhotoDetailResult;
import com.qjc.result.NearbyPhotoResult;
import com.qjc.IndoorNavigation.utils.MyListView;
import com.qjc.IndoorNavigation.utils.NearbyPhotoLayout;
import com.qjc.IndoorNavigation.utils.TextUtil;

public class Travel_Loction extends Activity{
	private Context mContext;
	private ComApplication mComApplication;
	private View mLbs;
	private Button mMenu;
	private ScrollView mDisplayLayout;
	private MyListView mDisplay;
	private TextView mNoDisplay1;
	private Button mCheckIn;
	private Button mNearbyPeople;
	private Button mNearbyPhoto;
	private int mChoosePostition = 0;
	private NearbyPeopleAdapter mNearbyPeopleAdapter;
	private NearbyPhotoAdapter mNearbyPhotoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lbs);
		mComApplication = new ComApplication();
		mContext=this;
		mMenu = (Button)findViewById(R.id.loc_title_back);
		mMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
    		{
    			Travel_Loction.this.finish();
    		}
		});
		mDisplayLayout = (ScrollView)findViewById(R.id.lbs_display_layout);
		mDisplay = (MyListView)findViewById(R.id.lbs_display);
		mNoDisplay1 = (TextView)findViewById(R.id.lbs_nodisplay);
		mCheckIn = (Button)findViewById(R.id.lbs_checkin);
		mNearbyPeople = (Button)findViewById(R.id.lbs_nearby_people);
		mNearbyPhoto = (Button)findViewById(R.id.lbs_nearby_photo);
		mCheckIn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 0) {
					mChoosePostition = 0;
					mDisplayLayout.setVisibility(View.GONE);
					mNoDisplay1.setVisibility(View.VISIBLE);
					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
				}
			}
		});
		mNearbyPeople.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 1) {
					mChoosePostition = 1;
					mDisplayLayout.setVisibility(View.VISIBLE);
					mNoDisplay1.setVisibility(View.GONE);
					mDisplay.setDivider(new ColorDrawable(Color
							.parseColor("#aaaaaa")));
					mDisplay.setDividerHeight(1);
					mDisplay.setBackgroundColor(Color.parseColor("#00000000"));
					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_red);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mDisplay.setAdapter(mNearbyPeopleAdapter);
				}
			}
		});
		mNearbyPhoto.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mChoosePostition != 2) {
					mChoosePostition = 2;
					mDisplayLayout.setVisibility(View.VISIBLE);
					mNoDisplay1.setVisibility(View.GONE);
					mDisplay.setDivider(null);
					mDisplay.setDividerHeight(0);
					mDisplay.setBackgroundResource(R.drawable.listbox);
					mCheckIn.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mNearbyPeople
							.setBackgroundResource(R.drawable.bottomtabbutton_white);
					mNearbyPhoto
							.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					mDisplay.setAdapter(mNearbyPhotoAdapter);
				}
			}
		});
		mDisplayLayout.setVisibility(View.GONE);
		mNoDisplay1.setVisibility(View.VISIBLE);
		// 获取附近的人的数据
		getNearbyPeople();
		// 获取附近的照片数据
		getNearbyPhoto();
		// 初始化适配器
		mNearbyPeopleAdapter = new NearbyPeopleAdapter(this);
		mNearbyPhotoAdapter = new NearbyPhotoAdapter(this);
	}

	/**
	 * 获取附近的人的数据
	 */
	private void getNearbyPeople() {
		if (mComApplication.mMyNearByPeopleResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open(
						"data/my_nearbypeople.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				NearbyPeopleResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new NearbyPeopleResult();
					result.setUid(array.getJSONObject(i).getString("uid"));
					result.setName(array.getJSONObject(i).getString("name"));
					result.setAvatar(array.getJSONObject(i).getInt("avatar"));
					result.setTime(array.getJSONObject(i).getString("time"));
					result.setDistance(array.getJSONObject(i).getString(
							"distance"));
					result.setPicture(array.getJSONObject(i).getBoolean(
							"picture"));
					result.setLocation(array.getJSONObject(i).getString(
							"location"));
					mComApplication.mMyNearByPeopleResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取附近的照片的数据
	 */
	private void getNearbyPhoto() {
		if (mComApplication.mMyNearbyPhotoResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open(
						"data/my_nearbyphoto.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				NearbyPhotoResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new NearbyPhotoResult();
					result.setPid(array.getJSONObject(i).getString("pid"));
					result.setImage(array.getJSONObject(i).getString("image"));
					result.setTitle(array.getJSONObject(i).getString("title"));
					result.setCount(array.getJSONObject(i).getInt("count"));
					result.setLocation(array.getJSONObject(i).getString(
							"location"));
					result.setDistance(array.getJSONObject(i).getString(
							"distance"));
					JSONArray imagesArray = array.getJSONObject(i)
							.getJSONArray("images");
					List<NearbyPhotoDetailResult> images = new ArrayList<NearbyPhotoDetailResult>();
					for (int j = 0; j < imagesArray.length(); j++) {
						NearbyPhotoDetailResult nearbyPhotoDetailResult = new NearbyPhotoDetailResult();
						nearbyPhotoDetailResult.setImage(imagesArray
								.getJSONObject(j).getString("image"));
						nearbyPhotoDetailResult.setOwners_uid(imagesArray
								.getJSONObject(j).getJSONObject("owners")
								.getString("uid"));
						nearbyPhotoDetailResult.setOwners_name(imagesArray
								.getJSONObject(j).getJSONObject("owners")
								.getString("name"));
						nearbyPhotoDetailResult.setOwners_avatar(imagesArray
								.getJSONObject(j).getJSONObject("owners")
								.getInt("avatar"));
						nearbyPhotoDetailResult.setTime(imagesArray
								.getJSONObject(j).getString("time"));
						nearbyPhotoDetailResult.setDescription(imagesArray
								.getJSONObject(j).getString("description"));
						nearbyPhotoDetailResult.setComment_count(imagesArray
								.getJSONObject(j).getInt("comment_count"));
						nearbyPhotoDetailResult.setLike_count(imagesArray
								.getJSONObject(j).getInt("like_count"));
						images.add(nearbyPhotoDetailResult);
					}
					result.setImages(images);
					mComApplication.mMyNearbyPhotoResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class NearbyPeopleAdapter extends BaseAdapter {
		
		public NearbyPeopleAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mComApplication.mMyNearByPeopleResults.size();
		}

		public Object getItem(int position) {
			return mComApplication.mMyNearByPeopleResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.lbs_nearby_people_item, null);
				holder = new ViewHolder();
				holder.avatar = (ImageView) convertView
						.findViewById(R.id.lbs_nearby_people_item_avatar);
				holder.name = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_name);
				holder.location = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_location);
				holder.time = (TextView) convertView
						.findViewById(R.id.lbs_nearby_people_item_time);
				holder.picture = (ImageView) convertView
						.findViewById(R.id.lbs_nearby_people_item_picture);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			NearbyPeopleResult result = mComApplication.mMyNearByPeopleResults
					.get(position);
			holder.avatar.setImageBitmap(mComApplication.getDefaultAvatar(result
					.getAvatar(),mContext));
			holder.name.setText(result.getName());
			holder.location.setText(result.getLocation());
			holder.time.setText(result.getTime() + "  " + result.getDistance());
			if (result.isPicture()) {
				holder.picture.setVisibility(View.VISIBLE);
			} else {
				holder.picture.setVisibility(View.GONE);
			}
			if (position == 0) {
				convertView.setBackgroundResource(R.drawable.lbs_photo_head_bg);
			} else if (position == getCount() - 1) {
				convertView.setBackgroundResource(R.drawable.lbs_photo_more_bg);
			} else {
				convertView
						.setBackgroundResource(R.drawable.lbs_photo_middle_bg);
			}
			return convertView;
		}

		class ViewHolder {
			ImageView avatar;
			TextView name;
			TextView location;
			TextView time;
			ImageView picture;
		}

	}

	private class NearbyPhotoAdapter extends BaseAdapter {
		
		public NearbyPhotoAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			return mComApplication.mMyNearbyPhotoResults.size();
		}

		public Object getItem(int position) {
			return mComApplication.mMyNearbyPhotoResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.lbs_nearby_photo_item, null);
				holder = new ViewHolder();
				holder.location = (TextView) convertView
						.findViewById(R.id.lbs_nearby_photo_item_location);
				holder.distance = (TextView) convertView
						.findViewById(R.id.lbs_nearby_photo_item_distance);
				holder.display = (LinearLayout) convertView
						.findViewById(R.id.lbs_nearby_photo_item_display);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			NearbyPhotoResult result = mComApplication.mMyNearbyPhotoResults
					.get(position);
			holder.location.setText(result.getLocation());
			holder.distance.setText(result.getDistance());
			if (result.getCount() > 0) {
				holder.display.setVisibility(View.VISIBLE);
				holder.display.removeAllViews();
				for (int i = 0; i < result.getCount(); i++) {
					NearbyPhotoDetailResult nearbyPhotoDetailResult = result
							.getImages().get(i);
					NearbyPhotoLayout layout = new NearbyPhotoLayout(mContext,
							mComApplication);
					holder.display.addView(layout.getLayout());
					holder.display.invalidate();
					layout.setPhoto(nearbyPhotoDetailResult.getImage());
					layout.getLayout().setTag(nearbyPhotoDetailResult);
					layout.getLayout().setOnClickListener(
							new OnClickListener() {

								public void onClick(View v) {
									NearbyPhotoDetailResult result = (NearbyPhotoDetailResult) v
											.getTag();
									System.out.println(result.getDescription());
								}
							});
				}
			} else {
				holder.display.setVisibility(View.GONE);
			}
			return convertView;
		}

		class ViewHolder {
			TextView location;
			TextView distance;
			LinearLayout display;
		}
	}

	public View getView() {
		return mLbs;
	}
}
