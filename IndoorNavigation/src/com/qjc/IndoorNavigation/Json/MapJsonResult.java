package com.qjc.IndoorNavigation.Json;

import java.util.List;

/**
 * @ClassName: MapJsonResult
 * @Description: TODO MapJson解析类
 * @author 锦年
 * @date 2015-3-23 下午8:58:59
 */
public class MapJsonResult {

	public String mapid;
	public String ch;
	public String en;
	public String version;
	public String addr;
	public String IS_date;
	public String floor_count;
	public List<FloorJsonResult> floors;
	/**
	 * @ClassName: FloorJsonResult 
	 * @Description: TODO
	 * @author 锦年 
	 * @date 2015-4-24 上午8:30:52
	 */
	public static class FloorJsonResult {
		public int width;
		public int height;
		public String fid;
		public String map_data;
		public String path_data;
		public String png;
		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * @param width the width to set
		 */
		public void setWidth(int width) {
			this.width = width;
		}
		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}
		/**
		 * @param height the height to set
		 */
		public void setHeight(int height) {
			this.height = height;
		}
		/**
		 * @return the fid
		 */
		public String getFid() {
			return fid;
		}
		/**
		 * @param fid the fid to set
		 */
		public void setFid(String fid) {
			this.fid = fid;
		}
		/**
		 * @return the map_data
		 */
		public String getMap_data() {
			return map_data;
		}
		/**
		 * @param map_data the map_data to set
		 */
		public void setMap_data(String map_data) {
			this.map_data = map_data;
		}
		/**
		 * @return the path_data
		 */
		public String getPath_data() {
			return path_data;
		}
		/**
		 * @param path_data the path_data to set
		 */
		public void setPath_data(String path_data) {
			this.path_data = path_data;
		}
		/**
		 * @return the png
		 */
		public String getPng() {
			return png;
		}
		/**
		 * @param png the png to set
		 */
		public void setPng(String png) {
			this.png = png;
		}
	}
	/**
	 * @return the mapid
	 */
	public String getMapid() {
		return mapid;
	}
	/**
	 * @param mapid the mapid to set
	 */
	public void setMapid(String mapid) {
		this.mapid = mapid;
	}
	/**
	 * @return the ch
	 */
	public String getCh() {
		return ch;
	}
	/**
	 * @param ch the ch to set
	 */
	public void setCh(String ch) {
		this.ch = ch;
	}
	/**
	 * @return the en
	 */
	public String getEn() {
		return en;
	}
	/**
	 * @param en the en to set
	 */
	public void setEn(String en) {
		this.en = en;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the iS_date
	 */
	public String getIS_date() {
		return IS_date;
	}
	/**
	 * @param iS_date the iS_date to set
	 */
	public void setIS_date(String iS_date) {
		IS_date = iS_date;
	}
	/**
	 * @return the floor_count
	 */
	public String getFloor_count() {
		return floor_count;
	}
	/**
	 * @param floor_count the floor_count to set
	 */
	public void setFloor_count(String floor_count) {
		this.floor_count = floor_count;
	}
	/**
	 * @return the floors
	 */
	public List<FloorJsonResult> getFloors() {
		return floors;
	}
	/**
	 * @param floors the floors to set
	 */
	public void setFloors(List<FloorJsonResult> floors) {
		this.floors = floors;
	}
}