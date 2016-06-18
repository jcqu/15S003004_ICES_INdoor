package com.qjc.IMU.cap;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CapDetector implements SensorEventListener {
	/**
	 * Current value of the accelerometer 计算加速计的值
	 */
	float x, y, z;

	float[] acceleromterVector = new float[3];// 加速计返回值
	float[] magneticVector = new float[3];// 磁力计返回值
	float[] resultMatrix = new float[9];// 设备旋转矩阵
	float[] values = new float[3];// 设备方向

	ArrayList<Float> capList = new ArrayList<Float>();
	boolean v2 = false;
	private ArrayList<CapListener> capListenerList;

	public CapDetector(boolean _v2) {
		capListenerList = new ArrayList<CapListener>();
		v2 = _v2;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// 加速计
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			acceleromterVector = event.values;
		// 磁力计
		else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			magneticVector = event.values;
		/**
		 * getRotationMatrix()方法，可以计算出旋转矩阵，进而通过getOrientation()方法求得设备的方向（航向角、俯仰角、
		 * 横滚角）。参数说明：第一个参数是需要填充的R数组，大小是9； 第二个是是一个转换矩阵，将磁场数据转换进实际的重力坐标中
		 * 一般默认情况下可以设置为null；第三个是一个大小为3的数组，表示从加速度感应器获取来的数据，在onSensorChanged中；
		 * 第四个是一个大小为3的数组，表示从磁场感应器获取来的数据 在onSensorChanged中。
		 */
		SensorManager.getRotationMatrix(resultMatrix, null, acceleromterVector, magneticVector);
		SensorManager.getOrientation(resultMatrix, values);

		// Math.toDegrees()方法，将角度单位从弧度转换为度。
		/**
		 * 使用加速计和磁力计获得的方向数据范围是（-180～180）,0表示正北，90表示正东，180/-180表示正南，-90表示正西。
		 * 仅使用方向感应器获得的方向数据范围是（0～360），360/0表示正北，90表示正东，180表示正南，270表示正西。
		 */
		// the azimuts Z轴的角度，顺逆时针旋转
		x = (float) Math.toDegrees(values[0]);
		// the pitch X轴的角度，前后翻转
		y = (float) Math.toDegrees(values[1]);
		// the roll Y轴的角度,左右翻转
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
