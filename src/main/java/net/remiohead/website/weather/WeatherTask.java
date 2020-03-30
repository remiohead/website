package net.remiohead.website.weather;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class WeatherTask implements Supplier<Map<String, Object>> {

    private final String location;

    public WeatherTask(final String location) {
        this.location = requireNonNull(location);
    }


    @Override
    public Map<String, Object> get() {
        try {
            final String response = Request
                    .Post(this.generateUrl())
                    .execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
            final JsonObject root =
                    new JsonParser()
                            .parse(response)
                            .getAsJsonObject();
            final JsonObject main =
                    root.getAsJsonObject("main");
            final int temp = Math.round(
                    main.get("temp").getAsFloat());
            final int humidity = main.get("humidity").getAsInt();

            final JsonObject weather =
                    root.getAsJsonArray("weather")
                            .get(0).getAsJsonObject();
            final String desc = weather.get("main").getAsString();

            return ImmutableMap.of(
                    "temp", temp,
                    "humidity", humidity,
                    "desc", desc
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl() {
        final StringBuilder url = new StringBuilder();
        url.append("https://api.openweathermap.org/data/2.5/weather");
        url.append("?zip="+this.location);
        url.append("&APPID="+ System.getenv("OPEN_WEATHER_API_KEY"));
        url.append("&units=imperial");
        return url.toString();
    }
}
