package net.remiohead.website.weather;

import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class WeatherTask implements Supplier<ImmutableWeatherData> {

    private final String location;

    public WeatherTask(final String location) {
        this.location = requireNonNull(location);
    }


    @Override
    public ImmutableWeatherData get() {
        try {
            final var response = Request
                    .Post(this.generateUrl())
                    .execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
            final var root = JsonParser
                    .parseString(response)
                    .getAsJsonObject();
            final var main = root.getAsJsonObject("main");
            final var temp = Math.round(
                    main.get("temp").getAsFloat());
            final var humidity = main.get("humidity").getAsInt();

            return ImmutableWeatherData.builder()
                    .temp(temp)
                    .humidity(humidity)
                    .location(this.location)
                    .response(response)
                    .build();

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

    public static ImmutableWeatherData home() {
        return new WeatherTask("98106,us").get();
    }
}
