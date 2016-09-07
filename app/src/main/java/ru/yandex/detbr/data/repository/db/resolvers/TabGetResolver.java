package ru.yandex.detbr.data.repository.db.resolvers;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import ru.yandex.detbr.data.repository.db.tables.TabsTable;
import ru.yandex.detbr.data.tabs.models.Tab;
import timber.log.Timber;

/**
 * Created by user on 04.09.16.
 */

public class TabGetResolver extends DefaultGetResolver<Tab> {
    @Override
    @NonNull
    public Tab mapFromCursor(@NonNull Cursor cursor) {
        byte[] preview = cursor.getBlob(cursor.getColumnIndex(TabsTable.COLUMN_PREVIEW));
        String title = cursor.getString(cursor.getColumnIndex(TabsTable.COLUMN_TITLE));
        String url = cursor.getString(cursor.getColumnIndex(TabsTable.COLUMN_URL));
        return Tab.builder()
                .preview(preview == null? null : getBitmapFromByteArray(preview))
                .title(title)
                .url(url)
                .build();
    }

    private Bitmap getBitmapFromByteArray(byte[] array) {
        ByteArrayInputStream stream = new ByteArrayInputStream(array);
        Bitmap bitmapPreview = BitmapFactory.decodeStream(stream);
        try {
            stream.close();
        } catch (IOException e) {
            Timber.e(e, "Can't close ByteArrayInputStream");
        }
        return bitmapPreview;
    }
}


