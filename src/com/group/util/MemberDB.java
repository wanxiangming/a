package com.group.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.YuvImage;

public class MemberDB {
	private DatabaseHelper databasehelper; 
	
	public MemberDB(Context context){
		databasehelper=new DatabaseHelper(context);
	}
	
	public void addmain(){
		SQLiteDatabase db=databasehelper.getWritableDatabase();
		db.execSQL("INSERT INTO memberinfo (g) VALUES('我的团队')");
	}
	
	public void addmember (String name,String g){
		SQLiteDatabase db=databasehelper.getWritableDatabase(); 
		db.execSQL("INSERT INTO memberinfo (Gname,g) VALUES ('"+name+"','"+g+"')");
		db.close();  
	}
	
	public void addg (String g){
		SQLiteDatabase db=databasehelper.getWritableDatabase(); 
		db.execSQL("INSERT INTO memberinfo (g) VALUES ('"+g+"')");
		db.close();
	}
	
	public void deletemember(int id){
		SQLiteDatabase db=databasehelper.getWritableDatabase();
		db.execSQL("DELETE FROM memberinfo WHERE ID="+id);
	}
	
	public void upmember(int id,String name,String g){
		SQLiteDatabase db=databasehelper.getWritableDatabase();
		db.execSQL("UPDATE memberinfo SET Gname='"+name+"',g='"+g+"' WHERE id="+id);
		db.close();
	}
	
	public void addGname(String Gname){
		SQLiteDatabase db=databasehelper.getWritableDatabase();
		db.execSQL("INSERT INTO memberinfo (Gname,g) VALUES ('"+Gname+"','我的团队')");
	}
}
