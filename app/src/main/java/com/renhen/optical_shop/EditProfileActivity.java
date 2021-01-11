package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditProfileActivity extends AppCompatActivity {

    OpticalShopDBHelper mDbHelper;
    private String currentID;

    Calendar dateAndTime = Calendar.getInstance();
    EditText line_birthdate;
    EditText line_password;
    EditText line_name;
    EditText line_login;
    EditText line_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        line_birthdate = (EditText) findViewById(R.id.editTextDate_Edit);
        line_login = (EditText) findViewById(R.id.editTextTextLogin_Edit);
        line_password = (EditText) findViewById(R.id.editTextTextPassword_Edit);
        line_name = (EditText) findViewById(R.id.editTextTextPersonName_Edit);
        line_phone = (EditText) findViewById(R.id.editTextPhone_Edit);

        Intent intent = getIntent();
        currentID = intent.getExtras().get("id").toString();

        mDbHelper = new OpticalShopDBHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OpticalShopContract.UserEntry.COLUMN_PASSWORD,
                OpticalShopContract.UserEntry.COLUMN_LOGIN,
                OpticalShopContract.UserEntry.COLUMN_BIRTHDATE,
                OpticalShopContract.UserEntry.COLUMN_PHONE,
                OpticalShopContract.UserEntry.COLUMN_FIO
        };

        String selection = OpticalShopContract.UserEntry._ID + " = ?";
        String[] selectionArgs = {currentID};

        try (Cursor cursor = db.query(
                OpticalShopContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null)) {
            cursor.moveToNext();
            String login = cursor.getString(cursor.getColumnIndex(OpticalShopContract.UserEntry.COLUMN_LOGIN));
            String password = cursor.getString(cursor.getColumnIndex(OpticalShopContract.UserEntry.COLUMN_PASSWORD));
            String FIO = cursor.getString(cursor.getColumnIndex(OpticalShopContract.UserEntry.COLUMN_FIO));
            String birthdate = cursor.getString(cursor.getColumnIndex(OpticalShopContract.UserEntry.COLUMN_BIRTHDATE));
            String phone = cursor.getString(cursor.getColumnIndex(OpticalShopContract.UserEntry.COLUMN_PHONE));

            line_login.setText(login);
            line_password.setText(password);
            line_name.setText(FIO);
            line_birthdate.setText(birthdate);
            line_phone.setText(phone);

        }
    }

    public void birthdateClicked(View view) {
        new DatePickerDialog(EditProfileActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            line_birthdate.setText(DateUtils.formatDateTime(EditProfileActivity.this,
                    dateAndTime.getTimeInMillis(),
                    DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR));
        }
    };

    public void saveProfile(View view) {
        if (line_password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Вы не ввели пароль", Toast.LENGTH_SHORT);
            toast.show();
        } else if (line_phone.toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Вы не ввели номер телефона", Toast.LENGTH_SHORT);
            toast.show();
        } else if (line_name.toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Вы не ввели имя", Toast.LENGTH_SHORT);
            toast.show();
        } else if (line_birthdate.toString().isEmpty()) {
            Toast toast = Toast.makeText(this, "Вы не ввели дату рождения", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            String password = line_password.getText().toString();
            String phone = line_phone.getText().toString();
            String name = line_name.getText().toString();
            String birthdate = line_birthdate.getText().toString();

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(OpticalShopContract.UserEntry.COLUMN_FIO, name);
            values.put(OpticalShopContract.UserEntry.COLUMN_PASSWORD, password);
            values.put(OpticalShopContract.UserEntry.COLUMN_BIRTHDATE, birthdate);
            values.put(OpticalShopContract.UserEntry.COLUMN_PHONE, phone);

            db.update(OpticalShopContract.UserEntry.TABLE_NAME,
                    values,
                    OpticalShopContract.UserEntry._ID + " = ?",
                    new String[]{currentID});

            finish();
        }
    }
}