package psa.navi;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import psa.navi.KeyboardUtil;
import app.psa.R;

public class NaviSearchActivity extends Activity{
	private ListView naviSearchlist;
	private Typeface tfNaviList;
	private TextView naviSearch;
	private EditText edit;
	private Context ctx;
	private Activity act;
	boolean result;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_navi_search);
		
		ctx = this;
		act = this;
		initial();
		naviSearchlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(NaviSearchActivity.this,NaviRouteActivity.class);
				startActivity(mIntent);
			}
		});
		edit.setInputType(InputType.TYPE_NULL);
		edit.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				new KeyboardUtil(act, ctx, edit).showKeyboard();
				return false;
			}
		});
		
	}

	private void initial() {
		// TODO Auto-generated method stub
		tfNaviList = Typeface.createFromAsset(getAssets(),
				"fonts/SourceHanSansCN-ExtraLight.ttf");
		//naviSearch = (TextView)(View)findViewById(R.id.navi_search_list_text);
		//naviSearch.setTypeface(tfNaviList);
		naviSearchlist = new ListView(this);
		naviSearchlist = (ListView)this.findViewById(R.id.navi_search_result_list);
		naviSearchlist.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list_navisearch, R.id.navi_search_list_text, getData()));
		edit = (EditText)this.findViewById(R.id.navi_search_word);
	}

	 private List<String> getData(){
         
	        List<String> data = new ArrayList<String>();
	        data.add("玛莎百货");
	        data.add("测试数据2");
	        data.add("测试数据3");
	        data.add("测试数据4");
	        data.add("测试数据1");
	        data.add("测试数据2");
	        data.add("测试数据3");
	        data.add("测试数据4");
	        data.add("测试数据1");
	        data.add("测试数据2");
	        data.add("测试数据3");
	        data.add("测试数据4");
	        return data;
	    }
	 
	 @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		result = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onStart", String.valueOf(result));
		
	}
	 
	 @Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		result = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onRestart", String.valueOf(result));
	}
	 
	 @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		result = getIntent().getBooleanExtra("TURN_TO_TURN", false);
		Log.e("onResume", String.valueOf(result));
		
	}
}
