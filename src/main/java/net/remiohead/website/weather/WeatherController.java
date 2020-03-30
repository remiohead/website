package net.remiohead.website.weather;

import net.remiohead.website.weather.WeatherConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WeatherController {

    private final WeatherConsumer consumer;

    @Autowired
    public WeatherController(
            final WeatherConsumer consumer) {
        this.consumer = consumer;
    }

    @GetMapping("/weather")
    public String get() {
        return this.consumer.getMessage();
    }
}
