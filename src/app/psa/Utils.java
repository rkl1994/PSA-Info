package app.psa;

import java.util.List;
import java.util.Stack;

import android.net.NetworkInfo.State;
import android.util.Log;

public class Utils {

    static int xCount = 0;
	/**
	 * �ж����Ʒ���
	 * @param stack
	 * @param x
	 * @return -1 ��������
	 * 			0 �󻬶���
	 * 			1 �һ�����
	 */
	public static int sildeUtil(Stack<Float> stack,float x){
			if (xCount == 3 && stack.size() < 2) {
				stack.push(x);
				xCount = 0;
			}if (stack.size() == 2) {
				float x2 = stack.pop();
				float x1 = stack.pop();
				if (Math.abs(x2-x1) < 30) {
					//�仯��̫С
					stack.clear();
					return -1;
				}
				if (x2 - x1 > 0) {
					//˵����ʱ���һ���
					stack.clear();
					Log.e("mBottomss", "��");
					return 1;
				}else {
					stack.clear();
					Log.e("mBottomss", "��");
					return 0;
				}
			}
		xCount++;
		return -1;
	}
	
	/**
	 * �жϻ���slide �Ƿ�����Ұ��
	 * ����slide Ϊ�յ�
	 * @param x
	 * @return
	 */
	public static boolean isInField(double x){
		
		if (x >100 && x<400) {
			return true;
		}
		return false;
	}
}
