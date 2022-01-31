package com.task.solution.payload.as_client.yandex.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.task.solution.utils.url.json.serialization.YandexWeatherDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Getter
@JsonDeserialize(using = YandexWeatherDeserializer.class)
public class YandexWeatherResponse {
    GeoObject geoObject;
    Short temp;
    Long uptime;
}


