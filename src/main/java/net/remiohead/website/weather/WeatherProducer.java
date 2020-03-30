package net.remiohead.website.weather;

import com.google.gson.Gson;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherProducer {

    static final String TOPIC = "weather";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    @Autowired
    public WeatherProducer(
            final KafkaTemplate kafkaTemplate,
            final Gson gson) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic(TOPIC);
        this.gson = gson;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void post() {
        this.kafkaTemplate.sendDefault(this.gson.toJson(WeatherTask.home()));
    }

    @Bean
    public NewTopic weather() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }
}
