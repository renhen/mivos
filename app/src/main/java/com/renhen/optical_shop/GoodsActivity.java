package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.renhen.optical_shop.adapter.GoodsAdapter;
import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.util.ArrayList;

public class GoodsActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView_Goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        db = new OpticalShopDBHelper(this).getReadableDatabase();
        listView_Goods = (ListView) findViewById(R.id.listView_goods);

        AdapterView.OnItemClickListener oneGoodsListener = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("myLog","new window" + Integer.toString(view.getId()));
                Intent intent = new Intent(GoodsActivity.this, ItemGoodsActivity.class);
                intent.putExtra("id", Integer.toString(view.getId()));
                startActivity(intent);
            }
        };

        listView_Goods.setOnItemClickListener(oneGoodsListener);

        ArrayList<String> address = new ArrayList<>();

        try (Cursor cursor = db.query(OpticalShopContract.OpticalShopEntry.TABLE_NAME,
                new String[]{OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS},
                null,
                null,
                null,
                null,
                OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS)) {
            while (cursor.moveToNext()) {
                address.add(cursor.getString(cursor.getColumnIndex(OpticalShopContract.OpticalShopEntry.COLUMN_ADDRESS)));
            }
            Spinner spin = (Spinner) findViewById(R.id.spin_OpticalShop);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, address);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);

            AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String item = (String) parent.getItemAtPosition(position);

                    if (!item.isEmpty()) {
                        Cursor cursor = db.rawQuery("SELECT goods._id as _id, photo_path,title, description FROM goods " +
                                "JOIN there_is on fk_goods_id = goods._id  " +
                                "JOIN optical_shop on optical_shop._id = there_is.fk_optical_shop_id " +
                                "WHERE address = ?;", new String[]{item});
                        ArrayList<Integer> list = new ArrayList<Integer>();
                        for (int i = 0; i < cursor.getCount(); i++) {
                            list.add(i);
                        }

                        GoodsAdapter adapter = new GoodsAdapter(GoodsActivity.this, R.layout.goods_item, list, cursor, getApplicationContext().getAssets());
                        listView_Goods.setAdapter(adapter);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            };
            spin.setOnItemSelectedListener(itemSelectedListener);
        }

    }
}