package com.example.flagpole;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class CreateUserActivity  extends AsyncTask<String,Void,String>{

   private TextView createResultsView;
   public CreateUserActivity(Context context, TextView createResultsView) {
      this.createResultsView = createResultsView;
   }

   protected void onPreExecute(){

   }
   @Override
   protected String doInBackground(String... arg0) {
         return DB.createUser((String)arg0[0], (String)arg0[1], (String)arg0[2], (String)arg0[3] ).toString(); 
   }
   @Override
   protected void onPostExecute(String result){
      this.createResultsView.setText(result);
   }
}