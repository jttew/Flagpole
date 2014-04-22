package com.example.flagpole;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountActivity extends Activity {

	
	EditText firstNameField, lastNameField, usernameField, emailField, 
		passwordField1, passwordField2;
	Button submitButton;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		firstNameField = (EditText) findViewById(R.id.createAccountFirstNameEditText);
		lastNameField = (EditText) findViewById(R.id.createAccountLastNameEditText);
		//usernameField = (EditText) findViewById(R.id.createAccountUsernameEditText);
		emailField = (EditText) findViewById(R.id.createAccountEmailEditText);
		passwordField1 = (EditText) findViewById(R.id.createAccountPasswordEditText1);
		passwordField2 = (EditText) findViewById(R.id.createAccountPasswordEditText2);
		
		submitButton = (Button) findViewById(R.id.createAccountSubmitButton);
		
		submitButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				String email = emailField.getText().toString();
				String password1 = passwordField1.getText().toString();
				String password2 = passwordField2.getText().toString();
				String firstName = firstNameField.getText().toString();
				String lastName = lastNameField.getText().toString();
				 //Check if passwords are the same.
				if (password1 == password2){
					Context context = getApplicationContext();
					CharSequence message = "Passwords must match.";
					Toast toast = Toast.makeText(context, message , Toast.LENGTH_LONG);
					toast.show();
				}
				else{
					Integer result = DB.createUser(email, password1, firstName, lastName);
					 //Toast if DB function fails.
					if ( result == 0 ){
			        	Context context = getApplicationContext();
			        	CharSequence text = "Error. Invalid field.";
			        	Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			        	toast.show();
					}
					 //Toast if success.
					else{
			        	Context context = getApplicationContext();
			        	CharSequence text = "Success.";
			        	Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
			        	toast.show();
			        	CreateAccountActivity.this.finish();
					}
				}
				//
			}
		});
		
		
	}
	
	
}
