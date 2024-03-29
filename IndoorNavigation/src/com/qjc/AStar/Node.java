package com.qjc.AStar;
/**
 * @ClassName: Node 
 * @Description: TODO
 * @author 锦年 
 * @date 2015-4-30 上午9:07:48
 */
class Node {
	private int x;// X坐标
	private int y;// Y坐标
	private Node parentNode;// 父类节点
	private int g;// 当前点到起点的移实际耗费
	private int h;// 当前点到目标点的预估消费
	private int f;// f=g+h

	public Node(int x, int y, Node parentNode) {
		this.x = x;
		this.y = y;
		this.parentNode = parentNode;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
}

