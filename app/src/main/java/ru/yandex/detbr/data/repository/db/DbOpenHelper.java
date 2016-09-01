package ru.yandex.detbr.data.repository.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.yandex.detbr.data.repository.db.tables.CardsTable;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, "detbr_db", null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CardsTable.getCreateTableQuery());
        sqLiteDatabase.execSQL(CardsTable.fillDatabaseWithDefaultCards());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // no impl
    }
}
