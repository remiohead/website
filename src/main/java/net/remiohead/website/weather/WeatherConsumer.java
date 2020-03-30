package net.remiohead.website.weather;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class WeatherConsumer {

    private String message = "";

    @KafkaListener(groupId = "remiohead", topics = "weather")
    public void processMessage(String content) {
        this.message = content;
    }

    public String getMessage() {
        return message;
    }
}
