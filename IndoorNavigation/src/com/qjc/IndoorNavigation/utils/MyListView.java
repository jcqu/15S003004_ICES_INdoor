package com.qjc.IndoorNavigation.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * @ClassName: MyListView 
 * @Description: TODO
 * @author ���� 
 * @date 2015-3-23 ����4:03:57
 */
public class MyListView extends ListView{

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context) {
		super(context);
	}
	/**
	 * ���ò�����
	 */
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
