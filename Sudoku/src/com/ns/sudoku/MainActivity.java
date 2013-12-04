package com.ns.sudoku;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	
	private static final String TAG = "SudokuDebug_MainActivity";
	
	private SharedPreferences SP;
	private SudokuTable table=null;
	private int itemSelectedX = 9, itemSelectedY = 9; // 0 to 8, 9 means nothing is selected
	private Button b1=null, b2=null, b3=null, b4=null, b5=null, b6=null, b7=null, b8=null, b9=null, br=null, bd=null;
	private TextView text_remaining=null;
	private BorderedTextView[][] textTab;
	private int[] difficultyValue;
	private String difficulty="EASY";
	private boolean isErrorColored = false;
	
	//for border creation
	private static final int BORDER_TOP = 0x00000001;
	private static final int BORDER_RIGHT = 0x00000002;
	private static final int BORDER_BOTTOM = 0x00000004;
	private static final int BORDER_LEFT = 0x00000008;
    private Border[] borders = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
				
		//creating textview tab
		Log.d(TAG,"Retreiving textView");
		textTab = new BorderedTextView[9][9];
		textTab[0][0] = (BorderedTextView) findViewById(R.id.textView1);
		textTab[0][1] = (BorderedTextView) findViewById(R.id.textView2);
		textTab[0][2] = (BorderedTextView) findViewById(R.id.textView3);
		textTab[0][3] = (BorderedTextView) findViewById(R.id.textView4);
		textTab[0][4] = (BorderedTextView) findViewById(R.id.textView5);
		textTab[0][5] = (BorderedTextView) findViewById(R.id.textView6);
		textTab[0][6] = (BorderedTextView) findViewById(R.id.textView7);
		textTab[0][7] = (BorderedTextView) findViewById(R.id.textView8);
		textTab[0][8] = (BorderedTextView) findViewById(R.id.textView9);
		
		textTab[1][0] = (BorderedTextView) findViewById(R.id.textView21);
		textTab[1][1] = (BorderedTextView) findViewById(R.id.textView22);
		textTab[1][2] = (BorderedTextView) findViewById(R.id.textView23);
		textTab[1][3] = (BorderedTextView) findViewById(R.id.textView24);
		textTab[1][4] = (BorderedTextView) findViewById(R.id.textView25);
		textTab[1][5] = (BorderedTextView) findViewById(R.id.textView26);
		textTab[1][6] = (BorderedTextView) findViewById(R.id.textView27);
		textTab[1][7] = (BorderedTextView) findViewById(R.id.textView28);
		textTab[1][8] = (BorderedTextView) findViewById(R.id.textView29);
		
		textTab[2][0] = (BorderedTextView) findViewById(R.id.textView31);
		textTab[2][1] = (BorderedTextView) findViewById(R.id.textView32);
		textTab[2][2] = (BorderedTextView) findViewById(R.id.textView33);
		textTab[2][3] = (BorderedTextView) findViewById(R.id.textView34);
		textTab[2][4] = (BorderedTextView) findViewById(R.id.textView35);
		textTab[2][5] = (BorderedTextView) findViewById(R.id.textView36);
		textTab[2][6] = (BorderedTextView) findViewById(R.id.textView37);
		textTab[2][7] = (BorderedTextView) findViewById(R.id.textView38);
		textTab[2][8] = (BorderedTextView) findViewById(R.id.textView39);
		
		textTab[3][0] = (BorderedTextView) findViewById(R.id.textView41);
		textTab[3][1] = (BorderedTextView) findViewById(R.id.textView42);
		textTab[3][2] = (BorderedTextView) findViewById(R.id.textView43);
		textTab[3][3] = (BorderedTextView) findViewById(R.id.textView44);
		textTab[3][4] = (BorderedTextView) findViewById(R.id.textView45);
		textTab[3][5] = (BorderedTextView) findViewById(R.id.textView46);
		textTab[3][6] = (BorderedTextView) findViewById(R.id.textView47);
		textTab[3][7] = (BorderedTextView) findViewById(R.id.textView48);
		textTab[3][8] = (BorderedTextView) findViewById(R.id.textView49);
		
		textTab[4][0] = (BorderedTextView) findViewById(R.id.textView51);
		textTab[4][1] = (BorderedTextView) findViewById(R.id.textView52);
		textTab[4][2] = (BorderedTextView) findViewById(R.id.textView53);
		textTab[4][3] = (BorderedTextView) findViewById(R.id.textView54);
		textTab[4][4] = (BorderedTextView) findViewById(R.id.textView55);
		textTab[4][5] = (BorderedTextView) findViewById(R.id.textView56);
		textTab[4][6] = (BorderedTextView) findViewById(R.id.textView57);
		textTab[4][7] = (BorderedTextView) findViewById(R.id.textView58);
		textTab[4][8] = (BorderedTextView) findViewById(R.id.textView59);
		
		textTab[5][0] = (BorderedTextView) findViewById(R.id.textView61);
		textTab[5][1] = (BorderedTextView) findViewById(R.id.textView62);
		textTab[5][2] = (BorderedTextView) findViewById(R.id.textView63);
		textTab[5][3] = (BorderedTextView) findViewById(R.id.textView64);
		textTab[5][4] = (BorderedTextView) findViewById(R.id.textView65);
		textTab[5][5] = (BorderedTextView) findViewById(R.id.textView66);
		textTab[5][6] = (BorderedTextView) findViewById(R.id.textView67);
		textTab[5][7] = (BorderedTextView) findViewById(R.id.textView68);
		textTab[5][8] = (BorderedTextView) findViewById(R.id.textView69);
		
		textTab[6][0] = (BorderedTextView) findViewById(R.id.textView71);
		textTab[6][1] = (BorderedTextView) findViewById(R.id.textView72);
		textTab[6][2] = (BorderedTextView) findViewById(R.id.textView73);
		textTab[6][3] = (BorderedTextView) findViewById(R.id.textView74);
		textTab[6][4] = (BorderedTextView) findViewById(R.id.textView75);
		textTab[6][5] = (BorderedTextView) findViewById(R.id.textView76);
		textTab[6][6] = (BorderedTextView) findViewById(R.id.textView77);
		textTab[6][7] = (BorderedTextView) findViewById(R.id.textView78);
		textTab[6][8] = (BorderedTextView) findViewById(R.id.textView79);
		
		textTab[7][0] = (BorderedTextView) findViewById(R.id.textView81);
		textTab[7][1] = (BorderedTextView) findViewById(R.id.textView82);
		textTab[7][2] = (BorderedTextView) findViewById(R.id.textView83);
		textTab[7][3] = (BorderedTextView) findViewById(R.id.textView84);
		textTab[7][4] = (BorderedTextView) findViewById(R.id.textView85);
		textTab[7][5] = (BorderedTextView) findViewById(R.id.textView86);
		textTab[7][6] = (BorderedTextView) findViewById(R.id.textView87);
		textTab[7][7] = (BorderedTextView) findViewById(R.id.textView88);
		textTab[7][8] = (BorderedTextView) findViewById(R.id.textView89);
		
		textTab[8][0] = (BorderedTextView) findViewById(R.id.textView91);
		textTab[8][1] = (BorderedTextView) findViewById(R.id.textView92);
		textTab[8][2] = (BorderedTextView) findViewById(R.id.textView93);
		textTab[8][3] = (BorderedTextView) findViewById(R.id.textView94);
		textTab[8][4] = (BorderedTextView) findViewById(R.id.textView95);
		textTab[8][5] = (BorderedTextView) findViewById(R.id.textView96);
		textTab[8][6] = (BorderedTextView) findViewById(R.id.textView97);
		textTab[8][7] = (BorderedTextView) findViewById(R.id.textView98);
		textTab[8][8] = (BorderedTextView) findViewById(R.id.textView99);
		
		setDefaultBorders();
		
		//Log.d(TAG,"All textViews have been retreived");
		
		//assign buttons
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		b3 = (Button) findViewById(R.id.button3);
		b4 = (Button) findViewById(R.id.button4);
		b5 = (Button) findViewById(R.id.button5);
		b6 = (Button) findViewById(R.id.button6);
		b7 = (Button) findViewById(R.id.button7);
		b8 = (Button) findViewById(R.id.button8);
		b9 = (Button) findViewById(R.id.button9);
		br = (Button) findViewById(R.id.buttonR);
		bd = (Button) findViewById(R.id.buttonD);
		
		//assign listeners
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
		b5.setOnClickListener(this);
		b6.setOnClickListener(this);
		b7.setOnClickListener(this);
		b8.setOnClickListener(this);
		b9.setOnClickListener(this);
		br.setOnClickListener(this);
		bd.setOnClickListener(this);
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				textTab[i][j].setOnClickListener(this);
			}
		}
		
		//get resources
		Resources res = getResources();
		difficultyValue = res.getIntArray(R.array.DifficultyValue);
		Bundle b = getIntent().getExtras();
		difficulty = b.getString("difficulty");
		
		//set default preferences
		SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		SP.registerOnSharedPreferenceChangeListener(preferenceListener);
		
		//creating table
		table = new SudokuTable(getBaseContext());
		
		text_remaining = (TextView) findViewById(R.id.boxes_remaining);
		
		generate();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case R.id.generate:
			askNewGame();
			return true;
		case R.id.restart:
			AlertDialog.Builder dialogRestart = new AlertDialog.Builder(this);
			dialogRestart.setMessage("Recommencer la partie ?");
			dialogRestart.setCancelable(true);
			dialogRestart.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					restartGame();
				}
			  });
			dialogRestart.setNegativeButton("Non", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			  });
			dialogRestart.show();
			break;
		case R.id.auto_finish:
			AlertDialog.Builder dialogFinish = new AlertDialog.Builder(this);
			dialogFinish.setMessage("Terminer automatiquement la partie ?");
			dialogFinish.setCancelable(true);
			dialogFinish.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					autoFinish();
				}
			  });
			dialogFinish.setNegativeButton("Non", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			  });
			dialogFinish.show();
			break;
		case R.id.colorRemainingBox:
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					if(table.getValue(i, j)==0)
						textTab[i][j].setBackgroundColor(Color.GREEN);
				}
			}
			break;
		case R.id.settings:
			Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Restart the current game
	 */
	private void restartGame(){
		//reset table
		table.resetTable();
		//update display
		updateGrid();
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}
	
	/**
	 * Update the texts in textTab with the values of table
	 */
	private void updateGrid(){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				int v = table.getValue(i,j);
				if(v!=0)
					textTab[i][j].setText(String.valueOf(v));
				else
					textTab[i][j].setText("-");
				
				if(table.isBase(i, j))
					textTab[i][j].setTypeface(null, Typeface.BOLD);
				else
					textTab[i][j].setTypeface(null, Typeface.ITALIC);
			}
		}
	}
	
	/**
	* Generate a sudoku table and update display
	 */
	private void generate(){
		Log.d(TAG, "difficulty = "+difficulty);
		//fetch difficulty
		if(difficulty.equals("DEB"))
			table.setLevel(difficultyValue[0]);
		else if(difficulty.equals("EASY"))
			table.setLevel(difficultyValue[1]);
		else if(difficulty.equals("MEDIUM"))
			table.setLevel(difficultyValue[2]);
		else if(difficulty.equals("HARD"))
			table.setLevel(difficultyValue[3]);
		
		
		if(difficulty.equals("RESUME")){
			table.load();
			if(table.getLevel()==difficultyValue[0])
				difficulty="DEB";
			else if(table.getLevel()==difficultyValue[1])
				difficulty="EASY";
			else if(table.getLevel()==difficultyValue[2])
				difficulty="MEDIUM";
			else if(table.getLevel()==difficultyValue[3])
				difficulty="HARD";
		}
		else
			table.build();
		
		//refresh grid
		updateGrid();
		setDefaultBackgroundColor();
		
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}

	/**
	* Method called when something is clicked
	*/
	@Override
	public void onClick(View v) {
		
		//check if the item clicked is a box
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(textTab[i][j]==v){
					itemSelectedX=i;
					itemSelectedY=j;
					setDefaultBackgroundColor();
					
					ColorizeBlocks(i,j);
						
					textTab[i][j].setBackgroundColor(Color.YELLOW);
					
					return;
				}
			}
		}
		
		if(itemSelectedX>8 || itemSelectedY>8){
			Toast.makeText(getApplicationContext(), "Sélectionnez d'abord une case", Toast.LENGTH_SHORT).show();
			return;
		}
		
		boolean color_errors = SP.getBoolean("color_errors", false);
			
		TextView text = textTab[itemSelectedX][itemSelectedY];
		
	    switch(v.getId()){
	    case R.id.button1:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 1)){
	    		text.setText("1");
	    		unselectItem();
	    		levelEnd();
	    	}	
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 1 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button2:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 2)){
	    		text.setText("2");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 2 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button3:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 3)){
	    		text.setText("3");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 3 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button4:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 4)){
	    		text.setText("4");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 4 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button5:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 5)){
	    		text.setText("5");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 5 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button6:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 6)){
	    		text.setText("6");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 6 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button7:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 7)){
	    		text.setText("7");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 7 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button8:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 8)){
	    		text.setText("8");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 8 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.button9:
	    	if(table.setValue(itemSelectedX, itemSelectedY, 9)){
	    		text.setText("9");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else{
	    		if(color_errors){
	    			colorBorders(itemSelectedX,itemSelectedY,Color.RED);
	    			isErrorColored=true;
	    		}
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 9 à cet endroit", Toast.LENGTH_SHORT).show();
	    	}
	    	break;
	    case R.id.buttonR:
	    	unselectItem();
			break;
	    case R.id.buttonD:
	    	if(table.isBase(itemSelectedX, itemSelectedY)){
	    		Toast.makeText(getApplicationContext(), "Impossible de supprimer cette valeur", Toast.LENGTH_SHORT).show();
	    		return;
	    	}
	    	text.setText("-");
	    	table.delete_value(itemSelectedX, itemSelectedY);
	    	unselectItem();
	    	break;
	    }
	    text_remaining.setText(String.valueOf(table.getRemaining()));
	}

	/**
	 * Process unselection of a box
	 */
	private void unselectItem(){
		itemSelectedX=9; itemSelectedY=9;
    	//reset background colors of all textView
		setDefaultBackgroundColor();
	}
	
	/**
	 * Check if the game is finished. if yes, display a dialog and start a new game or close activity
	 */
	private void levelEnd(){
		if(table.isEnd()==false)
			return;
		
		Log.d(TAG, "Game finished");
		AlertDialog.Builder dialogEnd = new AlertDialog.Builder(this);
		dialogEnd.setTitle("Partie terminée !");
		dialogEnd.setMessage("Voulez-vous commencer une nouvelle partie ?");
		dialogEnd.setCancelable(false);
		dialogEnd.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				generate();
			}
		  });
		dialogEnd.setNegativeButton("Non", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				setResult(1);
				finish();
			}
		  });
		dialogEnd.show();
	}
	
	private void askNewGame(){
		AlertDialog.Builder dialogNewGame = new AlertDialog.Builder(this);
		dialogNewGame.setMessage("Commencer une nouvelle partie ?");
		dialogNewGame.setCancelable(false);
		dialogNewGame.setNegativeButton("Non",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		  });
		dialogNewGame.setPositiveButton("Oui",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				generate();
			}
		  });
		dialogNewGame.show();
	}

	/**
	 * Complete the game automatically
	 */
	private void autoFinish(){
		table.completeTable();
		
		//refresh grid
		updateGrid();
		
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}
	
	/**
	 * Set backgroud color of grid
	 */
	private void setDefaultBackgroundColor(){
		boolean color_on = SP.getBoolean("color_on", true);
		
		if(color_on){
			boolean isColored = SP.getBoolean("color_mode", false);
			
			if(isColored==false){
				int c;
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						c=40;
						if(i<3) ;
						else if (i>5) c+=80;
						else c+=40;
						if(j<3) ;
						else if (j>5) c+=80;
						else c+=40;
						if(table.isBase(i, j))
							textTab[i][j].setBackgroundColor(Color.argb(120,c, c, c));
						else
							textTab[i][j].setBackgroundColor(Color.argb(70,c, c, c));
					}
				}
			}
			else{
				int r,g;
				for(int i=0;i<9;i++){
					for(int j=0;j<9;j++){
						if(i<3) r=0;
						else if(i>5) r=255;
						else r=128;
						if(j<3) g=0;
						else if(j>5) g=255;
						else g=128;
							
						if(table.isBase(i, j))
							textTab[i][j].setBackgroundColor(Color.argb(120,r, 128, g));
						else
							textTab[i][j].setBackgroundColor(Color.argb(70,r, 128, g));
					}
				}
			}
		}
		
		else{
			boolean color_base = SP.getBoolean("color_base", false);
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					if(table.isBase(i, j) && color_base)
						textTab[i][j].setBackgroundColor(Color.LTGRAY);
					else
						textTab[i][j].setBackgroundColor(Color.WHITE);
				}
			}
		}
		
		if(isErrorColored)
			setDefaultBorders();
		
		
	}

	
	public OnSharedPreferenceChangeListener preferenceListener = new OnSharedPreferenceChangeListener() {        

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        	if(key.equals("color_mode"))
        		setDefaultBackgroundColor();
        	
        	else if(key.equals("color_on") || key.equals("color_base"))
        		setDefaultBackgroundColor();
        	
        	else if(key.equals("color_block") || key.equals("color_digits")){
        		if(itemSelectedX<8 && itemSelectedY<8)
        			ColorizeBlocks(itemSelectedX,itemSelectedY);
        	}
        		
        		
        }
    };
	
    /**
     * Colorize blocks when something is selected
     * @param i
     * @param j
     */
    private void ColorizeBlocks(int i, int j){
    	
    	boolean isColored = SP.getBoolean("color_block", true);
    	boolean isDigitColored = SP.getBoolean("color_digits", true);
    	
    	if(isColored){
    		//change color of line and column
    		for(int k=0;k<9;k++){
    			if(table.isBase(i, k))
    				textTab[i][k].setBackgroundColor(Color.argb(150,255, 255, 60));
    			else
    				textTab[i][k].setBackgroundColor(Color.argb(60, 255, 255, 60));
    			
    			if(table.isBase(k, j))
    				textTab[k][j].setBackgroundColor(Color.argb(150,255, 255, 60));
    			else
    				textTab[k][j].setBackgroundColor(Color.argb(60, 255, 255, 60));
    		}
    		
    		//change color of current square -> find a corner of it and then parse all boxes
    		int x,y;
    		if(i<3)x=0;
    		else if(i>5)x=6;
    		else x=3;
    		if(j<3)y=0;
    		else if(j>5)y=6;
    		else y=3;
    		for(int k=x;k<x+3;k++){
    			for(int l=y;l<y+3;l++){
    				if(table.isBase(k, l))
    					textTab[k][l].setBackgroundColor(Color.argb(150,255, 255, 150));
    				else
    					textTab[k][l].setBackgroundColor(Color.argb(60, 255, 255, 150));
    			}
    		}
    	}
    	/*else{
    		for(int k=0;k<9;k++){
				for(int l=0;l<9;l++){
					textTab[k][l].setBackgroundColor(Color.WHITE);
				}
			}
    	}*/
    	
    	if(isDigitColored){
    		int val = table.getValue(i, j);
    		if(val!=0){
    			for(int k=0;k<9;k++){
    				for(int l=0;l<9;l++){
    					if(table.getValue(k, l)==val)
    						textTab[k][l].setBackgroundColor(Color.CYAN);
    				}
    			}
    		}
    		
    	}
    	
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	table.save();
    	Log.d(TAG, "MainActivity paused");
    }
    
    
    private void setDefaultBorders(){
    	for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				
				////corners
				if(i==0 && j==0){ //top left
					borders = new Border[4];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_TOP);
					borders[2].setWidth(8);
					borders[3] = new Border(BORDER_LEFT);
					borders[3].setWidth(8);
				}else
				if(j==8 && i==8){ //bottom right
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[0].setWidth(8);
					borders[1].setWidth(8);
				}else
				if(i==0 && j==8){ //top right
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_TOP);
					borders[2].setWidth(8);
					borders[0].setWidth(8);
				}else
				if(i==8 && j==0){ //bottom left
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_LEFT);
					borders[2].setWidth(8);
					borders[1].setWidth(8);
				}else
					
				
				////start of central columns
				if(i==0 && j==2){ //top 1st column
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_TOP);
					borders[2].setWidth(8);
					borders[0].setWidth(8);
				}else
				if(i==0 && j==5){ //top 2st column
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_TOP);
					borders[2].setWidth(8);
					borders[0].setWidth(8);
				}else
				if(i==8 && j==2){ //bottom 1st column
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
					borders[0].setWidth(8);
				}else
				if(i==8 && j==5){ //bottom 2st column
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
					borders[0].setWidth(8);
				}else
					
					
				////start of central lines
				if(i==2 && j==0){ //left 1st line
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_LEFT);
					borders[1].setWidth(8);
					borders[2].setWidth(8);
				}else
				if(i==5 && j==0){ //left 2st line
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_LEFT);
					borders[1].setWidth(8);
					borders[2].setWidth(8);
				}else
				if(i==2 && j==8){ //right 1st line
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
					borders[0].setWidth(8);
				}else
				if(i==5 && j==8){ //right 2st line
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
					borders[0].setWidth(8);
				}else
					
					
				//crossing of central lines
				if((i==2 && j==2) || (i==2 && j==5) || (i==5 && j==2) || (i==5 && j==5)){
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
					borders[0].setWidth(8);
				}else
					
					
				////lines and columns
				if(j==0){ //left column
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_LEFT);
					borders[2].setWidth(8);
				}else
				if(j==8 || j==2 || j==5){ //columns
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[0].setWidth(8);
				}else
				if(i==0){ //top line
					borders = new Border[3];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[2] = new Border(BORDER_TOP);
					borders[2].setWidth(8);
				}else
				if(i==8 || i==2 || i==5){ //bottom line
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
					borders[1].setWidth(8);
				}
				
				
				else { //normal case
					borders = new Border[2];
					borders[0] = new Border(BORDER_RIGHT);
					borders[1] = new Border(BORDER_BOTTOM);
				}
				textTab[i][j].setBorders(borders);
				borders = null;
			}
		}
    }
    
    private void colorBorders(int x, int y, int color){
    	borders = new Border[4];
    	borders[0] = new Border(BORDER_RIGHT);
		borders[1] = new Border(BORDER_BOTTOM);
		borders[2] = new Border(BORDER_TOP);
		borders[3] = new Border(BORDER_LEFT);
		borders[0].setWidth(12);
		borders[1].setWidth(12);
		borders[2].setWidth(8);
		borders[3].setWidth(8);
		borders[0].setColor(color);
		borders[1].setColor(color);
		borders[2].setColor(color);
		borders[3].setColor(color);
		
		textTab[x][y].setBorders(borders);
		textTab[x][y].invalidate();
		borders = null;
    }
    
} //class MainActivity
