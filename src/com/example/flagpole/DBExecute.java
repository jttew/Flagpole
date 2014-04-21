package com.example.flagpole;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

public class DBExecute extends AsyncTask<String, Void, String> {
	
	public static String link = "http://billyswifty.com/flagpole.php";

	@Override
	public String doInBackground(String... params) {
		try{
			URL url = new URL(link);
            URLConnection conn = url.openConnection(); 
            conn.setDoOutput(true); 
            OutputStreamWriter wr = new OutputStreamWriter
            (conn.getOutputStream());
            wr.write( params[0] ); 
            wr.flush(); 
            BufferedReader reader = new BufferedReader
            (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
               sb.append(line);
               break;
            }
            return sb.toString();
         }
		catch(Exception e) {
			e.printStackTrace();
			return "connection error"; 
		}
	}
	

}