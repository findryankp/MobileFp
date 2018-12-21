package com.example.mobiletercinta.absensionline;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private double longitude, latitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Absen");

        Intent intent = new Intent(getContext(), GPSTrackerActivity.class);
        startActivityForResult(intent,1);
        mScannerView = new ZXingScannerView(getContext());

        return mScannerView;
    }

    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        String[] splitted = rawResult.getText().split(",");
        double latQR = Double.valueOf(splitted[0]);
        double longQR = Double.valueOf(splitted[1]);
        String ruangan = splitted[2];
        Double jarak = distance(latQR, latitude, longQR, longitude);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Scan Result");
        builder.setMessage("Ruang :\n" + ruangan + "\n\nLokasi ruangan :\n" + latQR + ", " + longQR + "\n\nLokasi anda :\n" + latitude + ", " + longitude + "\n\nJarak :\n" + jarak + " meter");
        AlertDialog alert1 = builder.create();
        alert1.show();

        if(jarak < 1000000) {
            Intent intent = new Intent(getActivity(), SigninActivity.class);
            intent.putExtra("agenda", ruangan);
            intent.putExtra("lat", latitude + "");
            intent.putExtra("lon", longitude + "");
            intent.putExtra("tipe", ruangan+ ": " + jarak + " meter");
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Anda berada lebih dari 100 meter", Toast.LENGTH_LONG).show();
        }
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            Bundle extras = data.getExtras();
            longitude = extras.getDouble("Longitude");
            latitude = extras.getDouble("Latitude");
        }
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return round(Math.sqrt(distance), 2);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
