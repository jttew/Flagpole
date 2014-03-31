package com.example.flagpole;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

   private EditText usernameField,passwordField,firstNameField,lastNameField;
   private TextView resultsView, createResultsView;

   @Override 
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      usernameField = (EditText)findViewById(R.id.editText1);
      passwordField = (EditText)findViewById(R.id.editText2);
      firstNameField = (EditText)findViewById(R.id.firstNameField); 
      lastNameField = (EditText)findViewById(R.id.lastNameField); 
      resultsView = (TextView)findViewById(R.id.resultsView);
      createResultsView = (TextView)findViewById(R.id.createResult);
      Button viewMapButtons = (Button) this.findViewById(R.id.viewMap);
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }

   public void login(View view){
      String username = usernameField.getText().toString();
      String password = passwordField.getText().toString();
      new LoginActivity(this,resultsView).execute(username,password);
   }
  
   public void createUser(View view) {
	   String username = usernameField.getText().toString();
	   String password = passwordField.getText().toString();
	   String firstName = firstNameField.getText().toString(); 
	   String lastName = lastNameField.getText().toString();
	   new CreateUserActivity(this,createResultsView).execute(username,password,firstName,lastName); 
   }
   
   public void viewMap(View view)
   {
	   Intent mapIntent = new Intent(this, Map.class);
	   startActivity(mapIntent);
   }

}