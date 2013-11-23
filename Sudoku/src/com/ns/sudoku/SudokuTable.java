/**
 * 
 */
package com.ns.sudoku;

import android.util.Log;

/**
 * Class representing a Sudoku table
 * @author Nicolas Simon
 */
public class SudokuTable {
	
	private static final String TAG = "SudokuDebug_SudokuTable";

	private int tab[][];
	private int tab_base[][];
	private int level =3;
	private int remaining=level;;
	
	/**
	 * Constructor
	 */
	public SudokuTable() {
		tab = new int[9][9];
		tab_base = new int[9][9];
	}
	
	/**
	 * Build the table, i.e. generate and fill numbers in boxes
	 */
	public void build(){
		remaining = level;
		
		Log.d(TAG,"Starting generation");
		do{
			//put all values to 10
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					tab[i][j]=10;
				}
			}
		}while(rand_generate()==false);
		
		delete();
		
		Log.d(TAG,"Generation completed");
		
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tab_base[i][j]=tab[i][j];
			}
		}
	}
	
	/**
	 * Returns a value of the table
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @return the value at the corresponding coordinates
	 */
	public int getValue(int x, int y){
		return tab[x][y];
	}
	
	/**
	 * Generate a Sudoku table
	 */
	private boolean rand_generate(){
		for(int i=0;i<9;i++){
			
			int lower = 1;
			int higher = 9;
			int random=10;
			int count = 0;
			
			for(int j=0;j<9;j++){
				if(tab[i][j]==10){
					//Log.d(TAG, "generating random for "+String.valueOf(i)+","+String.valueOf(j));
					
					//find a number between 1 and 9
					while(checkPosition(i,j,random)==false){
						
						random = (int)(Math.random()*(higher+1-lower))+lower;
						count++;
						
						if(count>100)
							return false;
					}
					
					tab[i][j]=random;
					count=0;
					if(random==higher)
						higher--;
					else if(random==lower)
						lower++;
				
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
	private boolean checkPosition(int x, int y, int val){
		//Log.d(TAG, "Testing value "+String.valueOf(val)+" at position "+String.valueOf(x)+","+String.valueOf(y));
		boolean res = true;
		
		//check line
		for(int i=0;i<9;i++){
			if(tab[i][y]==val)
				res=false;
		}
		
		//check column
		for(int i=0;i<9;i++){
			if(tab[x][i]==val)
				res=false;
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
				if(tab[i][j]==val)
					res=false;
			}
		}
		
		
		return res;
	}
	
	/**
	 * Check if the given value can be put at the given position, if yes, set the value
	 * @brief Set a value in the table
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 * @param val value to be set
	 * @return true if the value has beed set, false otherwise
	 */
	public boolean setValue(int x, int y, int val){
		if(checkPosition(x,y,val)==false)
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
			
			while(tab[randomX][randomY]==10){
				randomX = (int)(Math.random()*(higher+1-lower))+lower;
				randomY = (int)(Math.random()*(higher+1-lower))+lower;
			}
			
			tab[randomX][randomY]=10;
		}
		
		
	}

	/**
	 * Delete a value of the table and update the remaining free values number
	 * @param x horizontal coordinate
	 * @param y vertical coordinate
	 */
	public void delete_value(int x,int y){
		tab[x][y]=10;
		remaining++;
	}

	/**
	 * Checks if there are any remaining free boxes
	 * @return true if there is no more free boxes remaining, false otherwise
	 */
	public boolean isEnd(){
		if(remaining==0)
			return true;
		else
			return false;
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
		if(tab_base[x][y]==10)
			return false;
		else
			return true;
	}
	
	/**
	 * Complete the game automatically
	 */
	public void completeTable(){
		Log.i(TAG, "Completing table automatically");
		//backing up current table
		int backup[][] = new int[9][9];
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				backup[i][j]=tab[i][j];
			}
		}
		
		//generate table
		while(rand_generate()==false){
			//restore old table if generating has failed
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					tab[i][j]=backup[i][j];
				}
			}
		}
		
		//clone to tab_base
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				tab_base[i][j]=tab[i][j];
			}
		}
		
		remaining=0;
	}
	
}//class SudokuTable

