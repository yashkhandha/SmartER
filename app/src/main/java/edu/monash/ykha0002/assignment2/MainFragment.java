package edu.monash.ykha0002.assignment2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by yashkhandha on 25/04/2018.
 */

public class MainFragment extends Fragment {

    View vMain;
    TextView tempView,usageText,messageText,firstNameText;
    ImageView image;
    Button sendButton;
    String[] coordinates = null;
    private DBHelperElectricityUsage dbHelperElectricityUsage;
    int currentTimeHHInt;
    Double totalUsage = 0.0;
    String currentTimeHH,currentTime,currentDate,temperatureValue = null;
    Resident resident = null;
    ElectricityUsage usage = null;
    boolean status = false;
    ScheduledExecutorService scheduledExecutorService;
    private View mProgressView;

    /**
     * method onCreateView initialised first
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return vMain
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Bundle bundle = this.getArguments();
        resident = bundle.getParcelable("resident");

        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        sendButton = (Button)vMain.findViewById(R.id.sendButton);
        tempView =(TextView)vMain.findViewById(R.id.tempTextView);
        usageText = (TextView)vMain.findViewById(R.id.totalUsageText);
        messageText = (TextView)vMain.findViewById(R.id.messageText);
        ImageView image = (ImageView)vMain.findViewById(R.id.image);
        mProgressView = vMain.findViewById(R.id.progressBarMain);
        firstNameText = vMain.findViewById(R.id.firstNameText);
        scheduledExecutorService = Executors
                .newSingleThreadScheduledExecutor();

        //async task to get coordinates based on logged in user
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String [] urlParams = {"address","postcode"};
                String [] urlValues = {resident.getAddress(), resident.getPostcode()};
                return RestClient.getCoOrdinates(urlParams,urlValues);
            }
            @Override
            protected void onPostExecute(String result) {
                coordinates = RestClient.getCoOrdinatesFetch(result);
                bundle.putStringArray("coordinates", coordinates);
            }
        }.execute();

        //generateUsage();

        //for getting usage values for every hour
        Runnable task = new Runnable(){
            public void run(){
                generateUsage();
                setView();
            }
        };
        long delay = 0;
        long periodicDelay = 1;
        scheduledExecutorService.scheduleAtFixedRate(task,delay,periodicDelay, TimeUnit.HOURS);
        //scheduledExecutorService.shutdown();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String[] urlParams = {"lat", "lon", "units"};
                        String[] urlValues = {coordinates[0], coordinates[1], "metric"};
                        return SearchWeatherAPI.search(urlParams, urlValues);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        temperatureValue = SearchWeatherAPI.getSnippet(result);
                        tempView.setText(temperatureValue);
                        status = true;
                        mProgressView.setVisibility(View.GONE);
                        //setView();
                    }
                }.execute();
            }
        },2000);
        //async task to get weather based on coordinates from openweatherapi

        //set the values on screen
        //setView();

        //button to send data to RESTFul and delete from SQLite
        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                usage.setTemperature(Double.parseDouble(temperatureValue));
                new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        RestClient.createUsage(usage);
                        return true;
                    }

                    @Override
                    protected void onPostExecute(final Boolean success) {
                        dbHelperElectricityUsage.deleteUsage(usage);
                    }
                }.execute();
            }
        });

        return vMain;
    }

    public void setView(){

        firstNameText.setText(resident.getFirstname());

        //for date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();
        currentDate = dateFormat.format(date);
        TextView dateView = (TextView)vMain.findViewById(R.id.dateTextView);
        dateView.setText(currentDate);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        currentTime = timeFormat.format(date);
        TextView timeView = (TextView)vMain.findViewById(R.id.timeTextView);
        timeView.setText(currentTime);

        SimpleDateFormat timeFormatHH = new SimpleDateFormat("HH");
        currentTimeHH = timeFormatHH.format(date);
        currentTimeHHInt = Integer.parseInt(currentTimeHH);

        usageText = (TextView)vMain.findViewById(R.id.totalUsageText);
        messageText = (TextView)vMain.findViewById(R.id.messageText);
        image = (ImageView)vMain.findViewById(R.id.image);

        //  to check if time and usage is in threshold bracket

        if(currentTimeHHInt >=9 || currentTimeHHInt <= 22){
                if(totalUsage > 1.50) {
                    messageText.setText("Usage limit over threshold");
                    image.setImageResource(R.drawable.warning);
                }
                else{
                    messageText.setText("Usage limit under threshold");
                    image.setImageResource(R.drawable.good);
                }
            }
            else{
                messageText.setText("Usage limit under threshold");
                image.setImageResource(R.drawable.good);
            }

    }
    /**
     * generates usage for all appliances in SQLite when user logs in
     */
    private void generateUsage() {
        EnergySimulator simulator = new EnergySimulator();
        try {
            usage = simulator.createUsage();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        usage.setResid(resident);
        if(status) {
            usage.setTemperature(Double.parseDouble(temperatureValue));
        }
        dbHelperElectricityUsage = new DBHelperElectricityUsage(getActivity().getApplicationContext());
        DecimalFormat df2 = new DecimalFormat(".##");
        totalUsage = usage.getFridgeusage()+ usage.getAirconditionerusage()+usage.getWashingmachineusage();
        usageText.setText(df2.format(totalUsage));

        dbHelperElectricityUsage.addUsage(usage);

       /* if(usage.getUsagehour() == 20){
            //code for writing to db
            HashMap<Integer,ElectricityUsage> map = new LinkedHashMap<>();
            map = dbHelperElectricityUsage.getAllUsage();

        }*/

    }
}

