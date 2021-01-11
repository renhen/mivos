package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.renhen.optical_shop.data.OpticalShopContract.UserEntry;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    Calendar dateAndTime = Calendar.getInstance();
    EditText line_birthdate;
    EditText line_password;
    EditText line_name;
    EditText line_login;
    EditText line_phone;

    OpticalShopDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        line_birthdate = (EditText) findViewById(R.id.editTextDate_Reg);
        line_login = (EditText) findViewById(R.id.editTextTextLogin_Reg);
        line_password = (EditText) findViewById(R.id.editTextTextPassword_Reg);
        line_name = (EditText) findViewById(R.id.editTextTextPersonName_Reg);
        line_phone = (EditText) findViewById(R.id.editTextPhone_Reg);

        mDbHelper = new OpticalShopDBHelper(this);
    }

    public void birthdateClicked(View view) {
        new DatePickerDialog(RegistrationActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            line_birthdate.setText(DateUtils.formatDateTime(RegistrationActivity.this,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    public void continueRegClicked(View view) {
        String[] parse_date = line_birthdate.getText().toString().split("\\.");
        String date = parse_date[2] + parse_date[1] + parse_date[0];
        String login = line_login.getText().toString();
        String password = line_password.getText().toString();
        String name = line_name.getText().toString();
        String phone = line_phone.getText().toString();

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserEntry.COLUMN_FIO,name);
        values.put(UserEntry.COLUMN_LOGIN,login);
        values.put(UserEntry.COLUMN_PASSWORD, password);
        values.put(UserEntry.COLUMN_BIRTHDATE, date);
        values.put(UserEntry.COLUMN_PHONE, phone);

        long id = db.insert(UserEntry.TABLE_NAME,null,values);
        if (id == -1){
            Toast toast = Toast.makeText(this, "Логин уже существует",Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            Intent intent = new Intent(this, ActionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("id", id);
            finish();
            startActivity(intent);
        }

    }

}