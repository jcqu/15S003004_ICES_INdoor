package com.qjc.IMU.cap;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CapDetector implements SensorEventListener {
	/**
	 * Current value of the accelerometer ������ټƵ�ֵ
	 */
	float x, y, z;

	float[] acceleromterVector = new float[3];// ���ټƷ���ֵ
	float[] magneticVector = new float[3];// �����Ʒ���ֵ
	float[] resultMatrix = new float[9];// �豸��ת����
	float[] values = new float[3];// �豸����

	ArrayList<Float> capList = new ArrayList<Float>();
	boolean v2 = false;
	private ArrayList<CapListener> capListenerList;

	public CapDetector(boolean _v2) {
		capListenerList = new ArrayList<CapListener>();
		v2 = _v2;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// ���ټ�
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			acceleromterVector = event.values;
		// ������
		else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			magneticVector = event.values;
		/**
		 * getRotationMatrix()���������Լ������ת���󣬽���ͨ��getOrientation()��������豸�ķ��򣨺���ǡ������ǡ�
		 * ����ǣ�������˵������һ����������Ҫ����R���飬��С��9�� �ڶ�������һ��ת�����󣬽��ų�����ת����ʵ�ʵ�����������
		 * һ��Ĭ������¿�������Ϊnull����������һ����СΪ3�����飬��ʾ�Ӽ��ٶȸ�Ӧ����ȡ�������ݣ���onSensorChanged�У�
		 * ���ĸ���һ����СΪ3�����飬��ʾ�Ӵų���Ӧ����ȡ�������� ��onSensorChanged�С�
		 */
		SensorManager.getRotationMatrix(resultMatrix, null, acceleromterVector, magneticVector);
		SensorManager.getOrientation(resultMatrix, values);

		// Math.toDegrees()���������Ƕȵ�λ�ӻ���ת��Ϊ�ȡ�
		/**
		 * ʹ�ü��ټƺʹ����ƻ�õķ������ݷ�Χ�ǣ�-180��180��,0��ʾ������90��ʾ������180/-180��ʾ���ϣ�-90��ʾ������
		 * ��ʹ�÷����Ӧ����õķ������ݷ�Χ�ǣ�0��360����360/0��ʾ������90��ʾ������180��ʾ���ϣ�270��ʾ������
		 */
		// the azimuts Z��ĽǶȣ�˳��ʱ����ת
		x = (float) Math.toDegrees(values[0]);
		// the pitch X��ĽǶȣ�ǰ��ת
		y = (float) Math.toDegrees(values[1]);
		// the roll Y��ĽǶ�,���ҷ�ת
		z = (float) Math.toDegrees(values[2]);

		// emit a changing
		for (CapListener listener : capListenerList) {
			listener.hasChanged(x, y, z);
		}
		if (v2) {
			capList.add(x);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor event, int arg1) {

	}

	// handle listener
	public void addHasChangedListener(CapListener listener) {
		capListenerList.add(listener);
	}

	public float getCap() {
		return x;
	}

	public float getOldCap() {
		return capList.get(capList.size() - 10);
	}

	public void clearList() {
		capList.clear();
	}
}
