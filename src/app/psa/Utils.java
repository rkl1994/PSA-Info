package app.psa;

import java.util.List;
import java.util.Stack;

import android.net.NetworkInfo.State;
import android.util.Log;

public class Utils {

	/**
	 * �ж����Ʒ���
	 * @param stack
	 * @param x
	 * @return -1 ��������
	 * 			0 �󻬶���
	 * 			1 �һ�����
	 */
	public static int sildeUtil(Stack<Float> stack,float x){
			if (stack.size() < 2) {
				stack.push(x);	
			}if (stack.size() == 2) {
				float x2 = stack.pop();
				float x1 = stack.pop();
				if (Math.abs(x2-x1) < 50) {
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
		return -1;
	}
}
