package com.qjc.IMU.localPoint;

// �����¼�������
public interface LocalisationListener {
	// ��λ����λ��
	public abstract void onNewPosition(float oldPosition[], float newPosition[], float stepLengh);
}
