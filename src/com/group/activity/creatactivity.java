package com.group.activity;

import com.example.g.R;
import com.group.util.DatabaseHelper;
import com.group.util.MemberDB;

import android.app.Activity;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class creatactivity extends Activity{
	Button creatbut=null;
	EditText editText=null;
	private MemberDB memberDB;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.creatactivity);
		creatbut=(Button)findViewById(R.id.creatbut);
		editText=(EditText)findViewById(R.id.createdittext);
		creatbut.setOnClickListener(new creatbutclicklistener());
		memberDB=new MemberDB(this);
	}
	private class creatbutclicklistener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String string=editText.getText().toString();
			if(string.isEmpty()){
				
			}
			else{
				memberDB.addGname(string);
				finish();
			}
		}
	}
}
