package com.renhen.optical_shop;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng VLG = new LatLng(48.703624387421584, 44.48745608141059);


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        OpticalShopDBHelper mDbHelper = new OpticalShopDBHelper(this);

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS,
                OpticalShopContract.OpticalShopEntry.COLUMN_TIMETABLE
        };

        try (Cursor cursor = db.query(
                OpticalShopContract.OpticalShopEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null)) {
            while (cursor.moveToNext()) {
                String currentAddress = cursor.getString(cursor.getColumnIndex(OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS));
                String currentTimeTable = cursor.getString(cursor.getColumnIndex(OpticalShopContract.OpticalShopEntry.COLUMN_TIMETABLE));

                addresses = geocoder.getFromLocationName(currentAddress, 1);
                if (addresses.size() > 0) {
                    double latitude = addresses.get(0).getLatitude();
                    double longitude = addresses.get(0).getLongitude();
                    LatLng currentOpticalShop = new LatLng(latitude,longitude);
                    mMap.addMarker(new MarkerOptions().position(currentOpticalShop).title(currentAddress).snippet(currentTimeTable));
                }
            }
            //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(VLG, (float) 10.5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}