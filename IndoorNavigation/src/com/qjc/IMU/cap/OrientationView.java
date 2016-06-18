
package com.qjc.IMU.cap;

import com.qjc.IndoorNavigation.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class OrientationView extends View {

	private CapActivity activity;

	private Paint paint = new Paint();
	private Path northPath = new Path();
	private Path southPath = new Path();
	private Paint.FontMetrics mFontMetrics;// �ı����ƶ���
	private int textColor, backgroundCircleColor, circleColor;// ��ɫ
	private float fontHeight;// �ı��߶�

	/**
	 * The source point of the transformation matrix
	 * ת���������Դ��
	 */
	float[] src = new float[8];
	/**
	 * The destination point of the transformation matrix
	 * ת�������Ŀ���
	 */
	float[] dst = new float[8];


	public OrientationView(Context context) {
		super(context);
		// instanciate the calling activity
		activity = (CapActivity) context;
		// Define the path that draw the North arrow (a path is a sucession of lines)
		// ���廭������ļ�ͷ
		northPath.moveTo(0, -60);
		northPath.lineTo(-10, 0);
		northPath.lineTo(10,0);
		northPath.close();
		// Define the path that draw the South arrow (a path is a sucession of lines)	
		// ���廭�Ϸ���ļ�ͷ
		southPath.moveTo(-10,0);
		southPath.lineTo(0,60);
		southPath.lineTo(10,0);
		southPath.close();
		// Define how to draw text
		// ������λ��ı�
		paint.setTextSize(20);
		// ���Ķ���
		paint.setTextAlign(Paint.Align.CENTER);
		// ȥ���
		paint.setAntiAlias(true);
		mFontMetrics = paint.getFontMetrics();
		fontHeight = mFontMetrics.ascent + mFontMetrics.descent;
		// define the color used
		// ����ʹ�õ���ɫ
		Resources res = getResources();
		textColor = res.getColor(R.color.text_color);
		backgroundCircleColor = res.getColor(R.color.background_circle_color);
		circleColor = res.getColor(R.color.circle_color);
	}

	
	/** Drawing method *********/
	@Override
	public void onDraw(Canvas canvas) {
		int w = getWidth();
		int h = getHeight();
		int cx = w / 2;
		int cy = h / 2;
		int lenght = h / 2;

		// draw the north arrow
		drawNorthArrow(canvas, lenght, cx, cy);
	}

	private void drawNorthArrow(Canvas canvas, int lenght, int cx, int cy) {
		float cap = activity.capDetector.getCap();
		
		canvas.save();
		// get the azimuth to display
		// ��ȡ��λ��
		float azimut = -cap;
		// center the arrow in the middle of the screen
		// �Ѽ�ͷ������Ļ����
		canvas.translate(cx, cy);
		// rotate the canvas such that the arrow will indicate the north
		// even if it draws vertical
		// ��ת�������ü�ͷָ��
		canvas.rotate(azimut);
		// draw the background circle
		// ������ԲȦ
		paint.setColor(circleColor);
		canvas.drawCircle(0, 0, lenght / 2 - fontHeight + 10, paint);
		paint.setColor(backgroundCircleColor);
		canvas.drawCircle(0, 0, lenght / 2, paint);
		
		// Now display the scale of the compass
		// ��ʾ����
		paint.setColor(Color.WHITE);
		
		// display the graduation
		// ���̶�
		// the text position
		// ����λ��
		float hText = - lenght/2 - fontHeight + 3;
		// each 15 draw a graduation |
		// ÿ15�Ȼ�һ���̶�
		int step = 15;
		for (int degree = 0; degree < 360; degree = degree + step) {
			// if it's not a cardinal point draw the graduation
			// ������ǻ�����ͻ��Ͽ̶�
			if ((degree % 45) != 0) {
				canvas.drawText("|", 0, hText, paint);				
			}
			canvas.rotate(-step);
		}
		
		// then draw cardinal points
		// Ȼ��
		canvas.drawText("N", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("NW", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("W", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("SW", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("S", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("SE", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("E", 0, hText, paint);
		canvas.rotate(-45);
		canvas.drawText("NE", 0, hText, paint);
		canvas.rotate(-45);

		// Draw the arrow
		// ����ͷ
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.RED);
		canvas.drawPath(northPath, paint);
		paint.setColor(circleColor);
		canvas.drawPath(southPath, paint);
		
		// and now displays the value of the azimuth
		// ��ʾ��λ��
		paint.setColor(textColor);
		paint.setStyle(Paint.Style.FILL);
		// Draw the text horizontal
		// ˮƽ�ı�
		canvas.restore();
		// center the arrow in the middle of the screen
		// �Ѽ�ͷ������Ļ����
		float hTextValue = - lenght/2 + fontHeight - 5;
		canvas.translate(cx, cy);
		
		if(cap < 0)
			canvas.drawText(((int) cap+360) + "", 0, -hTextValue, paint);
		else
			canvas.drawText(((int) cap) + "", 0, -hTextValue, paint);

		canvas.restore();
	}
}
