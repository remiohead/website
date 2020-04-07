package net.remiohead.website.weather;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class WeatherProducer {

    private final WeatherConsumer consumer;

    @Autowired
    public WeatherProducer(
            final WeatherConsumer consumer,
            final Gson gson) {
        this.consumer = requireNonNull(consumer);
    }

    @Scheduled(cron = "0 0 * * * *")
    public void post() {
        this.consumer.consume(WeatherTask.home());
    }
}
