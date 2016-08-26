package ru.yandex.detbr.wot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shmakova on 25.08.16.
 */

public interface WotService {
    String BASE_URL = "http://api.mywot.com/0.4/";

    String API_KEY = "key";

    String API_KEY_VALUE = "268548cfb0c277d2b49401be4a87e36d081051eb";

    @GET("public_link_json2")
    Call<WotResponse> getLinkReputation(@Query("hosts") String sort);
}
