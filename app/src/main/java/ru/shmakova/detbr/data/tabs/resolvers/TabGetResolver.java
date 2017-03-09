package ru.shmakova.detbr.data.tabs.resolvers;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.data.tabs.tables.TabsTable;
import timber.log.Timber;

public class TabGetResolver extends DefaultGetResolver<Tab> {
    @Override
    @NonNull
    public Tab mapFromCursor(@NonNull Cursor cursor) {
        byte[] preview = cursor.getBlob(cursor.getColumnIndex(TabsTable.COLUMN_PREVIEW));
        String title = cursor.getString(cursor.getColumnIndex(TabsTable.COLUMN_TITLE));
        String url = cursor.getString(cursor.getColumnIndex(TabsTable.COLUMN_URL));
        return Tab.builder()
                .preview(getBitmapFromByteArray(preview))
                .title(title)
                .url(url)
                .build();
    }

    private Bitmap getBitmapFromByteArray(byte[] array) {
        if (array == null) {
            return null;
        }
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
