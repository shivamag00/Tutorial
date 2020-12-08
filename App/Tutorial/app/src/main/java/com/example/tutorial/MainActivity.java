package com.example.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Defining class variables
    private String username;
    private String password;
    private String email;
    private Button registerButton;
    private Button loginButton;
    private Button logoutButton;
    private Button deleteUsersButton;
    private EditText usernameView;
    private EditText passwordView;
    private EditText emailView;
    private String url = "http://192.168.1.4:8080/users/";  //IP Address of our server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find a reference to the various views in the layout
        usernameView = (EditText)findViewById(R.id.editUsername);
        passwordView = (EditText)findViewById(R.id.editPassword);
        emailView = (EditText)findViewById(R.id.editEmail);
        registerButton = (Button)findViewById(R.id.registerButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        logoutButton = (Button)findViewById(R.id.logoutButton);
        deleteUsersButton = (Button)findViewById(R.id.deleteUsersButton);

        //Set a click listener on register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the value from the user
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                email = emailView.getText().toString();

                /**
                    Send the request to the server on another thread.
                    * Android runs the app by default on the main thread a.k.a the UI Thread.
                    * The UI Thread handles rendering of the views, Click Events etc.
                    * Hence, if mutiple things happen at the same time, they get queued up.
                    * Hence, the UI Thread should not be blocked. Android does not Activities like network requests, audio playback to be performed on main thread.
                */
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"register",username,password,email);
            }
        });

        //Set a click listener on login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                email = emailView.getText().toString();
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"login",username,password,email);
            }
        });

        //Set a click listener on logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                email = emailView.getText().toString();
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"logout",username,password,email);
            }
        });

        //Set a click listener on delete users button
        deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"all");
            }
        });
    }


    //Class for performing network calls on background thread
    private class TutorialAsyncTask extends AsyncTask<String, Void, String> {

        //Perform network call on background thread and return the reponse
        @Override
        protected String doInBackground(String... params) {
            if (params.length == 4) {
                String result = QueryUtils.fetchTutorialData(params[0], params[1], params[2], params[3]);
                return result;
            }
            else
            {
                String result = QueryUtils.fetchTutorialData(params[0]);
                return result;
            }
        }

        //Display the server's response to the user after the network call is complete
        @Override
        protected void onPostExecute(String data) {
            Context context = getApplicationContext();
            CharSequence text = data;
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();
        }
    }

}