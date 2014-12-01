package psa.navi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import app.psa.MainActivity;
import app.psa.R;

public class NaviRouteActivity extends Activity implements OnClickListener{
	
	private Button shortRouteBtn;
	private Button fastRouteBtn;
	private Button avoidtRouteBtn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_navi_route);
		initial();
	}

	private void initial() {
		// TODO Auto-generated method stub
		shortRouteBtn = (Button)this.findViewById(R.id.short_route_btn);
		shortRouteBtn.setOnClickListener(this);
		fastRouteBtn = (Button)this.findViewById(R.id.fast_route_btn);
		fastRouteBtn.setOnClickListener(this);
		avoidtRouteBtn = (Button)this.findViewById(R.id.avoid_route_btn);
		avoidtRouteBtn.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.short_route_btn:
			Intent mIntent = new Intent(NaviRouteActivity.this,MainActivity.class);
			mIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); //无传值
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME); 新实例
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY); 新实例
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT); 新实例
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			//mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			mIntent.putExtra("TURN_TO_TURN", true);
			startActivity(mIntent);
			this.finish();
			break;
		default:
			break;
		}
		// TODO Auto-generated method stub
		
	}

}
