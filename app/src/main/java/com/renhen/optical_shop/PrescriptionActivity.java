package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import com.renhen.optical_shop.adapter.PrescriptionAdapter;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {

    private ListView listView_pres;
    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        Intent intent = getIntent();
        currentID = intent.getExtras().get("id").toString();

        listView_pres = (ListView) findViewById(R.id.listView_pres);

        SQLiteDatabase db = new OpticalShopDBHelper(this).getReadableDatabase();

        String query = "SELECT number, detail, duration,datetime, FIO FROM prescription " +
                "JOIN reception on reception._id = prescription.fk_reception_id " +
                "JOIN doctor on doctor._id = reception.fk_doctor_id " +
                "WHERE reception.fk_user_id = ?;";
        Cursor cursor = db.rawQuery(query,new String[]{currentID});

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(i);
        }

        PrescriptionAdapter adapter = new PrescriptionAdapter(this,R.layout.prescription_item,list,cursor);
        listView_pres.setAdapter(adapter);
    }
}