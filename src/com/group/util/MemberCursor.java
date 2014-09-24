package com.group.util;

import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemberCursor {
	public SQLiteDatabase db=null;
	public List<String> grouplist=null;
	public int i,j,t;
	public List<String> strings=null;
	public ArrayList<List<String>> memberlist=null;
	public List<String> memberlistforlist=null;
	public MemberDB memberDB;
	
	public MemberCursor(SQLiteDatabase db){
		this.db=db;
	}
	
	public List<String> findgroup(){
		grouplist=new ArrayList<String>();
		String sql="SELECT id,Gname,g FROM memberinfo GROUP BY g";
		Cursor cursor=db.rawQuery(sql, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			 grouplist.add(cursor.getString(2));
		}
		
		return grouplist;
	}
	
	public ArrayList<List<String>> findmember(){
		memberlist=new ArrayList<List<String>>();
		for(i=0;i<grouplist.size();i++){
			List<String> test=new ArrayList<String>();
			String sql="SELECT Gname FROM memberinfo where g = "+"'"+grouplist.get(i)+"'"; //此处可以直接用=来比较两个String类型的变量是因为此处使用的是SQL的语法 和java不同 在java中需要用equals语句来比较
			Cursor cursor=db.rawQuery(sql, null);
			for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
				if(cursor.getString(0)!=null){
				test.add(cursor.getString(0));
				}
			}
			memberlist.add(test);
		}
		db.close();
		return memberlist;
	}
	
	public List<String> member(){
		memberlistforlist=new ArrayList<String>();
		String sql="SELECT Gname FROM memberinfo ";
		Cursor cursor=db.rawQuery(sql, null);
		for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
			if(cursor.getString(0)!=null){
				memberlistforlist.add(cursor.getString(0));
			}
		}
		db.close();
		return memberlistforlist;
	}
	
	public int initmemberinfo(){
		String sql="SELECT g FROM memberinfo GROUP BY g";
		Cursor cursor=db.rawQuery(sql, null);
		if(cursor.getCount()>0){
			t=1;
		}
		else{
			t=0;
		}
		return t;
	}
	
}
