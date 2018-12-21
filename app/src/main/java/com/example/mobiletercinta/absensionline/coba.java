package com.example.mobiletercinta.absensionline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class coba extends AppCompatActivity {
    CardView linearAbsen, lineardatabase, linearFoto, linearTTD ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        linearAbsen = (CardView) findViewById(R.id.linearAbsen);
        linearAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(coba.this, ActivityQR.class);
                startActivity(intent);
            }
        });

        lineardatabase = (CardView) findViewById(R.id.linearDatabase);
        lineardatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(coba.this, Activitydatabase.class);
                startActivity(intent);
            }
        });

        linearFoto = (CardView) findViewById(R.id.linearFoto);
        linearFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(coba.this, MenuFotoActivity.class);
                startActivity(intent);
            }
        });

        linearTTD = (CardView) findViewById(R.id.linearTTD);
        linearTTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(coba.this, MenuTtdActivity.class);
                startActivity(intent);
            }
        });
    }
}
