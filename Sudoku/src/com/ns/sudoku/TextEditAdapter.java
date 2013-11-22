package com.ns.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextEditAdapter extends BaseAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = "SudokuDebug_baseAdapter";
	
	private int tab[];
	private Context mContext;
	private SudokuTable sudoTable;
	
	
	public TextEditAdapter(Context c, SudokuTable sudoku) {
		tab = new int[81];
		mContext = c;
		sudoTable=sudoku;
		//Log.d(TAG, "Constructor done");
	}

	@Override
	public int getCount() {
		return tab.length;
	}

	@Override
	public Object getItem(int position) {
		return tab[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	/**
	 * Set the color of the text depending of the position
	 * @param text 
	 * @param position 
	 */
	private void setDefaultItemColor(TextView text, int position){
		int a = Color.GRAY;
		int b = Color.DKGRAY;
		int c = Color.LTGRAY;
		
		if(position<3)
			text.setTextColor(a);
		else if(position<6)
			text.setTextColor(b);
		else if(position<9)
			text.setTextColor(c);
		else if(position<12)
			text.setTextColor(a);
		else if(position<15)
			text.setTextColor(b);
		else if(position<18)
			text.setTextColor(c);
		else if(position<21)
			text.setTextColor(a);
		else if(position<24)
			text.setTextColor(b);
		else if(position<27)
			text.setTextColor(c);
		
		else if(position<30)
			text.setTextColor(c);
		else if(position<33)
			text.setTextColor(a);
		else if(position<36)
			text.setTextColor(b);
		else if(position<39)
			text.setTextColor(c);
		else if(position<42)
			text.setTextColor(a);
		else if(position<45)
			text.setTextColor(b);
		else if(position<48)
			text.setTextColor(c);
		else if(position<51)
			text.setTextColor(a);
		else if(position<54)
			text.setTextColor(b);
		
		else if(position<57)
			text.setTextColor(b);
		else if(position<60)
			text.setTextColor(c);
		else if(position<63)
			text.setTextColor(a);
		else if(position<66)
			text.setTextColor(b);
		else if(position<69)
			text.setTextColor(c);
		else if(position<72)
			text.setTextColor(a);
		else if(position<75)
			text.setTextColor(b);
		else if(position<78)
			text.setTextColor(c);
		else
			text.setTextColor(a);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = new TextView(mContext);
		
		//update();
		
		//get x-y coordiantes
		int x=(int)position/9;
		int y=position%9;
		
		if(sudoTable.isBase(x, y)==false){
			text.setText("-");
			text.setTypeface(null, Typeface.ITALIC);
		}
		else{
			text.setText(String.valueOf(tab[position]));
			text.setTypeface(null, Typeface.BOLD);
		}
		
		//text.setBackgroundColor(Color.YELLOW);
		
		setDefaultItemColor(text,position);
		
		return text;
	}
	
	/**
	 * Set the item at position pos with the value val 
	 * @param pos Position in the adapter
	 * @param val value to set 
	 */
	public void setValue(int pos, int val){
		tab[pos]=val;
	}

	/**
	 * update values of adapter with the values of sudoku table
	 */
	public void update(){
		//fill adapter with values
		int k=0;
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tab[k]=sudoTable.getValue(i, j);
				k++;
			}
		}
	}
	
}//class TextEditAdapter
