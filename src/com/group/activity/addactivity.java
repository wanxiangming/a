package com.group.activity;

import com.example.g.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class addactivity extends Activity{
	Button applybut=null;
	EditText editText=null;
	private final String tag="MainActivitystop";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addactivity);
		applybut=(Button)findViewById(R.id.applybut);
		applybut.setOnClickListener(new applybutonclicklstener());
		editText=(EditText)findViewById(R.id.addedittext);
	}
	
	private class applybutonclicklstener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (editText.length()<1){
				
			}
			else{
				Intent intent=new Intent();
				intent.setClass(addactivity.this, MainActivity.class);
				Intent intent2=new Intent(tag);
				startActivity(intent);
				sendBroadcast(intent2);
				finish();
				}
		}
		
	}
}
