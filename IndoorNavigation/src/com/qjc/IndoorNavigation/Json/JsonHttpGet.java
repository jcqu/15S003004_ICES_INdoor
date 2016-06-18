package com.qjc.IndoorNavigation.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qjc.IndoorNavigation.Json.MapJsonResult.FloorJsonResult;

/**
 * @ClassName: JsonHttpGet
 * @Description: TODO
 * @author 锦年
 * @date 2015-4-23 下午10:21:30
 */
public class JsonHttpGet {
	private static String strResult;// 结果集
	private static String[] getmapstr1;// 获取的地图信息"#"
	private static int[][] getmapstr2=new int[200][200];// 获取的地图信息","
	private static List<int[][]> getmaps=new ArrayList<int[][]>();// 获取的地图信息
	private static String urlDownload;//url
	private static String newFilename;//path
	private static String dirName;//dirpath
	public static Boolean getMapInfo(String httpurl, String mapname,
			String version) {
         
		// 要下载的文件路径
		urlDownload = httpurl + "/IndoorNavigation/map" + "_" + version
				+ "/map_" + mapname + "/map.json";
		//System.out.println("httpurl=" + urlDownload);
		// 获得存储卡路径，构成 保存文件的目标路径
		dirName = "";
		dirName = Environment.getExternalStorageDirectory() + "/IndoorMap/";
		File f = new File(dirName);
		// 判断文件夹是是否存在，如果不存在，则新建一个文件夹
		if (!f.exists()) {
			f.mkdir();
		}
		dirName = Environment.getExternalStorageDirectory() + "/IndoorMap/"
				+ "map_" + mapname + "/";
		File f1 = new File(dirName);
		if (!f1.exists()) {
			f1.mkdir();
		}
		// 准备拼接新的文件名（保存在存储卡后的文件名）
		newFilename = urlDownload
				.substring(urlDownload.lastIndexOf("/") + 1);
		newFilename = dirName + newFilename;
		File file = new File(newFilename);
		if (file.exists()) {
			file.delete();
		}
		Thread thread = new Thread(new Runnable() {
             @Override
             public void run() {
            	 try {
         			URL url = new URL(urlDownload);
         			URLConnection conn = url.openConnection();
         			InputStream is = conn.getInputStream();
         			int fileSize = conn.getContentLength();
         			if (fileSize < 1 || is == null) {
         				return; 
         			} else {
         				System.out.println("newFilename=" + newFilename);
         				FileOutputStream fos = new FileOutputStream(newFilename);
                        byte[] bytes = new byte[1024];
                        int len = -1;
                        while ((len = is.read(bytes)) != -1) {
                            fos.write(bytes, 0, len);
             				fos.flush();//调用flush是保证缓存清空输出
                        }
         				is.close();
         				fos.close();
         			}
         		} catch (Exception e) {
         			e.printStackTrace();
         		}
         		deMapdata(dirName);
             }
        });
        thread.start();
		return true;
	}

	public static Boolean deMapdata(String dirName) {
		try {
			File file = new File(dirName, "map.json");
			FileInputStream is = new FileInputStream(file);
			int length = is.available();   
	        byte [] buffer = new byte[length];   
	        is.read(buffer);       
	        strResult= EncodingUtils.getString(buffer, "UTF-8"); 
			if (strResult != null && strResult.startsWith("\ufeff"))
			{
				strResult = strResult.substring(1);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		java.lang.reflect.Type type = new TypeToken<MapJsonResult>() {}.getType();
		//System.out.println("strResult =" + strResult);
		try {
			MapJsonResult jsonBean = gson.fromJson(strResult, type);
			System.out.println("mapid=" + jsonBean.getMapid());
			FloorJsonResult token = new FloorJsonResult();			
			for (int i = 0; i < jsonBean.floors.size(); i++) {
				token = jsonBean.floors.get(i);
				int wightcount = Integer.valueOf(token.width).intValue();
				int heightcount = Integer.valueOf(token.height).intValue();
				String str = token.map_data;
				getmapstr1 = str.split("#");
				for (int j = 0; j < heightcount; j++) {
					for (int k = 0; k < wightcount; k++) {
					  getmapstr2[j][k]=Integer.valueOf(getmapstr1[j].split(",")[k]).intValue();
					}					
				}
				getmaps.add(getmapstr2);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}
}
