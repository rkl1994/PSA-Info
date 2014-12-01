package app.psa;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import psa.music.MusicActivity;
import psa.navi.NaviActivity;
import psa.navi.NaviTurnToTurnActivity;
import psa.phone.PhoneActivity;
import psa.radio.RadioActivity;
import android.os.Bundle;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends ActivityGroup implements OnClickListener{

	RelativeLayout container;
	ImageView mMusic;
	ImageView mNavi;
	ImageView mRadio;
	ImageView mPhone;
	Intent mIntent;
	Animation mInAnim;
	Animation mOutAnim;
	
	private Button leftAirNumUp;
	private Button leftAirNumDown;
	private Button rightAirNumUp;
	private Button rightAirNumDown;
	
	private int temp;
	//int n ;
	private Typeface tfAirCondition;
	private Typeface tfAirmode;
	
	private TextView acLeftNum;
	private TextView acRightNum;
	
	/**底部空调类型**/
	private TextView acModeAuto;
	private TextView acModeComfort;
	private TextView acModeEnviroment;
	
	private ImageView mMusicUnderline;
	private ImageView mNaviUnderline;
	private ImageView mRadioUnderline;
	private ImageView mPhoneUnderline;
	
	/**底部的slide**/
	private RelativeLayout mBottomSlideParent;
	private RelativeLayout.LayoutParams bottomSlideLp;
	private ImageView mBottomSlideLight;
	
	private TextView mBottomUpLeftNum;
	private TextView mBottomUpRightNum;
	
	
	boolean Navi_State;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
      //  Navi_State = getIntent().getBooleanExtra("TURN_TO_TURN",false);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
        	Navi_State = bundle.getBoolean("TURN_TO_TURN");
        }
        initial();
    }
    

    private void initial(){
    	mInAnim = AnimationUtils.loadAnimation(this, R.anim.left_in);
    	mOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out);
    	container = (RelativeLayout)findViewById(R.id.main_activity_view);
    	
    	container.addView(getLocalActivityManager().startActivity(
                "Music",
                new Intent(MainActivity.this,MusicActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
    	
    	tfAirCondition = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
    	tfAirmode = Typeface.createFromAsset(getAssets(),
				"fonts/SourceHanSansCN-ExtraLight.ttf");
    	mMusic = (ImageView)findViewById(R.id.main_music_icon);
    	mMusic.setOnClickListener(this);
    	mNavi = (ImageView)findViewById(R.id.main_navi_icon);
    	mNavi.setOnClickListener(this);
    	mRadio = (ImageView)findViewById(R.id.main_radio_icon);
    	mRadio.setOnClickListener(this);
    	mPhone = (ImageView)findViewById(R.id.main_phone_icon);
    	mPhone.setOnClickListener(this);
    	
    	acLeftNum = (TextView)findViewById(R.id.left_circle_num);
    	acRightNum = (TextView)findViewById(R.id.right_circle_num);
    	acModeAuto = (TextView)findViewById(R.id.air_mode_auto);
    	acModeComfort = (TextView)findViewById(R.id.air_mode_comfort);
    	acModeEnviroment = (TextView)findViewById(R.id.air_mode_enviroment);
    	
    	acLeftNum.setTypeface(tfAirCondition);
    	acRightNum.setTypeface(tfAirCondition);
    	acModeAuto.setTypeface(tfAirmode);
    	acModeComfort.setTypeface(tfAirmode);
    	acModeEnviroment.setTypeface(tfAirmode);
    	
    	leftAirNumUp = (Button)findViewById(R.id.left_bottom_circle_up);
    	leftAirNumUp.setOnClickListener(this);
    	leftAirNumDown = (Button)findViewById(R.id.left_bottom_circle_down);
    	leftAirNumDown.setOnClickListener(this);
    	rightAirNumUp = (Button)findViewById(R.id.right_bottom_circle_up);
    	rightAirNumUp.setOnClickListener(this);
    	rightAirNumDown = (Button)findViewById(R.id.right_bottom_circle_down);
    	rightAirNumDown.setOnClickListener(this);
    	mMusicUnderline = (ImageView)findViewById(R.id.main_music_underline);
    	mNaviUnderline = (ImageView)findViewById(R.id.main_navi_underline);
    	mRadioUnderline = (ImageView)findViewById(R.id.main_radio_underline);
    	mPhoneUnderline = (ImageView)findViewById(R.id.main_phone_underline);
    	mMusicUnderline.setVisibility(View.VISIBLE);
		mNaviUnderline.setVisibility(View.INVISIBLE);
		mRadioUnderline.setVisibility(View.INVISIBLE);
		mPhoneUnderline.setVisibility(View.INVISIBLE);
		
		mBottomUpLeftNum = (TextView)findViewById(R.id.left_bottomup_air_num);
		mBottomUpLeftNum.setTypeface(tfAirCondition);
		mBottomUpRightNum = (TextView)findViewById(R.id.right_bottomup_air_num);
		mBottomUpRightNum.setTypeface(tfAirCondition);
		bottomSlideInitial();
    }

    private void bottomSlideInitial(){
    	final Stack<Float> mStack = new Stack<Float>();
    	final List<String> airModeList = new ArrayList<String>();
    	Iterator i = airModeList.iterator();
    	airModeList.add("舒适");airModeList.add("自动");airModeList.add("环保");
    	airModeList.add("舒适");airModeList.add("自动");airModeList.add("环保");
    	airModeList.add("舒适");airModeList.add("自动");airModeList.add("环保");
    	//String a = airModeList.get(1);
    	final int pos = airModeList.size()/2;
    	final String string = "1";
    	
    	mBottomSlideParent = (RelativeLayout)findViewById(R.id.bottom_slide_parent);
    	mBottomSlideLight = new ImageView(this);
    	mBottomSlideLight.setImageResource(R.drawable.music_equalizer_bar_hl);
    	mBottomSlideLight.setVisibility(View.INVISIBLE);
    	bottomSlideLp = new LayoutParams(300,300);
    	bottomSlideLp.addRule(RelativeLayout.CENTER_IN_PARENT);
    	mBottomSlideLight.setLayoutParams(bottomSlideLp);
    	mBottomSlideParent.addView(mBottomSlideLight);
    	mBottomSlideParent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				float x;
				int n =pos;
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
							x = event.getX();
							mBottomSlideLight.setLayoutParams(bottomSlideLp);
							mBottomSlideLight.setX(x-150);
							mBottomSlideLight.setVisibility(View.VISIBLE);
							acModeComfort.setVisibility(View.VISIBLE);
							acModeAuto.setTextSize(32);
							acModeEnviroment.setVisibility(View.VISIBLE);
						break;
					case MotionEvent.ACTION_MOVE:
						    mBottomSlideParent.removeView(mBottomSlideLight);
							x = event.getX();
							mBottomSlideParent.addView(mBottomSlideLight);
							mBottomSlideLight.setLayoutParams(bottomSlideLp);
							mBottomSlideLight.setX(x-150);
							int a = Utils.sildeUtil(mStack, x);
							
							
							if ( a == 1) {
								//String abc = airModeList.get(0);
								acModeComfort.setText(airModeList.get(n+2));
								acModeAuto.setText(airModeList.get(n+1));
								acModeAuto.setTextSize(24);
								acModeEnviroment.setText(airModeList.get(n));
								n++;
				
							}if (a == 0) {
								
								acModeComfort.setText(airModeList.get(n-2));
								acModeAuto.setText(airModeList.get(n-1));
								acModeAuto.setTextSize(24);
								acModeEnviroment.setText(airModeList.get(n));
								n--;
							}
						break;
					case MotionEvent.ACTION_UP:
						mBottomSlideLight.setVisibility(View.INVISIBLE);
						mBottomSlideParent.removeView(mBottomSlideLight);
						acModeComfort.setVisibility(View.INVISIBLE);
						acModeEnviroment.setVisibility(View.INVISIBLE);
						acModeAuto.setTextSize(24);
						break;
					default:
						break;
				}
				
				return true;
			}
		});
    }
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.main_music_icon:
			switchActivity("Music", 1);
			break;
		case R.id.main_navi_icon:
			switchActivity("Navi", 2);
			break;
		case R.id.main_radio_icon:
			switchActivity("Radio", 3);
			break;
		case R.id.main_phone_icon:
			switchActivity("Phone", 4);
			break;
		case R.id.left_bottom_circle_up:
			temp = Integer.valueOf(acLeftNum.getText().toString());
			temp++;
			acLeftNum.setText(String.valueOf(temp));
			break;
		case R.id.left_bottom_circle_down:
			temp = Integer.valueOf(acLeftNum.getText().toString());
			temp--;
			acLeftNum.setText(String.valueOf(temp));
			break;
		case R.id.right_bottom_circle_up:
			temp = Integer.valueOf(acRightNum.getText().toString());
			temp++;
			acRightNum.setText(String.valueOf(temp));
			break;
		case R.id.right_bottom_circle_down:
			temp = Integer.valueOf(acRightNum.getText().toString());
			temp--;
			acRightNum.setText(String.valueOf(temp));
			break;


		default:
			break;
		}
	}
	
	private void switchActivity(String TAG,int id){
		
		
		switch(id){
			case 1:
				//Music
				mIntent = new Intent(MainActivity.this,MusicActivity.class);
				mMusicUnderline.setVisibility(View.VISIBLE);
				mNaviUnderline.setVisibility(View.INVISIBLE);
				mRadioUnderline.setVisibility(View.INVISIBLE);
				mPhoneUnderline.setVisibility(View.INVISIBLE);
				break;
			case 2:
				//Navi
				mIntent = new Intent(MainActivity.this,NaviActivity.class);
				mMusicUnderline.setVisibility(View.INVISIBLE);
				mNaviUnderline.setVisibility(View.VISIBLE);
				mRadioUnderline.setVisibility(View.INVISIBLE);
				mPhoneUnderline.setVisibility(View.INVISIBLE);
				break;
			case 3:
				//Radio
				mIntent = new Intent(MainActivity.this,RadioActivity.class);
				mMusicUnderline.setVisibility(View.INVISIBLE);
				mNaviUnderline.setVisibility(View.INVISIBLE);
				mRadioUnderline.setVisibility(View.VISIBLE);
				mPhoneUnderline.setVisibility(View.INVISIBLE);
				break;
			case 4:
				//Phone
				mIntent = new Intent(MainActivity.this,PhoneActivity.class);
				mMusicUnderline.setVisibility(View.INVISIBLE);
				mNaviUnderline.setVisibility(View.INVISIBLE);
				mRadioUnderline.setVisibility(View.INVISIBLE);
				mPhoneUnderline.setVisibility(View.VISIBLE);
				break;
			default:
				mIntent = new Intent(MainActivity.this,MusicActivity.class);
				mMusicUnderline.setVisibility(View.VISIBLE);
				mNaviUnderline.setVisibility(View.INVISIBLE);
				mRadioUnderline.setVisibility(View.INVISIBLE);
				mPhoneUnderline.setVisibility(View.INVISIBLE);
				break;
		}
		//container.startAnimation(mOutAnim);
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
                TAG,mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
	}
	
	private void swithView(){
		mIntent = new Intent(MainActivity.this,NaviTurnToTurnActivity.class);
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
                "turn to turn",mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                .getDecorView());
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Navi_State = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onRestart Code", String.valueOf(Navi_State));
		if(Navi_State){
			//转换面板
			swithView();
		}
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Navi_State = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onStart Code", String.valueOf(Navi_State));
		if(Navi_State){
			Log.e("MainActivity", "success");
		}
	
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Navi_State = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onResume Code", String.valueOf(Navi_State));
		if(Navi_State){
			Log.e("MainActivity", "success");
		}
	
	}
}
