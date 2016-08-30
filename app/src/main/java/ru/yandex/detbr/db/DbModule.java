package ru.yandex.detbr.db;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.detbr.cards.Card;
import ru.yandex.detbr.db.resolvers.CardDeleteResolver;
import ru.yandex.detbr.db.resolvers.CardGetResolver;
import ru.yandex.detbr.db.resolvers.CardPutResolver;


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

    @Provides
    @NonNull
    @Singleton
    public Repository provideRepository(@NonNull RepositoryImpl repositoryImpl) {
        return repositoryImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public RepositoryImpl provideRepositoryImpl(@NonNull StorIOSQLite storIOSQLite) {
        return new RepositoryImpl(storIOSQLite);
    }
}
