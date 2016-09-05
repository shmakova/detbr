package ru.yandex.detbr.di.modules;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.data.repository.db.DbOpenHelper;
import ru.yandex.detbr.data.repository.db.resolvers.CardDeleteResolver;
import ru.yandex.detbr.data.repository.db.resolvers.CardGetResolver;
import ru.yandex.detbr.data.repository.db.resolvers.CardPutResolver;
import ru.yandex.detbr.data.repository.db.resolvers.TabDeleteResolver;
import ru.yandex.detbr.data.repository.db.resolvers.TabGetResolver;
import ru.yandex.detbr.data.repository.db.resolvers.TabPutResolver;
import ru.yandex.detbr.data.repository.models.Card;
import ru.yandex.detbr.data.tabs.models.Tab;


@Module
public class DbModule {
    @Provides
    @NonNull
    @Singleton
    public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(Card.class, SQLiteTypeMapping.<Card>builder()
                        .putResolver(new CardPutResolver())
                        .getResolver(new CardGetResolver())
                        .deleteResolver(new CardDeleteResolver())
                        .build())
                .addTypeMapping(Tab.class, SQLiteTypeMapping.<Tab>builder()
                        .putResolver(new TabPutResolver())
                        .getResolver(new TabGetResolver())
                        .deleteResolver(new TabDeleteResolver())
                        .build())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull Application application) {
        return new DbOpenHelper(application);
    }
}
