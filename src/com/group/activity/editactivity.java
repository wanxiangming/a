package com.group.activity;

import com.example.g.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class editactivity extends Activity{
	EditText edittext;
	Button but1;
	private final String tag1="stop";
	private final String tag2="MainActivitystop";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editacitvity);
		edittext=(EditText)findViewById(R.id.edittext);
		but1=(Button)findViewById(R.id.editbut);
		but1.setOnClickListener(new but1onclicklistener());
	}
	private class but1onclicklistener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(edittext.length()<1){
				
			}
			else{
				Intent intent1=new Intent();
				intent1.setClass(editactivity.this,MainActivity.class);
				Intent intent2=new Intent(tag1);
				Intent intent3=new Intent(tag2);
				sendBroadcast(intent2);
				sendBroadcast(intent3);
				startActivity(intent1);
				finish();
				}
		}
	}

}
