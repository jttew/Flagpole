package com.example.flagpole;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class LoginActivity  extends AsyncTask<String,Void,String>{

   private TextView resultsView;
   public LoginActivity(Context context, TextView resultsView) {
      this.resultsView = resultsView;
   }

   protected void onPreExecute(){

   }
   @Override
   protected String doInBackground(String... arg0) {
         return DB.getDistanceToFlag(Integer.parseInt(arg0[0]),34.5114,-87.5467).toString(); 
   }
   @Override
   protected void onPostExecute(String result){
      this.resultsView.setText(result);
   }
}