package ru.shmakova.detbr.di.modules;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.shmakova.detbr.data.cards.Card;
import ru.shmakova.detbr.data.cards.resolvers.CardDeleteResolver;
import ru.shmakova.detbr.data.cards.resolvers.CardGetResolver;
import ru.shmakova.detbr.data.cards.resolvers.CardPutResolver;
import ru.shmakova.detbr.data.db.DbOpenHelper;
import ru.shmakova.detbr.data.tabs.Tab;
import ru.shmakova.detbr.data.tabs.resolvers.TabDeleteResolver;
import ru.shmakova.detbr.data.tabs.resolvers.TabGetResolver;
import ru.shmakova.detbr.data.tabs.resolvers.TabPutResolver;


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
