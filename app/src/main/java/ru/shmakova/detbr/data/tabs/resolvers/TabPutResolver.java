package ru.shmakova.detbr.data.tabs.resolvers;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import java.io.ByteArrayOutputStream;

import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.data.tabs.tables.TabsTable;

public class TabPutResolver extends DefaultPutResolver<Tab> {
    @Override
    @NonNull
    protected InsertQuery mapToInsertQuery(@NonNull Tab tab) {
        return InsertQuery.builder()
                .table(TabsTable.TABLE)
                .build();
    }

    @Override
    @NonNull
    protected UpdateQuery mapToUpdateQuery(@NonNull Tab tab) {
        return UpdateQuery.builder()
                .table(TabsTable.TABLE)
                .where(TabsTable.COLUMN_URL + " LIKE ?")
                .whereArgs(tab.getUrl())
                .build();
    }

    @Override
    @NonNull
    public ContentValues mapToContentValues(@NonNull Tab tab) {
        ContentValues contentValues = new ContentValues(3);

        Bitmap bitmap = tab.getPreview();
        contentValues.put(TabsTable.COLUMN_PREVIEW, bitmap == null? null : getByteArrayFromBitmap(bitmap));
        contentValues.put(TabsTable.COLUMN_TITLE, tab.getTitle());
        contentValues.put(TabsTable.COLUMN_URL, tab.getUrl());

        return contentValues;
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] array = null;
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
            array = stream.toByteArray();
        }
        return array;
    }
 }
