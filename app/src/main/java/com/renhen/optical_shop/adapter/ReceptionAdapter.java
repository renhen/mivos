package com.renhen.optical_shop.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.renhen.optical_shop.R;
import com.renhen.optical_shop.data.OpticalShopContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceptionAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Integer> states;
    private Cursor cursor;

    public ReceptionAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects, Cursor cursor) {
        super(context, resource, objects);
        this.states = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView label_doctor = (TextView) view.findViewById(R.id.label_doctor_reception);
        TextView label_data = (TextView) view.findViewById(R.id.label_datetime_reception);
        TextView label_address = (TextView) view.findViewById(R.id.label_address_receprion);

        Cursor new_cursor = cursor;
        new_cursor.moveToPosition(position);

        label_doctor.setText("Доктор: " + new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.DoctorEntry.COLUMN_FIO)));
        label_address.setText("Адрес салона оптики: " + new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS)));
        label_data.setText("Дата приема: " + parseData(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.ReceptionEntry.COLUMN_DATETIME))));

        view.setId(new_cursor.getInt(new_cursor.getColumnIndex(OpticalShopContract.ReceptionEntry._ID)));
        return view;
    }


    private String parseData(String date) {
        SimpleDateFormat spf=new SimpleDateFormat("yyyyMMddhhmmss");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return spf.format(newDate);
    }
}
