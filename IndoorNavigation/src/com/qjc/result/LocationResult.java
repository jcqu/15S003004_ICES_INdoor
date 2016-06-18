package com.qjc.result;

/**
 * @ClassName: LocationResult 
 * @Description: 地理位置数据实体类
 * @author 锦年 
 * @date 2015-3-23 下午3:56:55
 */
public class LocationResult {
	/**
	 * 地理位置名称
	 */
	private String name;
	/**
	 * 地理位置地点
	 */
	private String location;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
