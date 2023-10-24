package com.example.demo1;

import com.example.demo1.entities.categories.CategoryDeserializer;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.serializer.JsonbDeserializer;

public class CustomJsonbProvider {
    public Jsonb createJsonb() {
        JsonbConfig config = new JsonbConfig()
                .withDeserializers((JsonbDeserializer) new CategoryDeserializer());

        return JsonbBuilder.create(config);
    }
}