package ru.yandex.detbr.data.repository.db.resolvers;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;

import java.io.ByteArrayOutputStream;

import ru.yandex.detbr.data.repository.db.tables.TabsTable;
import ru.yandex.detbr.data.tabs.models.Tab;

/**
 * Created by user on 04.09.16.
 */

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

        contentValues.put(TabsTable.COLUMN_PREVIEW, getByteArrayFromBitmap(tab.getPreview()));
        contentValues.put(TabsTable.COLUMN_TITLE, tab.getTitle());
        contentValues.put(TabsTable.COLUMN_URL, tab.getUrl());

        return contentValues;
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] array = null;
        if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
            array = stream.toByteArray();
        }
        return array;
    }
 }
