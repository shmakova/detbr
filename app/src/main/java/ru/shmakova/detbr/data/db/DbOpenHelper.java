package ru.shmakova.detbr.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.shmakova.detbr.data.cards.tables.CardsTable;
import ru.shmakova.detbr.data.tabs.tables.TabsTable;


public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "detbr_db";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CardsTable.getCreateTableQuery());
        sqLiteDatabase.execSQL(TabsTable.getCreateTableQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // TODO: 01.09.16 удалить после отладки
        sqLiteDatabase.execSQL("DROP TABLE " + CardsTable.TABLE);
        sqLiteDatabase.execSQL("DROP TABLE " + TabsTable.TABLE);
        onCreate(sqLiteDatabase);
    }
}
