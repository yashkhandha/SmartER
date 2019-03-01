package edu.monash.ykha0002.assignment2;

import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by yashkhandha on 25/04/2018.
 */

public class EnergySimulator implements Runnable{
    Random r = null;
    int count = 0;

    @Override
    public void run() {

    }

    //generates random value between 0.3 and 0.8 inclusive for fridge usage (0.1 stepsize)
    public Double generateFridgeUsage() {
        r = new Random();
            double d1 = (r.nextInt(6) + 3) / 10.0;
            System.out.println(d1);
        return d1;
    }

    //generates random value between 1-5 inclusive for air conditioner usage (0.1 stepsize)
    public Double generateAirConditionerUsage() {
        r = new Random();
            double d2 = (r.nextInt(41) + 10) / 10.0;
            System.out.println(d2);
        return d2;
    }

    //generates random value between 0.4-1.3 inclusive for washing machine usage (0.1 stepsize)
    public Double generateWashingMachineUsage() {
        r = new Random();
            double d3 = (r.nextInt(91) + 13) / 10.0;
        return d3;
    }
    /**
     * generates random number for usageId
     */
    public int generateUsageId(){
        return new Random().nextInt(1000);
    }

    /**
     *  creates usage object with random values in range for all appliances
     */
    public ElectricityUsage createUsage() throws ParseException {

        Date date = new Date();

        SimpleDateFormat timeFormatHH = new SimpleDateFormat("HH");
        final String currentTimeHH = timeFormatHH.format(date);
        final int currentTimeHHInt = Integer.parseInt(currentTimeHH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = dateFormat.format(date);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date formattedDate = formatter.parse(currentDate);
        LocalDateTime localDateTime=null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Instant instant = formattedDate.toInstant();
            localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            currentDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
        }

        Double d1,d2,d3;
        d1 = generateFridgeUsage();

        if(currentTimeHHInt >= 9 && currentTimeHHInt <= 22){
            if(count<=10){
                d2 = generateAirConditionerUsage();
                count++;
            }
            else{
                d2 = 0.0;
            }
        }
        else{
            d2 = 0.0;
        }
        if(currentTimeHHInt == 23){
            count=0;
        }

        if (currentTimeHHInt >= 9 && currentTimeHHInt <= 11){
            d3 = generateWashingMachineUsage();
        }
        else{
            d3 = 0.0;
        }

        int usageId = generateUsageId();

        ElectricityUsage usage = new ElectricityUsage(usageId,currentDate,currentTimeHHInt,d1,d2,d3);

        return usage;

    }
}