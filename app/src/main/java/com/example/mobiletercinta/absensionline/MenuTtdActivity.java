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

public class MenuTtdActivity extends AppCompatActivity {
    CardView linearkirimttd, linearpredictttd, lineartrainttd, linearTTD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ttd);

        linearkirimttd = (CardView) findViewById(R.id.linearkirimttd);
        linearkirimttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTtdActivity.this, SignatureActivity.class);
                startActivity(intent);
            }
        });

        linearpredictttd = (CardView) findViewById(R.id.linearpredictttd);
        linearpredictttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuTtdActivity.this, SignatureActivity.class);
                startActivity(intent);
            }
        });

        lineartrainttd = (CardView) findViewById(R.id.lineartrainttd);
        lineartrainttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ApiInterface api = Server.getclient().create(ApiInterface.class);
                ActivityLogin activityLogin = new ActivityLogin();
                String id_user = activityLogin.NRPMhs.getText().toString();
                String password_user = activityLogin.PasswordNRP.getText().toString();
                Call<ResponseApi> training = api.trainTTD(id_user, password_user);
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

