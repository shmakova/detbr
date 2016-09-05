package ru.yandex.detbr.di.modules;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by shmakova on 03.09.16.
 */


@Module
public class FirebaseModule {
    @Provides
    @Singleton
    @NonNull
    public DatabaseReference provideDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }
}