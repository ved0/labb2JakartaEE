package com.example.demo1.entities.categories;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class CategoryDeserializer extends JsonDeserializer<Category> {

    @Override
    public Category deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String categoryValue = jsonParser.getText();
        if (Category.isValidCategory(categoryValue)) {
            return Category.valueOf(categoryValue.toUpperCase());
        } else {
            return Category.NON_EXISTENT;
        }
    }
}

