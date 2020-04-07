package net.remiohead.website.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
public class WeatherController {

    private final DefaultWeatherConsumer consumer;
    private final WeatherProducer producer;

    @Autowired
    public WeatherController(
            final DefaultWeatherConsumer consumer,
            final WeatherProducer producer) {
        this.consumer = requireNonNull(consumer);
        this.producer = requireNonNull(producer);
    }

    @GetMapping("/weather")
    public String weather() {
        return this.consumer.getMessage();
    }

    @GetMapping("/trigger")
    public void trigger() {
        this.producer.post();
    }

}
