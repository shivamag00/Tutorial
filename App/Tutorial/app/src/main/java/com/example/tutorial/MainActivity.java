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
    private String url = "http://192.168.1.4:8080/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameView = (EditText)findViewById(R.id.editUsername);
        passwordView = (EditText)findViewById(R.id.editPassword);
        emailView = (EditText)findViewById(R.id.editEmail);
        registerButton = (Button)findViewById(R.id.registerButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        logoutButton = (Button)findViewById(R.id.logoutButton);
        deleteUsersButton = (Button)findViewById(R.id.deleteUsersButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameView.getText().toString();
                password = passwordView.getText().toString();
                email = emailView.getText().toString();
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"register",username,password,email);
            }
        });

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

        deleteUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TutorialAsyncTask task = new TutorialAsyncTask();
                task.execute(url+"all");
            }
        });
    }


    private class TutorialAsyncTask extends AsyncTask<String, Void, String> {
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


        @Override
        protected void onPostExecute(String data) {
            Context context = getApplicationContext();
            CharSequence text = data;
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(context, text, duration).show();
        }
    }

}