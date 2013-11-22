package com.ns.sudoku;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
	
	private static final String TAG = "SudokuDebug_MainActivity";

	private int curLevel = 0;
	private TextEditAdapter tab;
	private GridView gridView;
	private SudokuTable table=null;
	private int itemSelected = 81; // 0 to 80, 81 means nothing is selected
	private Button b1=null, b2=null, b3=null, b4=null, b5=null, b6=null, b7=null, b8=null, b9=null, br=null, bd=null;
	private TextView text_remaining=null;
	final String[] levelList = {"Débutant","Facile","Moyen","Difficile"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		table = new SudokuTable();
		tab = new TextEditAdapter(this,table);
		
		setContentView(R.layout.activity_main);
		
		gridView = (GridView) findViewById(R.id.gridView1);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override 
			public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3){
				TextView text = (TextView)v;	
				
				//TODO : fix backgroud coloration of square when one of the first 3 columns are selected
				
				/*
				//check if the text can be selected or not
				if(text.getTypeface().getStyle()==Typeface.BOLD){
					Toast.makeText(getApplicationContext(), "Impossible de sélectionner cette case", Toast.LENGTH_SHORT).show();
					unselectItem();
					return;
				}*/
				
				unselectItem();
				itemSelected=position;
				
				//set up colors
				int selFree = 0x90ffff00;
				int selFull = 0x40919100;
				int selFullSquare = 0x40caca00;
				
				//update color of row, column and square
				int mod_pos=position;
				while(mod_pos%9!=0) mod_pos--; //find start of row
				for(int i=mod_pos;i<mod_pos+9;i++){
					text = (TextView)gridView.getChildAt(i);
					if(text.getText()=="-")
						text.setBackgroundColor(selFree);
					else
						text.setBackgroundColor(selFull);
				}
				
				mod_pos=position%9; //start of column
				for(int i=mod_pos;i<81;i=i+9){
					text = (TextView)gridView.getChildAt(i);
					if(text.getText()=="-")
						text.setBackgroundColor(selFree);
					else
						text.setBackgroundColor(selFull);
				}
				
				mod_pos=position; //go to start of square
				int bloc;
				if(mod_pos<27) bloc=0;
				else if(mod_pos>=54) bloc=54;
				else bloc=27;
				//int l=0;
				while(mod_pos>=bloc){
					//l++;
					mod_pos-=9;
				}
				mod_pos+=9;
				if(position%9<3) bloc=0;
				else if(position%9>5) bloc=6;
				else bloc=3;
				//int c=0;
				while(mod_pos%9>=bloc){
					//c++;
					mod_pos--;
				}
				mod_pos++;
				
				//colorize all boxes of the square
				for(int i=mod_pos;i<mod_pos+3;i++){
					text = (TextView)gridView.getChildAt(i);
					if(text.getText()=="-")
						text.setBackgroundColor(selFree);
					else
						text.setBackgroundColor(selFullSquare);
				}
				mod_pos+=9;
				for(int i=mod_pos;i<mod_pos+3;i++){
					text = (TextView)gridView.getChildAt(i);
					if(text.getText()=="-")
						text.setBackgroundColor(selFree);
					else
						text.setBackgroundColor(selFullSquare);
				}
				mod_pos+=9;
				for(int i=mod_pos;i<mod_pos+3;i++){
					text = (TextView)gridView.getChildAt(i);
					if(text.getText()=="-")
						text.setBackgroundColor(selFree);
					else
						text.setBackgroundColor(selFullSquare);
				}
				
				//colorize all boxes containing the selected value
				text = (TextView)gridView.getChildAt(position);
				String str = text.getText().toString();
				if(str!="-"){
					int val = Integer.parseInt(str);
					TextView t;
					for(int i=0;i<81;i++){
						t = (TextView)gridView.getChildAt(i);
						String s=t.getText().toString();
						if(s!="-"){
							if(Integer.parseInt(s)==val){
							t.setBackgroundColor(0x400000ff);
							}
						}
					}
				}
				
				//set specific background for selected box
				text = (TextView)gridView.getChildAt(position);
				text.setBackgroundColor(0x40ff0000);
				
			}
		});
		
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
		case R.id.setLevel:
			setLevel();
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
		tab.update();
		gridView.setAdapter(tab);
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}
	
	/**
	 * Set level of the game
	 */
	private void setLevel(){
		AlertDialog.Builder dialogSetLevel = new AlertDialog.Builder(this);
		dialogSetLevel.setTitle("Changer la difficulté");
		dialogSetLevel.setCancelable(true);
		dialogSetLevel.setSingleChoiceItems(levelList,curLevel,null);
		dialogSetLevel.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		  });
		dialogSetLevel.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				ListView lw = ((AlertDialog)dialog).getListView();
				switch(lw.getCheckedItemPosition()){
				case 0:
					table.setLevel(3);
					curLevel = 0;
					break;
				case 1:
					table.setLevel(10);
					curLevel = 1;
					break;
				case 2:
					table.setLevel(20);
					curLevel = 2;
					break;
				case 3:
					table.setLevel(30);
					curLevel = 3;
					break;
				}
				askNewGame();
			}
		  });
		dialogSetLevel.show();
	}
	
	/**
	* Generate a sudoku table and update display
	 */
	private void generate(){
		Log.d(TAG, "Generating table");
		//create a new table
		table.build();
		
		//refresh gridview
		tab.update();
		gridView.setAdapter(tab);
		
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}

	/**
	* Modify value of selected textView
	*/
	@Override
	public void onClick(View v) {
		
		if(itemSelected>80){
			Toast.makeText(getApplicationContext(), "Sélectionnez d'abord une case", Toast.LENGTH_SHORT).show();
			return;
		}
			
		TextView text = (TextView)gridView.getChildAt(itemSelected);
		
	    switch(v.getId()){
	    case R.id.button1:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 1)){
	    		text.setText("1");
	    		unselectItem();
	    		levelEnd();
	    	}	
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 1 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button2:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 2)){
	    		text.setText("2");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 2 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button3:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 3)){
	    		text.setText("3");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 3 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button4:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 4)){
	    		text.setText("4");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 4 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button5:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 5)){
	    		text.setText("5");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 5 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button6:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 6)){
	    		text.setText("6");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 6 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button7:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 7)){
	    		text.setText("7");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 7 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button8:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 8)){
	    		text.setText("8");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 8 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.button9:
	    	if(table.setValue(getXfromIndex(), getYfromIndex(), 9)){
	    		text.setText("9");
	    		unselectItem();
	    		levelEnd();
	    	}
	    	else 
	    		Toast.makeText(getApplicationContext(), "Impossible de placer la valeur 9 à cet endroit", Toast.LENGTH_SHORT).show();
	    	break;
	    case R.id.buttonR:
	    	unselectItem();
			break;
	    case R.id.buttonD:
	    	text.setText("-");
	    	table.delete_value(getXfromIndex(), getYfromIndex());
	    	unselectItem();
	    	break;
	    }
	    text_remaining.setText(String.valueOf(table.getRemaining()));
	  }
	
	private int getYfromIndex(){
		return itemSelected%9;
	}
	
	private int getXfromIndex(){
		return (int)itemSelected/9;
	}
	
	private void unselectItem(){
		itemSelected=81;
    	//reset background colors of all textView
		for(int i=0;i<81;i++){
			TextView text = (TextView)gridView.getChildAt(i);
			text.setBackgroundColor(Color.TRANSPARENT);
		}
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
				MainActivity.this.finish();
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
		
		//refresh gridview
		tab.update();
		gridView.setAdapter(tab);
		
		//update display of remaining boxes
		text_remaining.setText(String.valueOf(table.getRemaining()));
	}
	
} //class MainActivity
