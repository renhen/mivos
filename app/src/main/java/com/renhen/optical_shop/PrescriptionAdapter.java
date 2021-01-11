package com.renhen.optical_shop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;
import com.renhen.optical_shop.PrescriptionActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrescriptionAdapter extends ArrayAdapter<Integer> {

    private LayoutInflater inflater;
    private int layout;
    private List<Integer> states;
    private Cursor cursor;

    public PrescriptionAdapter(@NonNull Context context, int resource, @NonNull List<Integer> objects, Cursor cursor) {
        super(context, resource, objects);
        this.states = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        TextView label_number = (TextView) view.findViewById(R.id.label_number_pres);
        TextView label_issuedByDoctor = (TextView) view.findViewById(R.id.label_issuedByDoctor_pres);
        TextView label_data_pres = (TextView) view.findViewById(R.id.label_data_pres);
        TextView label_description_pres = (TextView) view.findViewById(R.id.label_description_pres);
        TextView label_duration_pres = (TextView) view.findViewById(R.id.label_duration_pres);

        Cursor new_cursor = cursor;
        new_cursor.moveToPosition(position);

        label_number.setText(cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.PrescriptionEntry.COLUMN_NUMBER)));
        label_issuedByDoctor.setText("Выдан: " + new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.DoctorEntry.COLUMN_FIO)));
        label_data_pres.setText("Дата выдачи: " + parseData(new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.ReceptionEntry.COLUMN_DATETIME))));
        label_description_pres.setText("Описание: " + new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.PrescriptionEntry.COLUMN_DETAIL)));
        label_duration_pres.setText("Действие рецепта: " + new_cursor.getString(new_cursor.getColumnIndex(OpticalShopContract.PrescriptionEntry.COLUMN_DURATION)));

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
        spf= new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
        return spf.format(newDate);
    }

}
