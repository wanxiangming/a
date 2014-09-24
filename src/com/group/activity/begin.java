package com.group.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.example.g.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class begin extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beginactivity);
		final Intent it = new Intent(this, MainActivity.class); //你要转向的Activity
		  Timer timer = new Timer();
		  TimerTask task = new TimerTask() {
		   @Override
		   public void run() {
		    startActivity(it);
		    finish();
		   }
		  };
		timer.schedule(task, 1000 * 2);
	}
	
	

}
