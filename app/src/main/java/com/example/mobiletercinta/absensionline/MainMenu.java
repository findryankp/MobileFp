package com.example.mobiletercinta.absensionline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenu extends AppCompatActivity {

    private final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filemenu);
    }

    public void Absen(View view) {
        Intent intent = new Intent(MainMenu.this, ActivityQR.class);
        startActivity(intent);
    }

    public void Database(View view) {
        Intent intent = new Intent(MainMenu.this, Activitydatabase.class);
        startActivity(intent);
    }

    public void Kirim(View view) {
        Intent intent = new Intent(MainMenu.this, Activitykirim.class);
        startActivity(intent);
    }

    public void Predict(View view) {
        Intent intent = new Intent(MainMenu.this, MainActivity.class);
        startActivity(intent);
    }

    public void Train(View view) {
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

    public void Predict_TTD(View view) {
        Intent intent = new Intent(MainMenu.this, SignatureActivity.class);
        startActivity(intent);
    }

    public void Train_TTD(View view) {
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

    public void Kirim_TTD(View view) {
        Intent intent = new Intent(MainMenu.this, SignatureActivity.class);
        startActivity(intent);
    }
}
