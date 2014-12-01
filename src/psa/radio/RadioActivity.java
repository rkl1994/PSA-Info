package psa.radio;

import psa.music.MusicActivity;
import psa.music.PlayerService;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import app.psa.R;

public class RadioActivity extends Activity implements OnTouchListener,OnClickListener{
	
	
	private Button radioVoiceMax;
	private Button radioVoiceMin;
	private Button radioNextBtn;
	private Button radioPreBtn;
	
	private Typeface tfRadioVenera;
	private Typeface tfRadioNexaLight;
	
	private TextView mRadioNumCenter;
	private TextView mRadioNumRight2;
	private TextView mRadioNumRight1;
	private TextView mRadioNumLeft1;
	private TextView mRadioNumLeft2;
	private TextView radioVoice;
	
	private int radioVoiceTmp;
	private int systemVoiceMax;
	private int systemVoiceCur;

	private AudioManager mAudioManager;
	public static MediaPlayer radioMediaPlayer;
	
	
	private Boolean radioIsPlaying;

	/**highlight 控件**/
	private RelativeLayout mRadioHighLight;
	private ViewGroup mRadioCtrLayout; // 白色光点所在布局
	private View selected_item = null;
	private int offset_x = 0;
	private int default_x = 0;
	private int default_y = 100;
	RelativeLayout.LayoutParams lp;
	int count = 0;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_radio);
		overridePendingTransition(R.anim.left_in,R.anim.left_out);
		
		initial();
		if(isFinishing())
		{
			radioMediaPlayer.release();
		}
		
	}
	
	private void initial() {
		tfRadioVenera = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
		tfRadioNexaLight = Typeface.createFromAsset(getAssets(),
				"fonts/nexalight.ttf");
		
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		systemVoiceCur = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		systemVoiceMax = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		
		mRadioNumCenter = (TextView) this.findViewById(R.id.radio_num_center);
		mRadioNumRight2 = (TextView) this.findViewById(R.id.radio_num_right_2);
		mRadioNumRight1 = (TextView) this.findViewById(R.id.radio_num_right_1);
		mRadioNumLeft1 = (TextView) this.findViewById(R.id.radio_num_left_1);
		mRadioNumLeft2 = (TextView) this.findViewById(R.id.radio_num_left_2);
		radioVoice = (TextView) this.findViewById(R.id.text_radio_voice);
		
		mRadioNumCenter.setTypeface(tfRadioNexaLight);
		mRadioNumRight2.setTypeface(tfRadioNexaLight);
		mRadioNumRight1.setTypeface(tfRadioNexaLight);
		mRadioNumLeft1.setTypeface(tfRadioNexaLight);
		mRadioNumLeft2.setTypeface(tfRadioNexaLight);
		radioVoice.setTypeface(tfRadioVenera);
		radioVoice.setText(String.valueOf(systemVoiceCur));
		
		radioVoiceMax = (Button)this.findViewById(R.id.radio_voice_max);
		radioVoiceMax.setOnClickListener(this);
		radioVoiceMin = (Button)this.findViewById(R.id.radio_voice_min);
		radioVoiceMin.setOnClickListener(this);
		radioNextBtn = (Button)this.findViewById(R.id.radio_next);
		radioNextBtn.setOnClickListener(this);
		radioPreBtn = (Button)this.findViewById(R.id.radio__pre);
		radioPreBtn.setOnClickListener(this);
		
		mRadioCtrLayout = (ViewGroup)findViewById(R.id.radio_ctr_layout);
		mRadioCtrLayout.setOnTouchListener(this);
		mRadioHighLight = (RelativeLayout)findViewById(R.id.radio_slide_highlight);
		mRadioHighLight.setOnTouchListener(this);
		}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// TODO Auto-generated method stub
		float x1 = 0;
		float x2 = 0;
		if(view.getId() == R.id.radio_ctr_layout){
			int x = (int) event.getX() - offset_x;
			switch (event.getActionMasked()) {

			case MotionEvent.ACTION_DOWN:
				x1 = event.getX();
				selected_item.setVisibility(View.VISIBLE);
				mRadioNumLeft1.setVisibility(View.VISIBLE);
				mRadioNumLeft2.setVisibility(View.VISIBLE);
				mRadioNumRight1.setVisibility(View.VISIBLE);
				mRadioNumRight2.setVisibility(View.VISIBLE);
				selected_item.setVisibility(View.VISIBLE);
				lp = new RelativeLayout.LayoutParams(
						new ViewGroup.MarginLayoutParams(
								LayoutParams.WRAP_CONTENT, 200));
				lp.setMargins(x-110, default_y, 0, 0);
				selected_item.setLayoutParams(lp);
				break;
			case MotionEvent.ACTION_MOVE:
				x2 = event.getX();
			    highlightRotate(x1, x2);
				lp = new RelativeLayout.LayoutParams(
						new ViewGroup.MarginLayoutParams(
								LayoutParams.WRAP_CONTENT, 200));
				lp.setMargins(x, default_y, 0, 0);

				selected_item.setLayoutParams(lp);
				selected_item.setVisibility(View.VISIBLE);
				Log.e("ACTION_MOVE", "1");
				break;

			case MotionEvent.ACTION_CANCEL:
				lp.setMargins(default_x, default_y, 0, 0);
				selected_item.setLayoutParams(lp);
				Log.e("ACTION_CANCEL", "1");
				mRadioNumLeft1.setVisibility(View.INVISIBLE);
				mRadioNumLeft2.setVisibility(View.INVISIBLE);
				mRadioNumRight1.setVisibility(View.INVISIBLE);
				mRadioNumRight2.setVisibility(View.INVISIBLE);
				break;
			case MotionEvent.ACTION_UP:
				lp.setMargins(default_x, default_y, 0, 0);
				selected_item.setVisibility(View.INVISIBLE);
				selected_item.setLayoutParams(lp);
				mRadioNumLeft1.setVisibility(View.INVISIBLE);
				mRadioNumLeft2.setVisibility(View.INVISIBLE);
				mRadioNumRight1.setVisibility(View.INVISIBLE);
				mRadioNumRight2.setVisibility(View.INVISIBLE);
				Log.e("ACTION_UP", "1");
			case MotionEvent.ACTION_OUTSIDE:
				Log.e("ACTION_OUTSIDE", "1");
				selected_item.setVisibility(View.INVISIBLE);
				lp.setMargins(default_x, default_y, 0, 0);
				selected_item.setLayoutParams(lp);
				mRadioNumLeft1.setVisibility(View.INVISIBLE);
				mRadioNumLeft2.setVisibility(View.INVISIBLE);
				mRadioNumRight1.setVisibility(View.INVISIBLE);
				mRadioNumRight2.setVisibility(View.INVISIBLE);
			default:
				break;
			}
		}
		if (view.getId() == R.id.radio_slide_highlight) {
			selected_item = view;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				selected_item.setVisibility(View.VISIBLE);
				mRadioNumLeft1.setVisibility(View.VISIBLE);
				mRadioNumLeft2.setVisibility(View.VISIBLE);
				mRadioNumRight1.setVisibility(View.VISIBLE);
				mRadioNumRight2.setVisibility(View.VISIBLE);
				offset_x = (int) event.getX();
				int[] location = new int[2];
				mRadioHighLight.getLocationOnScreen(location);
				if (count == 0) {
					default_x = location[0];
					count++;
				}
				Log.e("default:x", String.valueOf(default_x));
				break;
			case MotionEvent.ACTION_MOVE:
				selected_item.setVisibility(View.VISIBLE);
				break;
			case MotionEvent.ACTION_UP:
				selected_item.setVisibility(View.INVISIBLE);
				mRadioNumLeft1.setVisibility(View.INVISIBLE);
				mRadioNumLeft2.setVisibility(View.INVISIBLE);
				mRadioNumRight1.setVisibility(View.INVISIBLE);
				mRadioNumRight2.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		}
		return true;
	}
	
	private void highlightRotate(float x1, float x2) {

		String base = mRadioNumCenter.getText().toString();
		float num = Float.valueOf(base);
		Log.e("num",String.valueOf(num));
		Log.e("x1", String.valueOf(x1));
		Log.e("x2", String.valueOf(x2));

		if (690 > x2) {
			// 说明向右滑动
			mRadioNumLeft2.setText(String.valueOf(num - 3));
			mRadioNumLeft1.setText(String.valueOf(num - 2));
			mRadioNumCenter.setText(String.valueOf(num - 1));
			mRadioNumRight1.setText(String.valueOf(num));
			mRadioNumRight2.setText(String.valueOf(num + 1));
		} else {
			// 说明向左滑动	
			mRadioNumLeft1.setText(String.valueOf(num+3));
			mRadioNumLeft2.setText(String.valueOf(num+2));
			mRadioNumCenter.setText(String.valueOf(num +1));
			mRadioNumRight1.setText(String.valueOf(num));
			mRadioNumRight2.setText(String.valueOf(num+1));
		}
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.radio_voice_max:
			radioVoiceTmp = Integer.valueOf(radioVoice.getText().toString());
			radioVoiceTmp++;
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, 0);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_RAISE, 0);
			if (radioVoiceTmp > systemVoiceMax) {
				radioVoice.setText(String.valueOf(systemVoiceMax));

			} else {
				radioVoice.setText(String.valueOf(radioVoiceTmp));
			}
			break;
			
		case R.id.radio_voice_min:
			radioVoiceTmp = Integer.valueOf(radioVoice.getText().toString());
			radioVoiceTmp--;
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, 0);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_LOWER, 0);
			if (radioVoiceTmp < 0) {
				radioVoice.setText("0");
			} else {
				radioVoice.setText(String.valueOf(radioVoiceTmp));
			}
			break;
			
		case R.id.radio__pre:
			if(PlayerService.mediaPlayer!=null){
				PlayerService.mediaPlayer.release();
				
			}
			radioIsPlaying = true;
			
			playRadioPre();
			
			break;
			
		case R.id.radio_next:
			if(PlayerService.mediaPlayer!=null){
				PlayerService.mediaPlayer.release();
				
			}
			radioIsPlaying = true;
			
			playRadioNext();
			
			break;
		}
		
		
	}
	
	
	private void playRadioNext() {
		// TODO Auto-generated method stub
		//if(radioNumCenter == 
		//radioMediaPlayer.pause();
		if(radioMediaPlayer!=null){
		radioMediaPlayer.release();}
		String base = mRadioNumCenter.getText().toString();
		float num = Float.valueOf(base);
		num += 0.1;
		float c = (float) (Math.round((num ) * 10)) / 10;
		int b = (int) (c * 10);
		mRadioNumCenter.setText(String.valueOf(c));
		playRadio(b);
		
	
	}

	

	private void playRadioNull() {
		  
		radioMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.radio_null);  
		radioMediaPlayer.start();
		
		// TODO Auto-generated method stub
		
	}

	private void playRadio(int num) {
		//mediaPlayer
		//PlayerService.mediaPlayer.release();
		if(num == 931)
		{
			radioMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.fm931);  
			radioMediaPlayer.start();
			radioMediaPlayer.setLooping(true);
			
		}
		else if(num==935)
		{
			radioMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.fm935);  
			radioMediaPlayer.start();
			radioMediaPlayer.setLooping(true);
		}
		else{
			radioMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.radio_null); 
			
			radioMediaPlayer.start();
			radioMediaPlayer.setLooping(true);
		}
		// TODO Auto-generated method stub
		
	}

	private void playRadioPre() {
		// TODO Auto-generated method stub
		if(radioMediaPlayer!=null){
			radioMediaPlayer.release();}
		String base = mRadioNumCenter.getText().toString();
		float num = Float.valueOf(base);
		num -= 0.1;
		float c = (float) (Math.round((num ) * 10)) / 10;
		mRadioNumCenter.setText(String.valueOf(c));
		int b = (int) (c * 10);
		playRadio(b);
		
	}
	
	
	
	
}
