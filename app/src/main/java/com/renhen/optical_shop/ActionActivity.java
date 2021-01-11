package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActionActivity extends AppCompatActivity {

    private String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        Intent intent = getIntent();
        currentID = intent.getExtras().get("id").toString();
    }


    public void logoutClicked(View view)
    {
        finish();
    }

    public void editUserClicked(View view)
    {
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", currentID);
        startActivity(intent);
    }

    public void mapViewClicked(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void prescriptionClicked(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void receptionClicked(View view)
    {
    }

    public void viewGoodsClicked(View view)
    {
    }
}