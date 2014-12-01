package app.psa;

import java.util.List;
import java.util.Stack;

import android.net.NetworkInfo.State;
import android.util.Log;

public class Utils {

    static int xCount = 0;
	/**
	 * 判断手势方向
	 * @param stack
	 * @param x
	 * @return -1 不滑动；
	 * 			0 左滑动；
	 * 			1 右滑动；
	 */
	public static int sildeUtil(Stack<Float> stack,float x){
			if (xCount == 3 && stack.size() < 2) {
				stack.push(x);
				xCount = 0;
			}if (stack.size() == 2) {
				float x2 = stack.pop();
				float x1 = stack.pop();
				if (Math.abs(x2-x1) < 30) {
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
		xCount++;
		return -1;
	}
	
	/**
	 * 判断滑动slide 是否在视野内
	 * 滑动slide 为空调
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
