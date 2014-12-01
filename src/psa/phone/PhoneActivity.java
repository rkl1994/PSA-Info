package psa.phone;

import java.text.BreakIterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import app.psa.R;

public class PhoneActivity extends Activity implements OnClickListener{

	private TextView tvTitle; 	
	private GalleryView gallery; 	
	private ImageAdapter adapter;
	private RelativeLayout mParent;
	private RelativeLayout.LayoutParams rl;
	private ImageView mHighlight;
	
	private ImageView mDial;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		initRes();
	}
	
	private void initRes(){
		mDial = (ImageView)findViewById(R.id.phone_dial);
		mDial.setOnClickListener(this);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		gallery = (GalleryView) findViewById(R.id.mygallery);
		mHighlight = new ImageView(this);
		mHighlight.setImageResource(R.drawable.music_equalizer_bar_hl);
		mHighlight.setVisibility(View.INVISIBLE);
		rl = new LayoutParams(300,300);
		rl.addRule(RelativeLayout.CENTER_IN_PARENT);
		mHighlight.setLayoutParams(rl);
		mParent = (RelativeLayout)findViewById(R.id.phone_slide);
		mParent.addView(mHighlight);
		adapter = new ImageAdapter(this); 	
		gallery.setAdapter(adapter);
		mParent.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				float x;
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
							x = event.getX();
							mHighlight.setLayoutParams(rl);
							mHighlight.setX(x-150);
							mHighlight.setVisibility(View.VISIBLE);
						break;
					case MotionEvent.ACTION_MOVE:
							mParent.removeView(mHighlight);
							x = event.getX();
							mParent.addView(mHighlight);
							mHighlight.setLayoutParams(rl);
							mHighlight.setX(x-150);
						break;
					case MotionEvent.ACTION_UP:
						mHighlight.setVisibility(View.INVISIBLE);
						mParent.removeView(mHighlight);
						break;
					default:
						break;
				}
				
				return true;
			}
		});
		
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override 
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				tvTitle.setText(adapter.titles[position]);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		gallery.setOnItemClickListener(new OnItemClickListener() {			// 设置点击事件监听

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.phone_dial:
			Intent mIntent = new Intent(PhoneActivity.this,PhoneDialPanelActivity.class);
			startActivity(mIntent);
			break;

		default:
			break;
		}
	}

}

