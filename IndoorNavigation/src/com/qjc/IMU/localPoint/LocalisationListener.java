package com.qjc.IMU.localPoint;

// 本地事件监听器
public interface LocalisationListener {
	// 旧位置新位置
	public abstract void onNewPosition(float oldPosition[], float newPosition[], float stepLengh);
}
