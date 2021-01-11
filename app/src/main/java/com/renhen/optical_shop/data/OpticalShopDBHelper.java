package com.renhen.optical_shop.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.CarrierConfigManager;

import androidx.annotation.Nullable;

import com.renhen.optical_shop.data.OpticalShopContract.*;

public class OpticalShopDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "opticalshop.db";
    private static final int    DATABASE_VERSION = 3;


    public OpticalShopDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + UserEntry.TABLE_NAME + " ("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_FIO + " TEXT NOT NULL, "
                + UserEntry.COLUMN_PHONE + " TEXT NOT NULL, "
                + UserEntry.COLUMN_BIRTHDATE + " INTEGER NOT NULL, "
                + UserEntry.COLUMN_LOGIN + " TEXT UNIQUE NOT NULL, "
                + UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_USER_TABLE);

        String SQL_CREATE_OPTICAL_SHOP_TABLE = "CREATE TABLE IF NOT EXISTS " + OpticalShopEntry.TABLE_NAME + " ("
                + OpticalShopEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + OpticalShopEntry.COLUMN_ADDRESS + " TEXT UNIQUE NOT NULL, "
                + OpticalShopEntry.COLUMN_TIMETABLE + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_OPTICAL_SHOP_TABLE);

        String SQL_CREATE_SERVICE_TABLE = "CREATE TABLE IF NOT EXISTS " + ServiceEntry.TABLE_NAME + " ("
                + ServiceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ServiceEntry.COLUMN_TITLE + " TEXT UNIQUE NOT NULL);";

        db.execSQL(SQL_CREATE_SERVICE_TABLE);

        String SQL_CREATE_ATTRIBUTE_TABLE = "CREATE TABLE IF NOT EXISTS " + AttributeEntry.TABLE_NAME + " ("
                + AttributeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AttributeEntry.COLUMN_TITLE + " TEXT UNIQUE NOT NULL);";

        db.execSQL(SQL_CREATE_ATTRIBUTE_TABLE);

        String SQL_CREATE_GOODS_TABLE = "CREATE TABLE IF NOT EXISTS " + GoodsEntry.TABLE_NAME + " ("
                + GoodsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GoodsEntry.COLUMN_PHOTO_PATH + " TEXT NOT NULL, "
                + GoodsEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + GoodsEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_GOODS_TABLE);

        String SQL_CREATE_DOCTOR_TABLE = "CREATE TABLE IF NOT EXISTS " + DoctorEntry.TABLE_NAME + " ("
                + DoctorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DoctorEntry.COLUMN_FIO + " TEXT NOT NULL, "
                + DoctorEntry.COLUMN_SPECIALTY + " TEXT NOT NULL, "
                + DoctorEntry.COLUMN_FK_OPTICAL_SHOP + " INTEGER, "+
                "FOREIGN KEY ("+ DoctorEntry.COLUMN_FK_OPTICAL_SHOP +") "+
                "REFERENCES " + OpticalShopEntry.TABLE_NAME + "(" + OpticalShopEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_DOCTOR_TABLE);

        String SQL_CREATE_VALUE_TABLE = "CREATE TABLE IF NOT EXISTS " + ValueEntry.TABLE_NAME + " ("
                + ValueEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ValueEntry.COLUMN_STRING + " TEXT, "
                + ValueEntry.COLUMN_NUMERIC + " REAL, "
                + ValueEntry.COLUMN_FK_ATTRIBUTE_SHOP + " INTEGER, "+
                "FOREIGN KEY ("+ ValueEntry.COLUMN_FK_ATTRIBUTE_SHOP +") "+
                "REFERENCES " + AttributeEntry.TABLE_NAME + "(" + AttributeEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_VALUE_TABLE);

        String SQL_CREATE_RECEPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + ReceptionEntry.TABLE_NAME + " ("
                + ReceptionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReceptionEntry.COLUMN_DATETIME + " INTEGER NOT NULL, "
                + ReceptionEntry.COLUMN_FK_DOCTOR + " INTEGER, "
                + ReceptionEntry.COLUMN_FK_USER + " INTEGER, "
                + "FOREIGN KEY ("+ ReceptionEntry.COLUMN_FK_DOCTOR +") "
                + "REFERENCES " + DoctorEntry.TABLE_NAME + "(" + DoctorEntry._ID + ")"
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY ("+ ReceptionEntry.COLUMN_FK_USER +") "
                + "REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_RECEPTION_TABLE);

        String SQL_CREATE_PRESCRIPTION_TABLE = "CREATE TABLE IF NOT EXISTS " + PrescriptionEntry.TABLE_NAME + " ("
                + PrescriptionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PrescriptionEntry.COLUMN_NUMBER + " INTEGER NOT NULL, "
                + PrescriptionEntry.COLUMN_DETAIL + " TEXT NOT NULL, "
                + PrescriptionEntry.COLUMN_DURATION + " TEXT NOT NULL, "
                + PrescriptionEntry.COLUMN_FK_RECEPTION + " INTEGER, "+
                "FOREIGN KEY ("+ PrescriptionEntry.COLUMN_FK_RECEPTION +") "+
                "REFERENCES " + ReceptionEntry.TABLE_NAME + "(" + ReceptionEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_PRESCRIPTION_TABLE);

        String SQL_CREATE_PROVIDE_TABLE = "CREATE TABLE IF NOT EXISTS " + ProvideEntry.TABLE_NAME + " ("
                + ProvideEntry.COLUMN_DOCTOR_ID + " INTEGER, "
                + ProvideEntry.COLUMN_SERVICE_ID + " INTEGER, "
                + "FOREIGN KEY ("+ ProvideEntry.COLUMN_DOCTOR_ID +") "
                + "REFERENCES " + DoctorEntry.TABLE_NAME + "(" + DoctorEntry._ID + ")"
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY ("+ ProvideEntry.COLUMN_SERVICE_ID +") "
                + "REFERENCES " + ServiceEntry.TABLE_NAME + "(" + ServiceEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_PROVIDE_TABLE);

        String SQL_CREATE_THERE_IS_TABLE = "CREATE TABLE IF NOT EXISTS " + ThereIsEntry.TABLE_NAME + " ("
                + ThereIsEntry.COLUMN_OPTICAL_SHOP_ID + " INTEGER, "
                + ThereIsEntry.COLUMN_GOODS_ID + " INTEGER, "
                + ThereIsEntry.COLUMN_COUNT + " INTEGER NOT NULL, "
                + "FOREIGN KEY ("+ ThereIsEntry.COLUMN_OPTICAL_SHOP_ID +") "
                + "REFERENCES " + OpticalShopEntry.TABLE_NAME + "(" + OpticalShopEntry._ID + ")"
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY ("+ ThereIsEntry.COLUMN_GOODS_ID +") "
                + "REFERENCES " + GoodsEntry.TABLE_NAME + "(" + GoodsEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_THERE_IS_TABLE);

        String SQL_CREATE_GOODS_HAS_VALUE_TABLE = "CREATE TABLE IF NOT EXISTS " + GoodsHasValueEntry.TABLE_NAME + " ("
                + GoodsHasValueEntry.COLUMN_FK_GOODS + " INTEGER, "
                + GoodsHasValueEntry.COLUMN_FK_VALUE + " INTEGER, "
                + "FOREIGN KEY ("+ GoodsHasValueEntry.COLUMN_FK_GOODS +") "
                + "REFERENCES " + GoodsEntry.TABLE_NAME + "(" + GoodsEntry._ID + ")"
                + "ON UPDATE CASCADE ON DELETE CASCADE, "
                + "FOREIGN KEY ("+ GoodsHasValueEntry.COLUMN_FK_VALUE +") "
                + "REFERENCES " + ValueEntry.TABLE_NAME + "(" + ValueEntry._ID + ") "
                + "ON UPDATE CASCADE ON DELETE CASCADE);";

        db.execSQL(SQL_CREATE_GOODS_HAS_VALUE_TABLE);

        insertValue(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+OpticalShopEntry.TABLE_NAME);
        onCreate(db);
    }

    private void insertValue(SQLiteDatabase db) {

        db.execSQL("INSERT INTO " + OpticalShopEntry.TABLE_NAME
                + " (" + OpticalShopEntry.COLUMN_ADDRESS + "," + OpticalShopEntry.COLUMN_TIMETABLE + " )"
                + " VALUES ('улица Академика Богомольца, 16, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'),"
                + "('улица Ополченская, 48, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('улица Маршала Еременко, 35, Волгоград','пн-пт 9:00-19:00 сб-вс 10:00-18:00'), "
                + "('проспект Металлургов, 29, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('улица Репина, 13, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('улица Хиросимы, 7, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('Кузнецкая улица, 83, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('Калининградская улица, 3, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('улица Елисеева, 17, Волгоград','пн-пт 9:00-20:00 сб-вс 10:00-19:00'), "
                + "('улица Комсомольская, 14, Волгоград','пн-пт 9:00-20:30 сб-вс 10:00-19:30') ;");

        db.execSQL("INSERT INTO " + ServiceEntry.TABLE_NAME
                + " (" + ServiceEntry.COLUMN_TITLE + " )"
                + " VALUES ('Скрининг'),"
                + "('Селективная лазерная трабекулопластика'), "
                + "('Оптическая когерентная томография'), "
                + "('Лазерная коагуляция сетчатки'), "
                + "('Консультация врача-офтальмолога с линзой Гольдмана'), "
                + "('Консультация по подбору очков'), "
                + "('Подбор контактных линз'),"
                + "('Детский осмотр'),"
                + "('Лазерное лечение глаукомы') ;");

        db.execSQL("INSERT INTO " + AttributeEntry.TABLE_NAME
                + "(" + AttributeEntry.COLUMN_TITLE + " )"
                + "VALUES('Цвет'), "
                + "('Диоприя'), "
                + "('Фокусное растояние '), "
                + "('Тип переносицы'), "
                + "('Форма'), "
                + "('Материал '), "
                + "('Страна производитель'), "
                + "('Индекс линз'), "
                + "('Радиус кривизны'), "
                + "('Диаметр '), "
                + "('Дизайн'), "
                + "('Толщина в центре'), "
                + "('Кислородопроницаемость'), "
                + "('Влагосодержание'), "
                + "('Режим ношения'), "
                + "('Режим замены'), "
                + "('Тип ');");

        db.execSQL("INSERT INTO " + DoctorEntry.TABLE_NAME +
                "(" + DoctorEntry.COLUMN_FIO + "," + DoctorEntry.COLUMN_SPECIALTY + "," + DoctorEntry.COLUMN_FK_OPTICAL_SHOP  + ") " +
                "VALUES('Цызырева Эмилия Данииловна','медицинская сестра',1), " +
                "('Терехова Вероника Александровна','врач-офтальмолог',1), " +
                "('Эскина Варвара Брониславовна','медицинская сестра',2), " +
                "('Дубинина Светлана Игоревна','врач-офтальмолог',2), " +
                "('Халипова Кристина Тимофеевна','медицинская сестра',2), " +
                "('Винтухов Валерий Евграфович','врач-офтальмолог',2), " +
                "('Кунаев Игорь Эрнестович','медицинский брат',3), " +
                "('Морозова Екатерина Евгениевна','врач-офтальмолог',3), " +
                "('Косомова Евдокия Георгиевна','медицинская сестра',4), " +
                "('Канкия Адриан Фомевич','врач-офтальмолог',4);");

        db.execSQL("INSERT INTO " + ProvideEntry.TABLE_NAME +
                "( " + ProvideEntry.COLUMN_DOCTOR_ID + "," + ProvideEntry.COLUMN_SERVICE_ID + ") " +
                "VALUES(1,2), " +
                "(3,2), " +
                "(2,4), " +
                "(4,6), " +
                "(5,6), " +
                "(4,4), " +
                "(6,8), " +
                "(8,9), " +
                "(7,9), " +
                "(7,8);");

        db.execSQL("INSERT INTO " + ReceptionEntry.TABLE_NAME +
                "( " + ReceptionEntry.COLUMN_DATETIME + "," + ReceptionEntry.COLUMN_FK_USER + "," + ReceptionEntry.COLUMN_FK_DOCTOR + " )"+
                "VALUES(20201120183000,1,2), "+
                "(20201203103000,1,1), "+
                "(20210223,1,5);");

        db.execSQL("INSERT INTO " + PrescriptionEntry.TABLE_NAME +
                "( " + PrescriptionEntry.COLUMN_NUMBER + "," + PrescriptionEntry.COLUMN_DETAIL + "," + PrescriptionEntry.COLUMN_DURATION + "," + PrescriptionEntry.COLUMN_FK_RECEPTION + " ) " +
                "VALUES (20,'астигматические контактрые линзы -5,5 левый -5 правый', '3 мес', 2);");


        db.execSQL("INSERT INTO " + GoodsEntry.TABLE_NAME +
                " ( " + GoodsEntry.COLUMN_PHOTO_PATH + "," + GoodsEntry.COLUMN_TITLE + "," + GoodsEntry.COLUMN_DESCRIPTION +") " +
                "VALUES('frame1.png','оправа','тонкая металлическая оправа'), " +
                "('sunglasses1.png','солнцезащитные очки','очки в черной оправе с линзами, поглащающими уф лучи'), " +
                "('contact_lenses1.png','контактные лизы','гидрогелевые контактные линзы для ношения в течении 14 дней'), " +
                "('eyeglass_lens1.png','очковая линза','органическа утонченная линза с антибликовым покрытием'), " +
                "('contact_lens_solution1.png','раствор для контактных линз','универсальный раствор для линз'), " +
                "('eye_drops1.png','глазные капли','глазные капли с эффектом натуральной слезы'), " +
                "('contact_lens_care_kit1.png','набор для ухода за контактными линзами','коробочка для линз, пинцет, кейс'), " +
                "('eyeglass_care_kit1.png','набор для ухода за очками','салфетка для очков, раствор защитный от царапин'), " +
                "('eyeglass_case1.png','футляр для очков','футляр для очков с магнитом'), " +
                "('vitamins1.png','витамины ','витамины для зрения с черникой');");

        db.execSQL("INSERT INTO " + ValueEntry.TABLE_NAME +
                "( " + ValueEntry.COLUMN_NUMERIC + "," + ValueEntry.COLUMN_STRING + "," + ValueEntry.COLUMN_FK_ATTRIBUTE_SHOP + ") " +
                "VALUES(NULL,'Черный',1), " +
                "(2.5,NULL,2), " +
                "(0.4,NULL,3), " +
                "(NULL,'Россия',7), " +
                "(NULL,'Китай',7), " +
                "(NULL,'Тонкий',11), " +
                "(95,NULL,13), " +
                "(1.2,NULL,12), " +
                "(NULL,'Ежедневные',15), " +
                "(NULL,'Ежемесячно',16);");

        db.execSQL("INSERT INTO " + GoodsHasValueEntry.TABLE_NAME +
                "( " + GoodsHasValueEntry.COLUMN_FK_GOODS + "," + GoodsHasValueEntry.COLUMN_FK_VALUE +") " +
                "VALUES(1,1), " +
                "(1,4), " +
                "(1,3), " +
                "(2,1), " +
                "(2,5), " +
                "(2,11), " +
                "(3,2), " +
                "(3,3), " +
                "(3,7), " +
                "(3,8), " +
                "(3,9), " +
                "(3,10), " +
                "(4,2), " +
                "(4,3), " +
                "(4,4), " +
                "(4,7), " +
                "(4,8);");

        db.execSQL("INSERT INTO " + ThereIsEntry.TABLE_NAME +
                "( " + ThereIsEntry.COLUMN_OPTICAL_SHOP_ID + "," + ThereIsEntry.COLUMN_GOODS_ID + "," + ThereIsEntry.COLUMN_COUNT + ") "+
                "VALUES (1,1,2), " +
                "(1,2,5), " +
                "(1,3,4), " +
                "(1,4,7), " +
                "(1,5,1), " +
                "(1,6,9), " +
                "(1,7,2), " +
                "(1,8,5), " +
                "(1,9,4), " +
                "(1,10,7), " +
                "(2,1,2), " +
                "(2,2,1), " +
                "(2,3,7), " +
                "(2,4,2), " +
                "(2,5,8), " +
                "(2,6,5), " +
                "(2,7,1), " +
                "(2,8,3), " +
                "(2,9,8), " +
                "(2,10,1), " +
                "(3,1,8), " +
                "(3,3,5), " +
                "(3,4,1), " +
                "(3,6,3), " +
                "(3,8,8), " +
                "(3,9,1), " +
                "(4,2,8), " +
                "(4,3,5), " +
                "(4,5,1), " +
                "(4,7,3), " +
                "(4,9,8), " +
                "(4,10,1), " +
                "(5,1,1), " +
                "(5,2,8), " +
                "(5,3,5), " +
                "(5,5,1), " +
                "(5,6,3), " +
                "(5,7,8), " +
                "(5,8,1), " +
                "(6,2,1), " +
                "(6,4,8), " +
                "(6,6,5), " +
                "(6,7,1), " +
                "(6,8,3), " +
                "(6,3,8), " +
                "(6,10,1), " +
                "(7,1,1), " +
                "(7,3,8), " +
                "(7,4,5), " +
                "(7,6,1), " +
                "(7,7,3), " +
                "(7,8,8), " +
                "(7,9,1), " +
                "(8,1,8), " +
                "(8,2,5), " +
                "(8,4,1), " +
                "(8,5,3), " +
                "(8,7,8), " +
                "(8,8,1), " +
                "(9,1,5), " +
                "(9,3,1), " +
                "(9,4,3), " +
                "(9,6,8), " +
                "(9,10,1), " +
                "(10,1,3), " +
                "(10,5,8), " +
                "(10,9,1);");

    }
}
