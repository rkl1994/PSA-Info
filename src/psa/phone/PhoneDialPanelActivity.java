package psa.phone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import app.psa.R;

public class PhoneDialPanelActivity extends Activity implements OnClickListener{
	
	private TextView dialPhoneNum;
	
	private Typeface tfDialVenera;
	private Typeface tfDialNexaLight;
	
	private TextView textNumZero;
	private TextView textNumOne;
	private TextView textNumTwo;
	private TextView textNumThree;
	private TextView textNumFour;
	private TextView textNumFive;
	private TextView textNumSix;
	private TextView textNumSeven;
	private TextView textNumEight;
	private TextView textNumNine;
	
	private String dialNoString;
	
	private Button dialNoZeroBtn;
	private Button dialNoOneBtn;
	private Button dialNoTwoBtn;
	private Button dialNoThreeBtn;
	private Button dialNoFourBtn;
	private Button dialNoFiveBtn;
	private Button dialNoSixBtn;
	private Button dialNoSevenBtn;
	private Button dialNoEightBtn;
	private Button dialNoNineBtn;
	
	private Button dialOutBtn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phone_dial_panel);
		
		initial();
		
	}

	private void initial() {
		// TODO Auto-generated method stub
		tfDialVenera = Typeface.createFromAsset(getAssets(),
				"fonts/venera300.ttf");
		tfDialNexaLight = Typeface.createFromAsset(getAssets(),
				"fonts/nexalight.ttf");
		dialPhoneNum = (TextView)this.findViewById(R.id.dial_num);
		dialPhoneNum.setTypeface(tfDialNexaLight);
		
		textNumZero = (TextView)this.findViewById(R.id.num_no_zero);
		textNumOne = (TextView)this.findViewById(R.id.num_no_one);
		textNumTwo = (TextView)this.findViewById(R.id.num_no_two);
		textNumThree = (TextView)this.findViewById(R.id.num_no_three);
		textNumFour = (TextView)this.findViewById(R.id.num_no_four);
		textNumFive = (TextView)this.findViewById(R.id.num_no_five);
		textNumSix = (TextView)this.findViewById(R.id.num_no_six);
		textNumSeven = (TextView)this.findViewById(R.id.num_no_seven);
		textNumEight = (TextView)this.findViewById(R.id.num_no_eight);
		textNumNine = (TextView)this.findViewById(R.id.num_no_nine);
		
		textNumZero.setTypeface(tfDialVenera);
		textNumOne.setTypeface(tfDialVenera);
		textNumTwo.setTypeface(tfDialVenera);
		textNumThree.setTypeface(tfDialVenera);
		textNumFour.setTypeface(tfDialVenera);
		textNumFive.setTypeface(tfDialVenera);
		textNumSix.setTypeface(tfDialVenera);
		textNumSeven.setTypeface(tfDialVenera);
		textNumEight.setTypeface(tfDialVenera);
		textNumNine.setTypeface(tfDialVenera);
		
		dialNoZeroBtn = (Button)this.findViewById(R.id.no_zero);
		dialNoOneBtn = (Button)this.findViewById(R.id.no_one);
		dialNoTwoBtn = (Button)this.findViewById(R.id.no_two);
		dialNoThreeBtn = (Button)this.findViewById(R.id.no_three);
		dialNoFourBtn = (Button)this.findViewById(R.id.no_four);
		dialNoFiveBtn = (Button)this.findViewById(R.id.no_five);
		dialNoSixBtn = (Button)this.findViewById(R.id.no_six);
		dialNoSevenBtn = (Button)this.findViewById(R.id.no_seven);
		dialNoEightBtn = (Button)this.findViewById(R.id.no_eight);
		dialNoNineBtn = (Button)this.findViewById(R.id.no_nine);
		dialOutBtn = (Button)this.findViewById(R.id.phone_dial_out_btn);
		
		dialNoZeroBtn.setOnClickListener(this);
		dialNoOneBtn.setOnClickListener(this);
		dialNoTwoBtn.setOnClickListener(this);
		dialNoThreeBtn.setOnClickListener(this);
		dialNoFourBtn.setOnClickListener(this);
		dialNoFiveBtn.setOnClickListener(this);
		dialNoSixBtn.setOnClickListener(this);
		dialNoSevenBtn.setOnClickListener(this);
		dialNoEightBtn.setOnClickListener(this);
		dialNoNineBtn.setOnClickListener(this);
		dialOutBtn.setOnClickListener(this);
		//dialOutBtn = (Button)this.findViewById(R.id.)
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.no_zero:
			dialPhoneNum.append("0");
			break;
		case R.id.no_one:
			dialPhoneNum.append("1");
			break;
		case R.id.no_two:
			dialPhoneNum.append("2");
			break;
		case R.id.no_three:
			dialPhoneNum.append("3");
			break;
		case R.id.no_four:
			dialPhoneNum.append("4");
			break;
		case R.id.no_five:
			dialPhoneNum.append("5");
			break;
		case R.id.no_six:
			dialPhoneNum.append("6");
			break;
		case R.id.no_seven:
			dialPhoneNum.append("7");
			break;
		case R.id.no_eight:
			dialPhoneNum.append("8");
			break;
		case R.id.no_nine:
			dialPhoneNum.append("9");
			break;
		case R.id.phone_dial_out_btn:
			dialNoString = dialPhoneNum.getText().toString();
			if(dialNoString.length()!=0){
				Intent callingIntent = new Intent();
			
			callingIntent.putExtra("dialNoString", dialNoString);
			callingIntent.setClass(PhoneDialPanelActivity.this,PhoneCallingActivity.class);
			
			
            startActivity(callingIntent);
            finish();
			break;
			}
		
		}
		
	}

}
