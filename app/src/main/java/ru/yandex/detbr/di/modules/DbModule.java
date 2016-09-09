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
import ru.yandex.detbr.data.cards.Card;
import ru.yandex.detbr.data.cards.resolvers.CardDeleteResolver;
import ru.yandex.detbr.data.cards.resolvers.CardGetResolver;
import ru.yandex.detbr.data.cards.resolvers.CardPutResolver;
import ru.yandex.detbr.data.db.DbOpenHelper;


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
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(@NonNull Application application) {
        return new DbOpenHelper(application);
    }
}
