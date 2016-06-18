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
 * @author ���� 
 * @date 2015-3-23 ����4:05:10
 */
public class TextUtil {
	
	@SuppressWarnings("unused")
	private ComApplication mComApplication;

	public TextUtil(ComApplication application) {
		mComApplication = application;
	}
	/**
	 * ����������ת�����ַ���
	 * 
	 * @param inputStream
	 *            ����������
	 * @return �����ַ���(String ����)
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
