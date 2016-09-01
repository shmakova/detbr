package ru.yandex.detbr.data.wot_network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shmakova on 25.08.16.
 */

public class WotApiKeyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder().url(
                request.url()
                        .newBuilder().addQueryParameter(WotService.API_KEY, WotService.API_KEY_VALUE)
                        .build()
        ).build();
        return chain.proceed(newRequest);
    }
}