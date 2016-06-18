package com.qjc.IMU.localPoint;

import java.lang.Math;
import com.qjc.IMU.cap.CapDetector;
import com.qjc.IMU.cap.CapListener;
import com.qjc.IMU.step.IStepListener;
import com.qjc.IMU.step.StepDetector;
import com.qjc.IndoorNavigation.NaviPath;

public class LocalisationManager {

	private float currentPosition[] = { 0, 0, 0 };// 现在的位置坐标
	private float oldPosition[] = { 0, 0, 0 };// 上一个位置坐标
	@SuppressWarnings("unused")
	private float cap;// 方向值

	LocalisationListener localisationListener;// 位置监听器
	CapDetector capDetector;// 方向检测器
	StepDetector stepDetector;// 脚步检测器

	public LocalisationManager(NaviPath activity) {
		localisationListener = null;

		capDetector = new CapDetector(true);
		stepDetector = new StepDetector(activity);

		// On recupere les nouveaux cap en non-stop
		capDetector.addHasChangedListener(new CapListener() {
			@Override
			public void hasChanged(float capn, float pitch, float roll) {
				cap = capn;
			}
		});

		// des qu'on detecte un pas, on prend le dernier cap releve et on
		// l'envoie
		// TODO Voir si on peut pas faire mieux
		stepDetector.addStepListener(new IStepListener() {
			@Override
			public void stepDetected(float _stepLength) {
				computeNewPosition(_stepLength, capDetector.getOldCap());
				capDetector.clearList();
			}
		});
		stepDetector.registerSensors();
	}

	// 计算新位置坐标
	private void computeNewPosition(float _stepLength, float _cap) {
		// calculer la nouvelle position ac le cap
		float newPosition[] = { 0, 0, 0 };

		if (_cap < 0) {
			_cap += 360;
		}

		newPosition[0] = currentPosition[0] + _stepLength * (float) Math.sin(Math.toRadians(_cap));
		newPosition[1] = currentPosition[1] + _stepLength * (float) Math.cos(Math.toRadians(_cap));

		// M-a-J des variables
		oldPosition = currentPosition;
		currentPosition = newPosition;

		// declencher le listener pour dire qu'il y a une nouvelle position
		localisationListener.onNewPosition(oldPosition, currentPosition, _stepLength);
	}

	// 获取方向检测器
	public CapDetector getCapDetector() {
		return capDetector;
	}

	// 获取脚步检测器
	public StepDetector getStepDetector() {
		return stepDetector;
	}

	// 设置位置监听器
	public void setLocalisationListener(LocalisationListener listener) {
		localisationListener = listener;
	}
}
