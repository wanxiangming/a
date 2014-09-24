package com.group.util;

import java.util.List;
import java.util.zip.Inflater;

import com.example.g.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MemberlistviewAdapter extends BaseAdapter{
	private MemberCursor memberCursor=null;
	private DatabaseHelper databaseHelper=null;
	private Context context=null;
	public List<String> memberlist=null;
	private LayoutInflater layoutInflater=null;
	
	public MemberlistviewAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		databaseHelper=new DatabaseHelper(context);
		memberCursor=new MemberCursor(databaseHelper.getReadableDatabase());
		memberlist=memberCursor.member();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return memberlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return memberlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if(convertView==null){
            layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view=layoutInflater.inflate(R.layout.memberlistadapter, null);
		}
		else{
			view=convertView;
		}
		TextView textView=(TextView)view.findViewById(R.id.memberlistviewadapter);
		textView.setText(memberlist.get(position));
		return view;
	}

}
