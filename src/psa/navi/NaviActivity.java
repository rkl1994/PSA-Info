package psa.navi;

import psa.phone.PhoneActivity;
import psa.phone.PhoneDialPanelActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import app.psa.R;

public class NaviActivity extends Activity implements OnClickListener{
	
	private Typeface tfNaviExtraLight;
	private TextView naviNowLocation;
	private TextView naviNowLocationTitle;
	
	private Button naviSearchBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navi);
		overridePendingTransition(R.anim.left_in,R.anim.left_out);
		
		initial();
		
	}

	private void initial() {
		// TODO Auto-generated method stub
		tfNaviExtraLight = Typeface.createFromAsset(getAssets(),
				"fonts/SourceHanSansCN-ExtraLight.ttf");
		naviNowLocation = (TextView)this.findViewById(R.id.local_title);
		naviNowLocationTitle = (TextView)this.findViewById(R.id.local_now);
		naviNowLocation.setTypeface(tfNaviExtraLight);
		naviNowLocationTitle.setTypeface(tfNaviExtraLight);
		naviSearchBtn = (Button)this.findViewById(R.id.navi_search);
		naviSearchBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.navi_search:
			Intent mIntent = new Intent(NaviActivity.this,NaviSearchActivity.class);
			startActivity(mIntent);
			break;
			
		default:
			break;
		}
	}
	
}
