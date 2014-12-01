package app.psa;

import java.util.List;
import java.util.Stack;

import android.net.NetworkInfo.State;
import android.util.Log;

public class Utils {

	/**
	 * 判断手势方向
	 * @param stack
	 * @param x
	 * @return -1 不滑动；
	 * 			0 左滑动；
	 * 			1 右滑动；
	 */
	public static int sildeUtil(Stack<Float> stack,float x){
			if (stack.size() < 2) {
				stack.push(x);	
			}if (stack.size() == 2) {
				float x2 = stack.pop();
				float x1 = stack.pop();
				if (Math.abs(x2-x1) < 50) {
					//变化量太小
					stack.clear();
					return -1;
				}
				if (x2 - x1 > 0) {
					//说明此时向右滑动
					stack.clear();
					Log.e("mBottomss", "右");
					return 1;
				}else {
					stack.clear();
					Log.e("mBottomss", "左");
					return 0;
				}
			}
		return -1;
	}
}
