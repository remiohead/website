package net.remiohead.website.weather;

import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;
import static net.remiohead.websitedatabase.models.Tables.WEATHER;

@Component
public class WeatherConsumer {

    private final DSLContext context;
    private final Gson gson;
    private ImmutableWeatherData message = null;

    private static final Logger logger = LoggerFactory.getLogger(WeatherConsumer.class);

    @Autowired
    public WeatherConsumer(
            final DSLContext context,
            final Gson gson) {
        this.context = requireNonNull(context);
        this.gson = requireNonNull(gson);
    }

    @KafkaListener(groupId = "remiohead", topics = WeatherProducer.TOPIC)
    public void processMessage(
            final String content) {
        logger.info("Weather received: {}", content);
        this.message = this.gson.fromJson(content, ImmutableWeatherData.class);
        this.context
                .insertInto(WEATHER)
                .set(this.message.createWeatherRecord())
                .execute();
    }

    public String getMessage() {
        if(this.message == null) {
            return "";
        }
        return this.message.createResponse();
    }
}
