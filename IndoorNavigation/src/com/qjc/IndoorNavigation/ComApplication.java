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
 * @Description: TODO ��Ź��е�����
 * @author ���� 
 * @date 2015-3-23 ����3:59:19
 */
public class ComApplication extends Application {

	public Bitmap mDefault_Avatar;
	/**
	 * Ĭ��ͷ�񻺴�
	 */
	public HashMap<String, SoftReference<Bitmap>> mDefaultAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ͷ������
	 */
	public String[] mAvatars;
	/**
	 * ������ҳͷ�񻺴�
	 */
	public HashMap<String, SoftReference<Bitmap>> mPublicPageAvatarCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ������ҳͷ������
	 */
	public String[] mPublicPageAvatars;
	/**
	 * ��Ƭ����
	 */
	public HashMap<String, SoftReference<Bitmap>> mPhotoCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ��Ƭ����
	 */
	public String[] mPhotosName;
	/**
	 * ת��ͼƬ����
	 */
	public HashMap<String, SoftReference<Bitmap>> mViewedCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ת��ͼƬ����
	 */
	public String[] mViewedName;
	/**
	 * ����ת��ͼƬ����
	 */
	public HashMap<String, SoftReference<Bitmap>> mViewedHotCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ����ת��ͼƬ����
	 */
	public String[] mViewedHotName;
	/**
	 * ��ϷͼƬ����
	 */
	public HashMap<String, SoftReference<Bitmap>> mRecommendCache = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * ������Ƭ����
	 */
	public HashMap<String, SoftReference<Bitmap>> mNearbyPhoto = new HashMap<String, SoftReference<Bitmap>>();
	

	/**
	 * ��ǰ�û�����������պ�������
	 */
	public List<FriendsBirthdayResult> mMyFriendsBirthdayResults = new ArrayList<FriendsBirthdayResult>();

	/**
	 * ��ǰ�û����Ƽ��ٷ�ģ������
	 */
	public List<RecommendResult> mMyRecommendOfficialResults = new ArrayList<RecommendResult>();
	/**
	 * ��ǰ�û����Ƽ�Ӧ����������
	 */
	public List<RecommendResult> mMyRecommendAppDownLoadResults = new ArrayList<RecommendResult>();

	/**
	 * ��ǰ�û��ĸ�����������
	 */
	public List<NearbyPeopleResult> mMyNearByPeopleResults = new ArrayList<NearbyPeopleResult>();

	/**
	 * ��ǰ�û��ĸ�������Ƭ����
	 */
	public List<NearbyPhotoResult> mMyNearbyPhotoResults = new ArrayList<NearbyPhotoResult>();

	/**
	 * ��ǰ�û��ĵ���λ������
	 */
	public List<LocationResult> mMyLocationResults = new ArrayList<LocationResult>();



	/**
	 * �����������ĺ���
	 */
	public Map<String, Map<String, String>> mGiftFriendsList = new HashMap<String, Map<String, String>>();

	/**
	 * �������ͼƬ
	 */
	public HashMap<String, SoftReference<Bitmap>> mGiftsCache = new HashMap<String, SoftReference<Bitmap>>();

	public String[] mGiftsName;
	/**
	 * �������ľ�����Ϣ
	 */
	public List<GiftResult> mGiftResults = new ArrayList<GiftResult>();

	
	public void onCreate() {
		super.onCreate();	
		mDefault_Avatar = PhotoUtil.toRoundCorner(
				BitmapFactory.decodeResource(getResources(), R.drawable.head),
				15);
		/**
		 * ��ʼ�����е�������Ϣ
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
	 * ���������Ż�ȡ����ͼƬ
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
	 * ���ݱ�Ż�ȡ������ҳͷ��
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
	 * ���ݱ�Ż�ȡת��ͼƬ
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
	 * ���ݱ�Ż�ȡ����ת��ͼƬ
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
	 * ���ݱ�����ƻ�ȡ��ϷͼƬ
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
	 * ���ݱ�Ż�ȡ�û�Ĭ��ͷ��
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
	 * ����ͼƬ���ƻ�ȡ������Ƭ��ͼƬ
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
