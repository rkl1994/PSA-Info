package app.psa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.View;

public class MyAnalogClock extends View{
	private Drawable mHourHand;
	private Drawable mMinuteHand;
	private Drawable mSecondHand;
	private Drawable mDial;
	private Time mCalendar;
	
	 private String mDay;// ����  
	 private String mWeek;// ����  
	 
	 private int mDialWidth;
	 private int mDialHeight;
	 
	 private float mHour; //ʱ��ֵ
	 private float mMinutes; //����ֵ
	 private float mSecond;//����ֵ
	 private Boolean mChanged;
	 
	 private Paint mPaint;
	 
	 private Runnable mTicker;
	 private final Handler mHandler = new Handler();
	 
	 private boolean mTickerStopped = false;
	 
	 public MyAnalogClock(Context context) {  
	        this(context, null);  
	    }  
	 
	 public MyAnalogClock(Context context, AttributeSet attrs) {  
	        this(context, attrs, 0);  
	    }  
	 

	public MyAnalogClock(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		//Resources r = getContext().getResources();
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.AnalogClock,defStyle,0);
		mDial = a.getDrawable(R.styleable.AnalogClock_dial); //����attr����� 
		mHourHand = a.getDrawable(R.styleable.AnalogClock_hand_hour);
		mMinuteHand = a.getDrawable(R.styleable.AnalogClock_hand_minute);
		mSecondHand = a.getDrawable(R.styleable.AnalogClock_hand_second);
		
		if (mDial == null || mHourHand == null || mMinuteHand == null  
                || mSecondHand == null) {  
            //mDial = r.getDrawable(R.drawable.appwidget_clock_dial);  
            //mHourHand = r.getDrawable(R.drawable.appwidget_clock_hour);  
            //mMinuteHand = r.getDrawable(R.drawable.appwidget_clock_minute);  
            //mSecondHand = r.getDrawable(R.drawable.appwidget_clock_second);  
        }  
        a.recycle();
        
        mDialWidth = mDial.getIntrinsicWidth();
        mDialHeight = mDial.getIntrinsicHeight();
        
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#3399ff"));  
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);  
        mPaint.setFakeBoldText(true);  
        mPaint.setAntiAlias(true);  
        
        if (mCalendar == null) {  
            mCalendar = new Time();  
        }  
	}
	
	
	private void onTimeChanged(){
		mCalendar.setToNow();
		int hour = mCalendar.hour;
		int minute = mCalendar.minute;
		int second = mCalendar.second;
		
		mHour = hour + mMinutes / 60.0f + mSecond / 3600.0f;// Сʱֵ�����Ϸֺ��룬Ч������ӱ���  
        mMinutes = minute + second / 60.0f;// ����ֵ�������룬Ҳ��Ϊ��ʹЧ������  
        mSecond = second;  
  
        mChanged = true;
        //updateContentDescription(mCalendar);
	}
	
	@Override  
    protected void onAttachedToWindow() {  
        mTickerStopped = false;// ��ӵ������о�Ҫ����ʱ����  
        super.onAttachedToWindow();  
  
        /** 
         * requests a tick on the next hard-second boundary 
         */  
        mTicker = new Runnable() {  
            public void run() {  
                if (mTickerStopped)  
                    return;  
                onTimeChanged();  
                invalidate();  
                long now = SystemClock.uptimeMillis();  
                long next = now + (1000 - now % 1000);// �����´���Ҫ���µ�ʱ����  
                mHandler.postAtTime(mTicker, next);// �ݹ�ִ�У��ʹﵽ����һֱ�ڶ���Ч��  
            }  
        };  
        mTicker.run();  
    }  
	
	@Override  
    protected void onDetachedFromWindow() {  
        super.onDetachedFromWindow();  
        mTickerStopped = true;// ��view�ӵ�ǰ�������Ƴ�ʱ��ֹͣ����  
    }  
	
	/*@SuppressLint("NewApi")
	@Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        // ģʽ�� UNSPECIFIED(δָ��),��Ԫ�ز�����Ԫ��ʩ���κ���������Ԫ�ؿ��Եõ�������Ҫ�Ĵ�С��  
        // EXACTLY(��ȫ)����Ԫ�ؾ�����Ԫ�ص�ȷ�д�С����Ԫ�ؽ����޶��ڸ����ı߽���������������С��  
        // AT_MOST(����)����Ԫ������ﵽָ����С��ֵ��  
        // �����ṩ�Ĳ���ֵ(��ʽ)��ȡģʽ(��������ģʽ֮һ)  
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);  
        // �����ṩ�Ĳ���ֵ(��ʽ)��ȡ��Сֵ(�����СҲ��������ͨ����˵�Ĵ�С)  
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  
        // �߶���������  
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);  
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);  
  
        float hScale = 1.0f;// ����ֵ  
        float vScale = 1.0f;  
  
        if (widthMode != MeasureSpec.UNSPECIFIED && widthSize < mDialWidth) {  
            hScale = (float) widthSize / (float) mDialWidth;// �����Ԫ���ṩ�Ŀ�ȱ�ͼƬ���С������Ҫѹ��һ����Ԫ�صĿ��  
        }  
  
        if (heightMode != MeasureSpec.UNSPECIFIED && heightSize < mDialHeight) {  
            vScale = (float) heightSize / (float) mDialHeight;// ͬ��  
        }  
  
        float scale = Math.min(hScale, vScale);// ȡ��С��ѹ��ֵ��ֵԽС��ѹ��Խ����  
        // ��󱣴�һ�£��������һ��Ҫ����  
        setMeasuredDimension(  
                resolveSizeAndState((int) (mDialWidth * scale),  
                        widthMeasureSpec, 0),  
                resolveSizeAndState((int) (mDialHeight * scale),  
                        heightMeasureSpec, 0));  
    }  
  */
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
        mChanged = true;  
    }  
	
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
  
        boolean changed = mChanged;  
  
        if (changed) {  
            mChanged = false;  
        }  
  
        int availableWidth = getRight() - getLeft();// view���ÿ�ȣ�ͨ���������ȥ������  
        int availableHeight = getBottom() - getTop();// view���ø߶ȣ�ͨ���������ȥ������  
  
        int x = availableWidth / 2;// view������ĵ�����  
        int y = availableHeight / 2;// view�߶����ĵ�����  
  
        final Drawable dial = mDial;// ����ͼƬ  
        int w = dial.getIntrinsicWidth();// ���̿��  
        int h = dial.getIntrinsicHeight();  
  
        // int dialWidth = w;  
        int dialHeight = h;  
        boolean scaled = false;  
        // ���Ȼ����̣���ײ��Ҫ�Ȼ��ϻ���  
        if (availableWidth < w || availableHeight < h) {// ���view�Ŀ��ÿ��С�ڱ���ͼƬ����Ҫ��СͼƬ  
            scaled = true;  
            float scale = Math.min((float) availableWidth / (float) w,  
                    (float) availableHeight / (float) h);// ������Сֵ  
            canvas.save();  
            canvas.scale(scale, scale, x, y);// ʵ��������С�Ļ���  
        }  
  
        if (changed) {// ���ñ���ͼƬλ�á����������X���ϵ���㣻 ���������Y���ϵ���㣻 ����Ŀ�ȣ�����ĸ߶�  
            dial.setBounds(x - (w / 2), y - (h / 2), x + (w / 2), y + (h / 2));  
            //dial.setbo
        }  
        dial.draw(canvas);// ������������ѱ���ͼƬ���ڻ�����  
        canvas.save();// һ��Ҫ����һ��  
        // ��λ�����  
        if (changed) {  
            //w = (int) (mPaint.measureText(mWeek));// �������ֵĿ��  
           // canvas.drawText(mWeek, (x - w / 2), y - (dialHeight / 8), mPaint);// �������ڻ����ϣ�λ��Ϊ�м���������  
            //w = (int) (mPaint.measureText(mDay));  
            //canvas.drawText(mDay, (x - w / 2), y + (dialHeight / 8), mPaint);// ͬ��  
        }  
        // �ٻ�ʱ��  
        canvas.rotate(mHour / 12.0f * 360.0f, x, y);// ��ת���壬��һ������Ϊ��ת�Ƕȣ��ڶ�����������Ϊ��ת�����  
        final Drawable hourHand = mHourHand;  
        if (changed) {  
            w = hourHand.getIntrinsicWidth();  
            h = hourHand.getIntrinsicHeight();  
            hourHand.setBounds(x - (w / 2), y - (h / 2)-20, x + (w / 2), y  
                    + (h / 2)-10);  
        }  
        hourHand.draw(canvas);// ��ʱ�뻭�ڻ�����  
        canvas.restore();// �ָ����嵽���״̬  
  
        canvas.save();  
        // Ȼ�󻭷���  
        canvas.rotate(mMinutes / 60.0f * 360.0f, x, y);  
        final Drawable minuteHand = mMinuteHand;  
        if (changed) {  
            w = minuteHand.getIntrinsicWidth();  
            h = minuteHand.getIntrinsicHeight();  
            minuteHand.setBounds(x - (w / 2), y - (h / 2)-20, x + (w / 2), y  
                    + (h / 2)-20);  
        }  
        minuteHand.draw(canvas);  
        canvas.restore();  
  
        canvas.save();  
        // �������  
        canvas.rotate(mSecond / 60.0f * 360.0f, x, y);  
        final Drawable secondHand = mSecondHand;  
        if (changed) {  
            w = secondHand.getIntrinsicWidth();  
            h = secondHand.getIntrinsicHeight();  
            secondHand.setBounds(x - (w / 2), y - (h / 2)-60, x + (w / 2)-20, y  
                    + (h / 2)-60);  
        }  
        secondHand.draw(canvas);  
        canvas.restore();  
  
        if (scaled) {  
            canvas.restore();  
        }  
    }  
  
    /** 
     * �����view����һ�£� 
     *  
     * @param time 
     */  
    private void updateContentDescription(Time time) {  
        final int flags = DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_24HOUR;  
        String contentDescription = DateUtils.formatDateTime(getContext(),  
                time.toMillis(false), flags);  
        setContentDescription(contentDescription);  
    }  
  
    /** 
     * ��ȡ��ǰ���� 
     *  
     * @param week 
     * @return 
     */  
  /* private String getWeek(int week) {  
        switch (week) {  
        case 1:  
            return this.getContext().getString(R.string.monday);  
        case 2:  
            return this.getContext().getString(R.string.tuesday);  
        case 3:  
            return this.getContext().getString(R.string.wednesday);  
        case 4:  
            return this.getContext().getString(R.string.thursday);  
        case 5:  
            return this.getContext().getString(R.string.friday);  
        case 6:  
            return this.getContext().getString(R.string.saturday);  
        case 0:  
            return this.getContext().getString(R.string.sunday);  
        default:  
            return "";  
        }  
   }*/

}
