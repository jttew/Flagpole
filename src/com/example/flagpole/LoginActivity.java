package com.example.flagpole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText userField, passField;
	public static final String USER_DATA = "UserData";
	Button submitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userField = (EditText) findViewById(R.id.usernameEditText);
		passField = (EditText) findViewById(R.id.passwordEditText);
		submitButton = (Button) findViewById(R.id.submitButton1);
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = userField.getText().toString();
				String password = passField.getText().toString();
				Integer result = DB.validateUser(username, password);
				SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
		        SharedPreferences.Editor editor = settings.edit();
		        editor.putString("user_id", result.toString());
		        editor.commit();
		        if ( result == 0) {
		        	Context context = getApplicationContext();
		        	CharSequence text = "Invalid email or password";
		        	Toast toast = Toast.makeText(context, text, 3000);
		        	toast.show();
		        }
		        else {
		        	Intent mapIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
					startActivity(mapIntent);
		        }
			}
		});
		
		
		
	}
	
	
	
}
