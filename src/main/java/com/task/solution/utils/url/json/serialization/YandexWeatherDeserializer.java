package com.task.solution.utils.url.json.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.task.solution.payload.as_client.yandex.response.GeoObject;
import com.task.solution.payload.as_client.yandex.response.YandexWeatherResponse;

import java.io.IOException;

public class YandexWeatherDeserializer extends JsonDeserializer<YandexWeatherResponse> {

    @Override
    public YandexWeatherResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode nodeTree = jsonParser.getCodec().readTree(jsonParser);
        JsonNode geoObjectNode =  nodeTree.get("geo_object");
        JsonNode factNode = nodeTree.get("fact");
        String country =  geoObjectNode.get("country").get("name").textValue();
        String province =  geoObjectNode.get("province").get("name").textValue();
        String locality = geoObjectNode.get("locality").get("name").textValue();
        GeoObject geoObject = new GeoObject(country, province, locality);
        Short temp = factNode.get("temp").shortValue();
        Long obsTime = factNode.get("uptime").longValue();
        return new YandexWeatherResponse(geoObject,temp,obsTime);
    }
}
