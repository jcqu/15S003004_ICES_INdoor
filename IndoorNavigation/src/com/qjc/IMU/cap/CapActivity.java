package com.qjc.IMU.cap;

import com.qjc.IndoorNavigation.R;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class CapActivity extends Activity{

	/** * The sensor manager */
	private SensorManager m_sensorManager = null;
	private Sensor m_magnetic;
	private Sensor m_accelerometer;
	private Button mback;
	/** 
	 * Detect the cap and manage computing 
	 */
	CapDetector capDetector;
	
	LinearLayout.LayoutParams lParamsName;
	/**
	 * The layout within the graphic is draw
	 * 包含图像的布局
	 */
	LinearLayout xyAccelerationLayout;
	/**
	 * The view that draw the graphic
	 * 画图视图
	 */
	OrientationView xyAccelerationView;
	/**
	 * The progress bar that displays the X, Y, Z value of the vector
	 * X、Y、Z矢量进度条
	 */
	ProgressBar pgbX, pgbY, pgbZ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		capDetector = new CapDetector(false);
		
		// build the GUI
		setContentView(R.layout.caplayout);
		// instantiate the progress bars
		pgbX = (ProgressBar) findViewById(R.id.progressBarX);
		pgbY = (ProgressBar) findViewById(R.id.progressBarY);
		pgbZ = (ProgressBar) findViewById(R.id.progressBarZ);
		// the azimut max value
		pgbX.setMax((int) 360);
		// the pitch max value
		pgbY.setMax((int) 90);
		// the roll max value
		pgbZ.setMax((int) 180);
		// Then manage the sensors and listen for changes
		// Instantiate the SensorManager
		m_sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		// Instantiate the magnetic sensor and its max range
		m_magnetic = m_sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		// Instantiate the accelerometer
		m_accelerometer = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// Then build the GUI:
		// Build the acceleration view
		// first retrieve the layout:
		xyAccelerationLayout = (LinearLayout) findViewById(R.id.layoutOfXYAcceleration);
		// then build the view
		xyAccelerationView = new OrientationView(this);
		// define the layout parameters and add the view to the layout
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		// add the view in the layout
		xyAccelerationLayout.addView(xyAccelerationView, layoutParam);
		mback = (Button) findViewById(R.id.title_back);
		mback.setOnClickListener(new OnClickListener() {
			public void onClick(View v)
    		{
    			CapActivity.this.finish();
    		}
		});
		
		//listen to the cap
		capDetector.addHasChangedListener(new CapListener(){
				@Override
				public void hasChanged(float cap, float pitch, float roll){
					updateProgressBar(cap, pitch, roll);
					xyAccelerationView.invalidate();
				}
		});
	}

	@Override  
    protected void onPause() {  
        //unregister every sensor
        m_sensorManager.unregisterListener(capDetector, m_accelerometer); 
        m_sensorManager.unregisterListener(capDetector, m_magnetic); 
        super.onPause();  
    }

	@Override  
    protected void onResume() {  
        //register listener
        m_sensorManager.registerListener(capDetector,m_accelerometer, SensorManager.SENSOR_DELAY_UI); 
        m_sensorManager.registerListener(capDetector,m_magnetic, SensorManager.SENSOR_DELAY_UI);
        super.onResume(); 
    }
  
	@Override  
    protected void onStop() {
        //cancel register
        m_sensorManager.unregisterListener(capDetector, m_accelerometer); 
        m_sensorManager.unregisterListener(capDetector, m_magnetic);  
        super.onStop();  
    }
       
    public void updateProgressBar(float x, float y, float z) {
    	if(x < 0)
    		pgbX.setProgress((int) x+360);
    	else
    		pgbX.setProgress((int) x);
    	
		if (y > 0) 
			pgbY.setProgress((int) y);
		else
			pgbY.setSecondaryProgress(-1 * (int) y);

		if (z > 0)
			pgbZ.setProgress((int) z);
		else
			pgbZ.setSecondaryProgress(-1 * (int) z);
	}
}
