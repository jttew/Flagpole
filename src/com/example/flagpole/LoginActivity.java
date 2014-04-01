package com.example.flagpole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {
	
	EditText userField, passField;
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
				Log.d("username", username);
				Log.d("password", password);
			}
		});
		
		
		
	}
	
	
	
}
