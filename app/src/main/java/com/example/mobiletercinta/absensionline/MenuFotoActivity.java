package com.example.mobiletercinta.absensionline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFotoActivity extends AppCompatActivity {
    CardView linearkirimFoto, linearpredictFoto, lineartrainFoto, linearTTD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menufoto);

        linearkirimFoto = (CardView) findViewById(R.id.linearkirimFoto);
        linearkirimFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuFotoActivity.this, Activitykirim.class);
                startActivity(intent);
            }
        });

        linearpredictFoto = (CardView) findViewById(R.id.linearpredictFoto);
        linearpredictFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuFotoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        lineartrainFoto = (CardView) findViewById(R.id.lineartrainFoto);
        lineartrainFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ApiInterface api = Server.getclient().create(ApiInterface.class);
                ActivityLogin activityLogin = new ActivityLogin();
                String id_user = activityLogin.NRPMhs.getText().toString();
                String password_user = activityLogin.PasswordNRP.getText().toString();
                Call<ResponseApi> training = api.trainFoto(id_user, password_user);
                training.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        String hasil = response.body().getMessage();
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

    }
}
