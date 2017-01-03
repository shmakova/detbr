package ru.shmakova.detbr.di.modules;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.shmakova.detbr.data.wot_network.CacheInterceptor;
import ru.shmakova.detbr.data.wot_network.WotApiKeyInterceptor;
import ru.shmakova.detbr.data.wot_network.WotDeserializer;
import ru.shmakova.detbr.data.wot_network.WotService;
import ru.shmakova.detbr.data.wot_network.models.WotResponse;

/**
 * Created by shmakova on 25.08.16.
 */

@Module
public class WotNetworkModule {
    @Provides
    @Singleton
    WotApiKeyInterceptor provideWotApiKeyInterceptor() {
        return new WotApiKeyInterceptor();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(
            Cache cache,
            HttpLoggingInterceptor loggingInterceptor,
            CacheInterceptor cacheInterceptor,
            WotApiKeyInterceptor wotApiKeyInterceptor
    ) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .addNetworkInterceptor(wotApiKeyInterceptor)
                .cache(cache);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(WotResponse.class, new WotDeserializer())
                .create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(WotService.BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    WotService providesWotService(Retrofit retrofit) {
        return retrofit.create(WotService.class);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    @Provides
    @Singleton
    CacheInterceptor provideCacheInterceptor() {
        return new CacheInterceptor();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        File httpCacheDirectory = new File(application.getCacheDir(), "http-cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }
}
