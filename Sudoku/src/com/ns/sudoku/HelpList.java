package com.ns.sudoku;

/**
 * Created by Nicolas on 12/12/13.
 */
public class HelpList {

	private int[][][] helpList = new int[9][9][9];

	/*

	* Possible values for helpList :
	* 0 - Possible value and not set in display
	* 1 - Possible value and set in display
	* 2 - Impossible value

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







} //class HelpList
