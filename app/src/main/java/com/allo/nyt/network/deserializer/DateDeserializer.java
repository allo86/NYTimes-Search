package com.allo.nyt.network.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date Deserializer
 * <p/>
 * Created by allerena on 2/29/16.
 */
public class DateDeserializer implements JsonDeserializer<Date> {

    private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

    public DateDeserializer() {
        df.setTimeZone(TimeZone.getTimeZone("Zulu"));
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return df.parse(json.getAsString());
        } catch (ParseException e) {
            // TODO: Need to do something here?
            return null;
        }
    }

}
