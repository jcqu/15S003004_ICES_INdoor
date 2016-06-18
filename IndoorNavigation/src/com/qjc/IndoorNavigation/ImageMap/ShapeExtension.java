package com.qjc.IndoorNavigation.ImageMap;

import com.qjc.IndoorNavigation.ImageMap.Shape;

/**
 * ShapeExtension�ǳ���ImageMap�̳���FrameLayout������ҪǶ�뵽HighlightImageView�ڲ����̶���Ƶġ�
 * ��Ҫ�ǽ�HighlhgitImageView�ڲ����̵Ĳ�����չ��ImageMap�д���
 */
public interface ShapeExtension{

    public interface OnShapeActionListener {

        /**
         * ��һ��Shape�����
         * @param shape
         * @param xOnImage
         * @param yOnImage
         */
        void onShapeClick(Shape shape, float xOnImage, float yOnImage);

    }

    /**
     * �����״
     * @param shape ��״����
     */
    void addShape(Shape shape);

    /**
     * ɾ��ָ��Tag����״
     * @param tag ָ��Tag
     */
    void removeShape(Object tag);

    /**
     * ���������״
     */
    void clearShapes();
}