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
	//�̶�ҡ�˱���Բ�ε�X,Y�����Լ��뾶
	private static int default_RockerCircleX = 20;
	private static int default_RockerCircleY = 20;
	private static int default_SmallRockerCircleX = 75;
	private static int default_SmallRockerCircleY = 80;
	private static int default_offset = 100;
	private int RockerCircleX;
	private int RockerCircleY;
	private int RockerCircleR = 0;
	//ҡ�˵�X,Y�����Լ�ҡ�˵İ뾶
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
        //��ȡ���ͼƬ�Ŀ�͸�
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();
        
        //����Ԥת���ɵ�ͼƬ�Ŀ�Ⱥ͸߶�      
        //���������ʣ��³ߴ��ԭʼ�ߴ�
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        
        // ��������ͼƬ�õ�matrix����
        Matrix matrix = new Matrix();
        
        // ����ͼƬ����
        matrix.postScale(scaleWidth, scaleHeight);
        
        //��תͼƬ ����
        //matrix.postRotate(45);
        
        // �����µ�ͼƬ
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
	 * �õ�����֮��Ļ���
	 */
	public double getRad(float px1, float py1, float px2, float py2) {
		//�õ�����X�ľ���
		float x = px2 - px1;
		//�õ�����Y�ľ���
		float y = py1 - py2;
		//���б�߳�
		float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		//�õ�����Ƕȵ�����ֵ��ͨ�����Ǻ����еĶ��� ���ڱ�/б��=�Ƕ�����ֵ��
		float cosAngle = x / xie;
		//ͨ�������Ҷ����ȡ����ǶȵĻ���
		float rad = (float) Math.acos(cosAngle);
		//ע�⣺��������λ��Y����<ҡ�˵�Y��������Ҫȡ��ֵ-0~-180
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
			// �����������ڻ��Χ��
			double result = Math.sqrt(Math.pow((RockerCircleX - (int) event.getX()), 2) + 
					Math.pow((RockerCircleY - (int) event.getY()), 2));
			Log.e("Result ", String.valueOf(result)+"    "+String.valueOf(RockerCircleR));
			if (result < RockerCircleR) {
				Log.e("ͬ��", "result < RockerCircleR");
			}
			if (result >= RockerCircleR) {
				Log.d("y", String.valueOf(event.getY()));
				if (Math.sqrt(Math.pow(RockerCircleY+RockerCircleR - (int)event.getY(),2)) <= 100) {
					if ((int)event.getY()< RockerCircleY+RockerCircleR ) {
						//���ϻ���
						SmallRockerCircleY = (int)event.getY()-200;
						Log.d("���ϻ���", "���ϻ���:"+String.valueOf(event.getY()));
						return true;
					} 
					Log.d("event.getY()", String.valueOf(event.getY()));
					 SmallRockerCircleY = default_SmallRockerCircleY;
					return true;
			    }else {
		
			    } 
				//�õ�ҡ���봥�������γɵĽǶ�  
				double tempRad = getRad(RockerCircleX, RockerCircleY, event.getX(), event.getY());
				//��֤�ڲ�СԲ�˶��ĳ�������
				getXY(RockerCircleX, RockerCircleY, RockerCircleR, tempRad);
				//SmallRockerCircleY = event.getY();
			} else {//���С�����ĵ�С�ڻ�����������û��������ƶ�����
				Log.e("test", "С��");
				Log.e("Y:", String.valueOf(event.getY()));
				if (event.getY() > 100) {
					SmallRockerCircleY = (int) event.getY() - default_offset;
					//����һ���ж� ��������˴�С �� ����ԭ����λ��	
				}else {
					SmallRockerCircleY = default_SmallRockerCircleY;
					Log.e("12.1", "����");
				}
			
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			//���ͷŰ���ʱҡ��Ҫ�ָ�ҡ�˵�λ��Ϊ��ʼλ��
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
	 *            Բ���˶�����ת��
	 * @param centerX
	 *            ��ת��X
	 * @param centerY
	 *            ��ת��Y
	 * @param rad
	 *            ��ת�Ļ���
	 */
	public void getXY(float centerX, float centerY, float R, double rad) {
		
		//Log.d("getXY", "centerX: "+String.valueOf(centerX));
         //��ȡԲ���˶���X���� 
			//��ȡԲ���˶���Y����
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
			//����͸����
			//paint.setColor(0x70000000);
			//����ҡ�˱���
			//Log.i("draw RockerCircleX", String.valueOf(RockerCircleX));
			//Log.i("draw RockerCircleY", String.valueOf(RockerCircleY));
			//Log.i("draw smallRockerCircleX", String.valueOf(SmallRockerCircleX));
			//Log.i("draw smallRockerCircleY", String.valueOf(SmallRockerCircleY));
			canvas.drawBitmap(mBg,default_RockerCircleX,default_RockerCircleY, null);
			//canvas.drawCircle(RockerCircleX, RockerCircleY, RockerCircleR, paint);
			//paint.setColor(0x70ff0000);
			//����ҡ��
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