package com.ns.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity implements OnClickListener {

	private Button b_deb=null, b_easy=null, b_med=null, b_hard=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		b_deb = (Button)findViewById(R.id.buttonDeb);
		b_easy = (Button)findViewById(R.id.buttonEasy);
		b_med = (Button)findViewById(R.id.buttonMed);
		b_hard = (Button)findViewById(R.id.buttonHard);
		
		b_deb.setOnClickListener(this);
		b_easy.setOnClickListener(this);
		b_med.setOnClickListener(this);
		b_hard.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String diff="EASY";
		switch(v.getId()){
		case R.id.buttonDeb:
			diff="DEB";
			break;
		case R.id.buttonEasy:
			diff="EASY";
			break;
		case R.id.buttonMed:
			diff="MEDIUM";
			break;
		case R.id.buttonHard:
			diff="HARD";
			break;
		}
		Intent launchMainActivity= new Intent(getApplicationContext(),MainActivity.class);
		launchMainActivity.putExtra("difficulty", diff);
		startActivity(launchMainActivity);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start, menu);
		return true;
	}*/

}
