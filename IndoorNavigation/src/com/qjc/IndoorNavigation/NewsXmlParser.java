package com.qjc.IndoorNavigation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.qjc.IndoorNavigation.R;

/**
 * @ClassName: NewsXmlParser 
 * @Description: TODO ��������/���������б�
 * @author ���� 
 * @date 2015-3-23 ����4:01:24
 */
public class NewsXmlParser {
	
	// ����ͼƬ�ļ��ϣ��������ó��˹̶����أ���ȻҲ�ɶ�̬���ء�
	private int[] slideImages = {
			R.drawable.image01,
			R.drawable.image02,
			R.drawable.image03,
			R.drawable.image04,
			R.drawable.image05};
	
	// ��������ļ���
	private int[] slideTitles = {
			R.string.title1,
			R.string.title2,
			R.string.title3,
			R.string.title4,
			R.string.title5,
	};
	
	// �������ӵļ���
	private String[] slideUrls = {
			"http://mobile.csdn.net/a/20120616/2806676.html",
			"http://cloud.csdn.net/a/20120614/2806646.html",
			"http://mobile.csdn.net/a/20120613/2806603.html",
			"http://news.csdn.net/a/20120612/2806565.html",
			"http://mobile.csdn.net/a/20120615/2806659.html",
	};
	
	public int[] getSlideImages(){
		return slideImages;
	}
	
	public int[] getSlideTitles(){
		return slideTitles;
	}
	
	public String[] getSlideUrls(){
		return slideUrls;
	}
	
	/**
	 * ��ȡXmlPullParser����
	 * @param result
	 * @return
	 */
	private XmlPullParser getXmlPullParser(String result){
		XmlPullParser parser = Xml.newPullParser();
		InputStream inputStream = FileAccess.String2InputStream(result);
		
		try {
			parser.setInput(inputStream, "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return parser;
	}
	
	public int getNewsListCount(String result){
		int count = -1;
		
		try {
			XmlPullParser parser = getXmlPullParser(result);
	        int event = parser.getEventType();//������һ���¼�
	        
	        while(event != XmlPullParser.END_DOCUMENT){
	        	switch(event){
	        	case XmlPullParser.START_DOCUMENT:
	        		break;
	        	case XmlPullParser.START_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؿ�ʼ�¼�
	        		if("count".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���count
	        			count = Integer.parseInt(parser.nextText());
	                }
	        		
	        		break;
	        	case XmlPullParser.END_TAG://�жϵ�ǰ�¼��Ƿ��Ǳ�ǩԪ�ؽ����¼�
//	        		if("count".equals(parser.getName())){//�жϿ�ʼ��ǩԪ���Ƿ���count
//	        			count = Integer.parseInt(parser.nextText());
//	                }
	        		
	        		break;
	        	}
            
	        	event = parser.next();//������һ��Ԫ�ز�������Ӧ�¼�
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// �޷���ֵ���򷵻�-1
		return count;
	}
	
	/**
	 * �ļ����ʺͲ�����
	 */
	public static class FileAccess {
		/**
		 * String --> InputStream
		 * @param str
		 * @return
		 */
		public static InputStream String2InputStream(String str) {
			ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
			return stream;
		}
	}
}
