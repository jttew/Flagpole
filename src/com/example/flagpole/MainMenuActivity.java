package com.example.flagpole;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {

	Button login, logout, viewmap, createaccount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

        login = (Button) findViewById(R.id.log_in);
        logout = (Button) findViewById(R.id.log_out);
        viewmap = (Button) findViewById(R.id.view_map);
//        createaccount = (Button) findViewById(R.id.create_account);
        
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
				finish();
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

//	@Override
//	protected void onPause() {
//		super.onPause();
//		finish();
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}