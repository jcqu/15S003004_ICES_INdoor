package com.qjc.IndoorNavigation.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * @ClassName: MyGridView 
 * @Description: TODO
 * @author ½õÄê 
 * @date 2015-3-23 ÏÂÎç4:03:50
 */
public class MyGridView extends GridView {

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
