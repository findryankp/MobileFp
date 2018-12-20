package com.example.mobiletercinta.absensionline;

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

public class SignatureActivity extends AppCompatActivity {

    Bitmap bitmap;
    Button clear,save, kirimTTD2;
    SignatureView signatureView;
    String path;
    String nameFile;
    private static final String IMAGE_DIRECTORY = "/signdemo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        signatureView =  (SignatureView) findViewById(R.id.signature_view);
        clear = (Button) findViewById(R.id.clear);
        save = (Button) findViewById(R.id.save);
        kirimTTD2 = (Button) findViewById(R.id.kirimTTD2);

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

        kirimTTD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = signatureView.getSignatureBitmap();
                path = kirimTTD2(bitmap);
            }
        });

    }


    public String kirimTTD2(Bitmap myBitmap) {
        myBitmap = myBitmap.createScaledBitmap(myBitmap, 512,512, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);

        final ApiInterface api = Server.getclient().create(ApiInterface.class);
        Log.d("test", "onImage: "+myBase64Image);
        JSONObject paramObject = new JSONObject();

        final long StartTime = new Date().getTime();
//        final String id_user = "5115100035";
//        final String password_user = "F1ndryan";
        ActivityLogin activityLogin = new ActivityLogin();
        final String id_user = activityLogin.NRPMhs.getText().toString();
        final String password_user = activityLogin.PasswordNRP.getText().toString();
        Call<ResponseApi> kirim =api.kirimTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
        //Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
        kirim.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                final long EndTime = new Date().getTime();
                final long delta = EndTime - StartTime;
                String hasil = response.body().getMessage();
                Toast.makeText(SignatureActivity.this, hasil, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SignatureActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });
        return  "";
    }

    public String saveImage(Bitmap myBitmap) {

        myBitmap = myBitmap.createScaledBitmap(myBitmap, 512,512, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
        String myBase64Image = encodeToBase64(myBitmap, Bitmap.CompressFormat.JPEG, 100);


        final ApiInterface api = Server.getclient().create(ApiInterface.class);
        Log.d("test", "onImage: "+myBase64Image);
        JSONObject paramObject = new JSONObject();

        final long StartTime = new Date().getTime();
//        final String id_user = "5115100035";
//        final String password_user = "F1ndryan";
        ActivityLogin activityLogin = new ActivityLogin();
        final String id_user = activityLogin.NRPMhs.getText().toString();
        final String password_user = activityLogin.PasswordNRP.getText().toString();
        Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
        //Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
        kirim.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                final long EndTime = new Date().getTime();
                final long delta = EndTime - StartTime;
                String hasil = response.body().getMessage();
                Toast.makeText(SignatureActivity.this, hasil, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SignatureActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        });

//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(
//                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY /*iDyme folder*/);
//        // have the object build the directory structure, if needed.
//        if (!wallpaperDirectory.exists()) {
//            wallpaperDirectory.mkdirs();
//            Log.d("hhhhh",wallpaperDirectory.toString());
//        }
//
//        try {
//            nameFile = Calendar.getInstance().getTimeInMillis() + ".jpg";
//            File f = new File(wallpaperDirectory, nameFile);
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(SignatureActivity.this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
//            try {
//                //region
//
//                //endregion
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return f.getAbsolutePath();
//        }
//        catch (IOException e1)
//        {
//            e1.printStackTrace();
//        }
        return "";

    }


//    public void predictTTD2(View view) {
//        Bitmap myBitmap = bitmap;
//        myBitmap.createScaledBitmap(bitmap, 512,512, true);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        String myBase64Image = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);
//
//        final ApiInterface api = Server.getclient().create(ApiInterface.class);
//        Log.d("test", "onImage: "+myBase64Image);
//        JSONObject paramObject = new JSONObject();
//
//        final long StartTime = new Date().getTime();
////        final String id_user = "5115100035";
////        final String password_user = "F1ndryan";
//        ActivityLogin activityLogin = new ActivityLogin();
//        final String id_user = activityLogin.NRPMhs.getText().toString();
//        final String password_user = activityLogin.PasswordNRP.getText().toString();
//        //Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+imageString);
//        //Call<ResponseApi> kirim =api.predictTTD(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
//        kirim.enqueue(new Callback<ResponseApi>() {
//            @Override
//            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
//                final long EndTime = new Date().getTime();
//                final long delta = EndTime - StartTime;
//                String hasil = response.body().getMessage();
//                Toast.makeText(SignatureActivity.this, hasil, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<ResponseApi> call, Throwable t) {
//                t.printStackTrace();
//                Toast.makeText(SignatureActivity.this, "error", Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}