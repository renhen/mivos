package com.renhen.optical_shop.data;

import android.provider.BaseColumns;

import java.sql.Date;

public class OpticalShopContract {

    private OpticalShopContract(){
    }

    public static final class UserEntry implements BaseColumns{
        public final static String TABLE_NAME = "user";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FIO = "FIO";
        public final static String COLUMN_PHONE = "phone";
        public final static String COLUMN_BIRTHDATE = "birthdate";
        public final static String COLUMN_LOGIN = "login";
        public final static String COLUMN_PASSWORD = "password";

    }

}
