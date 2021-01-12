package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.renhen.optical_shop.adapter.ReceptionAdapter;
import com.renhen.optical_shop.data.OpticalShopDBHelper;
import com.renhen.optical_shop.dialog.AcceptDeleteReceptionDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReceptionActivity extends AppCompatActivity {

    private String userID;
    SQLiteDatabase db;
    ListView futureList;
    ListView pastList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);
        Intent intent = getIntent();
        userID = intent.getExtras().get("id").toString();
        futureList = (ListView) findViewById(R.id.listView_futureReception);
        pastList = (ListView) findViewById(R.id.listView_pastReception);

        db = new OpticalShopDBHelper(this).getReadableDatabase();

        onResume();

        AdapterView.OnItemClickListener receptionClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AcceptDeleteReceptionDialog dialog = new AcceptDeleteReceptionDialog();
                Bundle args = new Bundle();
                args.putString("receptionID", String.valueOf(view.getId()));
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), "custom");
            }
        };

        futureList.setOnItemClickListener(receptionClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        futureList.setAdapter(null);
        pastList.setAdapter(null);

        Cursor cursor = db.rawQuery("SELECT reception._id as _id, datetime, address, FIO from reception " +
                "JOIN doctor on doctor._id = fk_doctor_id " +
                "JOIN optical_shop on fk_optical_shop_id = optical_shop._id " +
                "WHERE fk_user_id = ? and datetime > ?;", new String[]{userID, getCurrentTime()});

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < cursor.getCount(); i++) {
            list.add(i);
        }

        ReceptionAdapter adapter = new ReceptionAdapter(this,R.layout.reception_item,list,cursor);
        futureList.setAdapter(adapter);

        Cursor cursor1 = db.rawQuery("SELECT reception._id as _id, datetime, address, FIO from reception " +
                "JOIN doctor on doctor._id = fk_doctor_id " +
                "JOIN optical_shop on fk_optical_shop_id = optical_shop._id " +
                "WHERE fk_user_id = ? and datetime <= ?;", new String[]{userID, getCurrentTime()});

        ArrayList<Integer> list1 = new ArrayList<Integer>();
        for (int i = 0; i < cursor1.getCount(); i++) {
            list1.add(i);
        }

        ReceptionAdapter adapter1 = new ReceptionAdapter(this,R.layout.reception_item,list1,cursor1);
        pastList.setAdapter(adapter1);

    }

    public void updateList(){
        onResume();
    }

    private String getCurrentTime(){
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(currentDate);
    }

    public void addReception(View view) {

        Intent intent = new Intent(this, AddReceptionActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }
}