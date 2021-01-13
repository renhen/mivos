package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.renhen.optical_shop.adapter.GoodsAdapter;
import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddReceptionActivity extends AppCompatActivity {

    SQLiteDatabase db;
    private String userID;
    //Button btn_addReception;
    Calendar dateAndTime = Calendar.getInstance();
    EditText line_datetime;
    Spinner spin_service;
    Spinner spin_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reception);
        Intent intent = getIntent();
        userID = intent.getExtras().get("id").toString();
        //btn_addReception = (Button) findViewById(R.id.btn_addReceprion);
        line_datetime = (EditText) findViewById(R.id.editTextTextDate_addReception);
        db = new OpticalShopDBHelper(this).getReadableDatabase();

        final ArrayList<String> service = new ArrayList<>();

        try (Cursor cursor = db.query(OpticalShopContract.ServiceEntry.TABLE_NAME,
                new String[]{OpticalShopContract.ServiceEntry.COLUMN_TITLE},
                null,
                null,
                null,
                null,
                OpticalShopContract.ServiceEntry.COLUMN_TITLE)) {
            while (cursor.moveToNext()) {
                service.add(cursor.getString(cursor.getColumnIndex(OpticalShopContract.ServiceEntry.COLUMN_TITLE)));
            }
            spin_service = (Spinner) findViewById(R.id.spinner_service_addReception);
            spin_doctor = (Spinner) findViewById(R.id.spinner_doctor_addReception);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, service);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin_service.setAdapter(adapter);

            AdapterView.OnItemSelectedListener serviceSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String) parent.getItemAtPosition(position);

                    if (!item.isEmpty()) {
                        Cursor cursor = db.rawQuery("SELECT FIO FROM DOCTOR " +
                                "JOIN provide on doctor_id = doctor._id " +
                                "JOIN service on service._id = service_id " +
                                "WHERE title = ?;", new String[]{item});
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        for (int i = 0; i < cursor.getCount(); i++) {
                            list.add(i);
                        }

                        ArrayList<String> FIO = new ArrayList<>();

                        while (cursor.moveToNext()){
                            FIO.add(cursor.getString(cursor.getColumnIndex(OpticalShopContract.DoctorEntry.COLUMN_FIO)));
                        }
                        Button btn_addReception = (Button) findViewById(R.id.button_addReception_2);
                        if (FIO.size() == 0) {
                            FIO.add("Нет врачей");
                            btn_addReception.setEnabled(false);
                        }
                        else {
                            btn_addReception.setEnabled(true);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddReceptionActivity.this, android.R.layout.simple_spinner_item, FIO);
                        Spinner spin_doctor = (Spinner) findViewById(R.id.spinner_doctor_addReception);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_doctor.setAdapter(adapter);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spin_service.setOnItemSelectedListener(serviceSelectedListener);

            AdapterView.OnItemSelectedListener doctorSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String) parent.getItemAtPosition(position);
                    TextView label_address = (TextView) findViewById(R.id.label_address_addReception);
                    TextView label_avg = (TextView) findViewById(R.id.label_avgReception);

                    if (!item.equals("Нет врачей")) {
                        Cursor cursor = db.rawQuery("SELECT address FROM DOCTOR " +
                                "JOIN optical_shop on optical_shop._id = fk_optical_shop_id " +
                                "WHERE FIO = ?;", new String[]{item});
                        cursor.moveToNext();
                        Cursor cursor2 = db.rawQuery("SELECT count(`datetime`)/7.0 as count from reception " +
                                "JOIN doctor on fk_doctor_id = doctor._id " +
                                "WHERE (FIO = ? and `datetime` between STRFTIME(\"%Y%m%d%H%M%S\",\"now\") and STRFTIME(\"%Y%m%d%H%M%S\",\"now\") + 7000000);",new String[]{item});
                        cursor2.moveToNext();

                        label_address.setText("Адрес салона оптики: " + cursor.getString(cursor.getColumnIndex(OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS)));
                        label_avg.setText("Нагрузка врача на неделю (приемов в день): " + cursor2.getString(cursor2.getColumnIndex("count")));
                    } else {
                        label_address.setText("Адрес салона оптики: ");
                        label_avg.setText("Нагрузка врача на неделю (приемов в день): ");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
            spin_doctor.setOnItemSelectedListener(doctorSelectedListener);

        }

    }

    public void dateClicked(View view) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void setTime() {
        new TimePickerDialog(this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTime();
        }
    };

    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            line_datetime.setText(DateUtils.formatDateTime(AddReceptionActivity.this,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_DATE |
                            DateUtils.FORMAT_SHOW_YEAR |
                            DateUtils.FORMAT_SHOW_TIME |
                            DateUtils.FORMAT_NUMERIC_DATE));
        }
    };

    public void addReceptionItem(View view){
        String setDate = line_datetime.getText().toString();
        if (setDate.isEmpty()){
            Toast toast = Toast.makeText(this, "Вы не ввели дату и время приема",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (parseData(setDate).compareTo(getCurrentTime()) <= 0) {
            Toast toast = Toast.makeText(this, "Нельзя выбирать прошедшие даты",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            String date = parseData(setDate);
            String FIO = spin_doctor.getSelectedItem().toString();
            db.execSQL("INSERT INTO reception (datetime,fk_doctor_id,fk_user_id) VALUES (?,(SELECT _ID FROM doctor WHERE FIO = ?),?);",new String[]{date,FIO,userID});
            finish();

        }
    }

    private String parseData(String date) {
        SimpleDateFormat spf=new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        Date newDate= null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spf= new SimpleDateFormat("yyyyMMddHHmmss");
        return spf.format(newDate);
    }

    private String getCurrentTime(){
        // Текущее время
        Date currentDate = new Date();
        // Форматирование времени как "день.месяц.год"
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(currentDate);
    }
}

