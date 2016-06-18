package com.qjc.IndoorNavigation;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.qjc.IndoorNavigation.utils.PhotoUtil;
import com.qjc.result.FriendsBirthdayResult;
import com.qjc.result.GiftResult;
import com.qjc.result.LocationResult;
import com.qjc.result.NearbyPeopleResult;
import com.qjc.result.NearbyPhotoResult;
import com.qjc.result.RecommendResult;

/**
 * @ClassName: ComApplication 
 * @Description: TODO 存放共有的数据
 * @author 锦年 
 * @date 2015-3-23 下午3:59:19
 */
public class ComApplication extends Application {

	public Bitmap mDefault_Avatar;
	/**
	 * 默认头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mDefaultAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 头像名称
	 */
	public String[] mAvatars;
	/**
	 * 公共主页头像缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPublicPageAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 公共主页头像名称
	 */
	public String[] mPublicPageAvatars;
	/**
	 * 照片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mPhotoCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 照片名称
	 */
	public String[] mPhotosName;
	/**
	 * 转帖图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mViewedCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 转帖图片名称
	 */
	public String[] mViewedName;
	/**
	 * 热门转帖图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mViewedHotCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 热门转帖图片名称
	 */
	public String[] mViewedHotName;
	/**
	 * 游戏图片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mRecommendCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 附近照片缓存
	 */
	public HashMap<String, SoftReference<Bitmap>> mNearbyPhoto = new HashMap<String, SoftReference<Bitmap>>();
	

	/**
	 * 当前用户的最近过生日好友数据
	 */
	public List<FriendsBirthdayResult> mMyFriendsBirthdayResults = new ArrayList<FriendsBirthdayResult>();

	/**
	 * 当前用户的推荐官方模块数据
	 */
	public List<RecommendResult> mMyRecommendOfficialResults = new ArrayList<RecommendResult>();
	/**
	 * 当前用户的推荐应用下载数据
	 */
	public List<RecommendResult> mMyRecommendAppDownLoadResults = new ArrayList<RecommendResult>();

	/**
	 * 当前用户的附近的人数据
	 */
	public List<NearbyPeopleResult> mMyNearByPeopleResults = new ArrayList<NearbyPeopleResult>();

	/**
	 * 当前用户的附近的照片数据
	 */
	public List<NearbyPhotoResult> mMyNearbyPhotoResults = new ArrayList<NearbyPhotoResult>();

	/**
	 * 当前用户的地理位置数据
	 */
	public List<LocationResult> mMyLocationResults = new ArrayList<LocationResult>();



	/**
	 * 存放赠送礼物的好友
	 */
	public Map<String, Map<String, String>> mGiftFriendsList = new HashMap<String, Map<String, String>>();

	/**
	 * 存放礼物图片
	 */
	public HashMap<String, SoftReference<Bitmap>> mGiftsCache = new HashMap<String, SoftReference<Bitmap>>();

	public String[] mGiftsName;
	/**
	 * 存放礼物的具体信息
	 */
	public List<GiftResult> mGiftResults = new ArrayList<GiftResult>();

	
	public void onCreate() {
		super.onCreate();	
		mDefault_Avatar = PhotoUtil.toRoundCorner(
				BitmapFactory.decodeResource(getResources(), R.drawable.head),
				15);
		/**
		 * 初始化所有的数据信息
		 */
		try {
			mAvatars = this.getAssets().list("avatar");			
			mPublicPageAvatars = this.getAssets().list("publicpage_avatar");
			mViewedHotName = this.getAssets().list("viewed_hot");
			mGiftsName = this.getAssets().list("gifts");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * 根据礼物编号获取礼物图片
	 */
	public Bitmap getGift(String gid,Context context) {
		try {
			Bitmap bitmap = null;
			if (mGiftsCache.containsKey(gid)) {
				SoftReference<Bitmap> reference = mGiftsCache.get(gid);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets()
					.open("gifts/" + gid));
			mGiftsCache.put(gid, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.gifts_default_01);
		}
	}


	/**
	 * 根据编号获取公共主页头像
	 */
	public Bitmap getPublicPageAvatar(int position,Context context) {
		try {
			String avatarName = mPublicPageAvatars[position];
			Bitmap bitmap = null;
			if (mPublicPageAvatarCache.containsKey(avatarName)) {
				SoftReference<Bitmap> reference = mPublicPageAvatarCache
						.get(avatarName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = PhotoUtil.toRoundCorner(
					BitmapFactory.decodeStream(context.getAssets().open(
							"publicpage_avatar/" + avatarName)), 15);
			mPublicPageAvatarCache.put(avatarName, new SoftReference<Bitmap>(
					bitmap));
			return bitmap;
		} catch (Exception e) {
			return mDefault_Avatar;
		}
	}

	/**
	 * 根据编号获取转帖图片
	 */
	public Bitmap getViewed(int position,Context context) {
		try {
			String viewedName = mViewedName[position];
			Bitmap bitmap = null;
			if (mViewedCache.containsKey(viewedName)) {
				SoftReference<Bitmap> reference = mViewedCache.get(viewedName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"viewed/" + viewedName));
			mViewedCache.put(viewedName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.picture_default_fg);
		}
	}

	/**
	 * 根据编号获取热门转帖图片
	 */
	public Bitmap getViewedHot(int position,Context context) {
		try {
			String viewedHotName = mViewedHotName[position];
			Bitmap bitmap = null;
			if (mViewedHotCache.containsKey(viewedHotName)) {
				SoftReference<Bitmap> reference = mViewedHotCache
						.get(viewedHotName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"viewed_hot/" + viewedHotName));
			mViewedHotCache.put(viewedHotName,
					new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.picture_default_fg);
		}
	}

	/**
	 * 根据编号名称获取游戏图片
	 */
	public Bitmap getRecommend(String position,Context context) {
		try {
			String recommendName = "icon_" + position + ".jpg";
			Bitmap bitmap = null;
			if (mRecommendCache.containsKey(recommendName)) {
				SoftReference<Bitmap> reference = mRecommendCache
						.get(recommendName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"recommend/" + recommendName));
			mRecommendCache.put(recommendName,
					new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 根据编号获取用户默认头像
	 */
	public Bitmap getDefaultAvatar(int position,Context context) {
		try {
			String avatarName = mAvatars[position];
			Bitmap bitmap = null;
			if (mDefaultAvatarCache.containsKey(avatarName)) {
				SoftReference<Bitmap> reference = mDefaultAvatarCache
						.get(avatarName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"avatar/" + avatarName));
			mDefaultAvatarCache.put(avatarName, new SoftReference<Bitmap>(
					bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory
					.decodeResource(context.getResources(), R.drawable.head);
		}
	}
	/**
	 * 根据图片名称获取附近照片的图片
	 */
	public Bitmap getNearbyPhoto(String position,Context context) {
		try {
			String nearbyPhotoName = position + ".jpg";
			Bitmap bitmap = null;
			if (mNearbyPhoto.containsKey(nearbyPhotoName)) {
				SoftReference<Bitmap> reference = mNearbyPhoto.get(nearbyPhotoName);
				bitmap = reference.get();
				if (bitmap != null) {
					return bitmap;
				}
			}
			bitmap = BitmapFactory.decodeStream(context.getAssets().open(
					"nearby_photo/" + nearbyPhotoName));
			mNearbyPhoto.put(nearbyPhotoName, new SoftReference<Bitmap>(bitmap));
			return bitmap;
		} catch (Exception e) {
			return BitmapFactory.decodeResource(context.getResources(),
					R.drawable.lbs_checkin_photo_icon);
		}
	}

	
}
