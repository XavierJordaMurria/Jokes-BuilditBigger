package com.jorda.jokes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidjokes.JokesActivity;

public class MainActivity extends AppCompatActivity
{

    MainActivity mainActivity;
    Intent intent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage(getResources().getString(R.string.loading_Joke));
                progressDialog.show();

                if (isNetworkAvailable())
                {
                    intent = new Intent(MainActivity.this, JokesActivity.class);
                    mainActivity = MainActivity.this;

                    EndpointsAsyncTask endpoint = new EndpointsAsyncTask(progressDialog, intent, mainActivity);
                    endpoint.execute();
                }
                else
                {
                    Toast.makeText(MainActivity.this, R.string.offline, Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}