package com.group.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.g.R;

import android.app.ActivityManager.RecentTaskInfo;
import android.content.Context;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Memberlistview extends ListView implements OnScrollListener{
	private final int release_to_refresh=0;     //�ɿ�ˢ�µ����
	private final int Pull_to_refresh=1;        //����ˢ�µ����
	private final int Refreshing=2;             //����ˢ�µ����
	private final int Done=3;                   //ʲô������
	private final int Loading=4;
	private final int Ratio=3;
	private LayoutInflater layoutInflater=null;
	private LinearLayout headview=null;
	private TextView headrecenttext=null;
	private TextView headpullrefreshtext=null;
	private ImageView headarrowview=null;
	private RotateAnimation animation=null;
	private RotateAnimation reverseAnimation=null;
	private int startY;    //��ִ���µ��Ǹ����������
	private int firstitmeindex;
	private int headcontentwidth;
	private int headcontentheigh;
	private int state;
	private boolean isrefreshable;   //�Ƿ��ˢ��
	private boolean isrecored;  //�����ж�startY��λ���Ƿ񱻼�¼�� ����¼�� ��Ϊtrue û�б���¼����Ϊfalse
	private boolean isback;     //�����ж��Ƿ��ڻ��������������ϻ��������
	private OnRefreshListener refreshListener;

	public Memberlistview(Context context) {
		super(context);
		init(context);
		// TODO Auto-generated constructor stub
	}
	

	public Memberlistview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		// TODO Auto-generated constructor stub
	}


	public Memberlistview(Context context, AttributeSet attrs) {
		super(context, attrs);		
		init(context);
		// TODO Auto-generated constructor stub
	}
	
	private void init(Context context){
		layoutInflater=LayoutInflater.from(context);
		headview=(LinearLayout)layoutInflater.inflate(R.layout.pullrefreshhead, null);
		headarrowview=(ImageView)headview.findViewById(R.id.headarrowview);
		headarrowview.setMinimumWidth(70);
		headarrowview.setMinimumHeight(50);
		headrecenttext=(TextView)headview.findViewById(R.id.headrecenttext);
		headpullrefreshtext=(TextView)headview.findViewById(R.id.headpullrefreshtext);
		measureView(headview);
		headcontentheigh=headview.getMeasuredHeight();
		headcontentwidth=headview.getMeasuredWidth();
		headview.setPadding(0, -1*headcontentheigh, 0, 0);
		headview.invalidate();
		addHeaderView(headview, null, false);
		setOnScrollListener(this);
		animation=new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);
		reverseAnimation=new RotateAnimation(-180, 0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);
		state=Done;
		isrefreshable=false;
	}


	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		firstitmeindex=arg1;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onTouchEvent(MotionEvent Event){
		if(isrefreshable){
			switch(Event.getAction()){
			case MotionEvent.ACTION_DOWN:
				if (firstitmeindex==0 && !isrecored){
					isrecored=true;
					startY=(int)Event.getY();
				}
				break;
			
			case MotionEvent.ACTION_UP:
				if(state!=Refreshing && state!=Loading){
					if(state==Done){
						
					}
					if(state==Pull_to_refresh){
						state=Done;
						changeHeaderViewBystate();
					}
					if(state==release_to_refresh){
						state=Refreshing;
						changeHeaderViewBystate();
						onRefresh();
					}
				}
				isrecored=false;
				isback=false;
				break;
				
			case MotionEvent.ACTION_MOVE:
				int tempY=(int)Event.getY();
				if(!isrecored && firstitmeindex==0){
					isrecored=true;
					startY=tempY;
				}
				if(state!=Refreshing && isrecored && state!=Loading){
					if(state==release_to_refresh){
						setSelection(0);       //��setselectionΪ0ʱ ��������ָ�������item
						if(((tempY-startY)/Ratio<headcontentheigh) && (tempY-startY)>0){
							state=Pull_to_refresh;
							isback=true;
							changeHeaderViewBystate();
						}
						else if(tempY-startY<=0){
							state=Done;
							changeHeaderViewBystate();
						}
						else{
							
						}
					}
					if(state==Pull_to_refresh){
						setSelection(0);
						if((tempY-startY)/Ratio>=headcontentheigh){
							state=release_to_refresh;
							changeHeaderViewBystate();
						}
						else if(tempY-startY<=0){
							state=Done;
							changeHeaderViewBystate();
						}
					}
					if(state==Done){
						if(tempY-startY>0){
							state=Pull_to_refresh;
							changeHeaderViewBystate();
						}
					}
					if(state==Pull_to_refresh){
						headview.setPadding(0, -1*headcontentheigh+(tempY-startY)/Ratio, 0, 0);
					}
					if(state==release_to_refresh){
						headview.setPadding(0, (tempY-startY)/Ratio-headcontentheigh, 0, 0);
					}
				}
				break;
			}
		}
		return super.onTouchEvent(Event);
	}
	
	private void setAdapter(BaseAdapter adapter){
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy��MM��DD��  HH:mm");
//		String date=dateFormat.format(new Date());
//		headrecenttext.setText("�ϴθ��£�"+date);
		super.setAdapter(adapter);
	}
	
	private void measureView(View v){
		ViewGroup.LayoutParams layoutParams=v.getLayoutParams();
		if(layoutParams==null){
			layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childwidthspec=ViewGroup.getChildMeasureSpec(0, 0+0, layoutParams.width);
		int lphright=layoutParams.height;
		int childheightspec;
		if(lphright>0){
			childheightspec=MeasureSpec.makeMeasureSpec(lphright, MeasureSpec.EXACTLY);
		}
		else{
			childheightspec=MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		v.measure(childwidthspec, childheightspec);
	}
	
	private void changeHeaderViewBystate(){
		switch(state){
		case release_to_refresh:                                          //��ͷ����  ��ʾ����Ϊ�ɿ�ˢ�µ����
			headarrowview.setVisibility(View.VISIBLE);
			headpullrefreshtext.setVisibility(View.VISIBLE);
			headrecenttext.setVisibility(View.VISIBLE);
			headarrowview.clearAnimation();
			headarrowview.startAnimation(animation);
			headpullrefreshtext.setText("�ɿ�ˢ��");
			break;
		case Pull_to_refresh:                                           //��ͷ���� ��ʾ����Ϊ����ˢ�µ����
			headpullrefreshtext.setVisibility(View.VISIBLE);
			headrecenttext.setVisibility(View.VISIBLE);
			headarrowview.clearAnimation();
			headarrowview.setVisibility(View.VISIBLE);
			if(isback){
				isback=false;
				headarrowview.clearAnimation();
				headarrowview.startAnimation(reverseAnimation);
				headpullrefreshtext.setText("����ˢ��");
			}
			else{
				headpullrefreshtext.setText("����ˢ��");
			}
			break;
		case Refreshing:                                               //��ͷ��ʧ ��ʾ������ʾ����ˢ�µ����
			headview.setPadding(0, 1, 0, 0);
			headarrowview.clearAnimation();
			headarrowview.setVisibility(View.INVISIBLE);
			headpullrefreshtext.setText("����ˢ�¡�");
			headpullrefreshtext.setVisibility(View.VISIBLE);
			headrecenttext.setVisibility(View.VISIBLE);
			break;
		case Loading:
		case Done:                                                   //ʲô��û�е����
			headview.setPadding(0, 0, 0, 0);
			headarrowview.clearAnimation();
			headarrowview.setVisibility(View.GONE);
			headpullrefreshtext.setVisibility(View.GONE);
			headrecenttext.setVisibility(View.GONE);
		}
	}
	
	private void onRefresh(){
		if(refreshListener!=null){
			refreshListener.onRefresh();
		}
	}
	
	public interface OnRefreshListener{
		public void onRefresh();
	}
	
	public void setOnRefreshListener(OnRefreshListener refreshListener){
		this.refreshListener=refreshListener;
		isrefreshable=true;
	}
	
	public void onRefreshComplete(){
		state=Done;
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH��mm");
		String date=dateFormat.format(new Date());
		headrecenttext.setText("�ϴθ��£�"+date);
		changeHeaderViewBystate();
	}

}
