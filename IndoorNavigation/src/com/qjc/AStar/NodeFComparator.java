package com.qjc.AStar;
import java.util.*;
/**
 * @ClassName: NodeFComparator 
 * @Description: TODO Comparator
 * @author ���� 
 * @date 2015-4-30 ����9:07:58
 */
class NodeFComparator implements Comparator<Node> {
	@Override
	public int compare(Node o1, Node o2) {
		return o1.getF() - o2.getF();
	}
}