package com.example.mobiletercinta.absensionline;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictFragment extends Fragment {
    private Button btn_predict;
    private CameraView camera;
    private CameraKitEventListener cameradListener;
    private EditText password, idUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_predict, container, false);
        getActivity().setTitle("Predict");
        btn_predict = (Button) rootView.findViewById(R.id.btn_predict);

        ActivityLogin activityLogin = new ActivityLogin();
        idUser = activityLogin.NRPMhs;
        password = activityLogin.PasswordNRP;

        cameradListener = new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                byte[] picture = cameraKitImage.getJpeg();
                Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                result = Bitmap.createScaledBitmap(result, 512,512, true);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                String myBase64Image = encodeToBase64(result, Bitmap.CompressFormat.JPEG, 100);

                final ApiInterface api = Server.getclient().create(ApiInterface.class);
                Log.d("test", "onImage: "+myBase64Image);
                JSONObject paramObject = new JSONObject();

                final long StartTime = new Date().getTime();
                final String id_user = idUser.getText().toString();
                final String password_user = password.getText().toString();
                Call<ResponseApi> kirim =api.predictFoto(id_user, password_user,"data:image/jpeg;base64,"+myBase64Image);
                kirim.enqueue(new Callback<ResponseApi>() {
                    @Override
                    public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                        final long EndTime = new Date().getTime();
                        final long delta = EndTime - StartTime;
                        String hasil = response.body().getMessage();
                        Toast.makeText(getActivity(), hasil, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseApi> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {
            }
        };

        camera = (CameraView) rootView.findViewById(R.id.camera_predict);
        camera.addCameraKitListener(cameradListener);

        btn_predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.captureImage();
            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    public void onPause() {
        camera.stop();
        super.onPause();
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
