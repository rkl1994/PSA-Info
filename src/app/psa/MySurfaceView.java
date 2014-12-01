package app.psa;

import java.util.Set;

import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.TextView;


public class MySurfaceView extends SurfaceView implements Callback, Runnable {

	private Thread th;
	private SurfaceHolder sfh;
	private Canvas canvas;
	private Paint paint;
	private boolean flag;
	//固定摇杆背景圆形的X,Y坐标以及半径
	private static int default_RockerCircleX = 20;
	private static int default_RockerCircleY = 20;
	private static int default_SmallRockerCircleX = 75;
	private static int default_SmallRockerCircleY = 80;
	private static int default_offset = 100;
	private int RockerCircleX;
	private int RockerCircleY;
	private int RockerCircleR = 0;
	//摇杆的X,Y坐标以及摇杆的半径
	private float SmallRockerCircleY;
	private float SmallRockerCircleR = 0;
	Bitmap mBg; 
	Bitmap mInner; 
	TextView mNum;
	int i = 44;
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
			super.handleMessage(msg);
			String text = (String)msg.obj;
			mNum.setText(text);
		}
	};
	
	public MySurfaceView(Context context) {
		super(context);
		initial();
	}

	public MySurfaceView(Context context,AttributeSet attributeSet) {
		// TODO Auto-generated constructor stub
		super(context, attributeSet);
		initial();
	}
	
	public void setview(TextView textView){
		mNum = textView;
	}
	
	private void initial(){
		this.setKeepScreenOn(true);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		setFocusable(true);
		setFocusableInTouchMode(true);
		mBg = BitmapFactory.decodeResource(getResources(), R.drawable.ac_main_bg);
		mInner = BitmapFactory.decodeResource(getResources(), R.drawable.ac_main_inner);
		mBg = picInitial(mBg,500,500);
		mInner = picInitial(mInner, 380, 380);
		RockerCircleX = default_RockerCircleX;
		RockerCircleY = default_RockerCircleY;
		RockerCircleR = mBg.getWidth()/2;
        SmallRockerCircleY = default_SmallRockerCircleY;//mInner.getWidth()/2;
	}
	
	private Bitmap picInitial(Bitmap bitmapOrg,int newWidth,int newHeight){
        //获取这个图片的宽和高
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        
        //定义预转换成的图片的宽度和高度      
        //计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        
        //旋转图片 动作
        //matrix.postRotate(45);
        
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
        width, height, matrix, true);
        return resizedBitmap;
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		th = new Thread(this);
		flag = true;
		th.start();
	}

	/***
	 * 得到两点之间的弧度
	 */
	public double getRad(float px1, float py1, float px2, float py2) {
		//得到两点X的距离
		float x = px2 - px1;
		//得到两点Y的距离
		float y = py1 - py2;
		//算出斜边长
		float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//得到这个角度的余弦值（通过三角函数中的定理 ：邻边/斜边=角度余弦值）
		float cosAngle = x / xie;
		//通过反余弦定理获取到其角度的弧度
		float rad = (float) Math.acos(cosAngle);
		//注意：当触屏的位置Y坐标<摇杆的Y坐标我们要取反值-0~-180
		if (py2 < py1) {
			rad = -rad;
		}
		return rad;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			float tmp = event.getY();
			SmallRockerCircleY = default_SmallRockerCircleY;	
			return true;
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// 当触屏区域不在活动范围内
			double result = Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2) + 
					Math.pow((RockerCircleY - (int) event.getY()), 2));
			Log.e("Result ", String.valueOf(result)+"    "+String.valueOf(RockerCircleR));
			if (result < RockerCircleR) {
				Log.e("同济", "result < RockerCircleR");
			}
			if (result >= RockerCircleR) {
				Log.d("y", String.valueOf(event.getY()));
				if (Math.sqrt(Math.pow(RockerCircleY+RockerCircleR - (int)event.getY(),2)) <= 100) {
					if ((int)event.getY()< RockerCircleY+RockerCircleR ) {
						//向上滑动
						SmallRockerCircleY = (int)event.getY()-200;
						Log.d("向上滑动", "向上滑动:"+String.valueOf(event.getY()));
						return true;
					} 
					Log.d("event.getY()", String.valueOf(event.getY()));
					 SmallRockerCircleY = default_SmallRockerCircleY;
					return true;
			    }else {
		
			    } 
				//得到摇杆与触屏点所形成的角度  
				double tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
				//保证内部小圆运动的长度限制
				getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
				//SmallRockerCircleY = event.getY();
			} else {//如果小球中心点小于活动区域则随着用户触屏点移动即可
				Log.e("test", "小于");
				Log.e("Y:", String.valueOf(event.getY()));
				if (event.getY() > 100) {
					SmallRockerCircleY = (int) event.getY() - default_offset;
					//进行一次判断 如果超过了大小 则 返回原来的位置	
				}else {
					SmallRockerCircleY = default_SmallRockerCircleY;
					Log.e("12.1", "返回");
				}
			
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//当释放按键时摇杆要恢复摇杆的位置为初始位置
			SmallRockerCircleY = default_SmallRockerCircleY;
			Message message = new Message();
			message.obj = String.valueOf(i);
			i++;
			mHandler.sendMessage(message);
			 
		}
		return true;
	}

	/**
	 * 
	 * @param R
	 *            圆周运动的旋转点
	 * @param centerX
	 *            旋转点X
	 * @param centerY
	 *            旋转点Y
	 * @param rad
	 *            旋转的弧度
	 */
	public void getXY(float centerX, float centerY, float R, double rad) {
		
		//Log.d("getXY", "centerX: "+String.valueOf(centerX));
         //获取圆周运动的X坐标 
			//获取圆周运动的Y坐标
			SmallRockerCircleY = (float) (R * Math.sin(rad)) + centerY;
		
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.d("onMeasure", "width: "+String.valueOf(widthMeasureSpec));
	}
	
	public void draw() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK);
			//canvas.drawCircle(cx, cy, radius, paint);
			//设置透明度
			//paint.setColor(0x70000000);
			//绘制摇杆背景
			//Log.i("draw RockerCircleX", String.valueOf(RockerCircleX));
			//Log.i("draw RockerCircleY", String.valueOf(RockerCircleY));
			//Log.i("draw smallRockerCircleX", String.valueOf(SmallRockerCircleX));
			//Log.i("draw smallRockerCircleY", String.valueOf(SmallRockerCircleY));
			canvas.drawBitmap(mBg,default_RockerCircleX,default_RockerCircleY, null);
			//canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
			//paint.setColor(0x70ff0000);
			//绘制摇杆
			Matrix matrix = new Matrix();
			matrix.setScale(0.7f,0.7f);
			canvas.drawBitmap(mInner,default_SmallRockerCircleX,SmallRockerCircleY, null);

			//canvas.drawCircle(SmallRockerCircleX, SmallRockerCircleY, SmallRockerCircleR, paint);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (canvas != null)
					sfh.unlockCanvasAndPost(canvas);
			} catch (Exception e2) {

			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			draw();
			try {
				Thread.sleep(50);
			} catch (Exception ex) {
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.d("surface", "surfaceChanged");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		Log.d("surface", "surfaceDestroyed");
	}
}