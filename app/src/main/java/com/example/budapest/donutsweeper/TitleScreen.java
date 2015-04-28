package com.example.budapest.donutsweeper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TitleScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        TextView titleString = (TextView) findViewById(R.id.title_string);
        titleString.setTextAppearance(this, R.style.TitleText);
        Button btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentOpenGame = new Intent();
                intentOpenGame.setClass(TitleScreen.this, MainActivity.class);
                startActivity(intentOpenGame);
                finish();
            }
        });
}}
