package com.qjc.IndoorNavigation.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import com.qjc.IndoorNavigation.ComApplication;

/**
 * @ClassName: TextUtil 
 * @Description: TODO
 * @author 锦年 
 * @date 2015-3-23 下午4:05:10
 */
public class TextUtil {
	
	@SuppressWarnings("unused")
	private ComApplication mComApplication;

	public TextUtil(ComApplication application) {
		mComApplication = application;
	}
	/**
	 * 根据输入流转换成字符串
	 * 
	 * @param inputStream
	 *            文字输入流
	 * @return 文字字符串(String 类型)
	 */
	public String readTextFile(InputStream inputStream) {
		String readedStr = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				readedStr += tmp;
			}
			br.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return readedStr;
	}

}
