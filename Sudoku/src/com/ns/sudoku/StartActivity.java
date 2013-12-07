package com.ns.sudoku;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends Activity implements OnClickListener {

	private static final int CODE_MAIN_ACTIVITY = 1;
	private Button b_resume=null;
	private TextView text_resume=null;
	private SharedPreferences SP=null;
	private String gameExists="none";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Button b_deb = (Button) findViewById(R.id.buttonDeb);
		Button b_easy = (Button) findViewById(R.id.buttonEasy);
		Button b_med = (Button) findViewById(R.id.buttonMed);
		Button b_hard = (Button) findViewById(R.id.buttonHard);
		Button b_pref = (Button) findViewById(R.id.buttonPref);
		b_resume = (Button)findViewById(R.id.buttonResume);
		text_resume = (TextView)findViewById(R.id.textViewResumeGame);
		
		b_deb.setOnClickListener(this);
		b_easy.setOnClickListener(this);
		b_med.setOnClickListener(this);
		b_hard.setOnClickListener(this);
		b_pref.setOnClickListener(this);
		b_resume.setOnClickListener(this);
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		//SP.registerOnSharedPreferenceChangeListener(preferenceListener);
		gameExists = SP.getString("gameExists", "none");
		
		setTextGameResume(gameExists);
			
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.buttonDeb:
			startGame("DEB");
			break;
		case R.id.buttonEasy:
			startGame("EASY");
			break;
		case R.id.buttonMed:
			startGame("MEDIUM");
			break;
		case R.id.buttonHard:
			startGame("HARD");
			break;
		case R.id.buttonPref:
			Intent launchPrefActivity = new Intent(getApplicationContext(),SettingsActivity.class);
			startActivity(launchPrefActivity);
			break;
		case R.id.buttonResume:
			startGame("RESUME");
			break;
		}
		
	}
	
	private void startGame(String diff){
		
		if(!diff.equals("RESUME")){
			SharedPreferences.Editor SPE = SP.edit();
			SPE.putString("gameExists", diff).commit();
			setTextGameResume(diff);
		}
		
		Intent launchMainActivity = new Intent(getApplicationContext(),MainActivity.class);
		launchMainActivity.putExtra("difficulty", diff);
		startActivityForResult(launchMainActivity,CODE_MAIN_ACTIVITY);
	}

	/*public OnSharedPreferenceChangeListener preferenceListener = new OnSharedPreferenceChangeListener() {        

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        	if(key.equals("gameExists")){
        		SP.getString("gameExists", "none");
        		if(gameExists.equals("DEB"))
        			text_resume.setText("Une partie en cours (Débutant)");
        		else if(gameExists.equals("EASY"))
        			text_resume.setText("Une partie en cours (Facile)");
        		else if(gameExists.equals("MEDIUM"))
        			text_resume.setText("Une partie en cours (Moyen)");
        		else if(gameExists.equals("HARD"))
        			text_resume.setText("Une partie en cours (Difficile)");
        		else{
        			text_resume.setText("Pas de partie en cours");
        			b_resume.setEnabled(false);
        		}
        	}	
        		
        }
    };*/
	
	
	private void setTextGameResume(String str){
		if(str.equals("DEB")){
			text_resume.setText("Une partie en cours (Débutant)");
			b_resume.setEnabled(true);
		}
		else if(str.equals("EASY")){
			text_resume.setText("Une partie en cours (Facile)");
			b_resume.setEnabled(true);
		}
		else if(str.equals("MEDIUM")){
			text_resume.setText("Une partie en cours (Moyen)");
			b_resume.setEnabled(true);
		}
		else if(str.equals("HARD")){
			text_resume.setText("Une partie en cours (Difficile)");
			b_resume.setEnabled(true);
		}
		else{
			text_resume.setText("Pas de partie en cours");
			b_resume.setEnabled(false);
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		switch(requestCode){
		case CODE_MAIN_ACTIVITY:
			if(resultCode==1){//game is finished
				SharedPreferences.Editor SPE = SP.edit();
				SPE.putString("gameExists", "none") 
					.commit();
				setTextGameResume("none");
			}
		}
	}

}
