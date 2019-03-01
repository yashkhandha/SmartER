package edu.monash.ykha0002.assignment2;

/**
 * Created by yashkhandha on 24/04/2018.
 */

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class RestClient {

    private static final String BASE_URI =
            "http://192.168.206.1:16432/SmartER/webresources";
    /**
     * to fetch usage for a resident
     * @param resid
     * @return textResult
     */
    public static String findUsageForResid(int resid) {
        final String methodPath = "/restws.electricityusage/findByResid/" + resid;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");

            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    /**
     * parse JSON
     * @param result
     * @return snippet
     */
    public static Object getSnippet(String result){
        JSONObject snippet = null;
        try{
            JSONArray jsonArray1 = new JSONArray(result);
            System.out.println("1="+jsonArray1);
            JSONObject jsonObject = jsonArray1.getJSONObject(0);
            System.out.println("2="+jsonObject);
            String fridgeusage = jsonObject.getString("fridgeusage");
            if(jsonObject != null ) {
                snippet =jsonObject;
            }
        }catch (Exception e){
            e.printStackTrace();
            //snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    /**
     * to fetch total usage for a resident on hourly basis
     * @param params
     * @param values
     * @return textResult
     */
    public static String findTotalUsageForResidHourly(String[] params, String[] values) {
        final String methodPath = "/restws.electricityusage/findHourlyusageOfAllAppliance";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        String query_parameter = "";
            if (params != null && values != null) {
                for (int i = 0; i < params.length; i++) {
                    query_parameter += "/";
                    query_parameter += values[i];
                }
            }
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath + query_parameter);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");

            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    /**
     * parse JSON received
     * @param result
     * @return totalUsage
     */
    public static Double findTotalUsageForResidHourlyFetch(String result){
        JSONObject snippet = null;
        double totalUsage = 0.00;
        try{
            JSONArray jsonArray1 = new JSONArray(result);
            System.out.println("1="+jsonArray1);
            JSONObject jsonObject = jsonArray1.getJSONObject(0);
            System.out.println("2="+jsonObject);
            double fridgeUsage = jsonObject.getDouble("fridgeusage");
            double airconditionerUsage = jsonObject.getDouble("airconditionerusage");
            double washingmachineUsage = jsonObject.getDouble("washingmachineusage");
            totalUsage = fridgeUsage + airconditionerUsage + washingmachineUsage;
        }catch (Exception e){
            e.printStackTrace();
            //snippet = "NO INFO FOUND";
        }
        return totalUsage;
    }

    /**
     * to fetch total usage on daily basis
     * @param params
     * @param values
     * @return texxtResult
     */
    public static String findTotalUsageForResidDaily(String[] params, String[] values) {
        final String methodPath = "/restws.electricityusage/findDailyUsageForOneResident";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
            String query_parameter = "";
            if (params != null && values != null) {
                for (int i = 0; i < params.length; i++) {
                    query_parameter += "/";
                    query_parameter += values[i];
                }
            }
        //Making HTTP request
        try {
            url = new URL(BASE_URI + methodPath+query_parameter);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");

            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    /**
     * to parse JSON
     * @param result
     * @return totalUsage
     */
    public static Double[] findTotalUsageForResidDailyFetch(String result){
        JSONObject snippet = null;
        Double[] totalUsage = null;
        try{
            JSONArray jsonArray1 = new JSONArray(result);
            System.out.println("1="+jsonArray1);
            JSONObject jsonObject = jsonArray1.getJSONObject(0);
            System.out.println("2="+jsonObject);
            double fridgeUsage = jsonObject.getDouble("fridge usage");
            double airconditionerUsage = jsonObject.getDouble("airconditioner usage");
            double washingmachineUsage = jsonObject.getInt("washingmachine usage");
            totalUsage = new Double[]{fridgeUsage, airconditionerUsage, washingmachineUsage};
        }catch (Exception e){
            e.printStackTrace();
        }
        return totalUsage;
    }

    /**
     * to fetch coordinates from geocoding address
     * @param params
     * @param values
     * @return textResult
     */
    public static String getCoOrdinates(String[] params, String[] values){
            //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        char[] c = null;
       String query_parameter = "address=";
       //parsing address in required format for google geocoding
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                c = values[i].toCharArray();
                for (int j = 0; j < c.length;j++){
                    if( c[j] == ' '){
                        c[j] = '+';
                    }
                }
                char[] x = c;
                System.out.println(x);
                query_parameter += String.valueOf(c);
            }

            }


        //Making HTTP request
        try {
            url = new URL("https://maps.googleapis.com/maps/api/geocode/json?" + query_parameter + "&key=AIzaSyCJ9HKJzuANUwAvA_hsi-azm-CiTX8K-Sg");

            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");

            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    /**
     * parse JSON to get coordinates
     * @param result
     * @return
     */
    public static String[] getCoOrdinatesFetch(String result){
        String coordinates[] = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray root = (JSONArray) jsonObject.get("results");
            JSONObject details = root.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            String latitude = details.getString("lat");
            String longitude = details.getString("lng");
            coordinates = new String[]{latitude, longitude};

        }catch (Exception e){
            e.printStackTrace();
        }
        return coordinates;
    }

    //login check - get all users
    public static String findAllresidents() {
        final String methodPath = "/restws.residentcredential/findAllResidents";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
    /**
     * convert to hashmap to check users
     * @param result
     * @return
     */
    public static HashMap findAllresidentsFetch(String result){
        HashMap<String,String> hmap = new HashMap<>();
        String[] details = null;
        try{
            JSONArray jsonArray1 = new JSONArray(result);
            System.out.println("1="+jsonArray1);
            for(int i =0;i<jsonArray1.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray1.get(i);
                String passwordHash = jsonObject.getString("passwordhash");
                JSONObject js2 = jsonObject.getJSONObject("resid");
                String email = js2.getString("email");
                hmap.put(passwordHash,email);
            }System.out.println("HMAP = "+hmap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return hmap;
    }

    //get resident id of logged in user using email
    public static String findResidentId(String[] params, String[] values) {
        final String methodPath = "/restws.resident/findByEmail";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";

        String query_parameter = "";
        if (params != null && values != null) {
            for (int i = 0; i < params.length; i++) {
                query_parameter += "/";
                query_parameter += values[i];
            }
        }
        try {
            url = new URL(BASE_URI + methodPath+query_parameter);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }
    /**
     * to fetch resident object
     * @param result
     * @return resident
     */
    public static Resident findResidentIdFetch(String result){

        Resident resident = null;
        String address;
        String email;
        String firstname;
        String postcode;
        int resid;

        try{
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            address = jsonObject.getString("address");
            email = jsonObject.getString("email");
            postcode = jsonObject.getString("postcode");
            resid = Integer.parseInt(jsonObject.getString("resid"));
            firstname = jsonObject.getString("firstname");


            resident = new Resident(resid,email,address,postcode,firstname);


        }catch (Exception e){
            e.printStackTrace();
        }
        return resident;
    }

    /**
     * POST to create a new resident in REST webservices
     */
    public static void createResident(Resident resident, ResidentCredential credential){

        //generate random resid here
        int resid = new Random().nextInt(10000);
        resident.setResid(resid);
        credential.setResid(resident);

        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/restws.resident/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(resident);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            //credentials

            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            createResidentCredential(credential);
            conn.disconnect();
        }
    }

    /**
     * POST to enter details in Resident credential table in REST web services
     * @param credential
     */
    public static void createResidentCredential(ResidentCredential credential){

        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/restws.residentcredential/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(credential);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static String createUsage(ElectricityUsage usage) {

        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath="/restws.electricityusage/";
        try {
            Gson gson =new Gson();
            String stringCourseJson=gson.toJson(usage);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length);
            //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json");
            //Send the POST out
            PrintWriter out= new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            //credentials

            Log.i("error",new Integer(conn.getResponseCode()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;

    }
}
