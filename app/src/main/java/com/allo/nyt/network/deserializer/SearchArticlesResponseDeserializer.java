package com.allo.nyt.network.deserializer;

import com.allo.nyt.model.ByLine;
import com.allo.nyt.network.model.response.SearchArticlesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by ALLO on 29/7/16.
 */
public class SearchArticlesResponseDeserializer implements JsonDeserializer<SearchArticlesResponse> {

    @Override
    public SearchArticlesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement content = json.getAsJsonObject().get("response");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .registerTypeAdapter(ByLine.class, new ByLineDeserializer())
                .create();

        return gson.fromJson(content, SearchArticlesResponse.class);
    }

}
