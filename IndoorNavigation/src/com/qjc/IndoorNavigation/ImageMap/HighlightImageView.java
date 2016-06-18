package com.qjc.IndoorNavigation.ImageMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.qjc.IndoorNavigation.ImageMap.Shape;
import com.qjc.IndoorNavigation.ImageMap.ShapeExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HighlightImageView����TouchImageView�Ĺ��ܣ���ImageView��Canvas�ϻ���һЩ��״��
 * Based on TouchImageView class, Design for draw shapes on canvas of ImageView
 */

public class HighlightImageView extends TouchImageView implements ShapeExtension {

	public HighlightImageView(Context context) {
		this(context,null);
	}

	public HighlightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private Map<Object,Shape> shapesCache = new HashMap<Object, Shape>();
    private OnShapeActionListener onShapeClickListener;

    public void setOnShapeClickListener(OnShapeActionListener onShapeClickListener){
        this.onShapeClickListener = onShapeClickListener;
    }

    @Override
	public void addShape(Shape shape){

		shapesCache.put(shape.tag, shape);
		postInvalidate();
	}

    @Override
	public void removeShape(Object tag){
		if(shapesCache.containsKey(tag)){
			shapesCache.remove(tag);
			postInvalidate();
		}
	}

    @Override
    public void clearShapes() {
        shapesCache.clear();
    }

    public List<Shape> getShapes(){
        return new ArrayList<Shape>(shapesCache.values());
    }

	@SuppressLint("WrongCall")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
        for(Shape shape : shapesCache.values()){
            shape.onDraw(canvas);
        }
        canvas.save();
        onDrawWithCanvas(canvas);
	}

    /**
     * ����̳�HighlightImageView������Ҫ��Canvas�ϻ��ƣ�����Override���������ʵ�֡�
	 * - Override this method for draw something on canvas when YourClass extends HighlightImageView.
     * @param canvas ����
     */
    protected void onDrawWithCanvas(Canvas canvas){}

    @Override
    protected void onViewClick (float xOnView, float yOnView) {
        if(onShapeClickListener == null) return;
        for(Shape shape : shapesCache.values()){
            if(shape.inArea(xOnView,yOnView)){
                // ���һ����״�������ͨ�������ӿڻص�������¼��Ĺ�ע�ߡ�
				// Callback by listener when a shape has been clicked
                onShapeClickListener.onShapeClick(shape, xOnView, yOnView);
                break; // ֻ��һ����״���Ա���� - Only one shape can be click
            }
        }
    }

    @Override
	protected void postScale(float scaleFactor, float scaleCenterX,float scaleCenterY) {
		super.postScale(scaleFactor, scaleCenterX, scaleCenterY);
		if(scaleFactor != 0){
            for(Shape shape : shapesCache.values()){
                if(scaleFactor != 0){
                    shape.onScale(scaleFactor, scaleCenterX, scaleCenterY);
                }
            }
		}
	}

    @Override
    protected void postTranslate(float deltaX, float deltaY) {
		super.postTranslate(deltaX, deltaY);
        if( !(deltaX == 0 && deltaY == 0)){
            for(Shape shape : shapesCache.values()){
                shape.onTranslate(deltaX, deltaY);
            }
        }
    }

}
