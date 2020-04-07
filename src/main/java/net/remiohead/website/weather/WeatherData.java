package net.remiohead.website.weather;

import net.remiohead.websitedatabase.models.tables.records.WeatherRecord;
import org.immutables.gson.Gson;
import org.immutables.value.Value;
import org.jooq.JSON;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Gson.TypeAdapters
@Value.Immutable
public interface WeatherData {
    int getTemp();
    int getHumidity();
    String getLocation();
    String getResponse();

    @Value.Default
    default LocalDateTime getDateTime() {
        return LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
    }

    default WeatherRecord createWeatherRecord() {
        final var result = new WeatherRecord();
        result.setId(UUID.randomUUID());
        result.setTemp(this.getTemp());
        result.setHumidity(this.getHumidity());
        result.setLocation(this.getLocation());
        result.setResponse(JSON.valueOf(this.getResponse()));
        return result;
    }

    default String createResponse() {
        final var formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss");

        return new StringBuilder()
                .append("At ")
                .append(formatter.format(this.getDateTime()))
                .append(" ")
                .append("the temperature is ")
                .append(this.getTemp())
                .append(" degrees with ")
                .append(this.getHumidity())
                .append(" percent humidity")
                .toString();
    }
}
