package com.example.flagpole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Application;

public class DB extends Application {


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String link = "http://billyswifty.com/flagpole.php";
	
	
	public static String getDataString ( String[] args ) {
		String data_string = ""; 
		for ( int i = 0; i < args.length; i += 2 ) {
			try {
				data_string += URLEncoder.encode( args[i] , "UTF-8");
				data_string += "=";
				data_string += URLEncoder.encode( args[i+1], "UTF-8"); 
				data_string += "&"; 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
		}
		data_string = data_string.substring(0, data_string.length()-1);
		return data_string; 
	}
	public static String getResult( String data ) {
		try{
			URL url = new URL(link);
            URLConnection conn = url.openConnection(); 
            conn.setDoOutput(true); 
            OutputStreamWriter wr = new OutputStreamWriter
            (conn.getOutputStream()); 
            wr.write( data ); 
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
	
	public static Integer validateUser (String email, String password) {
		try{
			String[] args = new String[6];
			args[0] = "method"; args[1] = "validateUser"; 
			args[2] = "email"; args[3] = email; args[4] = "password"; args[5] = password; 
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	public static Integer createUser (String email, String password, String first_name, String last_name) {
		try{
			String[] args = new String[10];
			args[0] = "method"; args[1] = "createUser"; 
			args[2] = "email"; args[3] = email; args[4] = "password"; args[5] = password; 
			args[6] = "first_name"; args[7] = first_name; args[8] = "last_name"; args[9] = last_name; 
			String data = getDataString( args );
		//	System.out.println("data: " + data); 
		//	System.out.println("result: " + getResult(data));
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static String getUserFirstName (Integer user_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getUserFirstName"; 
			args[2] = "user_id"; args[3] = user_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static String getUserLastName (Integer user_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getUserLastName"; 
			args[2] = "user_id"; args[3] = user_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static String getUserEmail (Integer user_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getUserEmail"; 
			args[2] = "user_id"; args[3] = user_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static Integer getUserByEmail (String email) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getUserByEmail"; 
			args[2] = "email"; args[3] = email;  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static List<Integer> getFlagsInRadius (Double latitude, Double longitude, Integer radius) {
		List<Integer> results = new ArrayList<Integer>(); 
		try{
			String[] args = new String[8];
			args[0] = "method"; args[1] = "getFlagsInRadius"; 
			args[2] = "latitude"; args[3] = latitude.toString(); 
			args[4] = "longitude"; args[5] = longitude.toString(); 
			args[6] = "radius"; args[7] = radius.toString(); 
			String data = getDataString( args );
			String answer = getResult(data); 
			String[] answers = answer.split(","); 
			for (int i = 0; i < answers.length; i++ ) {
				results.add(Integer.parseInt(answers[i])); 
			}
			return results; 
         }
		 catch(Exception e){
            return results;
         }
	}
	
	public static List<Integer> getFlagsByAuthor (Integer user_id) {
		List<Integer> results = new ArrayList<Integer>(); 
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagsByAuthor"; 
			args[2] = "user_id"; args[3] = user_id.toString();
			String data = getDataString( args );
			String answer = getResult(data); 
			String[] answers = answer.split(","); 
			for (int i = 0; i < answers.length; i++ ) {
				results.add(Integer.parseInt(answers[i])); 
			}
			return results; 
         }
		 catch(Exception e){
            return results;
         }
	}
	
	public static String getFlagTitle (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagTitle"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static String getFlagContent (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagContent"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static Integer getFlagAuthor (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagAuthor"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static Double getFlagLatitude (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagLatitude"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Double.parseDouble(getResult(data)); 
         }
		 catch(Exception e){
            return 0.0;
         }
	}
	
	public static Double getFlagLongitude (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagLongitude"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Double.parseDouble(getResult(data)); 
         }
		 catch(Exception e){
            return 0.0;
         }
	}
	
	public static Integer getFlagScore (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagScore"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static Integer getFlagRecipients (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagRecipients"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static String getFlagStatus (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "getFlagRecipients"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return getResult(data); 
         }
		 catch(Exception e){
            return "";
         }
	}
	
	public static Integer incrementFlagScore (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "incrementFlagScore"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static Integer decrementFlagScore (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "decrementFlagScore"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static Integer incrementFlagRecipients (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "incrementFlagRecipients"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
	
	public static Integer decrementFlagRecipients (Integer flag_id) {
		try{
			String[] args = new String[4];
			args[0] = "method"; args[1] = "decrementFlagRecipients"; 
			args[2] = "flag_id"; args[3] = flag_id.toString();  
			String data = getDataString( args );
			return Integer.parseInt(getResult(data)); 
         }
		 catch(Exception e){
            return 0;
         }
	}
}
