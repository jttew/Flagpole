package com.example.flagpole;

//import java.util.ArrayList;
import java.util.List;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;
//import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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


public class Map extends FragmentActivity implements OnMapLongClickListener
//GooglePlayServicesClient.ConnectionCallbacks,
//GooglePlayServicesClient.OnConnectionFailedListener,
//, NoticeDialogFragment.NoticeDialogListener
{
	 //Global variable to hold the current location
    Location mCurrentLocation;
    LocationClient mLocationClient;
    public static final String USER_DATA = "UserData";
    //static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    //static final LatLng KIEL = new LatLng(53.551, 9.993);
//  private LocationManager locationManager;
    private static GoogleMap map;
    private static LatLng currentPoint;
    private static Integer user_id; 
    /*
    //Define a request code to send to Google Play services
    //This code is returned in Activity.onActivityResul
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    //Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment
    {
        //Global field to contain the error dialog
        private Dialog mDialog;
        //Default constructor. Sets the dialog field to null
        public ErrorDialogFragment()
        {
            super();
            mDialog = null;
        }
        //Set the dialog to display
        public void setDialog(Dialog dialog)
        {
            mDialog = dialog;
        }
        //Return a Dialog to the DialogFragment
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            return mDialog;
        }
    }

    //Handle results returned to the FragmentActivity by Google Play services
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //Decide what to do based on the original request code
        switch(requestCode)
        {
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :
                //If the result code is Activity.RESULT_OK, try to connect again
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                    //Try the request again
                    break;
                }
        }
    }
    private void servicesConnected()
    {
        //Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        //If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode)
        {
            Log.d("Location Updates", "Google Play services is available.");
            //Continue
            //return true;
        }
        //Google Play services was not available for some reason

        else
        {
            //Get the error code
            int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
            if (errorCode != ConnectionResult.SUCCESS)
            {
                GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show();
            }
            /*
            //Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            //If Google Play services can provide an error dialog
            if (errorDialog != null)
            {
                //Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                //Set the dialog in the DialogFragment
                errorFragment.setDialog(errorDialog);
                //Show the error dialog in the DialogFragment
                errorFragment.show(getSupportFragmentManager(), "Location Updates");
            }
            
        }
    }

    /*Called by Location Services when the request to connect
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     
    @Override
    public void onConnected(Bundle dataBundle)
    {
        //Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    }
    //Called by Location Services if the connection to the
    //location client drops because of an error
    @Override
    public void onDisconnected()
    {
        //Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    //Called by Location Services if the attempt to Location Services fails
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to start a
         * Google Play services activity that can resolve error
         
        if (connectionResult.hasResolution())
        {
            try
            {
                //Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                //Thrown if Google Play services canceled the original PendingIntent
            }
            catch(IntentSender.SendIntentException e)
            {
                //Log the error
                e.printStackTrace();
            }
        }
        else
        {
            //If no resolution is available, display a dialog to the user with the error
            showDialog(connectionResult.getErrorCode());
        }
    }
    */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setOnMapLongClickListener(this);
        map.setMyLocationEnabled(true);
        SharedPreferences settings = getSharedPreferences(USER_DATA, 0);
        user_id = Integer.parseInt(settings.getString("user_id", "0"));
        //Gets the center of the map
        //Gets the latitude and longitude for the center of the map
       // LocationClient mLocationClient = new LocationClient(this, this, this);
       // mCurrentLocation = mLocationClient.getLastLocation();
       // map.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));
       // Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("HAMBURG"));
        //Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
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
    		
    		if (user_id == flagAuthor) {
    			map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).snippet(flagContent).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon6)));
    		}
    		else {
    			map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).snippet(flagContent).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon5)));
    		}
    		//map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).snippet(flagContent).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    		
    		i++; 
    	}
    }
    
    /*
    public void showNoticeDialog()
    {
        //Create an instance of the dialog fragment and show it
        DialogFragment dialog = new NoticeDialogFragment();
        dialog.show(getFragmentManager(), "NoticeDialogFragment");
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
        //...
    }
    */
    
    @Override
    public void onMapLongClick(LatLng point)
    {
    	if (user_id != 0) {
    		DialogFragment newFragment = new FlagDialogBox();
            newFragment.show(getFragmentManager(), "Flag");
            //showNoticeDialog();
            currentPoint = point;
    	}
        
        //map.addMarker(new MarkerOptions().position(point).title("marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //map.addMarker(new MarkerOptions().position(point).title("marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    public static void addFlagToMap(String title, String description)
    {
        Marker flag = map.addMarker(new MarkerOptions().position(currentPoint).title(title).snippet(description).icon(BitmapDescriptorFactory.fromResource(R.drawable.flag_icon6)));
        DB.createFlag(user_id, title, description, 150, "", flag.getPosition().latitude, flag.getPosition().longitude);
    }

    
    //Called when the Activity becomes visible
    @Override
    protected void onStart()
    {
        super.onStart();
     //   mLocationClient.connect();
        /*
        Display display = getWindowManager().getDefaultDisplay();
        Point center = new Point(display.getWidth()/2, display.getHeight()/2);
        double centerLat = map.getProjection().fromScreenLocation(center).latitude;
        double centerLong = map.getProjection().fromScreenLocation(center).longitude;
        Log.d("centerLat: ", String.valueOf(centerLat)); 
        Log.d("centerLong: ", String.valueOf(centerLong));  */
        List<Integer> flagIds = DB.getFlagsInRadius(33.21262, -87.54256, 5000);
     //   locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     //   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        addFlagsFromDB(flagIds);
    }
    

    /*
    //Called when the Activity is no longer visible.
    @Override
    protected void onStop()
    {
        //Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }
    */

}
