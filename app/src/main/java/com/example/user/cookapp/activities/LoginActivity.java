package com.example.user.cookapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.cookapp.async_tasks.HttpRequest;
import com.example.user.cookapp.utils.Utils;
import com.example.user.cookingapp.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    private EditText usernameEditText, passwordEditText;
    private Button loginButton, registerButton;
    private LoginButton facebookButton;
    private ConstraintLayout loginLayoutFile;
    private CallbackManager callbackManager;
    private Intent redirectLoginFacebook;
    private CheckBox rememberMeCheckBox;
    private Context context;

    public static final String REQUEST_TYPE     = "login";
    public static final String STORE_FACEBOOK   = "facebook_login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /* Check if seesion with FACEBOOK is setted*/
        checkFacebookSession();

        loginLayoutFile     = (ConstraintLayout) findViewById(R.id.loginLayoutFile);
        usernameEditText    = (EditText)    findViewById(R.id.usernameText);
        passwordEditText    = (EditText)    findViewById(R.id.passwordText);
        loginButton         = (Button)      findViewById(R.id.loginButton);
        registerButton      = (Button)      findViewById(R.id.registerButton);
        facebookButton      = (LoginButton) findViewById(R.id.facebookLoginButton);
        rememberMeCheckBox  = (CheckBox)    findViewById(R.id.rememberMeCheckBox);
        context             = LoginActivity.this;

        SharedPreferences sharedPrefs = getSharedPreferences("loginCredentials", MODE_PRIVATE);

        if(sharedPrefs.contains("user_remember_me")) {

            if( sharedPrefs.getBoolean("user_remember_me", false) == true ) {

                usernameEditText.setText(sharedPrefs.getString("user_email", ""));
                passwordEditText.setText(sharedPrefs.getString("user_password", ""));
                rememberMeCheckBox.setChecked(true);

            }
        }

        /**
        *   Facebook have some public permissions and private permissions
        *   All type permissions are published here https://developers.facebook.com/docs/facebook-login/permissions#reference-public_profile
        *   If a information need permission to be visible/read you have to set a read permission like this
        */
        facebookButton.setReadPermissions("email");
        callbackManager     = CallbackManager.Factory.create();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCredentials();

            }
        });

        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        /**
                        * Store the information from facebook account
                        */
                        try {

                            String facebookUserId, facebookUserEmail, facebookUserFirstName, facebookUserLastName;

                            facebookUserId          = object.getString("id");
                            facebookUserEmail       = object.getString("email");
                            facebookUserFirstName   = object.getString("first_name");
                            facebookUserLastName    = object.getString("last_name");

                            /*
                            *   If the account is new then store the facebook account login information on database
                            * */
                            new HttpRequest(context, loginLayoutFile).execute(Utils.REQUEST_LINK, STORE_FACEBOOK, String.valueOf(facebookUserId), facebookUserEmail, facebookUserFirstName, facebookUserLastName);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                /**
                 * Create a bundle with all field that we want to receive from facebook account
                 */
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id ,first_name, last_name, email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        Bundle longBundle = getIntent().getExtras();
        if(longBundle != null && longBundle.getString("üser_first_name") != null) {
            if(longBundle.getBoolean("remember_me") != true) {

                usernameEditText.setText("");
                passwordEditText.setText("");
                rememberMeCheckBox.setChecked(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        context = null;
    }

    private void checkFacebookSession() {

        /* Store if is login with facebook */
        /* If it is logged in with facebook redirect automaticaly to MainActivity */
        boolean facebookLoggedIn = AccessToken.getCurrentAccessToken() == null;
        if(facebookLoggedIn == false) {
            redirectLoginFacebook = new Intent(this, MainActivity.class);
            startActivity(redirectLoginFacebook);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(LoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
    *   Check if username and password is completed.
    *   If there are completed create an request for server to check credentials if exists
    */
    private void checkCredentials() {

        if(usernameEditText.getText().toString().length() == 0 || passwordEditText.getText().toString().length() == 0) {

            Snackbar.make(findViewById(android.R.id.content), "Please complete all the fields",Snackbar.LENGTH_LONG).show();

        } else {

            new HttpRequest(this, loginLayoutFile).execute(Utils.REQUEST_LINK, REQUEST_TYPE, usernameEditText.getText().toString(), passwordEditText.getText().toString(), String.valueOf(rememberMeCheckBox.isChecked()));
        }

    }
}
