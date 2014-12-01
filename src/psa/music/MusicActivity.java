package psa.music;

import java.text.BreakIterator;
import java.util.List;

import org.w3c.dom.Text;

import psa.radio.RadioActivity;

import android.R.bool;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import app.psa.R;

public class MusicActivity extends Activity implements OnClickListener {
	
	private TextView musicTitle;
	private TextView musicArtist;
	private TextView musicAlbum;
	private TextView musicVoice;
	
	private TextView musicEqualizerRock; 
	private TextView musicEqualizerFunk;
	private TextView musicEqualizerJazz;
	
	private Typeface tfMusicNexaLight;
	private Typeface tfMusicVenera;

	private Button musicVoiceMax;
	private Button musicVoiceMin;
	private Button musicNextBtn;
	private Button musicPreBtn;
	private Button musicPausePlayBtn;

	private int musicVoiceTmp;
	private int musicListPos = 0;
	private int systemVoiceMax;
	private int systemVoiceMin;
	private int systemVoiceCur;

	private boolean isPlaying;
	private boolean isPause;

	private AudioManager mAudioManager;
	List<Mp3Info> mp3Infos;

	/**topSlide**/
	private RelativeLayout mTopSlideParent;
	private RelativeLayout.LayoutParams topSlideLp;
	private ImageView mTopSlideLight;
	
	/**centerSlide**/
	private RelativeLayout mCenterSlideParent;
	private RelativeLayout.LayoutParams centerSlideLp;
	private ImageView mCenterSlideLight;
	
	/**bottomSlide**/
	private RelativeLayout mBottomSlideParent;
	private RelativeLayout.LayoutParams bottomSlideLp;
	private ImageView mBottomSlideLight;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_music);
		overridePendingTransition(R.anim.left_in, R.anim.left_out);
		initial();
	}

	private void initial() {
		tfMusicVenera = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
		tfMusicNexaLight = Typeface.createFromAsset(getAssets(),
				"fonts/nexalight.ttf");

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		systemVoiceCur = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		systemVoiceMax = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

		mp3Infos = MediaUtil.getMp3Infos(getApplicationContext());

		musicTitle = (TextView) this.findViewById(R.id.text_music_title);
		musicArtist = (TextView) this.findViewById(R.id.text_music_artist);
		musicAlbum = (TextView) this.findViewById(R.id.text_music_album);
		musicVoice = (TextView) this.findViewById(R.id.text_music_voice);
		musicVoice.setText(String.valueOf(systemVoiceCur));
		
		musicEqualizerFunk = (TextView)findViewById(R.id.text_music_equalizer_funk);
		musicEqualizerRock = (TextView)findViewById(R.id.text_music_equalizer_rock);
		musicEqualizerJazz = (TextView)findViewById(R.id.text_music_equalizer_jazz);
		
		musicTitle.setTypeface(tfMusicNexaLight);
		musicArtist.setTypeface(tfMusicNexaLight);
		musicAlbum.setTypeface(tfMusicNexaLight);
		musicVoice.setTypeface(tfMusicVenera);
		
		musicEqualizerFunk.setTypeface(tfMusicVenera);
		musicEqualizerRock.setTypeface(tfMusicVenera);
		musicEqualizerJazz.setTypeface(tfMusicVenera);
		
		musicTitle.setText(mp3Infos.get(0).getTitle());
		musicArtist.setText(mp3Infos.get(0).getArtist());
		musicAlbum.setText(mp3Infos.get(0).getAlbum());

		musicVoiceMax = (Button) this.findViewById(R.id.music_voice_max);
		musicVoiceMin = (Button) this.findViewById(R.id.music_voice_min);
		musicNextBtn = (Button) this.findViewById(R.id.music_next);
		musicPreBtn = (Button) this.findViewById(R.id.musci_pre);
		musicPausePlayBtn = (Button) this.findViewById(R.id.music_pause);

		musicVoiceMax.setOnClickListener(this);
		musicVoiceMin.setOnClickListener(this);
		musicNextBtn.setOnClickListener(this);
		musicPreBtn.setOnClickListener(this);
		musicPausePlayBtn.setOnClickListener(this);
		topInitial();
		centerInitial();
		bottomInitial();
	}

	float tmp = 0;
	private void topInitial(){
	   mTopSlideParent = (RelativeLayout)findViewById(R.id.music_top_layout);
	   mTopSlideLight = new ImageView(this);
	   mTopSlideLight.setImageResource(R.drawable.music_equalizer_bar_hl);
	   mTopSlideLight.setVisibility(View.INVISIBLE);
	   topSlideLp = new LayoutParams(300,300);
	   topSlideLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
	   topSlideLp.setMargins(0, 55, 0, 0);
	   mTopSlideLight.setLayoutParams(topSlideLp);
	   mTopSlideParent.addView(mTopSlideLight);
	   mTopSlideParent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				float x;
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
							x = event.getX();
							mTopSlideLight.setLayoutParams(topSlideLp);
							mTopSlideLight.setX(x-150);
							mTopSlideLight.setVisibility(View.VISIBLE);
						break;
					case MotionEvent.ACTION_MOVE:
						    mTopSlideParent.removeView(mTopSlideLight);
							x = event.getX();
							mTopSlideParent.addView(mTopSlideLight);
							mTopSlideLight.setLayoutParams(topSlideLp);
							mTopSlideLight.setX(x-150);
						break;
					case MotionEvent.ACTION_UP:
						mTopSlideLight.setVisibility(View.INVISIBLE);
						mTopSlideParent.removeView(mTopSlideLight);
						break;
					default:
						break;
				}
				
				return true;
			}
		});
	}
	float x = 0;
	private void centerInitial(){
		mCenterSlideParent = (RelativeLayout)findViewById(R.id.music_center_layout);
    	mCenterSlideLight = new ImageView(this);
    	mCenterSlideLight.setImageResource(R.drawable.music_equalizer_bar_hl);
    	mCenterSlideLight.setVisibility(View.INVISIBLE);
    	centerSlideLp = new LayoutParams(300,300);
    	centerSlideLp.addRule(RelativeLayout.CENTER_IN_PARENT);
    	mCenterSlideLight.setLayoutParams(centerSlideLp);
    	mCenterSlideParent.addView(mCenterSlideLight);
    	mCenterSlideParent.setOnTouchListener(new OnTouchListener() {
  
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				
				tmp = x;
				x = event.getX();
				
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
							x = event.getX();
							Log.e("Music ACTION_DOWN", String.valueOf(x));
							mCenterSlideLight.setLayoutParams(centerSlideLp);
							mCenterSlideLight.setX(x-150);
							mCenterSlideLight.setVisibility(View.VISIBLE);
						break;
					case MotionEvent.ACTION_MOVE:
						    mCenterSlideParent.removeView(mCenterSlideLight);
							mCenterSlideParent.addView(mCenterSlideLight);
							mCenterSlideLight.setLayoutParams(centerSlideLp);
							mCenterSlideLight.setX(x-150);
							musicVoice.setTextSize(70);
							Log.e("Music ACTION_MOVE x", String.valueOf(tmp));
							Log.e("Music ACTION_MOVE tmp", String.valueOf(x));
						break;
					case MotionEvent.ACTION_UP:
						  
							mCenterSlideLight.setVisibility(View.INVISIBLE);
							mCenterSlideParent.removeView(mCenterSlideLight);
							musicVoice.setTextSize(27);
						break;
					default:
						break;
				}
				
				return true;
			}
		});
	}
	
	private void bottomInitial(){
		mBottomSlideParent = (RelativeLayout)findViewById(R.id.music_bottom_layout);
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
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
							x = event.getX();
							mBottomSlideLight.setLayoutParams(bottomSlideLp);
							mBottomSlideLight.setX(x-150);
							mBottomSlideLight.setVisibility(View.VISIBLE);
							musicEqualizerFunk.setVisibility(View.VISIBLE);
							musicEqualizerJazz.setVisibility(View.VISIBLE);
						break;
					case MotionEvent.ACTION_MOVE:
						    mBottomSlideParent.removeView(mBottomSlideLight);
							x = event.getX();
							mBottomSlideParent.addView(mBottomSlideLight);
							mBottomSlideLight.setLayoutParams(bottomSlideLp);
							mBottomSlideLight.setX(x-150);
							musicEqualizerRock.setTextSize(40);
							
						break;
					case MotionEvent.ACTION_UP:
						    mBottomSlideLight.setVisibility(View.INVISIBLE);
					 	    mBottomSlideParent.removeView(mBottomSlideLight);
					 	    musicEqualizerFunk.setVisibility(View.INVISIBLE);
					 	    musicEqualizerJazz.setVisibility(View.INVISIBLE);
					 	    musicEqualizerRock.setTextSize(27);
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
		switch (view.getId()) {
		case R.id.music_pause:
			if(RadioActivity.radioMediaPlayer!=null){
				RadioActivity.radioMediaPlayer.release();
			}
			//RadioActivity.radioMediaPlayer.release();
			play();
			break;
		case R.id.musci_pre:
			if(RadioActivity.radioMediaPlayer!=null){
				RadioActivity.radioMediaPlayer.release();
			}
			//RadioActivity.radioMediaPlayer.release();
			playPre();
			musicPausePlayBtn
					.setBackgroundResource(R.drawable.music_pause_icon);
			break;
		case R.id.music_next:
			if(RadioActivity.radioMediaPlayer!=null){
				RadioActivity.radioMediaPlayer.release();
			}
			//RadioActivity.radioMediaPlayer.release();
			playNext();
			musicPausePlayBtn.setBackgroundResource(R.drawable.music_pause_icon);
			break;
			
		case R.id.music_voice_max:
			musicVoiceTmp = Integer.valueOf(musicVoice.getText().toString());
			musicVoiceTmp++;
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, 0);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_RAISE, 0);
			if (musicVoiceTmp > systemVoiceMax) {
				musicVoice.setText(String.valueOf(systemVoiceMax));

			} else {
				musicVoice.setText(String.valueOf(musicVoiceTmp));
			}
			break;
			
		case R.id.music_voice_min:
			musicVoiceTmp = Integer.valueOf(musicVoice.getText().toString());
			musicVoiceTmp--;
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, 0);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,
					AudioManager.ADJUST_LOWER, 0);
			if (musicVoiceTmp < 0) {
				musicVoice.setText("0");
			} else {
				musicVoice.setText(String.valueOf(musicVoiceTmp));
			}
			break;

		}

		// TODO Auto-generated method stub

	}

	private void play() {
		// TODO Auto-generated method stub
		Mp3Info mp3Info = mp3Infos.get(musicListPos);
		musicTitle.setText(mp3Info.getTitle());
		musicArtist.setText(mp3Info.getArtist());
		musicAlbum.setText(mp3Info.getAlbum());
		Intent intent = new Intent();

		if (isPlaying == false) {
			musicPausePlayBtn
					.setBackgroundResource(R.drawable.music_pause_icon);
			intent.putExtra("url", mp3Info.getUrl());
			intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			intent.setClass(getApplicationContext(), PlayerService.class);
			startService(intent);
			isPlaying = true;
			// isPause = false;
			// isFirstTime = false;
		} else {
			if (isPlaying) {
				musicPausePlayBtn
						.setBackgroundResource(R.drawable.music_play_icon);
				intent.putExtra("url", mp3Info.getUrl());
				intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
				intent.setClass(getApplicationContext(), PlayerService.class);
				startService(intent);
				isPlaying = false;
				isPause = true;
			} else if (isPause = true) {
				musicPausePlayBtn
						.setBackgroundResource(R.drawable.music_play_icon);
				intent.putExtra("MSG", AppConstant.PlayerMsg.CONTINUE_MSG);
				intent.setClass(getApplicationContext(), PlayerService.class);
				startService(intent);
				isPause = false;
				isPlaying = true;
			}
		}
	}

	private void playNext() {
		isPlaying = true;
		musicListPos = musicListPos + 1;
		if (musicListPos > mp3Infos.size() - 1) {
			musicListPos = 0;
		}
		Mp3Info mp3Info = mp3Infos.get(musicListPos);
		musicTitle.setText(mp3Info.getTitle());
		musicArtist.setText(mp3Info.getArtist());
		musicAlbum.setText(mp3Info.getAlbum());

		Intent intent = new Intent();
		intent.putExtra("url", mp3Info.getUrl());
		intent.putExtra("MSG", AppConstant.PlayerMsg.NEXT_MSG);
		intent.setClass(getApplicationContext(), PlayerService.class);
		startService(intent);
	}

	private void playPre() {
		// TODO Auto-generated method stub
		isPlaying = true;
		musicListPos = musicListPos - 1;
		if (musicListPos < 0) {
			musicListPos = mp3Infos.size() - 1;
			// System.out.println("xx");
		}
		Mp3Info mp3Info = mp3Infos.get(musicListPos);
		musicTitle.setText(mp3Info.getTitle());
		musicArtist.setText(mp3Info.getArtist());
		musicAlbum.setText(mp3Info.getAlbum());

		Intent intent = new Intent();
		intent.putExtra("url", mp3Info.getUrl());
		intent.putExtra("MSG", AppConstant.PlayerMsg.PRIVIOUS_MSG);
		intent.setClass(getApplicationContext(), PlayerService.class);
		startService(intent);
	}

}
