package com.example.mobiletercinta.absensionline;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kyanogen.signatureview.SignatureView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TtdSigninActivity extends AppCompatActivity {

    Bitmap bitmap;
    Button clear,save, kirimTTD2, absenTTD;
    SignatureView signatureView;
    String path;
    String nameFile;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absensignature);

        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        kirimTTD2 = (Button) findViewById(R.id.kirimTTD2);
        absenTTD = (Button) findViewById(R.id.absenTTD);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signatureView.clearCanvas();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = saveImage(bitmap);
            }
        });

        absenTTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = absenTTD(bitmap);
            }
        });
    }


    public String absenTTD(Bitmap myBitmap){

        //region DATA-DATA USER
        ActivityLogin activityLogin = new ActivityLogin();
        final String id_user = activityLogin.NRPMhs.getText().toString();
        final String password_user = activityLogin.PasswordNRP.getText().toString();
        SigninActivity signinActivity = new SigninActivity();
        final String lati = signinActivity.latitude1;
        final String longi = signinActivity.longitude1;
        final String agenda = signinActivity.agenda;
//        final String id_user = "5115100035";
//        final String password_user = "F1ndryan";
//        final String lati = "-7.27935080";
//        final String longi = "112.79754800";
//        final String agenda = "mobile_18";
        //endregion

        //region CONVERT IMAGE TTD
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
        //endregion

        //region API ACTION
        final ApiInterface api = Server.getclient().create(ApiInterface.class);
        Log.d("test", "onImage: "+myBase64Image);
        JSONObject paramObject = new JSONObject();
        final long StartTime = new Date().getTime();
        Call<ResponseApi> kirim =api.signinTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image,
                lati,longi,agenda);
        kirim.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                final long EndTime = new Date().getTime();
                final long delta = EndTime - StartTime;
                String hasil = response.body().getMessage();
                Toast.makeText(TtdSigninActivity.this, hasil, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TtdSigninActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });

        //endregion
        return "";
    }

    public String saveImage(Bitmap myBitmap) {

        //region DATA-DATA USER
        ActivityLogin activityLogin = new ActivityLogin();
        final String id_user = activityLogin.NRPMhs.getText().toString();
        final String password_user = activityLogin.PasswordNRP.getText().toString();
//        final String id_user = "5115100035";
//        final String password_user = "F1ndryan";
        //endregion

        //region CONVERT IMAGE TTD
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);
        //endregion

        //region API ACTION
        final ApiInterface api = Server.getclient().create(ApiInterface.class);
        Log.d("test", "onImage: "+myBase64Image);
        JSONObject paramObject = new JSONObject();
        final long StartTime = new Date().getTime();
        Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
        kirim.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                final long EndTime = new Date().getTime();
                final long delta = EndTime - StartTime;
                String hasil = response.body().getMessage();
                Toast.makeText(TtdSigninActivity.this, hasil, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TtdSigninActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });
        //endregion

        return "";

    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
