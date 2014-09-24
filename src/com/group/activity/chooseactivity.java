package com.group.activity;

import com.example.g.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class chooseactivity extends Activity{
	private Button but1;
	private final String tag="stop";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseacitivity);
		but1=(Button)findViewById(R.id.confirmbut);
		but1.setOnClickListener(new but1listener());
		IntentFilter intent2=new IntentFilter();
		intent2.addAction(tag);
		BroadcastReceiver chooseBR=new BroadcastReceiver(){
				@Override
				public void onReceive(Context arg0, Intent arg1) {
					// TODO Auto-generated method stub
					finish();
				}
		};
		registerReceiver(chooseBR, intent2);
		
	}
	private class but1listener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent but1intent=new Intent();
			but1intent.setClass(chooseactivity.this, editactivity.class);
			startActivity(but1intent);
		}
		
	}
	
}
