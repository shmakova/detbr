package ru.yandex.detbr.wot;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by shmakova on 26.08.16.
 */

public class WotDeserializer implements JsonDeserializer<WotResponse> {

    @Override
    public WotResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement content = json.getAsJsonObject().entrySet().iterator().next().getValue();

        return new Gson().fromJson(content, WotResponse.class);
    }
}