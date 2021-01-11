package com.renhen.optical_shop.data;

import android.provider.BaseColumns;

import java.sql.Date;

public class OpticalShopContract {

    private OpticalShopContract(){
    }

    public static final class UserEntry implements BaseColumns{
        public final static String TABLE_NAME = "user";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FIO           = "FIO";
        public final static String COLUMN_PHONE         = "phone";
        public final static String COLUMN_BIRTHDATE     = "birthdate";
        public final static String COLUMN_LOGIN         = "login";
        public final static String COLUMN_PASSWORD      = "password";

    }

    public static final class OpticalShopEntry implements BaseColumns {
        public final static String TABLE_NAME = "optical_shop";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ADDRESS   = "address";
        public final static String COLUMN_TIMETABLE = "timetable";
    }

    public static final class ServiceEntry implements BaseColumns {
        public final static String TABLE_NAME = "service";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
    }

    public static final class ProvideEntry implements BaseColumns {
        public final static String TABLE_NAME = "provide";

        public final static String COLUMN_DOCTOR_ID     = "doctor_id";
        public final static String COLUMN_SERVICE_ID    = "service_id";
    }

    public static final class DoctorEntry implements BaseColumns {
        public final static String TABLE_NAME = "doctor";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FIO             = "FIO";
        public final static String COLUMN_SPECIALTY       = "specialty";
        public final static String COLUMN_FK_OPTICAL_SHOP = "fk_optical_shop_id";
    }

    public static final class ReceptionEntry implements BaseColumns {
        public final static String TABLE_NAME = "reception";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_DATETIME  = "datetime";
        public final static String COLUMN_FK_DOCTOR = "fk_doctor_id";
        public final static String COLUMN_FK_USER   = "fk_user_id";
    }

    public static final class PrescriptionEntry implements BaseColumns {
        public final static String TABLE_NAME = "prescription";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NUMBER        = "number";
        public final static String COLUMN_DETAIL        = "detail";
        public final static String COLUMN_DURATION      = "duration";
        public final static String COLUMN_FK_RECEPTION  = "fk_reception_id";
    }

    public static final class ThereIsEntry implements BaseColumns {
        public final static String TABLE_NAME = "there_is";

        public final static String COLUMN_OPTICAL_SHOP_ID   = "fk_optical_shop_id";
        public final static String COLUMN_GOODS_ID          = "fk_goods_id";
        public final static String COLUMN_COUNT             = "count";
    }

    public static final class GoodsEntry implements BaseColumns {
        public final static String TABLE_NAME = "goods";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PHOTO_PATH = "photo_path";
        public final static String COLUMN_TITLE = "title";
        public final static String COLUMN_DESCRIPTION   = "description";
    }

    public static final class GoodsHasValueEntry implements BaseColumns {
        public final static String TABLE_NAME = "goods_has_value";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FK_GOODS   = "fk_goods_id";
        public final static String COLUMN_FK_VALUE   = "fk_value_id";
    }

    public static final class ValueEntry implements BaseColumns {
        public final static String TABLE_NAME = "value";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NUMERIC           = "numeric";
        public final static String COLUMN_STRING            = "string";
        public final static String COLUMN_FK_ATTRIBUTE_SHOP = "fk_attribute_id";
    }

    public static final class AttributeEntry implements BaseColumns {
        public final static String TABLE_NAME = "attribute";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TITLE = "title";
    }
}
