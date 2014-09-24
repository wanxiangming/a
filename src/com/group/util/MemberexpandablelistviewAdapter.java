package com.group.util;

import java.util.ArrayList;
import java.util.List;

import com.example.g.R;
import com.group.activity.MainActivity;
import com.group.activity.manageractivity;

import android.R.string;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MemberexpandablelistviewAdapter extends BaseExpandableListAdapter {
	private Context context=null;
	public List<String> findgroup=null;
	public ArrayList<List<String>> findmember=null;
	private MemberCursor memberCursor=null;
	private DatabaseHelper databaseHelper=null;
	
	public MemberexpandablelistviewAdapter(Context context){
		this.context=context;
		databaseHelper=new DatabaseHelper(context);
		memberCursor=new MemberCursor(databaseHelper.getReadableDatabase());
		findgroup=new ArrayList<String>();
		findgroup=memberCursor.findgroup();
		findmember=new ArrayList<List<String>>();
		findmember=memberCursor.findmember();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return findmember.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandablelistadapterchildadapter, null);
        }
		else{
			view=convertView;
		}
		TextView textView=(TextView)view.findViewById(R.id.EXchild);
		textView.setText(findmember.get(groupPosition).get(childPosition));
		return textView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return findmember.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return findgroup.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return findgroup.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.expandablelistadaptergroupadapter, null);
        }
		else{
			view=convertView;
		}
		TextView textView=(TextView)view.findViewById(R.id.EXPparent);
		textView.setText(findgroup.get(groupPosition));
		return view;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
