package com.example.rinkesh.nyaay_srmhack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Status extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void FirClick(View view) {

        Intent FirIntent = new Intent(this, FileFIR.class);
        startActivity(FirIntent);
    }
}
