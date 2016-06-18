package com.zbar.lib;

/**
 * @ClassName: ZbarManager 
 * @Description: TODO ZBAR调用
 * @author 锦年 
 * @date 2015-3-23 下午4:07:33
 */
public class ZbarManager {

	static {
		System.loadLibrary("zbar");
	}

	public native String decode(byte[] data, int width, int height, boolean isCrop, int x, int y, int cwidth, int cheight);
}

