package net.remiohead.website.weather;

import java.util.Map;
import java.util.function.Supplier;

public class WeatherResponse implements Supplier<String> {

    public WeatherResponse() {

    }

    @Override
    public String get() {
        final Map<String, Object> result =
                new WeatherTask("98106,us").get();

        final StringBuilder out = new StringBuilder();
        out.append("The temperature is ");
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
