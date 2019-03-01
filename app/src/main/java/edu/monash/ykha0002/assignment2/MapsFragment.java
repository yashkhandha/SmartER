package edu.monash.ykha0002.assignment2;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

//for showing map
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapquest.mapping.constants.Style;
import com.mapquest.mapping.maps.OnMapReadyCallback;
import com.mapquest.mapping.maps.MapboxMap;
import com.mapquest.mapping.maps.MapView;

//for showing absolute location
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yashkhandha on 25/04/2018.
 */

public class MapsFragment extends Fragment{

    //for getting map view for mapfragment
    View vMaps;
    private MapboxMap mMapboxMap;
    private MapView mMapView;
    String loggedInResid;
    private final LatLng MONASH = new LatLng(-37.876823, 145.045837);
    private final LatLng RACECOURSE = new LatLng(-37.8772669,145.0387721);
    private final LatLng HOUSE2 = new LatLng(-37.8980661,145.0965165);
    private final LatLng HOUSE3 = new LatLng(-37.9222187,145.0634813);

    private LatLng HOME;

    //for Spinner
    private Spinner usageSpinner;
    TextView usageTextView;
    Resident resident;
    String coordinates[] = null;
    /**
     * onCreate to intialize
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        Bundle bundle = this.getArguments();
        loggedInResid = bundle.getString("resid");
        resident = bundle.getParcelable("resident");
        coordinates = bundle.getStringArray("coordinates");

        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(getActivity().getApplicationContext());
        vMaps = inflater.inflate(R.layout.fragment_maps, container, false);

        //setting coordinates

        double lat = Double.parseDouble(coordinates[0]);
        double lon = Double.parseDouble(coordinates[1]);

        HOME = new LatLng(lat,lon);

        mMapView = (MapView)vMaps.findViewById(R.id.mapquestMapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStyleUrl(Style.MAPQUEST_SATELLITE);
                mMapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOME,12));
                addMarker(mMapboxMap);
            }
        });
        return vMaps;
    }
    /**
     * method to add marker based on location and also to fetch hourly and daily usage for that location
     * @param mapboxMap
     */
    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(HOME);
        markerOptions.title("MY HOME");
        markerOptions.snippet("Welcome to Home!");
        mapboxMap.addMarker(markerOptions);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(HOUSE2);
        markerOptions2.title("HOUSE2");
        markerOptions2.snippet("Welcome to HOUSE2!");

        mapboxMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions2.position(HOUSE3);
        markerOptions2.title("HOUSE3");
        markerOptions2.snippet("Welcome to HOUSE!");


        mapboxMap.setOnMarkerClickListener(new com.mapbox.mapboxsdk.maps.MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                usageSpinner = (Spinner) vMaps.findViewById(R.id.spinner);
                usageTextView = (TextView) vMaps.findViewById(R.id.UsageValue);

                String selectedView = usageSpinner.getSelectedItem().toString();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String todayDate = format.format(date);

                SimpleDateFormat timeFormatHH = new SimpleDateFormat("HH");
                final String currentTimeHH = timeFormatHH.format(date);

                //if clicked on first marker. task to find daily and hourly usage
                if((marker.getPosition()).equals(markerOptions.getPosition())){
                    if(selectedView.equals("Daily")){
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String [] urlParams = {"resid","todayDate"};
                                //pass todayDate in later stage
                                String [] urlValues = {String.valueOf(resident.getResid()),todayDate};
                                return RestClient.findTotalUsageForResidDaily(urlParams,urlValues);
                            }
                            @Override
                            protected void onPostExecute(String result) {
                                Double[] total = RestClient.findTotalUsageForResidDailyFetch(result);
                                DecimalFormat df2 = new DecimalFormat(".##");
                                String sum = df2.format(total[0]+total[1]+total[2]);
                                usageTextView.setText(sum);
                            }
                        }.execute();
                    }
                    else{
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String [] urlParams = {"resid","todayDate","currentTimeHH"};
                                String [] urlValues = {String.valueOf(resident.getResid()) ,todayDate,currentTimeHH};
                                return RestClient.findTotalUsageForResidHourly(urlParams,urlValues);
                            }
                            @Override
                            protected void onPostExecute(String result) {
                                usageTextView.setText(String.valueOf(RestClient.findTotalUsageForResidHourlyFetch(result)));
                            }
                        }.execute();
                    }
                    //markerOptions.setIcon();
                    return true;
                }

                //if second marker selected

                if((marker.getPosition()).equals(markerOptions2.getPosition())){
                    String resid = "1002";

                    if(selectedView.equals("Daily")){
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String [] urlParams = {"resid","todayDate"};
                                //pass todayDate in later stage
                                String [] urlValues = {resid,"2018-02-02"};
                                return RestClient.findTotalUsageForResidDaily(urlParams,urlValues);
                            }
                            @Override
                            protected void onPostExecute(String result) {
                                Double[] total = RestClient.findTotalUsageForResidDailyFetch(result);
                                usageTextView.setText(String.valueOf(total[0]+total[1]+total[2]));
                            }
                        }.execute();
                    }
                    else{
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                String [] urlParams = {"resid","todayDate","currentTimeHH"};
                                //pass todayDate and currentTimeHH in later stage
                                String [] urlValues = {resid,"2018-03-03","11"};
                                return RestClient.findTotalUsageForResidHourly(urlParams,urlValues);
                            }
                            @Override
                            protected void onPostExecute(String result) {
                                usageTextView.setText(String.valueOf(RestClient.findTotalUsageForResidHourlyFetch(result)));
                            }
                        }.execute();
                    }
                    return true;
                }
                return true;
            }
        });
    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }
    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }
    @Override
    public void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }
    @Override
    public void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
}
