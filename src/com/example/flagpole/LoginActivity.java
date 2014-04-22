package com.example.flagpole;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	EditText emailField, passField;
	public static final String USER_DATA = "UserData";
	Button submitButton, createAccountButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		emailField = (EditText) findViewById(R.id.emailEditText);
		passField = (EditText) findViewById(R.id.passwordEditText);
		submitButton = (Button) findViewById(R.id.submitButton1);
		createAccountButton = (Button) findViewById(R.id.createAccountActivityButton);
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String username = emailField.getText().toString();
				String password = passField.getText().toString();
				Integer result = DB.validateUser(username, password);
				SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
		        SharedPreferences.Editor editor = settings.edit();
		        editor.putString("user_id", result.toString());
		        editor.commit();
		        if ( result == 0) {
		        	Context context = getApplicationContext();
		        	CharSequence text = "Invalid email or password";
		        	Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		        	toast.show();
		        }
		        else {
		        	Intent mapIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
					startActivity(mapIntent);
		        }
			}
		});
		
		createAccountButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent createAccountIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
				startActivity(createAccountIntent);
			}
		});
		
		
		
	}
	
	
	
}
