package com.example.user.cookapp.async_tasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.facebook.AccessToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.example.user.cookapp.activities.MainActivity;

/**
 * Created by user on 08.02.2018.
 */

public class HttpRequest extends AsyncTask<String, String, JSONObject> {

    private JSONObject sendJsonInfo     = new JSONObject();
    private JSONObject resultFromServer = new JSONObject();
    private String code, message, userEmail, userFirstName, userLastName = "";
    private boolean facebookLoggedIn = false;
    private Bundle loginCredentialsBundle = new Bundle();
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ConstraintLayout parentLayout;
    private ProgressDialog progressDialog;
    private Intent loginIntent;

    public HttpRequest(Context context, ConstraintLayout parentLayout) {

        this.context        = context;
        this.parentLayout   = parentLayout;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        try {

            switch(strings[1]) {

                case "login"            :   createJsonToSendOnTheServerForLogin(strings);           break;
                case "facebook_login"   :   createJsonToSendOnTheServerForFacebookLogin(strings);   break;
            }

            /* Create the url where to make the request */
            URL urlRequest = new URL(strings[0]);

            //Create a connection
            HttpURLConnection connection =(HttpURLConnection) urlRequest.openConnection();
            connection.setReadTimeout(15000); /* milliseconds */
            connection.setConnectTimeout(15000); /* milliseconds */
            connection.setRequestMethod("POST"); /* Set the method type */
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(sendJsonInfo.toString());  /* Converting a json to string and sended to server */
            wr.flush();

            /**
            *  Storing the response code ( Ex: 404, 200, 402 ) from the server
            */
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                /* Read the output from server */
                BufferedReader serverAnswer = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                /**
                *  Convert the output from server into a jsonObject
                */
                resultFromServer = new JSONObject(serverAnswer.readLine());

            } else {

                resultFromServer    = new JSONObject("{\"code\":\"There is a problem connecting with the server!\",\"user_id\":\"\",\"user_email\":\"\",\"user_first_name\":\"\",\"user_last_name\":\"\"}");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultFromServer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = ProgressDialog.show(context, "Loading", "Wait while loading...");
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);

        progressDialog.dismiss();

        /**
        *   Check if json from server is empty.
        *   If it is empty there is a problem connectiong with server
        *   If login was made with facebook there is no posibility to store the facebook account information
        * */
        if (jsonObject.length() != 0) {

            try {
                code                = jsonObject.getString("code");
                message             = jsonObject.getString("message");
                userEmail           = jsonObject.getString("user_email");
                userFirstName       = jsonObject.getString("user_first_name");
                userLastName        = jsonObject.getString("user_last_name");
                facebookLoggedIn    = jsonObject.getBoolean("facebook_logged_in");

                /**
                *   If code received from server is error display a snackbar
                *   If code received is success store credentials in a bundle the redirect to MainActivity
                */
                if (code.equals("error")) {

                    Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
                    snackbar.show();

                } else {

                    loginCredentialsBundle.putString("user_email", userEmail);
                    loginCredentialsBundle.putString("user_first_name", userFirstName);
                    loginCredentialsBundle.putString("user_last_name", userLastName);
                    loginCredentialsBundle.putBoolean("facebook_logged_in", facebookLoggedIn);
                    loginIntent = new Intent(context, MainActivity.class);
                    loginIntent.putExtra("loginCredentialsBundle", loginCredentialsBundle);
                    context.startActivity(loginIntent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

            /**
            *   Store the facebook logged in ( If there was a problem with connection with the server but the user logged with facebook )
            *   If login was successfully redirect to MainActivity
            */
            facebookLoggedIn = AccessToken.getCurrentAccessToken() == null;
            if(facebookLoggedIn == false) {

                loginIntent = new Intent(context, MainActivity.class);
                context.startActivity(loginIntent);

            } else {

                message = "There is a problem connectin with the server! Please try again later!";
                Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    /**
    *   Create the Json for login type
    */
    private void createJsonToSendOnTheServerForLogin(String[] strings){

        try {
            sendJsonInfo.put("type", strings[1]);
            sendJsonInfo.put("email", strings[2]);
            sendJsonInfo.put("password", strings[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
    * Create the json for facebook login
    */
    private void createJsonToSendOnTheServerForFacebookLogin(String[] strings) {

        try {

            sendJsonInfo.put("type", strings[1]);
            sendJsonInfo.put("idFacebook", strings[2]);
            sendJsonInfo.put("emailFacebook", strings[3]);
            sendJsonInfo.put("firstNameFacebook", strings[4]);
            sendJsonInfo.put("lastNameFacebook", strings[5]);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
