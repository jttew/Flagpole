package com.example.flagpole;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenuActivity extends Activity {

	Button login, logout, viewmap, createaccount;
	TextView name;
	private static Integer user_id; 
	public static final String USER_DATA = "UserData";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
        login = (Button) findViewById(R.id.log_in);
        name = (TextView) findViewById(R.id.userName); 
        logout = (Button) findViewById(R.id.log_out);
        viewmap = (Button) findViewById(R.id.view_map);
        SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
        user_id = Integer.parseInt(settings.getString("user_id", "0"));
//        createaccount = (Button) findViewById(R.id.create_account);
        
        // if logged out
        if (user_id == 0) {
        	name.setVisibility(View.GONE);
        	logout.setVisibility(View.GONE);
        }
        // if logged in
        else {
        	String first_name = DB.getUserFirstName(user_id); 
        	String last_name = DB.getUserLastName(user_id); 
        	name.setText("Hello, " + first_name + " " + last_name + "!"); 
        	login.setVisibility(View.GONE);
        }
        
        login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent loginIntent = new Intent();
				loginIntent.setClass(MainMenuActivity.this, LoginActivity.class);
				startActivity(loginIntent);
			}
		});
        
        logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
				SharedPreferences.Editor editor = settings.edit();
		        editor.putString("user_id", "0");
		        editor.commit();
		        Intent menuIntent = new Intent();
				menuIntent.setClass(MainMenuActivity.this, MainMenuActivity.class);
				finish(); 
				startActivity(menuIntent);
			}
		});
        
        viewmap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mapIntent = new Intent(MainMenuActivity.this, Map.class);
				startActivity(mapIntent);
			}
		});
	}
        
        
 //       createaccount.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO learn about activity lifecycle to better decide what to do here
//				finish();
//			}
//		});
//	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
        user_id = Integer.parseInt(settings.getString("user_id", "0"));
     // if logged out
        if (user_id == 0) {
        	name.setVisibility(View.GONE);
        	logout.setVisibility(View.GONE);
        }
        // if logged in
        else {
        	String first_name = DB.getUserFirstName(user_id); 
        	String last_name = DB.getUserLastName(user_id); 
        	name.setText("Hello, " + first_name + " " + last_name + "!"); 
        	login.setVisibility(View.GONE);
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}