package com.qjc.IMU.cap;

//Listeners for the Cap
public interface CapListener {
	// listener - listen to the cap modifications
	// ������ - ����ָ����ı�
	public abstract void hasChanged(float cap, float pitch, float roll);
}