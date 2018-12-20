package com.example.mobiletercinta.absensionline;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ActivityLogin extends AppCompatActivity {
    public static EditText NRPMhs, PasswordNRP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filelogin);
    }

    public void MasukLogin(View view) {
        NRPMhs = (EditText) findViewById(R.id.NRPMhs);
        PasswordNRP = (EditText) findViewById(R.id.PasswordNRP);

        Intent intent = new Intent(ActivityLogin.this, MainMenu.class);
        startActivity(intent);
    }
}
