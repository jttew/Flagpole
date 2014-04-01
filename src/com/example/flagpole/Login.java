package com.example.flagpole;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class Login  extends AsyncTask<String,Void,String>{

   private TextView resultsView;
   public Login(Context context, TextView resultsView) {
      this.resultsView = resultsView;
   }

   protected void onPreExecute(){

   }
   @Override
   protected String doInBackground(String... arg0) {
         return DB.validateUser(arg0[0],arg0[1]).toString(); 
   }
   @Override
   protected void onPostExecute(String result){
      this.resultsView.setText(result);
   }
}