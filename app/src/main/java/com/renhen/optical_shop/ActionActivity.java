package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActionActivity extends AppCompatActivity {

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Intent intent = getIntent();
        userID = intent.getExtras().get("id").toString();
    }


    public void logoutClicked(View view)
    {
        finish();
    }

    public void editUserClicked(View view)
    {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    public void mapViewClicked(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void prescriptionClicked(View view)
    {
        Intent intent = new Intent(this, PrescriptionActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    public void receptionClicked(View view)
    {
        Intent intent = new Intent(this, ReceptionActivity.class);
        intent.putExtra("id", userID);
        startActivity(intent);
    }

    public void viewGoodsClicked(View view)
    {
        Intent intent = new Intent(this, GoodsActivity.class);
        startActivity(intent);
    }
}