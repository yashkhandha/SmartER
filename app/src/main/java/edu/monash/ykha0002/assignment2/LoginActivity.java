package edu.monash.ykha0002.assignment2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    HashMap<String,String> details;
    Resident resident = null;
    String emails[] = null;
    
    /**
     * onCreate method to load on create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        //async task to find all the residents in the database for checking login
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return RestClient.findAllresidents();
            }
            @Override
            protected void onPostExecute(String result) {
                details = new HashMap();
                details = RestClient.findAllresidentsFetch(result);
            }
        }.execute();


        //button to sign in
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                attemptLogin();
            }

        });
        //registration button to start registration activity
        Button mRegisterButton = (Button)findViewById(R.id.registerButton);
        mRegisterButton.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                emails = new String[details.size()];
                Set set = details.entrySet();
                Iterator iterator = set.iterator();
                int i = 0;
                while(iterator.hasNext()){
                    Map.Entry mEntry = (Map.Entry)iterator.next();
                    emails[i] = mEntry.getValue().toString();
                    i++;
                }
                Intent intent2 = new Intent(LoginActivity.this,RegisterActivity.class);
                intent2.putExtra("emails",emails);
                startActivity(intent2);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        //async task to find the resident information of the logged in user for future reference

        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String [] urlParams = {"email"};
                String [] urlValues = {email};
                return RestClient.findResidentId(urlParams,urlValues);
            }
            @Override
            protected void onPostExecute(String result) {
                resident = RestClient.findResidentIdFetch(result);

            }
        }.execute();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        //check if email password exist and same

        else if(!isUserValid(email,password)){
            mEmailView.setError("Please check your email");
            mPasswordView.setError("Password does not match. Please try again!");
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    //to check valid email

    private boolean isEmailValid(String email) {
        return (email.contains("@"));
    }
    //to check for valid password

    private boolean isPasswordValid(String password) {
        return (password.length() > 6);
    }
    //to check if user email and password match in database

    private boolean isUserValid(String email, String password){

        String shaPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString().toUpperCase();
        Set set = details.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mEntry = (Map.Entry)iterator.next();
            String dbPassword = mEntry.getKey().toString();
            String dbEmail = mEntry.getValue().toString();

            boolean s = dbEmail.equals(email);
            boolean d = dbPassword.equals(shaPassword);
            if(s){
                if(d){
                return true;}
            }
        }
        return false;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }


    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            if (success) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("resident",resident);
                startActivity(intent);
                clearForm();

            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        protected void clearForm(){
            mEmailView = findViewById(R.id.email);
            mEmailView.setText("");
            mPasswordView = findViewById(R.id.password);
            mPasswordView.setText("");
        }

    }
}

