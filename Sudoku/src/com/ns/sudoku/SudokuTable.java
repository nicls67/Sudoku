/**
 * 
 */
package com.ns.sudoku;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;


/**
 * Class representing a Sudoku table
 * @author Nicolas Simon
 */
public class SudokuTable {
	
	private static final String TAG = "SudokuDebug_SudokuTable";

	private int tab[][];
	private int tab_base[][];
	private int level =3;
	private int remaining=level;
	private SharedPreferences SP=null;
	private int[][] wBox = new int[3][2];
	
	/**
	 * Constructor
	 */
	public SudokuTable() {
		tab = new int[9][9];
		tab_base = new int[9][9];
	}
	
	public SudokuTable(Context context) {
		tab = new int[9][9];
		tab_base = new int[9][9];
		SP = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	/**
	 * Build the table, i.e. generate and fill numbers in boxes
	 */
	public void build(){
		remaining = level;
		
		Log.d(TAG,"Starting generation");
		do{
			//put all values to 0
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					tab[i][j]=0;
				}
			}
		}while(!rand_generate());
		
		delete();
		
		Log.d(TAG,"Generation completed");
		
		for(int i=0;i<9;i++) System.arraycopy(tab[i], 0, tab_base[i], 0, 9);
	}
	
	/**
	 * Returns a value of the table
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @return the value at the corresponding coordinates, 0 if the box is free
	 */
	public int getValue(int x, int y){
		return tab[x][y];
	}
	
	/**
	 * Generate a Sudoku table
	 */
	private boolean rand_generate(){
		int[] list = new int[9];
		int size;

		for (int i=0;i<9;i++){
			for (int j=0;j<9;j++){
				if (tab[i][j]==0)
				{
					size=0;

					for (int k=1;k<10;k++){
						if (checkPosition(i,j,k,true)){
							list[size]=k;
							size++;
						}
					}

					if (size==0) return false;

					int val = (int)(Math.random()*size);
					tab[i][j] = list[val];
				}

			}
		}


		return true;
	}
	
	/**
	 * Checks if the value val can be put at the given position
	 * @param x first coordinate
	 * @param y second coordinate
	 * @param val value to check
	 * @return true if it is possible to put the value at this position, false otherwise
	 */
	private boolean checkPosition(int x, int y, int val, boolean isBuild){
		//if (!isBuild)	Log.d(TAG, "Testing value "+String.valueOf(val)+" at position "+String.valueOf(x)+","+String.valueOf(y));

		boolean res = true;

		for(int i=0;i<3;i++){
			wBox[i][0]=10;
			wBox[i][1]=10;
		}

		if(val==0)
			return false;
		
		//check column
		for(int i=0;i<9;i++){
			if(tab[i][y]==val){
				if(isBuild) return false;
				else{
					wBox[0][0]=i;
					wBox[0][1]=y;
					res=false;
				}
			}
		}
		
		//check row
		for(int i=0;i<9;i++){
			if(tab[x][i]==val){
				if(isBuild)return false;
				else{
					wBox[1][0]=x;
					wBox[1][1]=i;
					res=false;
				}
			}
		}
		
		//check square, first find square bounds, then parse the square for checking
		int x_low = 0, x_high = 0, y_low = 0, y_high = 0;
		switch(x){
		case 0:
			x_low=0;
			x_high=2;
			break;
		case 1:
			x_low=0;
			x_high=2;
			break;
		case 2:
			x_low=0;
			x_high=2;
			break;
		case 3:
			x_low=3;
			x_high=5;
			break;
		case 4:
			x_low=3;
			x_high=5;
			break;
		case 5:
			x_low=3;
			x_high=5;
			break;
		case 6:
			x_low=6;
			x_high=8;
			break;
		case 7:
			x_low=6;
			x_high=8;
			break;
		case 8:
			x_low=6;
			x_high=8;
			break;
		}
		
		switch(y){
		case 0:
			y_low=0;
			y_high=2;
			break;
		case 1:
			y_low=0;
			y_high=2;
			break;
		case 2:
			y_low=0;
			y_high=2;
			break;
		case 3:
			y_low=3;
			y_high=5;
			break;
		case 4:
			y_low=3;
			y_high=5;
			break;
		case 5:
			y_low=3;
			y_high=5;
			break;
		case 6:
			y_low=6;
			y_high=8;
			break;
		case 7:
			y_low=6;
			y_high=8;
			break;
		case 8:
			y_low=6;
			y_high=8;
			break;
		}
		
		for(int i=x_low;i<=x_high;i++){
			for(int j=y_low;j<=y_high;j++){
				if(tab[i][j]==val){
					if(isBuild)return false;
					else{
						wBox[2][0]=i;
						wBox[2][1]=j;
						res=false;
					}
				}
			}
		}

		/*if (!isBuild){
			//Log.d(TAG,"size = "+wrongBoxList.size());
			for(int i = 0; i<3; i++){
				Log.d(TAG, String.valueOf(wBox[i][0]));
				Log.d(TAG, String.valueOf(wBox[i][1]));
			}
		}*/

		if(isBuild) return true;
		else return res;
	}
	
	/**
	 * Check if the given value can be put at the given position, if yes, set the value
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param val value to be set
	 * @return true if the value has been set, false otherwise
	 */
	public boolean setValue(int x, int y, int val){
		if(!checkPosition(x, y, val,false))
			return false;
		
		tab[x][y]=val;

		if(remaining!=0)
			remaining--;
		
		return true;
	}
	
	/**
	 * Set the difficulty of the game
	 * @param lvl difficulty (how many numbers will be deleted)
	 */
	public void setLevel(int lvl){
		level=lvl;
	}
	
	/**
	 * Delete random items in the table
	 */
	private void delete(){
		int lower = 0;
		int higher = 8;
		int randomX, randomY;
		
		randomX = (int)(Math.random()*(higher+1-lower))+lower;
		randomY = (int)(Math.random()*(higher+1-lower))+lower;
		
		for(int i=1;i<=level;i++){
			
			while(tab[randomX][randomY]==0){
				randomX = (int)(Math.random()*(higher+1-lower))+lower;
				randomY = (int)(Math.random()*(higher+1-lower))+lower;
			}
			
			tab[randomX][randomY]=0;
		}
		
		
	}

	/**
	 * Delete a value of the table and update the remaining free values number
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public void delete_value(int x,int y){
		tab[x][y]=0;
		remaining++;
	}

	/**
	 * Checks if there are any remaining free boxes
	 * @return true if there is no more free boxes remaining, false otherwise
	 */
	public boolean isEnd(){
		return remaining == 0;
	}

	/**
	 * Give the number of remaining free boxes
	 * @return Number of remaining free boxes
	 */
	public int getRemaining(){
		return remaining;
	}

	/**
	 * Reset the table with start values
	 */
	public void resetTable(){
		tab=tab_base;
		remaining=level;
	}
	
	/**
	 * Check if the box at the given coordiantes was free at the beginning
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @return true if the box is a base box, false otherwise
	 */
	public boolean isBase(int x, int y){
		return tab_base[x][y] != 0;
	}
	
	/**
	 * Complete the game automatically
	 */
	public void completeTable(){
		Log.i(TAG, "Completing table automatically");
		//backing up current table
		int backup[][] = new int[9][9];
		for(int i=0;i<9;i++) System.arraycopy(tab[i], 0, backup[i], 0, 9);
		
		//generate table
		while(!rand_generate()){
			//restore old table if generating has failed
			for(int i=0;i<9;i++) System.arraycopy(backup[i], 0, tab[i], 0, 9);
		}
		
		//clone to tab_base
		/*for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tab_base[i][j]=tab[i][j];
			}
		}*/
		
		remaining=0;
	}
	
	
	
	/**
	 * Save the current game in shared preferences
	 */
	public void save(){
		//Log.d(TAG, "Saving table");
		
		Set<String> set = new HashSet<String>();
		
		String s_tab = "";
		String s_tab_base = "0";
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				s_tab += String.valueOf(tab[i][j]);
				s_tab_base += String.valueOf(tab_base[i][j]);
			}
		}
		
		/*Log.d(TAG, "s_tab size = "+s_tab.length());
		Log.d(TAG, s_tab);
		Log.d(TAG, "s_tab_base size = "+s_tab_base.length());
		Log.d(TAG, s_tab_base);*/
		
		set.add(s_tab);
		set.add(s_tab_base);
		set.add(String.valueOf(level));
		
		SharedPreferences.Editor SPE = SP.edit();
		SPE.putStringSet("gameSaved", set).commit();
		
		Log.d(TAG, "Table saved successfully");
	}
	
	
	
	/**
	 * Load saved game from shared preferences
	 */
	public void load(){
		//Log.d(TAG, "Loading table");
		
		Set<String> set = SP.getStringSet("gameSaved",null);
		Iterator<String> iterator = set.iterator();
		
		String s_tab = null, s_tab_base = null, str;
		
		while(iterator.hasNext()){
			str = iterator.next();
			switch(str.length()){
			case 81:
				s_tab = str;
				break;
			case 82:
				s_tab_base = str;
				break;
			case 2:
				level = Integer.valueOf(str);
				break;
			}
		}
		
		/*Log.d(TAG, "set size : "+set.size());
		Log.d(TAG, "s_tab size = "+s_tab.length());
		Log.d(TAG, s_tab);
		Log.d(TAG, "s_tab_base size = "+s_tab_base.length());
		Log.d(TAG, s_tab);
		Log.d(TAG, "remaining : "+level);*/
		
		int pos=0;
		remaining=0;
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tab[i][j]=Integer.valueOf(s_tab.substring(pos, pos+1));
				if(tab[i][j]==0)
					remaining++;
				tab_base[i][j]=Integer.valueOf(s_tab_base.substring(pos+1, pos+2));
				pos++;
			}
		}
		
		Log.d(TAG, "Table loaded successfully");		
	}
	
	/**
	 * Get game level
	 * @return level
	 */
	public int getLevel(){
		return level;
	}


	/**
	 * Returns the list of all boxes containing the given wrong number
	 * @return the list
	 */
	public int[][] getWrongBoxList(){
		return wBox;
	}
	
}//class SudokuTable

