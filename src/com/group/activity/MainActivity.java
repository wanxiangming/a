package com.group.activity;


import java.util.ArrayList;
import java.util.List;

import com.example.g.R;
import com.group.util.DatabaseHelper;
import com.group.util.MemberDB;
import com.group.util.MemberexpandablelistviewAdapter;
import com.group.util.MemberCursor;
import com.group.util.Memberexpandablelistview;
import com.group.util.Memberlistview;
import com.group.util.Memberexpandablelistview.OnExpandablelistviewRefreshListener;
import com.group.util.Memberlistview.OnRefreshListener;
import com.group.util.MemberlistviewAdapter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TabHost tab= null;
	private Button sendbut=null;;
	private Button addbut=null;
	private Button creatbut=null;
	private Button managerbut=null;
	private Button but1=null;
	private Button but3=null;
	private final String tag1="MainActivitystop";
	private MemberexpandablelistviewAdapter expandablelistviewadapter=null;
	public List<String> findgroup=null;
	public ArrayList<List<String>> findmember=null;
	private View popview=null;
	private PopupWindow popupWindow=null;
	private List<String> memberlist=null;
	private Memberlistview memberlistview=null;
	private MemberCursor memberCursor=null;
	private DatabaseHelper databaseHelper=null;
	private MemberlistviewAdapter memberlistviewAdapter=null;
	public MemberlistviewAdapter adapter=null;
	private Memberexpandablelistview memberexpandablelistview=null;
	private MemberDB memberDB=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tab = (TabHost) findViewById(R.id.tabhost);             
        tab.setup();
        sendbut=(Button)findViewById(R.id.sendbut);                  
        addbut=(Button)findViewById(R.id.addbut);           
        managerbut=(Button)findViewById(R.id.managerbut);       
        creatbut=(Button)findViewById(R.id.creatbut);  
        init();
        
        
        memberlistview=(Memberlistview)findViewById(R.id.infolist);
        memberlistviewAdapter=new MemberlistviewAdapter(this);
        memberlistview.setAdapter(memberlistviewAdapter);
        memberlistview.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				MemberlistviewrefreshTask Task=new MemberlistviewrefreshTask();
				Task.execute(1000);
			}
		});
        memberlistview.setDivider(getResources().getDrawable(R.drawable.exbandablelistviewbottom));
        memberlistview.setDividerHeight(5);
        
        
//        databaseHelper1=new DatabaseHelper(this);
//		memberCursor1=new MemberCursor(databaseHelper1.getReadableDatabase());
//		memberlistview=(ListView)findViewById(R.id.infolist);
//		memberlist=memberCursor1.member();
//        memberlistview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1, memberlist));
//        
        memberexpandablelistview=new Memberexpandablelistview(this);
        memberexpandablelistview=(Memberexpandablelistview)findViewById(R.id.testEX); 
        memberexpandablelistview.setDivider(getResources().getDrawable(R.drawable.exbandablelistviewbottom));
        memberexpandablelistview.setChildDivider(getResources().getDrawable(R.drawable.exbandablelistviewbottom));
        memberexpandablelistview.setDividerHeight(5);
        memberexpandablelistview.setDrawingCacheBackgroundColor(Color.BLUE);
        expandablelistviewadapter=new MemberexpandablelistviewAdapter(this); 
        memberexpandablelistview.setAdapter(expandablelistviewadapter);
        memberexpandablelistview.setOnChildClickListener(new expandablechildlistener());
        memberexpandablelistview.setOnRefreshListener(new OnExpandablelistviewRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				MemberexpandablelistviewrefreshTask EXTask=new MemberexpandablelistviewrefreshTask();
				EXTask.execute(1000);
			}
		});
		
		
		
        managerbut.setOnClickListener(new managerbutonclicklistener());
        sendbut.setOnClickListener(new sendonclicklistener());
        addbut.setOnClickListener(new addonclicklistener());
        creatbut.setOnClickListener(new creatonclicklistener());
        
        
        
        IntentFilter intentFilter1=new IntentFilter();
        intentFilter1.addAction(tag1);
        BroadcastReceiver broadcastReceiver=new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				finish();
			}
        };
        registerReceiver(broadcastReceiver, intentFilter1);
        
        View view1=this.getLayoutInflater().inflate(R.layout.tabviewinfo, null);
        TextView tv1=(TextView)view1.findViewById(R.id.tv);
        tv1.setText("消息");
        View view2=this.getLayoutInflater().inflate(R.layout.tabviewmain,null);
        TextView tv2=(TextView)view2.findViewById(R.id.tv);
        tv2.setText("团队");
        View view3=this.getLayoutInflater().inflate(R.layout.tabviewme,null);
        TextView tv3=(TextView)view3.findViewById(R.id.tv);
        tv3.setText("自己");
        
        TabSpec tabSpec1=tab.newTabSpec("tab1");
        tabSpec1.setIndicator(view1);
        tabSpec1.setContent(R.id.info);
        tab.addTab(tabSpec1);
        
        TabSpec tabSpec2=tab.newTabSpec("tab2");
        tabSpec2.setIndicator(view2);
        tabSpec2.setContent(R.id.main);
        tab.addTab(tabSpec2);
        
        TabSpec tabSpec3=tab.newTabSpec("tab3");
        tabSpec3.setIndicator(view3);
        tabSpec3.setContent(R.id.me);
        tab.addTab(tabSpec3);
    }
    
//-------------------------------------------------------------------------------------------

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}



	//-------------------------------------------------------------------------------    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			popupWindow = null;
			return onTouchEvent(event);
			}
		else {
			return false;
		}
	
	}  
    
    
    private class sendonclicklistener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int hight=MainActivity.this.getWindowManager().getDefaultDisplay().getHeight(); 
		LayoutInflater layoutInflater=LayoutInflater.from(MainActivity.this);
		popview=layoutInflater.inflate(R.layout.sendactivity, null);
		popupWindow=new PopupWindow(popview,ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT,true);
		but1=(Button)popview.findViewById(R.id.sendnotificationbut);
		but3=(Button)popview.findViewById(R.id.sendgroupmissionbut);
		but1.setOnClickListener(new sendlistener());
		but3.setOnClickListener(new sendlistener());
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAsDropDown(popview,0,0);
		
	}
	
	
	private class sendlistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent but1intent=new Intent();
			but1intent.setClass(MainActivity.this, chooseactivity.class);
			startActivity(but1intent);
			popupWindow.dismiss();
		}
	}
	
//		Intent sendbutintent=new Intent();
//		sendbutintent.setClass(MainActivity.this, sendactivity.class);
//		startActivity(sendbutintent);
	}
		
    
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, 1, 1, R.string.exit);
		menu.findItem(R.id.action_settings);
		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==1){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
    
    private class addonclicklistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent addbutintent=new Intent();
			addbutintent.setClass(MainActivity.this, addactivity.class);
			startActivity(addbutintent);
		}
    	
    }
    
    private class creatonclicklistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, creatactivity.class);
			startActivity(intent);
		}
    	
    }
    private class managerbutonclicklistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			intent.setClass(MainActivity.this, manageractivity.class);
			startActivity(intent);
		}
    	
    }
    private class expandablechildlistener implements OnChildClickListener{

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			// TODO Auto-generated method stub
			long a=id;
			int b=groupPosition;
			int c=childPosition;
			String string=new String();
			string=expandablelistviewadapter.findmember.get(groupPosition).get(childPosition).toString();
			Toast.makeText(MainActivity.this, a+"id"+b+"G"+c+"C"+"  "+string, Toast.LENGTH_LONG).show();
			return true;
		}
    	
    }
    
    public class MemberlistviewrefreshTask extends AsyncTask<Integer, Integer,String>{

		@Override
		protected String doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			try{
				Thread.sleep(params[0]);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			memberlistviewAdapter=new MemberlistviewAdapter(MainActivity.this);
			memberlistview.setAdapter(memberlistviewAdapter);
			memberlistviewAdapter.notifyDataSetChanged();
			memberlistview.onRefreshComplete();
		}
    }
		
		public class MemberexpandablelistviewrefreshTask extends AsyncTask<Integer, Integer,String>{
			
			@Override
			protected String doInBackground(Integer... params) {
				// TODO Auto-generated method stub
				try{
					Thread.sleep(params[0]);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				expandablelistviewadapter=new MemberexpandablelistviewAdapter(MainActivity.this);
				memberexpandablelistview.setAdapter(expandablelistviewadapter);
				expandablelistviewadapter.notifyDataSetChanged();
				memberexpandablelistview.onRefreshComplete();
			}
		}
		
		private void init(){
			databaseHelper=new DatabaseHelper(this);
	        memberCursor=new MemberCursor(databaseHelper.getWritableDatabase());
	        int t=memberCursor.initmemberinfo();
	        if(t==1){
	        	
	        }
	        else{
	        	memberDB=new MemberDB(this);
	        	memberDB.addmain();
	        }
		}
		
}
