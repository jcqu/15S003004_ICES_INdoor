package com.qjc.IndoorNavigation.utils;

import com.qjc.IndoorNavigation.ComApplication;
import com.qjc.IndoorNavigation.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
/**
 * @ClassName: NearbyPhotoLayout 
 * @Description: TODO
 * @author ½õÄê 
 * @date 2015-3-23 ÏÂÎç4:04:02
 */
public class NearbyPhotoLayout {
	private ComApplication mComApplication;
	private View mLayout;
	private Context mContext;
	private ImageView mPhoto;

	public NearbyPhotoLayout(Context context, ComApplication application) {
		mContext=context;
		mComApplication = application;
		mLayout = LayoutInflater.from(context).inflate(R.layout.lbs_nearby_photo_display_item, null);
		mPhoto = (ImageView) mLayout.findViewById(R.id.lbs_nearby_photo_display_item_photo);
	}

	public View getLayout() {
		return mLayout;
	}

	public void setPhoto(String image) {
		mPhoto.setImageBitmap(mComApplication.getNearbyPhoto(image,mContext));
	}
}
