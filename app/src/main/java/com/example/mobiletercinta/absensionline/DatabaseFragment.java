package com.example.mobiletercinta.absensionline;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DatabaseFragment extends Fragment {
    DatabaseHelper mDatabaseHelper;
    TextView ratarata;
    private int jumlahwaktu, jumlahdata;
    private float rerata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_database, container, false);
        getActivity().setTitle("Database Waktu Upload");

        mDatabaseHelper = new DatabaseHelper(getContext());
        ratarata = (TextView) rootView.findViewById(R.id.ratarata);
        jumlahdata = 0;
        jumlahwaktu = 0;

        TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.tablelayout);

        TableRow rowHeader = new TableRow(getContext());
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        String[] headerText = {"ID", "Nama", "Start", "End", "Delta"};
        for (String c : headerText) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        Cursor data = mDatabaseHelper.getData();

        while (data.moveToNext()) {
            int data_no = (data.getInt(0));
            String data_name = data.getString(1);
            String data_start = data.getString(2);
            String data_end = data.getString(3);
            String data_delta = data.getString(4);

            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            String[] colText = {data_no + "", data_name, data_start, data_end,data_delta};

            for (String text : colText) {
                TextView tv = new TextView(getContext());
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                row.addView(tv);
            }

            jumlahwaktu += Integer.parseInt(data_delta);
            jumlahdata += 1;
            tableLayout.addView(row);
        }

        rerata = ((float) jumlahwaktu / jumlahdata) / 1000;
        if(!(rerata > 0) ) {
            rerata = 0;
        }
        String hasil = "Rata-rata: " + Float.toString(rerata) + " detik";
        ratarata.setText(hasil);

        return rootView;
    }
}
