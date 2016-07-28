package com.allo.nyt.network.deserializer;

import com.allo.nyt.model.ByLine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by ALLO on 27/7/16.
 */
public class ByLineDeserializer implements JsonDeserializer<ByLine> {

    @Override
    public ByLine deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonArray) {
            return null;
        } else {
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(json, ByLine.class);
        }
    }

}
