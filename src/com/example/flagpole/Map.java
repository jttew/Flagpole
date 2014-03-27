package com.example.flagpole;

import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;


public class Map extends FragmentActivity implements OnMapLongClickListener
//GooglePlayServicesClient.ConnectionCallbacks,
//GooglePlayServicesClient.OnConnectionFailedListener,
//, NoticeDialogFragment.NoticeDialogListener
{
	 //Global variable to hold the current location
    //Location mCurrentLocation;
    //LocationClient mLocationClient;
    //static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    //static final LatLng KIEL = new LatLng(53.551, 9.993);
    private static GoogleMap map;
    private static LatLng currentPoint;

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
        //Gets the center of the map
        Display display = getWindowManager().getDefaultDisplay();
        Point center = new Point(display.getWidth()/2, display.getHeight()/2);
        //Gets the latitude and longitude for the center of the map
        double centerLat = map.getProjection().fromScreenLocation(center).latitude;
        double centerLong = map.getProjection().fromScreenLocation(center).longitude;
        List<Integer> flagIds = DB.getFlagsInRadius(centerLat, centerLong, 1000000000);
        addFlagsFromDB(flagIds);
        //LocationClient mLocationClient = new LocationClient(this, this, this);
        //mCurrentLocation = mLocationClient.getLastLocation();
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));
       // Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("HAMBURG"));
        //Marker kiel = map.addMarker(new MarkerOptions().position(KIEL).title("Kiel").snippet("Kiel is cool").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
    
    protected void addFlagsFromDB(List<Integer> flagIds)
    {
    	Log.d("Debug", "it got here0");
    	int i = 0;
    	while (i < flagIds.size())
    	{
    		String flagTitle = DB.getFlagTitle(flagIds.get(i));
    		Double flagLat = DB.getFlagLatitude(flagIds.get(i));
    		Double flagLong = DB.getFlagLongitude(flagIds.get(i));
    		LatLng flagLatLng = new LatLng(flagLat, flagLong);
    		map.addMarker(new MarkerOptions().position(flagLatLng).title(flagTitle).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    		Log.d("Debug", "it got here1");
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
        DialogFragment newFragment = new FlagDialogBox();
        newFragment.show(getFragmentManager(), "Flag");
        //showNoticeDialog();
        currentPoint = point;
        //map.addMarker(new MarkerOptions().position(point).title("marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //map.addMarker(new MarkerOptions().position(point).title("marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    public static void addFlagToMap(String title, String description)
    {
        Marker flag = map.addMarker(new MarkerOptions().position(currentPoint).title(title).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        DB.createFlag(0, title, description, 100, "", flag.getPosition().latitude, flag.getPosition().longitude);
        
    }

    /*
    //Called when the Activity becomes visible
    @Override
    protected void onStart()
    {
        super.onStart();
        //Connect the client
        mLocationClient.connect();
    }
    
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
