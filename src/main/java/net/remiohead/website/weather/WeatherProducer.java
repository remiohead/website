package net.remiohead.website.weather;

import net.remiohead.website.weather.WeatherResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public WeatherProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setDefaultTopic("weather");
    }

    @Scheduled(fixedDelay=60*60*1000)
    public void post() {
        this.kafkaTemplate.sendDefault(WeatherResponse.weather());
    }

    @Bean
    public NewTopic weather() {
        return new NewTopic("weather", 1, (short) 1);
    }
}
