package edu.monash.ykha0002.assignment2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mLastName;
    private EditText mDateOfBirth;
    private EditText mAddress;
    private EditText mPostCode;
    private EditText mMobile;
    private EditText mEmail;
    private Spinner mNoOfResidents;
    private Spinner mEnergyProvider;
    private EditText mUserName;
    private EditText mPassword;
    private TextView mSelectDate;

    private View mProgressView;
    private View mRegisterFormView;

    Calendar mCurrentDate;
    int day, month, year;
    Resident resident;
    ResidentCredential credential;
    String emails[] = null;


    private UserRegisterTask mAuthTask = null;

    /**
     * onCretae to initialise
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastName);
        mDateOfBirth = findViewById(R.id.dateofbirth);
        mAddress = findViewById(R.id.address);
        mPostCode = findViewById(R.id.postcode);
        mMobile = findViewById(R.id.mobile);
        mEmail = findViewById(R.id.emailId);
        mNoOfResidents = findViewById(R.id.numberofresidents);
        mEnergyProvider = findViewById(R.id.energyprovider);
        mUserName = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mSelectDate = findViewById(R.id.selectDate);

        mCurrentDate = Calendar.getInstance();
        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        Intent i = this.getIntent();
        emails = i.getStringArrayExtra("emails");

        mSelectDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                setDate();
            }
        });
        //attempt to register

        Button mRegisterButtonFinal = (Button)findViewById(R.id.registerButtonRegister);
        mRegisterButtonFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    attemptRegister();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        mProgressView = findViewById(R.id.register_progress);
        mRegisterFormView = findViewById(R.id.register_form);
    }

    //parsing and formatting date from datepicker
    private void setDate(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                mDateOfBirth.setText(formattedDayOfMonth + "-" + (formattedMonth) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void attemptRegister() throws ParseException {

        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mFirstName.setError(null);
        mLastName.setError(null);
        mDateOfBirth.setError(null);
        mAddress.setError(null);
        mPostCode.setError(null);
        mMobile.setError(null);
        mEmail.setError(null);
        mUserName.setError(null);
        mPassword.setError(null);


        // Store values at the time of the register attempt
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        String dateOfBirth = mDateOfBirth.getText().toString();
        String address = mAddress.getText().toString();
        String postCode = mPostCode.getText().toString();
        String mobile = mMobile.getText().toString();
        String email = mEmail.getText().toString();
        String noOfResidents = mNoOfResidents.getSelectedItem().toString();
        String energyProvider = mEnergyProvider.getSelectedItem().toString();
        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        String registrationDate = null;

        boolean cancel = false;
        View focusView = null;

        //to check blank first name
        if(TextUtils.isEmpty(firstName)){
            mFirstName.setError("Please enter First Name");
            focusView = mFirstName;
            cancel = true;
        }
        //blank last name
        else if(TextUtils.isEmpty(lastName)){
            mLastName.setError("Please enter Surname");
            focusView = mLastName;
            cancel = true;
        }
        else if(TextUtils.isEmpty(dateOfBirth)){
            mDateOfBirth.setError("Please enter Date of birth");
            focusView = mDateOfBirth;
            cancel = true;
        }
        else if(TextUtils.isEmpty(address)){
            mAddress.setError("Please enter Address");
            focusView = mAddress;
            cancel = true;
        }
        else if(TextUtils.isEmpty(postCode)){
            mPostCode.setError("Please enter Postcode");
            focusView = mPostCode;
            cancel = true;
        }
        else if (postCode.length()<4 || postCode.length()>4 || !TextUtils.isDigitsOnly(postCode)){
            mPostCode.setError("Please enter 4 digit Postcode only");
            focusView = mPostCode;
            cancel = true;
        }
        else if(TextUtils.isEmpty(mobile)){
            mMobile.setError("Please enter Mobile number");
            focusView = mMobile;
            cancel = true;
        }
        //mobile no should be 10 digit only
        else if (mobile.length() < 10 || mobile.length() > 10 || !TextUtils.isDigitsOnly(mobile)){
            mMobile.setError("Please enter 10 digit Mobile number only");
            focusView = mMobile;
            cancel = true;
        }
        else if(TextUtils.isEmpty(email)){
            mEmail.setError("Please enter Email");
            focusView = mEmail;
            cancel = true;
        }
        else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }
        else if(TextUtils.isEmpty(userName)){
            mUserName.setError("Please enter username");
            focusView = mUserName;
            cancel = true;
        }
        else if(TextUtils.isEmpty(password)){
            mPassword.setError("Please enter password");
            focusView = mPassword;
            cancel = true;
        }
        //password has to be 6 characters atleast
        else if(!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }
        //email already exists
        else{
            for(int i = 0; i<emails.length; i++){
                if(email.equals(emails[i])){
                    mEmail.setError("User with email already exist. Try different email");
                    focusView = mEmail;
                    cancel = true;
                }
            }
        }

        if(cancel){
            focusView.requestFocus();
        }
        else{
            showProgress(true);
            //to convert date to format needed to post in JSON on restful POST method
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(dateOfBirth);
            LocalDateTime localDateTime=null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Instant instant = date.toInstant();
                localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                dateOfBirth = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime);
            }
            LocalDateTime localDateTime1=null;
            Date date1=new Date();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Instant instant = date1.toInstant();
                localDateTime1 = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
                registrationDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime1);
            }
            String shaPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString().toUpperCase();

            resident = new Resident(firstName,lastName,dateOfBirth,address,postCode,email,mobile,Integer.parseInt(noOfResidents),energyProvider);
            credential = new ResidentCredential(userName,shaPassword,registrationDate);
            mAuthTask = new UserRegisterTask(resident,credential);
            mAuthTask.execute((Void)null);
        }
    }

    private boolean isEmailValid(String email) {
        return (email.contains("@"));
    }

    private boolean isPasswordValid(String password) {
        return (password.length() >= 6);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mFirstName;
        private final String mLastname;
        private final String mDateOfBirth;
        private final String mAddress;
        private final String mPostCode;
        private final String mMobile;
        private final String mEmail;
        private final int mNoOfResidents;
        private final String mEnergyProvider;
        private final String mUserName;
        private final String mPassword;


        UserRegisterTask(Resident resident, ResidentCredential credential) {
            mFirstName = resident.getFirstname();
            mLastname = resident.getSurname();
            mDateOfBirth = resident.getDateofbirth();
            mAddress = resident.getAddress();
            mPostCode = resident.getPostcode();
            mMobile = resident.getMobilenumber();
            mEmail = resident.getEmail();
            mNoOfResidents = resident.getNumberofresidents();
            mEnergyProvider = resident.getEnergyprovider();
            mUserName = credential.getUsername();
            mPassword = credential.getPasswordhash();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            RestClient.createResident(resident,credential);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
               Toast.makeText(getApplicationContext(),"Registered Successfully ! ",Toast.LENGTH_LONG).show();
                finish();
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Oops");
                builder.setMessage("Something went wrong. Please try again !! ");
                builder.setPositiveButton("ok", null);
                builder.show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}
