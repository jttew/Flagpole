package com.example.flagpole;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
//import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
//import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//import android.app.Activity;
//import android.app.Dialog;
import android.app.DialogFragment;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentSender;
import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.graphics.Point;
import android.location.Location;
//import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.view.Display;
//import android.widget.Toast;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Map extends FragmentActivity implements OnMapLongClickListener
{
	 class MyInfoWindowAdapter implements InfoWindowAdapter{

	      private final View myContentsView;
	  
		  MyInfoWindowAdapter(){
		   myContentsView = getLayoutInflater().inflate(R.layout.info_box, null);
		  }
		  
		  @Override
		  public View getInfoContents(Marker marker) {
		   
		   TextView tvTitle = ((TextView)myContentsView.findViewById(R.id.title));
		   tvTitle.setText(marker.getTitle());
		   TextView tvSnippet = ((TextView)myContentsView.findViewById(R.id.snippet));
		   tvSnippet.setText(marker.getSnippet());
		   return myContentsView;
		  }
	
		  @Override
		  public View getInfoWindow(Marker marker) {
		   return null;
		  }
	  
	 }

    Location mCurrentLocation;
    LocationClient mLocationClient;
    public static final String USER_DATA = "UserData";
    private static GoogleMap map;
    private static LatLng currentPoint;
    private static Integer user_id; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setOnMapLongClickListener(this);
        map.setMyLocationEnabled(true);
        SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
        user_id = Integer.parseInt(settings.getString("user_id", "0"));
        map.setInfoWindowAdapter(new MyInfoWindowAdapter());
    }
   
    protected void addFlagsFromDB(List<Integer> flagIds)
    {
    	int i = 0;
    	while (i < flagIds.size())
    	{
    		String flagTitle = DB.getFlagTitle(flagIds.get(i));
    		Double flagLat = DB.getFlagLatitude(flagIds.get(i));
    		Double flagLong = DB.getFlagLongitude(flagIds.get(i));
    		LatLng flagLatLng = new LatLng(flagLat, flagLong);
    		String flagContent = DB.getFlagContent(flagIds.get(i));
    		Integer flagAuthor = DB.getFlagAuthor(flagIds.get(i)); 
    		String flagScore = DB.getFlagScore(flagIds.get(i)).toString(); 
    		Date time_posted = DB.getFlagPostTime(flagIds.get(i)); 
 		    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy @ hh:mm a");
 		    String time_posted_string = DATE_FORMAT.format(time_posted);
    		if (user_id == flagAuthor) { // use blue flag icon if created by this user
    			map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).snippet(flagContent + "\n\nScore: " + flagScore + "\nPosted: " + time_posted_string).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon6)));
    		}
    		else { // use red flag icon otherwise
    			map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).snippet(flagContent + "\n\nScore: " + flagScore + "\nPosted: " + time_posted_string).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon5)));
    		}
    		i++; 
    	}
    }
    
    @Override
    public void onMapLongClick(LatLng point)
    {
    	if (user_id != 0) {
    		DialogFragment newFragment = new FlagDialogBox();
            newFragment.show(getFragmentManager(), "Flag");
            currentPoint = point;
    	}
    }

    public static void addFlagToMap(String title, String description)
    {
        Marker flag = map.addMarker(new MarkerOptions().position(currentPoint).title(title).snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon6)));
        Date now = new Date(); 
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy @ hh:mm a");
		String time_posted_string = DATE_FORMAT.format(now);
        DB.createFlag(user_id, title, description, 150, "", flag.getPosition().latitude, flag.getPosition().longitude);
        flag.setSnippet(description + "\n\nScore: " + "0" + "\nPosted: " + time_posted_string); 
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        List<Integer> flagIds = DB.getFlagsInRadius(33.21262, -87.54256, 5000);
        addFlagsFromDB(flagIds);
    }
}
