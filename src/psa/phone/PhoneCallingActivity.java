package psa.phone;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.psa.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

public class PhoneCallingActivity extends Activity implements OnClickListener {
	private TextView phoneTime;
	private TextView phoneVoice;
	private TextView contactName;

	private int systemVoiceMax;
	private int systemVoiceCur;
	private int phoneVoiceTmp;
	private Typeface tfPhoneVenera;

	private Typeface tfPhoneSourceHanSansCNLight;

	private Button phoneVoiceMax;
	private Button phoneVoiceMin;
	private Button phoneHangUp;

	private TextView minText; // ·Ö
	private TextView secText; // Ãë

	private boolean isPaused = false;
	private String timeUsed;
	private String callingNum;
	private int timeUsedInsec;
	private AudioManager mPhoneManager;
	
	Bundle bl;
	Intent intent;

	private Handler uiHandle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				if (!isPaused) {
					addTimeUsed();
					updateClockUI();
				}
				uiHandle.sendEmptyMessageDelayed(1, 1000);
				break;
			default:
				break;
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_calling);
		Intent intent1 = getIntent();
		callingNum = intent1.getStringExtra("dialNoString"); 
		
		initial();

		
		 
		uiHandle.removeMessages(1);
		startTime();
		isPaused = false;

	}

	private void initial() {

		tfPhoneVenera = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
		tfPhoneSourceHanSansCNLight = Typeface.createFromAsset(getAssets(),
				"fonts/SourceHanSansCNLight.ttf");
		mPhoneManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		systemVoiceCur = mPhoneManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		systemVoiceMax = mPhoneManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		phoneVoice = (TextView) this.findViewById(R.id.phone_voice_num);
		contactName = (TextView) this.findViewById(R.id.phone_calling_contact);

		minText = (TextView) findViewById(R.id.num1);
		secText = (TextView) findViewById(R.id.num2);

		minText.setTypeface(tfPhoneVenera);
		secText.setTypeface(tfPhoneVenera);
		// phoneTime.setTypeface(tfPhoneVenera);
		phoneVoice.setTypeface(tfPhoneVenera);
		contactName.setTypeface(tfPhoneSourceHanSansCNLight);
		
		contactName.setText(callingNum);

		phoneVoiceMax = (Button) this.findViewById(R.id.phone_voice_max);
		phoneVoiceMax.setOnClickListener(this);

		phoneVoiceMin = (Button) this.findViewById(R.id.phone_voice_min);
		phoneVoiceMin.setOnClickListener(this);

		phoneVoice.setText(String.valueOf(systemVoiceCur));

		phoneHangUp = (Button) this.findViewById(R.id.phone_hang_up_btn);
		phoneHangUp.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.phone_hang_up_btn:
			finish();
			break;

		case R.id.phone_voice_max:
			phoneVoiceTmp = Integer.valueOf(phoneVoice.getText().toString());
			phoneVoiceTmp++;
			mPhoneManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, 0);
			mPhoneManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_RAISE, 0);
			if (phoneVoiceTmp > systemVoiceMax) {
				phoneVoice.setText(String.valueOf(systemVoiceMax));

			} else {
				phoneVoice.setText(String.valueOf(phoneVoiceTmp));
			}
			break;

		case R.id.phone_voice_min:
			phoneVoiceTmp = Integer.valueOf(phoneVoice.getText().toString());
			phoneVoiceTmp--;
			mPhoneManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, 0);
			mPhoneManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_LOWER, 0);
			if (phoneVoiceTmp < 0) {
				phoneVoice.setText("0");
			} else {
				phoneVoice.setText(String.valueOf(phoneVoiceTmp));
			}
			break;

		default:
			break;
		}
		// TODO Auto-generated method stub

	}

	/***********************************/

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isPaused = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isPaused = false;
	}

	private void startTime() {
		uiHandle.sendEmptyMessageDelayed(1, 1000);
	}

	private void updateClockUI() {
		minText.setText(getMin() + ":");
		secText.setText(getSec());
	}

	public void addTimeUsed() {
		timeUsedInsec = timeUsedInsec + 1;
		timeUsed = this.getMin() + ":" + this.getSec();
	}

	public CharSequence getMin() {
		int min = timeUsedInsec/60;
		return min < 10 ? "0" + min : String.valueOf(timeUsedInsec / 60);
		//return String.valueOf( timeUsedInsec / 60);
	}

	public CharSequence getSec() {
		int sec = timeUsedInsec % 60;
		return sec < 10 ? "0" + sec : String.valueOf(sec);
	}

}
