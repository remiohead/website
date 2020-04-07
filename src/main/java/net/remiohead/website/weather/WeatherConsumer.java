package net.remiohead.website.weather;

@FunctionalInterface
public interface WeatherConsumer {

    void consume(WeatherData data);
}
