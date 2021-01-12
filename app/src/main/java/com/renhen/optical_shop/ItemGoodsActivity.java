package com.renhen.optical_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.renhen.optical_shop.adapter.ItemGoodsAdapter;
import com.renhen.optical_shop.data.OpticalShopContract;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ItemGoodsActivity extends AppCompatActivity {

    SQLiteDatabase db;
    ListView listView_ItemGoods;
    String currentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_goods);
        listView_ItemGoods = (ListView) findViewById(R.id.listView_Values);

        db = new OpticalShopDBHelper(this).getReadableDatabase();
        Intent intent = getIntent();
        currentID = intent.getExtras().get("id").toString();

        try (Cursor cursor = db.query(OpticalShopContract.GoodsEntry.TABLE_NAME,
                new String[]{OpticalShopContract.GoodsEntry.COLUMN_PHOTO_PATH, OpticalShopContract.GoodsEntry.COLUMN_TITLE, OpticalShopContract.GoodsEntry.COLUMN_DESCRIPTION},
                OpticalShopContract.GoodsEntry._ID + "=?",
                new String[]{currentID},
                null,
                null,
                null)) {
            TextView label_title = (TextView) findViewById(R.id.label_titleItemGoods);
            TextView label_description = (TextView) findViewById(R.id.label_descriptionItemGoods);
            ImageView imageView = (ImageView) findViewById(R.id.imageView_ItemGoods);

            cursor.moveToNext();
            loadImage(cursor.getString(cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_PHOTO_PATH)),imageView);
            label_title.setText(cursor.getString(cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_TITLE)));
            label_description.setText(cursor.getString(cursor.getColumnIndex(OpticalShopContract.GoodsEntry.COLUMN_DESCRIPTION)));

        }

        //  "        IF (value.`numeric` is null, value.`string`,value.`numeric`) as value " +
            Cursor cursor = db.rawQuery("SELECT  attribute.title as attribute, " +
                " CASE WHEN value.numeric is null THEN string ELSE value.numeric END as value " +
                "        FROM goods_has_value " +
                "        JOIN goods on fk_goods_id = goods._id " +
                "        JOIN value on fk_value_id = value._id " +
                "        JOIN attribute on fk_attribute_id = attribute._id " +
                "        WHERE goods._id = ?;",new String[]{currentID});
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < cursor.getCount(); i++) {
                list.add(i);
            }

            ItemGoodsAdapter adapter = new ItemGoodsAdapter(ItemGoodsActivity.this,R.layout.value_goods_item,list,cursor);
            listView_ItemGoods.setAdapter(adapter);


    }

    private void loadImage(String path, ImageView view){
        InputStream inputStream = null;
        try{
            inputStream = getApplication().getAssets().open(path);
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            view.setImageDrawable(drawable);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }
}