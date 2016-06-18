package com.qjc.IndoorNavigation;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.qjc.IndoorNavigation.R;
import com.qjc.IndoorNavigation.travel.Travel_Brands;
import com.qjc.IndoorNavigation.travel.Travel_Game;
import com.qjc.IndoorNavigation.travel.Travel_Loction;
import com.qjc.IndoorNavigation.travel.Travel_Message;
import com.qjc.IndoorNavigation.travel.Travel_Gifts;
import com.qjc.IndoorNavigation.utils.SlideImageLayout;
/**
 * @ClassName: TravelDiaryActivity 
 * @Description: TODO 服务页面Activity
 * @author 锦年 
 * @date 2015-3-23 下午4:01:51
 */
public class TravelDiaryActivity extends Activity implements View.OnClickListener{
	// 滑动图片的集合
		private ArrayList<View> mImagePageViewList = null;
		private ViewGroup mViewGroup = null;
		private ViewPager mViewPager = null;
		// 包含圆点图片的View
		private ViewGroup mImageCircleView = null;
		private ImageView[] mImageCircleViews = null; 
		
		// 滑动标题
		private TextView mSlideTitle = null;
		//ViewPager索引
		//private int mPagerIndex;
		// 布局设置类
		private SlideImageLayout mSlideLayout = null;
		// 数据解析类
		private NewsXmlParser mParser = null; 
		private TextView lt1,lt2,lt3,rt1,rt2,rt3;
			
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			initView();
			lt1=(TextView) findViewById(R.id.ltview1);
			lt2=(TextView) findViewById(R.id.ltview2);
			lt3=(TextView) findViewById(R.id.ltview3);
			rt1=(TextView) findViewById(R.id.rtview1);
			rt2=(TextView) findViewById(R.id.rtview2);
			rt3=(TextView) findViewById(R.id.rtview3);
			lt1.setOnClickListener(this);  			lt2.setOnClickListener(this);  
			lt3.setOnClickListener(this);  			rt1.setOnClickListener(this);  
			rt2.setOnClickListener(this);  			rt3.setOnClickListener(this);  
			lt1.setTag(1); 			lt2.setTag(2); 		lt3.setTag(3); 
			rt1.setTag(4); 			rt2.setTag(5);		rt3.setTag(6); 
		}


		private void initView() {
			// 滑动图片区域

			mImagePageViewList = new ArrayList<View>();
			LayoutInflater inflater = getLayoutInflater();  
			mViewGroup = (ViewGroup)inflater.inflate(R.layout.traveldiary_page, null);
			mViewPager = (ViewPager) mViewGroup.findViewById(R.id.image_slide_page);  
			// 圆点图片区域
			mParser = new NewsXmlParser();
			int length = mParser.getSlideImages().length;
			mImageCircleViews = new ImageView[length];
			mImageCircleView = (ViewGroup) mViewGroup.findViewById(R.id.layout_circle_images);
			mSlideLayout = new SlideImageLayout(TravelDiaryActivity.this);
			mSlideLayout.setCircleImageLayout(length);
			
			for(int i = 0; i < length; i++){
				mImagePageViewList.add(mSlideLayout.getSlideImageLayout(mParser.getSlideImages()[i]));
				mImageCircleViews[i] = mSlideLayout.getCircleImageLayout(i);
				mImageCircleView.addView(mSlideLayout.getLinearLayout(mImageCircleViews[i], 10, 10));
			}
			
			// 设置默认的滑动标题
			mSlideTitle = (TextView) mViewGroup.findViewById(R.id.tvSlideTitle);
			mSlideTitle.setText(mParser.getSlideTitles()[0]);
			
			setContentView(mViewGroup);
			
			// 设置ViewPager
	        mViewPager.setAdapter(new SlideImageAdapter());  
	        mViewPager.setOnPageChangeListener(new ImagePageChangeListener());
		}
	
		//滑动图片数据适配器
		private class SlideImageAdapter extends PagerAdapter {  
			@Override  
			public int getCount() { 
				return mImagePageViewList.size();  
			}  

			@Override  
			public boolean isViewFromObject(View view, Object object) {  
				return view == object;  
			}  

			@Override  
			public int getItemPosition(Object object) {  
				return super.getItemPosition(object);  
			}  

			@Override  
			public void destroyItem(View view, int arg1, Object arg2) {  
				((ViewPager) view).removeView(mImagePageViewList.get(arg1));  
			}  
			
			@Override  
			public Object instantiateItem(View view, int position) {  
				((ViewPager) view).addView(mImagePageViewList.get(position));
        
				return mImagePageViewList.get(position);  
			}  
		}


		 public void onClick(View v){  
			 
			 int tag = (Integer) v.getTag();  
		     switch(tag){  
		     	case 1:  
		     		Intent intent1 = new Intent(TravelDiaryActivity.this, Travel_Message.class);  
                    startActivity(intent1);  
		            break;  
		        case 2:  
		        	Intent intent2 = new Intent(TravelDiaryActivity.this, Travel_Loction.class);  
                    startActivity(intent2);
		            break;  
		        case 3:   
		        	Intent intent3 = new Intent(TravelDiaryActivity.this, Travel_Gifts.class);  
                    startActivity(intent3);
		            break;  
		        case 4:  
		        	Intent intent4 = new Intent(TravelDiaryActivity.this, Travel_Game.class);  
                    startActivity(intent4);
		            break; 
		        case 5:   
		        	Intent intent5 = new Intent(TravelDiaryActivity.this, Travel_Brands.class);  
                    startActivity(intent5);
		            break; 
		        case 6:   
		            break; 
		        default :  
		            break;  
		        }  
		  
		    }  
		 // 滑动页面更改事件监听器
		 private class ImagePageChangeListener implements OnPageChangeListener {
			 @Override  
			 public void onPageScrollStateChanged(int arg0) {  
			 }  
			 
			 @Override  
			 public void onPageScrolled(int arg0, float arg1, int arg2) {  
			 }  

			 @Override  
			 public void onPageSelected(int index) {  
				 //    	pageIndex = index;
				 mSlideLayout.setPageIndex(index);
				 mSlideTitle.setText(mParser.getSlideTitles()[index]);
    	
				 for (int i = 0; i < mImageCircleViews.length; i++) {  
					 mImageCircleViews[index].setBackgroundResource(R.drawable.dot_selected);
            
					 if (index != i) {  
						 mImageCircleViews[i].setBackgroundResource(R.drawable.dot_none);  
					 }  
				 }
			 }  
		 }
}
