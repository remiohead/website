package net.remiohead.website.weather;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.Supplier;

public class WeatherResponse implements Supplier<String> {

    public WeatherResponse() {

    }

    @Override
    public String get() {
        final Map<String, Object> result =
                new WeatherTask("98106,us").get();

        final LocalDateTime date = LocalDateTime
                .now(ZoneId.of("America/Los_Angeles"));
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss");

        final StringBuilder out = new StringBuilder();
        out.append("At ").append(formatter.format(date)).append(" ");
        out.append("the temperature is ");
        out.append(result.get("temp"));
        out.append(" degrees with ");
        out.append(result.get("humidity"));
        out.append(" percent humidity");

        return out.toString();
    }

    public static String weather() {
        return new WeatherResponse().get();
    }
}
