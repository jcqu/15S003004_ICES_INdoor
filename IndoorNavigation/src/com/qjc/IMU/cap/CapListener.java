package com.qjc.IMU.cap;

//Listeners for the Cap
public interface CapListener {
	// listener - listen to the cap modifications
	// 监听器 - 监听指南针改变
	public abstract void hasChanged(float cap, float pitch, float roll);
}