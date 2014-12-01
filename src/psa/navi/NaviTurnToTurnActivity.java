package psa.navi;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.psa.R;

public class NaviTurnToTurnActivity extends Activity{
	private TextView distanceToTurn;
	private TextView turnGuideText;
	private ImageView turnIcon;
	private Typeface tfTurnVenera;
	private Typeface tfSourceHanLight;
	private HelloServer server;
	
	Handler mNaviHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				//更新ui
				Log.e("NaviTurnToTurnActivity", "更新UI");
				Bundle data = (Bundle)msg.obj;
				int message = Integer.valueOf(data.getString("message"));
				
				switch (message) {
				case 0:
					turnIcon.setBackgroundResource(R.drawable.navi_turn_straight);
					turnGuideText.setText("直走");
					break;
				case 1:
					turnIcon.setBackgroundResource(R.drawable.navi_turn_right);
					turnGuideText.setText("右转进入");
					break;
				case 2:
					turnIcon.setBackgroundResource(R.drawable.navi_turn_left);
					turnGuideText.setText("左转进入");
					break;
				default:
					break;
				}
			}
		};
	};
	
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.view_navi_turn_to_turn);
	//overridePendingTransition(R.anim.left_in,R.anim.left_out);
		server = new HelloServer();
		server.setHandler(mNaviHandler);
	    try {
			initial();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initial() throws IOException {
		// TODO Auto-generated method stub
		tfTurnVenera = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
		tfSourceHanLight = Typeface.createFromAsset(getAssets(),
				"fonts/SourceHanSansCN-ExtraLight.ttf");
		
		turnIcon = (ImageView)this.findViewById(R.id.navi_turn_icon);
		distanceToTurn = (TextView)this.findViewById(R.id.navi_distance);
		distanceToTurn.setTypeface(tfTurnVenera);
		turnGuideText = (TextView)this.findViewById(R.id.navi_hint);
		turnGuideText.setTypeface(tfSourceHanLight);
		server.start(); 
	}

}
