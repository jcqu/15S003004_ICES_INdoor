package com.qjc.IndoorNavigation.travel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.qjc.IndoorNavigation.ComApplication;
import com.qjc.result.RecommendResult;
import com.qjc.IndoorNavigation.utils.TextUtil;
import com.qjc.IndoorNavigation.R;

public class Travel_Game extends Activity{ 
	private Context mContext;
	private ComApplication mComApplication;
	private View mRecommend;
	private Button mMenu;
	private ListView mDisplay;
	private Button mOfficial;
	private Button mAppDownLoad;
	private RecommendAdapter mAdapter;
	// 是否为官方模块
	private boolean mIsOfficial = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend);
		mComApplication = new ComApplication();
		mMenu = (Button) findViewById(R.id.gift_title_back);
		mMenu.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
    		{
    			Travel_Game.this.finish();
    		}
		});
		mDisplay = (ListView)findViewById(R.id.recommend_display);
		mOfficial = (Button)findViewById(R.id.recommend_official);
		mAppDownLoad = (Button)findViewById(R.id.recommend_appdownload);
		mContext=this;
		mOfficial.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!mIsOfficial) {
					mIsOfficial = true;
					mOfficial
							.setBackgroundResource(R.drawable.bottomtabbutton_leftred);
					mAppDownLoad
							.setBackgroundResource(R.drawable.bottomtabbutton_rightwhite);
					mAdapter = new RecommendAdapter(mComApplication.mMyRecommendOfficialResults,mContext);
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		mAppDownLoad.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (mIsOfficial) {
					mIsOfficial = false;
					mOfficial
							.setBackgroundResource(R.drawable.bottomtabbutton_leftwhite);
					mAppDownLoad
							.setBackgroundResource(R.drawable.bottomtabbutton_rightred);
					mAdapter = new RecommendAdapter(mComApplication.mMyRecommendAppDownLoadResults,mContext);
					mDisplay.setAdapter(mAdapter);
				}
			}
		});
		// 获取官方模块数据
		getOfficial();
		// 获取应用下载数据
		getAppDownLoad();
		// 添加适配器
		mAdapter = new RecommendAdapter(mComApplication.mMyRecommendOfficialResults,this);
		mDisplay.setAdapter(mAdapter);
	}

	/**
	 * 获取官方模块数据
	 */
	private void getOfficial() {
		if (mComApplication.mMyRecommendOfficialResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open(
						"data/recommend_official.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				RecommendResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new RecommendResult();
					result.setName(array.getJSONObject(i).getString("name"));
					result.setIcon(array.getJSONObject(i).getString("icon"));
					result.setTitle(array.getJSONObject(i)
							.getJSONObject("title").getBoolean("istitle"));
					result.setTitleName(array.getJSONObject(i)
							.getJSONObject("title").getString("titlename"));
					mComApplication.mMyRecommendOfficialResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取应用下载数据
	 */
	private void getAppDownLoad() {
		if (mComApplication.mMyRecommendAppDownLoadResults.isEmpty()) {
			InputStream inputStream;
			try {
				inputStream = mContext.getAssets().open(
						"data/recommend_appdownload.KX");
				String json = new TextUtil(mComApplication)
						.readTextFile(inputStream);
				JSONArray array = new JSONArray(json);
				RecommendResult result = null;
				for (int i = 0; i < array.length(); i++) {
					result = new RecommendResult();
					result.setName(array.getJSONObject(i).getString("name"));
					result.setIcon(array.getJSONObject(i).getString("icon"));
					result.setDescription(array.getJSONObject(i).getString(
							"description"));
					mComApplication.mMyRecommendAppDownLoadResults.add(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class RecommendAdapter extends BaseAdapter {
		private List<RecommendResult> mResults;

		public RecommendAdapter(List<RecommendResult> results,Context context) {
			mContext = context;
			if (results != null) {
				mResults = results;
			} else {
				mResults = new ArrayList<RecommendResult>();
			}
		}

		public int getCount() {
			return mResults.size();
		}

		public Object getItem(int position) {
			return mResults.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.recommend_item, null);
				holder = new ViewHolder();
				holder.title = (TextView) convertView
						.findViewById(R.id.recommend_item_title);
				holder.title_line = (ImageView) convertView
						.findViewById(R.id.recommend_item_title_line);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.recommend_item_icon);
				holder.name = (TextView) convertView
						.findViewById(R.id.recommend_item_name);
				holder.description = (TextView) convertView
						.findViewById(R.id.recommend_item_description);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			RecommendResult result = mResults.get(position);
			if (mIsOfficial) {
				holder.description.setVisibility(View.GONE);
				if (result.isTitle()) {
					holder.title.setVisibility(View.VISIBLE);
					holder.title_line.setVisibility(View.VISIBLE);
					holder.title.setText(result.getTitleName());
				} else {
					holder.title.setVisibility(View.GONE);
					holder.title_line.setVisibility(View.GONE);
				}
			} else {
				holder.description.setVisibility(View.VISIBLE);
				holder.title.setVisibility(View.GONE);
				holder.title_line.setVisibility(View.GONE);
				holder.description.setText(result.getDescription());
			}
			holder.icon.setImageBitmap(mComApplication.getRecommend(result
					.getIcon(),mContext));
			holder.name.setText(result.getName());
			return convertView;
		}

		class ViewHolder {
			TextView title;
			ImageView title_line;
			ImageView icon;
			TextView name;
			TextView description;
		}
	}

	public View getView() {
		return mRecommend;
	}

}
