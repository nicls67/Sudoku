package com.ns.sudoku;

import android.util.Log;

/**
 * Created by Nicolas on 12/12/13.
 */
public class HelpList {

	private static final String TAG = "Sudoku_HelpList";
	private int[][][] helpList = new int[9][9][9];

	/*

	* Possible values for helpList :
	* 0 - Possible value and not set in display (white)
	* 1 - Possible value and set in display (green)
	* 2 - Impossible value (grey)

	*/

	public HelpList(){
		clear();
	}

	public void clear(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				for(int k=0;k<9;k++) helpList[i][j][k] = 0;
			}
		}
	}

	public void setPossible(int x, int y, int z, boolean b){
		if(b) helpList[x][y][z]=0;
		else helpList[x][y][z]=2;
	}

	public void setDisplay(int x, int y, int z, boolean b){
		if(b) helpList[x][y][z]=1;
		else helpList[x][y][z]=0;
	}

	public void resetBox(int x, int y){
		for(int i=0;i<9;i++) helpList[x][y][i]=0;
	}

	public int getState(int x, int y, int z){
		return helpList[x][y][z];
	}

	public int[] getState(int x, int y){
		return helpList[x][y];
	}

	public void setBoxImpossible(int x, int y){
		for(int i=0;i<9;i++) helpList[x][y][i]=2;
	}

	@Override
	public String toString(){
		String str="";

		for (int i=0;i<9;i++){
			for (int j=0;j<9;j++){
				for (int k=0;k<9;k++){
					str+=String.valueOf(helpList[i][j][k]);
				}
			}
		}

		return str;
	}

	public void fromString(String str){
		Log.d(TAG,"Restoring HelpList");
		Log.d(TAG,str);
		int index=0;
		for (int i=0;i<9;i++){
			for (int j=0;j<9;j++){
				for (int k=0;k<9;k++){
					helpList[i][j][k]=Integer.valueOf(str.substring(index,index+1));
					index++;
				}
			}
		}
	}



} //class HelpList
