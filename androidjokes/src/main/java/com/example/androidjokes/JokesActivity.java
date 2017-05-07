package com.example.androidjokes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        String joke= getIntent().getExtras().getString("joke");
        TextView textView= (TextView) findViewById(R.id.textview);
        textView.setText(joke);
    }
}
