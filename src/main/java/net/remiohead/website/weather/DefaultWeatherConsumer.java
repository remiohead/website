package net.remiohead.website.weather;

import com.google.gson.Gson;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;
import static net.remiohead.websitedatabase.models.Tables.WEATHER;

@Service
public class DefaultWeatherConsumer implements WeatherConsumer {

    private final DSLContext context;
    private final Gson gson;
    private ImmutableWeatherData message = null;

    private static final Logger logger = LoggerFactory.getLogger(DefaultWeatherConsumer.class);

    @Autowired
    public DefaultWeatherConsumer(
            final DSLContext context,
            final Gson gson) {
        this.context = requireNonNull(context);
        this.gson = requireNonNull(gson);
    }

    @Override
    public void consume(
            final WeatherData data) {
        logger.info("Weather received: {}", data);
        this.message = ImmutableWeatherData.copyOf(data);
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
