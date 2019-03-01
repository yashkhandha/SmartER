package edu.monash.ykha0002.assignment2;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/** Handling report generation
 * Created by yashkhandha on 25/04/2018.
 */

public class ReportsFragment extends Fragment {

    //initialise
    View vReports;
    Spinner graphType, hourlyDailySpinner;
    Button showButton;
    TextView dateTV;
    Calendar mCurrentDate;
    int day, month, year;
    PieChart pieChart;
    LineChart lineChart,lineChart2;
    BarChart barChart;
    Double[] usages;
    private View mProgressView;
    Resident resident;

    /**
     * to initialize view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return vReport
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Bundle bundle = this.getArguments();
        resident = bundle.getParcelable("resident");
        vReports = inflater.inflate(R.layout.fragment_reports, container, false);
        graphType = (Spinner)vReports.findViewById(R.id.graphTypeSpinner);
        dateTV = (TextView)vReports.findViewById(R.id.datePickerGraph);
        hourlyDailySpinner = (Spinner)vReports.findViewById(R.id.hourlyDailySpinner);
        pieChart = (PieChart)vReports.findViewById(R.id.pieChart);
        lineChart = (LineChart)vReports.findViewById(R.id.lineChart);
        lineChart2 = (LineChart)vReports.findViewById(R.id.lineChart2);
        barChart = (BarChart)vReports.findViewById(R.id.barChart);
        showButton = (Button)vReports.findViewById(R.id.showButton1);
        mProgressView = vReports.findViewById(R.id.progressBarReport);
        Button select = (Button)vReports.findViewById(R.id.selectButton);

        //on clicking the value in spinner for type of graph
        graphType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if pie chart is selected
                if ((graphType.getSelectedItem().toString()).equals("Pie chart")) {

                    //setting visibilities to handle different components on the same page
                    mCurrentDate = Calendar.getInstance();
                    day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
                    month = mCurrentDate.get(Calendar.MONTH);
                    year = mCurrentDate.get(Calendar.YEAR);
                    lineChart.setVisibility(View.INVISIBLE);
                    barChart.setVisibility(View.INVISIBLE);
                    hourlyDailySpinner.setVisibility(View.INVISIBLE);
                    select.setVisibility(View.GONE);
                    showButton.setVisibility(View.INVISIBLE);
                    dateTV.setVisibility(View.VISIBLE);
                    dateTV.setText("Select date");

                }
                //if bar graph is selected
                else if((graphType.getSelectedItem().toString()).equals("Bar graph")){
                    //setting visibilities to handle different components on the same page

                    dateTV.setVisibility(View.GONE);
                    pieChart.setVisibility(View.INVISIBLE);
                    lineChart.setVisibility(View.INVISIBLE);
                    select.setVisibility(View.VISIBLE);
                    showButton.setVisibility(View.INVISIBLE);
                    barChart.setVisibility(View.INVISIBLE);
                    hourlyDailySpinner.setVisibility(View.VISIBLE);
                }
                //if line graph is selected
                else if ((graphType.getSelectedItem().toString()).equals("Line graph")){
                    //setting visibilities to handle different components on the same page

                    dateTV.setVisibility(View.GONE);
                    pieChart.setVisibility(View.INVISIBLE);
                    select.setVisibility(View.VISIBLE);
                    showButton.setVisibility(View.INVISIBLE);
                    barChart.setVisibility(View.INVISIBLE);
                    lineChart.setVisibility(View.INVISIBLE);
                    hourlyDailySpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //on clicking the date field show datepicker
        dateTV.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                select.setVisibility(View.GONE);
                showButton.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        monthOfYear+=1;
                        String formattedMonth = "" + monthOfYear;
                        String formattedDayOfMonth = "" + dayOfMonth;

                        //formatting the correct date value
                        if(monthOfYear < 10){
                            formattedMonth = "0" + monthOfYear;
                        }
                        if(dayOfMonth < 10){
                            formattedDayOfMonth = "0" + dayOfMonth;
                        }
                        dateTV.setText(year + "-" + (formattedMonth) + "-" + formattedDayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
                select.setVisibility(View.VISIBLE);
            }
        });

        //to confirm the selected items and search
        select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String selectedOption = graphType.getSelectedItem().toString();
                showButton.setVisibility(View.VISIBLE);

                if(selectedOption.equals("Pie chart")){
                    String selectedDate = dateTV.getText().toString();
                    //search for usage based on selected date
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            String[] urlParams = {"resid", "datePicked"};
                            String[] urlValues = {String.valueOf(resident.getResid()), selectedDate};
                            return RestClient.findTotalUsageForResidDaily(urlParams, urlValues);
                        }

                        @Override
                        protected void onPostExecute(String result) {
                            usages = RestClient.findTotalUsageForResidDailyFetch(result);
                        }
                    }.execute();
                }
                else if(selectedOption.equals("Line graph")){
                    dateTV.setVisibility(View.GONE);
                    hourlyDailySpinner.setVisibility(View.VISIBLE);
                }
                else if(selectedOption.equals("Bar graph")){
                    dateTV.setVisibility(View.GONE);
                    hourlyDailySpinner.setVisibility(View.VISIBLE);
                }

            }
        });

        //to show view of graph absed on selection
        showButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String selectedOption = graphType.getSelectedItem().toString();
                if(selectedOption.equals("Pie chart")){
                    if (usages == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("No data found");
                        builder.setMessage("Please select a different date");
                        builder.setPositiveButton("ok", null);
                        builder.show();
                    }
                    else {
                        pieChart.setVisibility(View.VISIBLE);
                        pieChart.setUsePercentValues(true);
                        pieChart.getDescription().setEnabled(false);
                        pieChart.setExtraOffsets(5, 10, 5, 5);

                        pieChart.setDragDecelerationFrictionCoef(0.95f);

                        pieChart.setDrawHoleEnabled(false);
                        pieChart.setHoleColor(Color.WHITE);
                        pieChart.setTransparentCircleRadius(30f);
                        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

                        ArrayList<PieEntry> yValues = new ArrayList<>();


                        yValues.add(new PieEntry((usages[0]).floatValue(), "Fridge"));
                        yValues.add(new PieEntry(usages[1].floatValue(), "Airconditioner"));
                        yValues.add(new PieEntry(usages[2].floatValue(), "WashingMachine"));

                        PieDataSet dataSet = new PieDataSet(yValues, "Appliances");
                        dataSet.setSliceSpace(2f);
                        dataSet.setSelectionShift(5f);
                        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

                        PieData data = new PieData(dataSet);
                        data.setValueTextSize(10f);
                        data.setValueTextColor(Color.BLACK);
                        pieChart.setEntryLabelColor(Color.BLACK);

                        pieChart.setData(data);
                    }

                }
                else if (selectedOption.equals("Bar graph")){
                    barChart.setVisibility(View.VISIBLE);

                    String selectedType = hourlyDailySpinner.getSelectedItem().toString();

                    ArrayList<BarEntry> barEntries = new ArrayList<>();

                    barEntries.add(new BarEntry(0f,0.5f));
                    barEntries.add(new BarEntry(1f,1.6f));
                    barEntries.add(new BarEntry(2f,2.5f));
                    barEntries.add(new BarEntry(3f,6.4f));
                    barEntries.add(new BarEntry(4f,7f));
                    barEntries.add(new BarEntry(5f,2f));


                    BarDataSet barDataSet = new BarDataSet(barEntries,"DataSet");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    String[] days = new String[]{"02-05","03-05","04-05","05-05","06-05","07-05"};

                    BarData barData = new BarData(barDataSet);
                    barChart.setData(barData);
                    barChart.setScaleEnabled(true);

                    IAxisValueFormatter formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return days[(int)value];
                        }
                    };
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setValueFormatter(formatter);

                    XAxis xAxisFromChart = barChart.getXAxis();
                    xAxisFromChart.setDrawAxisLine(true);
                    xAxisFromChart.setGranularity(1f);
                    xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);


                }
                else if (selectedOption.equals("Line graph")){
                    pieChart.setVisibility(View.GONE);
                    lineChart.setVisibility(View.VISIBLE);
                    List<Entry> entries = new ArrayList<Entry>();
                    float[] xAxis = {0f,1f,2f,3f,4f,5f,6f};
                    float[] yAxis = {20.1f,14,21,14.5f,10,12.3f,4.5f};

                    for (int i=0; i<xAxis.length; i++){
                        entries.add(new Entry(xAxis[i], yAxis[i]));
                    }

                    //for temperature records
                    List<Entry> entries2 = new ArrayList<Entry>();
                    float[] xAxis2 = {0f,1f,2f,3f,4f,5f,6f};
                    float[] yAxis2 = {24f,21,24,23.5f,30,22.5f,21};

                    for (int i=0; i<xAxis.length; i++){
                        entries2.add(new Entry(xAxis2[i], yAxis2[i]));
                    }

                    final String[] years = new String[] { "01-05", "02-05", "03-05", "04-05","05-05","06-05","07-05" };
                    IAxisValueFormatter formatter = new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return years[(int)value];
                        }
                    };
                    LineDataSet dataSet = new LineDataSet(entries, "Usage (kw)");
                    dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                    LineData lineData = new LineData(dataSet);
                    lineChart.setData(lineData);

                    //for multiple y axis
                   /* LineDataSet dataSet1 = new LineDataSet(entries2,"Temperature");
                    dataSet1.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    LineData lineData1 = new LineData(dataSet1);
                    lineChart.setData(lineData1);*/

                    XAxis xAxisFromChart = lineChart.getXAxis();
                    xAxisFromChart.setDrawAxisLine(true);
                    xAxisFromChart.setValueFormatter(formatter);
                    xAxisFromChart.setGranularity(1f);
                    xAxisFromChart.setPosition(XAxis.XAxisPosition.BOTTOM);

                }

            }

        });

        return vReports;
    }
}



