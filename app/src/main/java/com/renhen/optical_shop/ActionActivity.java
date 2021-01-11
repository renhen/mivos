package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
    }


    public void logoutClicked(View view)
    {

        finish();
    }

    public void editUserClicked(View view)
    {
    }

    public void mapViewClicked(View view)
    {
    }

    public void prescriptionClicked(View view)
    {
    }

    public void addReceptionClicked(View view)
    {
    }

    public void viewGoodsClicked(View view)
    {
    }
}