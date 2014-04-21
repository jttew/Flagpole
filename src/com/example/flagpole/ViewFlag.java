package com.example.flagpole;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewFlag extends Activity {

	Button up_vote, down_vote;
	TextView flag_title, flag_score, flag_content;
	private static Integer flag_id; 
	private static Integer user_id; 
	private static Float flag_lat;
	private static Float flag_long; 
	public static final String USER_DATA = "UserData";
	private String flag_title_string = ""; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_flag);
        flag_title = (TextView) findViewById(R.id.flag_title); 
        flag_content = (TextView) findViewById(R.id.flag_content); 
        flag_score = (TextView) findViewById(R.id.flag_score);
        down_vote = (Button) findViewById(R.id.down_vote);
        up_vote = (Button) findViewById(R.id.up_vote);
        SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
        user_id = Integer.parseInt(settings.getString("user_id", "0"));
        
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag_lat = extras.getFloat("latitude");
            System.out.println("lat: " + flag_lat.toString()); 
            flag_long = extras.getFloat("longitude"); 
            System.out.println("long: " + flag_long.toString()); 
        }
        
        flag_id = DB.getFlagByCoords(flag_lat, flag_long); 
        System.out.println("flag_id" + flag_id.toString()); 
        
        flag_title.setText(DB.getFlagTitle(flag_id)); 
        flag_content.setText(DB.getFlagContent(flag_id)); 
        flag_score.setText(DB.getFlagScore(flag_id).toString()); 
        
        up_vote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Integer current_score = 0; 
				current_score = Integer.parseInt(flag_score.getText().toString()) + 1;
				flag_score.setText(current_score.toString()); 
				DB.incrementFlagScore(flag_id); 
			}
		});
        
        down_vote.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Integer current_score = 0; 
				current_score = Integer.parseInt(flag_score.getText().toString()) - 1;
				flag_score.setText(current_score.toString()); 
				DB.decrementFlagScore(flag_id); 
			}
		});
        
	}
        

	@Override
	protected void onStart() {
		super.onStart();


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}