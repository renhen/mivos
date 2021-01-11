package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.renhen.optical_shop.data.OpticalShopContract.UserEntry;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

public class MainActivity extends AppCompatActivity {

    TextView lineLogin;
    TextView linePass;
    Button login;
    Button registration;

    OpticalShopDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineLogin =     (TextView)  findViewById(R.id.editTextTextLogin);
        linePass =      (TextView)  findViewById(R.id.editTextTextPassword);
        login =         (Button)    findViewById(R.id.btn_login);
        registration =  (Button)    findViewById(R.id.btn_registration);

        mDbHelper = new OpticalShopDBHelper(this);
    }

    public void loginClicked(View view) {

        // Проверка входных данных
        if (lineLogin.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this, "Вы не ввели логин",Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (linePass.getText().toString().isEmpty())
        {
            Toast toast = Toast.makeText(this, "Вы не ввели пароль",Toast.LENGTH_SHORT);
            toast.show();
        }
        // Действия для перехода
        else
        {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();

            String[] projection = {
                    UserEntry._ID
            };

            String selection = UserEntry.COLUMN_LOGIN + " = ? AND " + UserEntry.COLUMN_PASSWORD + "= ?";
            String[] selectionArgs = {lineLogin.getText().toString(), linePass.getText().toString()};

            try (Cursor cursor = db.query(
                    UserEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null)) {
                if (cursor.getCount() == 0) {
                    Toast toast = Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    cursor.moveToNext();
                    int currentID = cursor.getInt(cursor.getColumnIndex(UserEntry._ID));
                    Intent intent = new Intent(this, ActionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.putExtra("id", currentID);
                    lineLogin.setText("");
                    linePass.setText("");
                    startActivity(intent);
                }
            }
        }
    }

    public void registrationClicked(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class); // TODO: 10.01.2021 Add new Activity
        startActivity(intent);
    }
}